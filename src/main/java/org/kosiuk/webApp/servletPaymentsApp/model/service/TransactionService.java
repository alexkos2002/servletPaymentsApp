package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoConnection;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoFactory;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.TransactionDao;

import java.util.ResourceBundle;

public class TransactionService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ResourceBundle rb = ResourceBundle.getBundle("/db/application");
    public static final Logger logger = Logger.getLogger(TransactionService.class);

    public long getNumberOfRecordsByReceiverMonAccId(int receiverMoneyAccountId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            return transactionDao.getNumberOfRecordsByReceiverMonAccId(receiverMoneyAccountId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }
}
