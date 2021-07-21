package com.epam.jwd.web.pool;

import com.epam.jwd.web.exception.CouldNotDestroyConnectionPoolException;
import com.epam.jwd.web.exception.CouldNotInitializeConnectionPoolException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection();

    void releaseConnection(Connection connection);

    void init() throws CouldNotInitializeConnectionPoolException;

    void destroy() throws CouldNotDestroyConnectionPoolException;
}
