package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.UnsafeOrderConfirmationException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

import java.util.Collections;
import java.util.List;

public class CreditCardOrderService {

    MoneyAccountService moneyAccountService = MoneyAccountService.getInstance();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private boolean isAnyOrderOnConfirmation = false;
    public static final Logger logger = Logger.getLogger(CreditCardOrderService.class);

    public void create(int userId, PaymentSystem desPaymentSystem, String message) {
        CreditCardOrder order = CreditCardOrder.builder().
                paymentSystem(desPaymentSystem)
                .message(message)
                .status(OrderStatus.ON_CHECK)
                .userId(userId)
                .build();

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
            orderDao.create(order);
            try {
                UserDao userDao = daoFactory.createUserDao(connection);
                userDao.setOrderOnCheckTrue(userId);
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
            connection.commit();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public void rejectCreditCardOrder(int id, String rejectMessage, int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.setOrderOnCheckFalse(ownerId);
            try {
                CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
                orderDao.reject(id, rejectMessage);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public void prepareCreditCardOrderConfirmation() throws UnsafeOrderConfirmationException {
        if (isAnyOrderOnConfirmation) {
            throw new UnsafeOrderConfirmationException();
        }
        isAnyOrderOnConfirmation = true;
    }

    public void cancelCreditCardOrderConfirmation() {
        isAnyOrderOnConfirmation = false;
    }

    public void confirmCreditCardOrder(int orderId, int ownerId, long creditCardNumber,
                                       int cvv, String expireDateString, long moneyAccountNumber,
                                       String moneyAccountName, PaymentSystem paymentSystem) {

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
            orderDao.updateOrderStatus(OrderStatus.CONFIRMED, orderId);
            try {
                UserDao userDao = daoFactory.createUserDao(connection);
                userDao.setOrderOnCheckFalse(ownerId);
            } catch (DaoException e) {
                connection.rollback();
                logger.warn(e.getMessage());
            }
            int moneyAccId = moneyAccountService.create(moneyAccountNumber, moneyAccountName);
            if (moneyAccId == -1) {
                connection.rollback();
            }
            try {
                CreditCard creditCard = CreditCard.builder()
                        .number(creditCardNumber)
                        .cvv(cvv)
                        .expireDateString(expireDateString)
                        .paymentSystem(paymentSystem)
                        .availableSumInt(0)
                        .availableSumDec(0)
                        .accountId(ownerId)
                        .moneyAccountId(moneyAccId)
                        .build();
                CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
                creditCardDao.create(creditCard);
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
            try {
                AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
                if (paymentSystem == PaymentSystem.VISA) {
                    addPropDao.incCurVisaCardNum();
                } else {
                    addPropDao.incCurMasterCardNum();
                }
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
        isAnyOrderOnConfirmation = false;
    }

    public List<CreditCardOrder> getAllUsersCreditCardOrders(int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findAllByOwnerId(ownerId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }


    public List<CreditCardOrderWithUserDto> getAllCreditCardOrderWithUserDtosPage(int pageNumber) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findAllWithUserPage(pageNumber);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            CreditCardOrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.getNumberOfRecords();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }
}
