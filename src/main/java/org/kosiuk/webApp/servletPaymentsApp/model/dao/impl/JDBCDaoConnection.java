package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoConnection implements DaoConnection {
    private final Connection connection;
    private boolean inTransaction;

    JDBCDaoConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {

        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {

        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
            connection.setAutoCommit(true);
        } catch (SQLException e) {

        }
    }

    @Override
    public void close() {
        if (inTransaction) {
            rollback();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
