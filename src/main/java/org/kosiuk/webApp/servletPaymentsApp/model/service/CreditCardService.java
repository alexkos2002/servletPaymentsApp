package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class CreditCardService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ResourceBundle rb = ResourceBundle.getBundle("application");
    private final long ONE_TRILLION = Long.parseLong(rb.getString("oneTrillion"));
    private final int VISA_CODE = Integer.parseInt(rb.getString("visaCode"));
    private final int MASTERCARD_CODE = Integer.parseInt(rb.getString("masterCardCode"));
    private final Random random = new Random();
    public static final Logger logger = Logger.getLogger(CreditCardService.class);

    public CreditCardConfirmationDto getCreditCardConfirmationDto(PaymentSystem paymentSystem) {
        long number = 0;
        try (DaoConnection connection = daoFactory.getConnection()) {
            AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
            if (paymentSystem == PaymentSystem.VISA) {
                number = VISA_CODE * ONE_TRILLION + addPropDao.getCurVisaCardNum();
            } else {
                number = MASTERCARD_CODE * ONE_TRILLION + addPropDao.getCurMasterCardNum();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return new CreditCardConfirmationDto();
        }
        int cvv = random.nextInt(899) + 100;
        return new CreditCardConfirmationDto(number, cvv, LocalDate.now().plusYears(3).toString(), paymentSystem);
    }

    public List<CreditCard> getAllUsersCreditCardPage(int userId, int pageNumber) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
            return creditCardDao.findAllByUserIdPageable(userId, pageNumber);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public long getNumberOfRecordsByUserId(int userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
            return creditCardDao.getNumberOfRecordsByUserId(userId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

    public int putMoneyWithUserIdReturn(int cardId, String sumString) {
        String[] sumIntDec = sumString.split("\\.");
        long sumInt = Long.parseLong(sumIntDec[0]);
        int sumDec = Integer.parseInt(sumIntDec[1]);

        int moneyAccountId;
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            CreditCardDao creditCardDao = daoFactory.createCreditCardDao(connection);
            long sumPrevInt = creditCardDao.selectSumAvailableInt(cardId);
            int sumPrevDec = creditCardDao.selectSumAvailableDec(cardId);
            long sum = (sumInt + sumPrevInt) * 100 + sumDec + sumPrevDec;
            sumPrevDec = (int)(sum % 100);
            sumPrevInt = (sum - sumPrevDec) / 100;
            creditCardDao.updateAvailableSum(cardId, sumPrevInt, sumPrevDec);
            try {
                moneyAccountId = creditCardDao.selectMoneyAccountId(cardId);
                MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
                long curSumAvPrevInt = moneyAccountDao.selectCurSumAvailableInt(moneyAccountId);
                int curSumAvPrevDec = moneyAccountDao.selectCurSumAvailableDec(moneyAccountId);
                sum = (sumInt + curSumAvPrevInt) * 100 + sumDec + curSumAvPrevDec;
                int curSumAvailableDec = (int)(sum % 100);
                long curSumAvailableInt = (sum - sumDec) / 100;
                moneyAccountDao.updateSum(moneyAccountId, sumPrevInt, sumPrevDec);
                moneyAccountDao.updateCurSumAvailable(moneyAccountId, curSumAvailableInt, curSumAvailableDec);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
                return 0;
            }
            try {
                return creditCardDao.selectAccountId(cardId);
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
                return 0;
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

}
