package com.epam.jwd.web.pool;

import com.epam.jwd.web.exception.CouldNotDestroyConnectionPoolException;
import com.epam.jwd.web.exception.CouldNotInitializeConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolManager implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SUCCESSFUL_POOL_INIT_MSG = "Connection pool was successfully initialized";
    private static final String SUCCESSFUL_POOL_DESTROY_MSG = "Connection pool was successfully destroyed";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConcurrentConnectionPool.getInstance().init();
            LOGGER.debug(SUCCESSFUL_POOL_INIT_MSG);
        } catch (CouldNotInitializeConnectionPoolException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConcurrentConnectionPool.getInstance().destroy();
            LOGGER.debug(SUCCESSFUL_POOL_DESTROY_MSG);
        } catch (CouldNotDestroyConnectionPoolException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
