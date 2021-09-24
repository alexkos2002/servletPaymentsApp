package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentDetailsDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment>{

    @Override
    Optional<Payment> findById(int id) throws DaoException;

    Optional<Payment> findByNumber(long number) throws DaoException;

    List<Payment> findAllSortedPageable(int pageNumber, String sortParameter) throws DaoException;

    List<Payment> findAllBySenderMonAccIdSortedPageable(int moneyAccountId, int pageNumber, String sortParameter)  throws DaoException;

    Optional<PaymentDetailsDto> findPaymentDetails(long paymentNumber) throws DaoException;

    @Override
    void create(Payment entity) throws DaoException;

    @Override
    void update(Payment entity) throws DaoException;

    void updateStatus(long number, PaymentStatus status) throws DaoException;

    @Override
    void delete(int id) throws DaoException;

    void deleteByNumber(long number) throws DaoException;

    long getNumberOfRecordsBySenderMonAccId(int moneyAccountId) throws DaoException;

    long getNumberOfRecords() throws DaoException;
}
