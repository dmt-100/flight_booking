package ru.dmt100.flight_booking.util;

import lombok.extern.slf4j.Slf4j;
import ru.dmt100.flight_booking.exception.ConnectionException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

@Slf4j
public class ConnectionManager {
    private static final int CONNECTION_POLL_SIZE = 3;
    private static BlockingQueue<Connection> pool;

    static {
        initConnectionPool();
    }

    // Initializes the connection pool with a specified size and adds connections to the pool
    private static void initConnectionPool() {
        String poolSize = PropertyUtil.getKey("spring.datasource.pool.size");
        int size = poolSize == null ? CONNECTION_POLL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);

        log.info("Initializing connection pool with size: {}", size);

        IntStream.range(0, size).forEach(i -> {
            var con = getConnection();

            log.info("Created connection {} of {}", i + 1, size);

            var proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("close")) {
                            log.info("<initConnectionPool()> method.getName(): {}", method.getName());
                            pool.add((Connection) proxy);
                            log.info("<pool.add((Connection) proxy> pool.size(): {}", pool.size());
                            return null;
                        } else {
                            return method.invoke(con, args);
                        }
                    }
            );
            pool.add(proxyConnection);
        });

        log.info("Connection pool initialized successfully.");
    }

    public static Connection open() {
        try {
            log.info("pool.size() before pool.take(): {}", pool.size());
            Connection conn = pool.take();
            log.info("pool.size() after pool.take(): {}", pool.size());
            return conn;
        } catch (InterruptedException e) {
            throw new ConnectionException("Some problem with connection: " + e.getMessage());
        }
    }

    private static Connection getConnection() {
        try {
            String URL_KEY = "spring.datasource.url";
            String USERNAME_KEY = "spring.datasource.username";
            String PASSWORD_KEY = "spring.datasource.password";
            DriverManager.setLoginTimeout(20);
            return DriverManager.getConnection(
                    PropertyUtil.getKey(URL_KEY),
                    PropertyUtil.getKey(USERNAME_KEY),
                    PropertyUtil.getKey(PASSWORD_KEY)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

