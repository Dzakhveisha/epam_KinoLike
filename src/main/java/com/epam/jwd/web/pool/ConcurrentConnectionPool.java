package com.epam.jwd.web.pool;

import com.epam.jwd.web.exception.CouldNotDestroyConnectionPoolException;
import com.epam.jwd.web.exception.CouldNotInitializeConnectionPoolException;
import com.epam.jwd.web.util.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    static final Logger LOGGER = LogManager.getRootLogger();
    private static final ConcurrentConnectionPool instance = new ConcurrentConnectionPool();
    private static final int START_POOL_SIZE = 8;
    private static final int MAX_CONNECTION_AMOUNT = 32;
    private static final int GROW_FACTOR = 4;

    private static final String POOL_DESTROYING_FAIL_MSG = "Pool destroying failed.";
    private static final String POOL_INITIALIZATION_FAIL_MSG = "Pool initialisation failed.";
    private static final String TAKING_CONNECTION_FAIL_MSG = "Taking connection failed.";
    private static final String DRIVER_REGISTRATION_FAIL_MSG = "Driver registration failed.";
    private static final String DRIVER_UNREGISTERING_FAIL_MSG = "Unregistering drivers failed";

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
                LOGGER.error(POOL_INITIALIZATION_FAIL_MSG + e.getMessage());
                initialized.set(false);
                throw new CouldNotInitializeConnectionPoolException(POOL_INITIALIZATION_FAIL_MSG, e);
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
            LOGGER.error(POOL_DESTROYING_FAIL_MSG + e.getMessage());
            throw new CouldNotDestroyConnectionPoolException(POOL_DESTROYING_FAIL_MSG, e);
        }
    }

    public Connection takeConnection() {
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
                LOGGER.error(TAKING_CONNECTION_FAIL_MSG + e.getMessage());
            }
        }

        Connection connection = null;
        try {
            connection = connectionQueue.take();
            busyConQueue.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error(TAKING_CONNECTION_FAIL_MSG + e.getMessage());
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
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            LOGGER.error(DRIVER_REGISTRATION_FAIL_MSG + e.getMessage());
            throw new CouldNotInitializeConnectionPoolException(DRIVER_REGISTRATION_FAIL_MSG, e);
        }
    }

    private void deregisterDrivers() throws CouldNotDestroyConnectionPoolException {
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOGGER.error(DRIVER_REGISTRATION_FAIL_MSG + e.getMessage());
                throw new CouldNotDestroyConnectionPoolException(DRIVER_UNREGISTERING_FAIL_MSG, e);
            }
        }
    }
}


