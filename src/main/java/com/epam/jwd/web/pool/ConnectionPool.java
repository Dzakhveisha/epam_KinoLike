package com.epam.jwd.web.pool;

import com.epam.jwd.web.exception.CouldNotDestroyConnectionPoolException;
import com.epam.jwd.web.exception.CouldNotInitializeConnectionPoolException;

import java.sql.Connection;

/**
 * Database connection pool
 */
public interface ConnectionPool {

    /**
     * @return database connection
     */
    Connection takeConnection();

    /**
     * Returns connection back to connection pool
     * @param connection returnable connection
     */
    void releaseConnection(Connection connection);

    /**
     * Initializes pool from properties file and creates connections
     * @throws CouldNotInitializeConnectionPoolException
     */
    void init() throws CouldNotInitializeConnectionPoolException;

    /**
     * Destroys connection pool. Closes all connections
     * @throws CouldNotDestroyConnectionPoolException
     */
    void destroy() throws CouldNotDestroyConnectionPoolException;
}
