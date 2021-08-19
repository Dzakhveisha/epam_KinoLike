package com.epam.jwd.web.pool;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

;

public class ConcurrentConnectionPoolTest {

    @Test
    public void getInstanceSingleThreadTest() {
        ConnectionPool pool1 = ConcurrentConnectionPool.getInstance();
        ConnectionPool pool2 = ConcurrentConnectionPool.getInstance();

        assertEquals(pool1, pool2);
    }

    @Test
    public void getInstanceMultiThreadTest() throws InterruptedException {
        ConnectionPool pool1 = ConcurrentConnectionPool.getInstance();
        Thread t1 = new Thread(() -> {
            ConnectionPool pool2 = ConcurrentConnectionPool.getInstance();
            assertEquals(pool1, pool2);
        });

        t1.start();
        t1.join();
    }

    @Test
    public void takeConnectionTest() throws SQLException {
        assertTrue(ConcurrentConnectionPool.getInstance().takeConnection().isValid(10));
    }
}