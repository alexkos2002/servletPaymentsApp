package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserBasicDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao extends GenericDao<User>{

    @Override
    default void create(User entity) throws DaoException {}

    Optional<User> findByUsername(String username) throws DaoException;

    @Override
    default Optional<User> findById(int id) throws DaoException{
        return Optional.empty();
    }

    List<User> findAll() throws DaoException;

    List<User> findAllPageable(int pageNumber) throws DaoException;

    Optional<UserBasicDto> findByIdAsUserBasicDto(int id) throws DaoException;

    void changeRoles(int userId, Set<Role> newRoles) throws DaoException;

    void banUser(int id) throws DaoException;

    void unbanUser(int id) throws DaoException;

    void setOrderOnCheckTrue(int userId) throws DaoException;

    void setOrderOnCheckFalse(int userId) throws DaoException;

    void setHasBlockedAccountTrue(int userId) throws DaoException;

    void setHasBlockedAccountFalse(int userId) throws DaoException;

    long getNumberOfRecords() throws DaoException;

    @Override
    default void update(User entity) throws DaoException {

    }

    @Override
    default void delete(int id) throws DaoException {}
}
