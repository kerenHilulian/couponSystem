package com.kh.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private static final int NUM_OF_CONS = 10;
    private static ConnectionPool instance = null;
    private Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        openAllConnections();
    }
    public static ConnectionPool getInstance() {

        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {

        synchronized (connections) {
            if(connections.isEmpty()) {
                connections.wait();
            }

            return connections.pop();
        }
    }

    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * This method will open 10 connections in advanced
     * @throws SQLException
     */

    public void openAllConnections() throws SQLException {
        for (int i = 0; i < NUM_OF_CONS; i++) {
            Connection connection = DriverManager.getConnection(DatabaseManager.url,DatabaseManager.username,
                    DatabaseManager.password);
            connections.push(connection);
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while(connections.size()<NUM_OF_CONS) {
                connections.wait();
            }

            connections.removeAllElements();
        }
    }


}