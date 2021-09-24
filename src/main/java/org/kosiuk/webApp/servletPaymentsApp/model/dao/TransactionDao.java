package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao extends GenericDao<Transaction>{

    @Override
    Optional<Transaction> findById(int id) throws DaoException;

    List<Transaction> findAllByReceiverMoneyAccountId(int receiverMoneyAccountId) throws DaoException;

    @Override
    void create(Transaction entity) throws DaoException;

    @Override
    void update(Transaction entity) throws DaoException;

    @Override
    void delete(int id) throws DaoException;

    long getNumberOfRecordsByReceiverMonAccId(int receiverMoneyAccountId) throws DaoException;
}
