package org.kosiuk.webApp.model.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CreditCardOrderService.class, MoneyAccountService.class, Logger.class, DaoFactory.class})
public class CreditCardOrderServiceTest {
    CreditCardOrderService orderService;
    Logger logMock;
    DaoFactory daoFactoryMock;
    DaoConnection daoConnectionMock;
    MoneyAccountService moneyAccountServiceMock;
    static final String orderMessage = "I want my money account to be called FoodShop";
    static final long creditCardNumber = 4149000000000000L;
    static final int cvv = 123;
    static final String expireDateString = "2024-09-27";
    static final long moneyAccountNumber = 110000000000L;
    static final String moneyAccountName = "Money Account";

    @Before
    public void prepareTest() {
        mockStatic(Logger.class);
        logMock = mock(Logger.class);
        when(Logger.getLogger(UserService.class)).thenReturn(logMock);

        mockStatic(DaoFactory.class);
        daoFactoryMock = mock(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactoryMock);

        mockStatic(MoneyAccountService.class);
        moneyAccountServiceMock = mock(MoneyAccountService.class);
        when(MoneyAccountService.getInstance()).thenReturn(moneyAccountServiceMock);

        daoConnectionMock = mock(DaoConnection.class);
        when(daoFactoryMock.getConnection()).thenReturn(daoConnectionMock);

        orderService = new CreditCardOrderService();
    }

    @Test
    public void shouldCreateOrder() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        CreditCardOrder order = initCreditCardOrder();

        try {
            orderService.create(1, PaymentSystem.VISA, orderMessage);
            verify(daoConnectionMock, times(1)).beginTransaction();
            verify(orderDaoMock, times(1)).create(order);
            verify(userDaoMock, times(1)).setOrderOnCheckTrue(1);
            verify(daoConnectionMock, times(1)).commit();
        } catch (DaoException e) {
            Assert.fail();
        }

    }

    @Test
    public void shouldRejectOrder() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        try {
            orderService.rejectCreditCardOrder(1, "No cards left", 1);
            verify(daoConnectionMock, times(1)).beginTransaction();
            verify(userDaoMock, times(1)).setOrderOnCheckFalse(1);
            verify(daoConnectionMock, times(1)).commit();
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldConfirmOrder() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        UserDao userDaoMock = mock(UserDao.class);
        CreditCardDao creditCardDaoMock = mock(CreditCardDao.class);
        AdditionalPropertiesDao addPropDaoMock = mock(AdditionalPropertiesDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);
        when(daoFactoryMock.createCreditCardDao(any(DaoConnection.class))).thenReturn(creditCardDaoMock);
        when(daoFactoryMock.createAdditionalPropertiesDao(any(DaoConnection.class))).thenReturn(addPropDaoMock);

        CreditCardOrder order = initCreditCardOrder();
        CreditCard creditCard = initCreditCard();

        orderService.confirmCreditCardOrder(1, 1, creditCardNumber, cvv, expireDateString,
                moneyAccountNumber, moneyAccountName, PaymentSystem.VISA);
        try {
            verify(daoConnectionMock, times(1)).beginTransaction();
            verify(orderDaoMock, times(1)).updateOrderStatus(OrderStatus.CONFIRMED, 1);
            verify(userDaoMock, times(1)).setOrderOnCheckFalse(1);
            verify(moneyAccountServiceMock, times(1)).create(moneyAccountNumber, moneyAccountName);
            verify(creditCardDaoMock, times(1)).create(creditCard);
            verify(addPropDaoMock, times(1)).incCurVisaCardNum();
            verify(daoConnectionMock, times(1)).commit();
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetAllUsersCreditCardOrders() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        orderService.getAllUsersCreditCardOrders(1);
        try {
            verify(orderDaoMock, times(1)).findAllByOwnerId(1);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetAllCreditCardOrderWithUserDtosPage() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        orderService.getAllCreditCardOrderWithUserDtosPage(3);
        try {
            verify(orderDaoMock, times(1)).findAllWithUserPage(3);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetNumberOfRecords() {
        CreditCardOrderDao orderDaoMock = mock(CreditCardOrderDao.class);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        orderService.getNumberOfRecords();
        try {
            verify(orderDaoMock, times(1)).getNumberOfRecords();
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    private static CreditCardOrder initCreditCardOrder() {
        return CreditCardOrder.builder()
                .paymentSystem(PaymentSystem.VISA)
                .status(OrderStatus.ON_CHECK)
                .message(orderMessage)
                .userId(1)
                .build();
    }

    private static CreditCard initCreditCard() {
        return CreditCard.builder()
                .number(creditCardNumber)
                .cvv(cvv)
                .expireDateString(expireDateString)
                .paymentSystem(PaymentSystem.VISA)
                .availableSumInt(0)
                .availableSumDec(0)
                .accountId(1)
                .moneyAccountId(0)
                .build();
    }

}
