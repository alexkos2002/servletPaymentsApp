package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;

import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> findById(int id) throws DaoException;
    void create(T entity) throws DaoException;
    void update(T entity) throws DaoException;
    void delete(int id) throws DaoException;
}
