package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;

public interface AdditionalPropertiesDao {

    int getCurVisaCardNum() throws DaoException;

    int incCurVisaCardNum() throws DaoException;

    int getCurMasterCardNum() throws DaoException;

    int incCurMasterCardNum() throws DaoException;

    long getCurMoneyAccountNum() throws DaoException;

    long incCurMoneyAccountNum() throws DaoException;

    long getCurPaymentNum() throws DaoException;

    long incCurPaymentNum() throws DaoException;

}
