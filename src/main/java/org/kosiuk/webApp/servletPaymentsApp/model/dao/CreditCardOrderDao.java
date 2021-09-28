package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface CreditCardOrderDao extends GenericDao<CreditCardOrder>{

    @Override
    default Optional<CreditCardOrder> findById(int id) throws DaoException {
        return Optional.empty();
    }

    List<CreditCardOrder> findAllByOwnerId(int ownerId) throws DaoException;

    List<CreditCardOrderWithUserDto> findAllWithUserPage(int pageNumber) throws DaoException;

    void updateOrderStatus(OrderStatus orderStatus, int id) throws DaoException;

    long getNumberOfRecords() throws DaoException;

    void reject(int id, String rejectionMessage) throws DaoException;

    @Override
    default void create(CreditCardOrder entity) throws DaoException {}

    @Override
    default void update(CreditCardOrder entity) throws DaoException {}

    @Override
    default void delete(int id) throws DaoException {}
}
