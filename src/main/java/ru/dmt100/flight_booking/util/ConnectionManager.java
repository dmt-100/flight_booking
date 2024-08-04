package ru.dmt100.flight_booking.util;

import ru.dmt100.flight_booking.exception.ConnectionException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class ConnectionManager {

    private static String URL_KEY = "spring.datasource.url";
    private static String USERNAME_KEY = "spring.datasource.username";
    private static String PASSWORD_KEY = "spring.datasource.password";
    private static int CONNECTION_POLL_SIZE = 10;
    private static BlockingQueue<Connection> pool;

    static {
        initConnectionPoll();
    }

    // Initializes the connection pool with a specified size and adds connections to the pool
    private static void initConnectionPoll() {
        String poolSize = PropertyUtil.getKey("spring.datasource.pool.size");
        int size = poolSize == null ? CONNECTION_POLL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue(size);

        IntStream.range(0, size).forEach(i -> {
            var con = open();

        /* Create a proxy connection that intercepts the close method
        If the close method is called, the connection is added back to the pool
        Otherwise, the original method is invoked on the actual connection */
            var proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    ((proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(con, args))
            );

            pool.add(proxyConnection);
        });
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new ConnectionException("Some problem with connection: " + e.getMessage());
        }
    }

    private static Connection open() {
        try {
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
