package com.epam.jwd.web.pool;

import com.epam.jwd.web.exception.CouldNotDestroyConnectionPoolException;
import com.epam.jwd.web.exception.CouldNotInitializeConnectionPoolException;
import com.epam.jwd.web.util.PropertyReader;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentConnectionPool implements ConnectionPool {

    private static final ConcurrentConnectionPool instance = new ConcurrentConnectionPool();

    private static final int START_POOL_SIZE = 8;
    private static final int MAX_CONNECTION_AMOUNT = 32;
    private static final int GROW_FACTOR = 4;

    private final String url;
    private final String user;
    private final String password;
    private final String driverName;

    BlockingQueue<Connection> connectionQueue;
    BlockingQueue<Connection> busyConQueue;

    private static AtomicBoolean initialized;
    private final AtomicInteger connectionsOpened;

    static public ConcurrentConnectionPool getInstance() {
        return instance;
    }

    private ConcurrentConnectionPool() {
        initialized = new AtomicBoolean(false);
        connectionsOpened = new AtomicInteger(0);
        PropertyReader propertyReader = PropertyReader.getInstance();
        this.url = propertyReader.getProperty(DBParameter.DB_URL);
        this.user = propertyReader.getProperty(DBParameter.DB_USER);
        this.password = propertyReader.getProperty(DBParameter.DB_PASSWORD);
        this.driverName = propertyReader.getProperty(DBParameter.DB_DRIVER_NAME);
        try {
            this.init();
        } catch (CouldNotInitializeConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    public void init() throws CouldNotInitializeConnectionPoolException {
        if (initialized.compareAndSet(false, true)) {
            registerDrivers();
            try {
                busyConQueue = new ArrayBlockingQueue<>(START_POOL_SIZE);
                connectionQueue = new ArrayBlockingQueue<>(START_POOL_SIZE);
                for (int i = 0; i < START_POOL_SIZE; i++) {
                    final Connection connection = DriverManager.getConnection(url, user, password);
                    final PooledConnection pooledConnection = new PooledConnection(connection);
                    connectionQueue.add(pooledConnection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                initialized.set(false);
                throw new CouldNotInitializeConnectionPoolException("Pool initialisation failed.", e);
            }
            connectionsOpened.set(START_POOL_SIZE);
        }
    }

    public void destroy() throws CouldNotDestroyConnectionPoolException {
        if (initialized.compareAndSet(true, false)) {
            clearConnectQueue();
            deregisterDrivers();
        }
    }

    private void clearConnectQueue() throws CouldNotDestroyConnectionPoolException {
        try {
            closeConnectionsQueue(busyConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CouldNotDestroyConnectionPoolException("Pool initialisation failed.", e);
        }
    }

    public Connection takeConnection(){
        int curOpenedConnections = connectionsOpened.get();
        if (connectionQueue.size() <= curOpenedConnections * 0.25
                && curOpenedConnections < MAX_CONNECTION_AMOUNT) {
            try {
                for (int i = 0; i < GROW_FACTOR; i++) {
                    final Connection connection;
                    connection = DriverManager.getConnection(url, user, password);
                    final PooledConnection pooledConnection = new PooledConnection(connection);
                    connectionQueue.add(pooledConnection);
                }
                connectionsOpened.set(connectionsOpened.get() + GROW_FACTOR);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection = null;
        try {
            connection = connectionQueue.take();
            busyConQueue.add(connection);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            if (connection instanceof PooledConnection) {
                busyConQueue.remove(connection);
                connectionQueue.add(connection);
            }
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    private void registerDrivers() throws CouldNotInitializeConnectionPoolException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            //DriverManager.registerDriver(DriverManager.getDriver(url));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new CouldNotInitializeConnectionPoolException("drivers registration failed", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void deregisterDrivers() throws CouldNotDestroyConnectionPoolException {
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
                throw  new CouldNotDestroyConnectionPoolException("unregistering drivers failed", e);
            }
        }
    }
}


