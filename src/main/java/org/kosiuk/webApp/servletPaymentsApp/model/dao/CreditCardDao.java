package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardDao extends GenericDao<CreditCard>{

    @Override
    Optional<CreditCard> findById(int id) throws DaoException;

    @Override
    void create(CreditCard entity) throws DaoException;

    @Override
    void update(CreditCard entity) throws DaoException;

    @Override
    void delete(int id) throws DaoException;

    long getNumberOfRecordsByUserId(int userId) throws DaoException;

    List<CreditCard> findAllByUserIdPageable(int userId, int pageNumber) throws DaoException;

    Optional<CreditCard> findCreditCardByNumber(long number) throws DaoException;

    Optional<CreditCard> findByMoneyAccountId(int moneyAccountId) throws DaoException;

    void updateAvailableSum(int cardId, long availableSumInt, int availableSumDec) throws DaoException;

    int selectMoneyAccountId(int id) throws DaoException;

    int selectAccountId(int id) throws DaoException;

    long selectSumAvailableInt(int id) throws DaoException;

    int selectSumAvailableDec(int id) throws DaoException;
}
