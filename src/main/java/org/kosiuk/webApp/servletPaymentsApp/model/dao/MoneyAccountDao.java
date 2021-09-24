package org.kosiuk.webApp.servletPaymentsApp.model.dao;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.MoneyAccountWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccountActStatus;

import java.util.List;
import java.util.Optional;

public interface MoneyAccountDao extends GenericDao<MoneyAccount>{

    @Override
    Optional<MoneyAccount> findById(int id) throws DaoException;

    Optional<MoneyAccount> findByNumber(long number) throws DaoException;

    int createWithIdReturn(MoneyAccount entity) throws DaoException;

    @Override
    void update(MoneyAccount entity) throws DaoException;

    @Override
    void delete(int id) throws DaoException;

    void updateSum(int moneyAccId, long sumInt, int sumDec) throws DaoException;

    void updateCurSumAvailable(int moneyAccId, long availableSumInt, int availableSumDec) throws DaoException;

    List<MoneyAccount> findAllSortedPageableByUserId(int userId, int pageNumber, String sortParameter) throws DaoException;

    List<MoneyAccountWithUserDto> findAllWithUserSortedPageable (int pageNumber, String sortParameter) throws DaoException;

    void updateActive(int id, MoneyAccountActStatus active) throws DaoException;

    Optional<MoneyAccountActStatus> selectActive(int id) throws DaoException;

    long selectCurSumAvailableInt(int id) throws DaoException;

    int selectCurSumAvailableDec(int id) throws DaoException;

    long selectSumInt(int id) throws DaoException;

    int selectSumDec(int id) throws DaoException;

    List<Integer> selectAllIds() throws DaoException;

    long getNumberOfRecords() throws DaoException;

    long getNumberOfRecordsByUserId(int userId) throws DaoException;
}
