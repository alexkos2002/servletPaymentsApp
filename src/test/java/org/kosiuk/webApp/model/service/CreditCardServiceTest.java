package org.kosiuk.webApp.model.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CreditCardService.class, Logger.class, DaoFactory.class, ResourceBundle.class})
public class CreditCardServiceTest {
    CreditCardService creditCardService;
    Logger logMock;
    DaoFactory daoFactoryMock;
    DaoConnection daoConnectionMock;
    ResourceBundle rbMock;
    static final long creditCardNumber = 5168000000000000L;
    static final int cvv = 123;

    @Before
    public void prepareTest() {
        mockStatic(Logger.class);
        logMock = mock(Logger.class);
        when(Logger.getLogger(UserService.class)).thenReturn(logMock);

        mockStatic(DaoFactory.class);
        daoFactoryMock = mock(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactoryMock);

        daoConnectionMock = mock(DaoConnection.class);
        when(daoFactoryMock.getConnection()).thenReturn(daoConnectionMock);

        mockStatic(ResourceBundle.class);
        rbMock = mock(ResourceBundle.class);
        when(ResourceBundle.getBundle("application")).thenReturn(rbMock);

        when(rbMock.getString("oneTrillion")).thenReturn("1000000000000");
        when(rbMock.getString("visaCode")).thenReturn("4149");
        when(rbMock.getString("masterCardCode")).thenReturn("5168");

        creditCardService = new CreditCardService();
    }

    @Test
    public void shouldGetCreditCardConfirmationDto() {
        AdditionalPropertiesDao addPropDaoMock = mock(AdditionalPropertiesDao.class);
        when(daoFactoryMock.createAdditionalPropertiesDao(any(DaoConnection.class))).thenReturn(addPropDaoMock);

        CreditCardConfirmationDto creditCardConfDto = creditCardService.getCreditCardConfirmationDto(PaymentSystem.MASTERCARD);
        try {
            verify(addPropDaoMock, times(1)).getCurMasterCardNum();
        } catch (DaoException e) {
            Assert.fail();
        }

        Assert.assertEquals(creditCardConfDto, initCreditCardConfDto());
    }

    @Test
    public void shouldGetAllUsersCreditCardPage() {
        CreditCardDao creditCardDaoMock = mock(CreditCardDao.class);
        when(daoFactoryMock.createCreditCardDao(any(DaoConnection.class))).thenReturn(creditCardDaoMock);

        creditCardService.getAllUsersCreditCardPage(2, 3);
        try {
            verify(creditCardDaoMock, times(1)).findAllByUserIdPageable(2, 3);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetNumberOfRecordsByUserId() {
        CreditCardDao creditCardDaoMock = mock(CreditCardDao.class);
        when(daoFactoryMock.createCreditCardDao(any(DaoConnection.class))).thenReturn(creditCardDaoMock);

        try {
            creditCardService.getNumberOfRecordsByUserId(2);
            verify(creditCardDaoMock, times(1)).getNumberOfRecordsByUserId(2);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldPutMoneyWithUserIdReturn() {
        CreditCardDao creditCardDaoMock = mock(CreditCardDao.class);
        when(daoFactoryMock.createCreditCardDao(any(DaoConnection.class))).thenReturn(creditCardDaoMock);
        MoneyAccountDao moneyAccountDaoMock = mock(MoneyAccountDao.class);
        when(daoFactoryMock.createMoneyAccountDao(any(DaoConnection.class))).thenReturn(moneyAccountDaoMock);

        try {
            creditCardService.putMoneyWithUserIdReturn(2, "20.25");
            verify(daoConnectionMock, times(1)).beginTransaction();
            verify(creditCardDaoMock, times(1)).selectSumAvailableInt(2);
            verify(creditCardDaoMock, times(1)).selectSumAvailableDec(2);
            verify(daoConnectionMock, times(1)).commit();
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    private static CreditCardConfirmationDto initCreditCardConfDto() {
        return new CreditCardConfirmationDto(creditCardNumber, cvv, LocalDate.now().plusYears(3).toString(), PaymentSystem.MASTERCARD);
    }
}
