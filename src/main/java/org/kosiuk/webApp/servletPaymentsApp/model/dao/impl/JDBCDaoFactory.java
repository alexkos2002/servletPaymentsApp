package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao(DaoConnection connection) {
        return new JDBCUserDao(connection.getConnection());
    }

    @Override
    public CreditCardOrderDao createOrderDao(DaoConnection connection) {
        return new JDBCreditCardOrderDao(connection.getConnection());
    }

    @Override
    public CreditCardDao createCreditCardDao(DaoConnection connection) {
        return new JDBCCreditCardDao(connection.getConnection());
    }

    @Override
    public MoneyAccountDao createMoneyAccountDao(DaoConnection connection) {
        return new JDBCMoneyAccountDao(connection.getConnection());
    }

    @Override
    public PaymentDao createPaymentDao(DaoConnection connection) {
        return new JDBCPaymentDao(connection.getConnection());
    }

    @Override
    public TransactionDao createTransactionDao(DaoConnection connection) {
        return new JDBCTransactionDao(connection.getConnection());
    }

    @Override
    public AdditionalPropertiesDao createAdditionalPropertiesDao(DaoConnection connection) {
        return new JDBCAdditionalPropertiesDao(connection.getConnection());
    }


    @Override
    public DaoConnection getConnection() {
        try {
            return new JDBCDaoConnection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
