package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;
    /**
     * Get JDBCDaoFactory singleton instance
     */
    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao(DaoConnection connection);

    public abstract CreditCardOrderDao createOrderDao(DaoConnection connection);

    public abstract CreditCardDao createCreditCardDao(DaoConnection connection);

    public abstract MoneyAccountDao createMoneyAccountDao(DaoConnection connection);

    public abstract PaymentDao createPaymentDao(DaoConnection connection);

    public abstract TransactionDao createTransactionDao(DaoConnection connection);

    public abstract AdditionalPropertiesDao createAdditionalPropertiesDao(DaoConnection connection);

    public abstract DaoConnection getConnection();
}
