package pool;

import exception.CouldNotDestroyConnectionPoolException;
import exception.CouldNotInitializeConnectionPoolException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection();

    void releaseConnection(Connection connection);

    void init() throws CouldNotInitializeConnectionPoolException;

    void destroy() throws CouldNotDestroyConnectionPoolException;
}
