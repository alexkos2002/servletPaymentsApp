package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.MoneyAccountWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.*;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccountActStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.concurrent.MoneyAccountMonitor;

import java.util.*;

public class MoneyAccountService {

    private static MoneyAccountService instance;
    private final DaoFactory daoFactory;
    private final ResourceBundle rb;
    private final long ACCOUNT_CODE;
    private final Map<Integer, MoneyAccountMonitor> moneyAccountMonitorsMap;
    public static final Logger logger = Logger.getLogger(MoneyAccountService.class);

    private MoneyAccountService() {
        daoFactory = DaoFactory.getInstance();
        rb = ResourceBundle.getBundle("/db/application");
        ACCOUNT_CODE = Long.parseLong(rb.getString("thisSystemMoneyAccountCode"));
        moneyAccountMonitorsMap = new HashMap<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            List<Integer> moneyAccountIds = moneyAccountDao.selectAllIds();
            for (int curMoneyAccountId : moneyAccountIds) {
                moneyAccountMonitorsMap.put(curMoneyAccountId, new MoneyAccountMonitor(curMoneyAccountId));
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public static synchronized MoneyAccountService getInstance() {
        if (instance == null) {
            instance = new MoneyAccountService();
        }
        return instance;
    }

    public List<MoneyAccountWithUserDto> getAllMoneyAccountPage(int pageNumber, String sortParameter) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            return moneyAccountDao.findAllWithUserSortedPageable(pageNumber, sortParameter);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<MoneyAccount> getAllUsersMoneyAccountPage(int userId, int pageNum, String sortParameter) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            return moneyAccountDao.findAllSortedPageableByUserId(userId, pageNum, sortParameter);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public Optional<MoneyAccount> getMoneyAccountById(int id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            return moneyAccountDao.findById(id);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public long getCurMoneyAccountNum() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
            return addPropDao.getCurMoneyAccountNum() + ACCOUNT_CODE;
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return ACCOUNT_CODE;
        }
    }

    public int create(long number, String name) {
        int moneyAccId = -1;
        MoneyAccount moneyAccount = new MoneyAccount(number, name, MoneyAccountActStatus.ACTIVE, 0, 0, 0, 0);
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            AdditionalPropertiesDao addPropDao = daoFactory.createAdditionalPropertiesDao(connection);
            addPropDao.incCurMoneyAccountNum();
            try {
                MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
                moneyAccId = moneyAccountDao.createWithIdReturn(moneyAccount);
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
                return moneyAccId;
            }
            moneyAccountMonitorsMap.put(moneyAccId, new MoneyAccountMonitor(moneyAccId));
            connection.commit();
            return moneyAccId;
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return moneyAccId;
        }
    }

    public void block(int id, int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            moneyAccountDao.updateActive(id, MoneyAccountActStatus.BLOCKED);
            try {
                UserDao userDao = daoFactory.createUserDao(connection);
                userDao.setHasBlockedAccountTrue(ownerId);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public void askToUnlock(int id, int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            moneyAccountDao.updateActive(id, MoneyAccountActStatus.UNLOCK_REQUESTED);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public void unlock(int id, int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            moneyAccountDao.updateActive(id, MoneyAccountActStatus.ACTIVE);
            try {
                UserDao userDao = daoFactory.createUserDao(connection);
                userDao.setHasBlockedAccountFalse(ownerId);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public void delete(int id, int ownerId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            Optional<MoneyAccountActStatus> active = moneyAccountDao.selectActive(id);
            if (!active.isEmpty()) {
                if (active.get() != MoneyAccountActStatus.ACTIVE) {
                    try {
                        UserDao userDao = daoFactory.createUserDao(connection);
                        userDao.setHasBlockedAccountFalse(ownerId);
                    } catch (DaoException e) {
                        logger.warn(e.getMessage());
                        connection.rollback();
                    }
                }
            }
            try {
                moneyAccountDao.delete(id);
                connection.commit();
            } catch (DaoException e) {
                logger.warn(e.getMessage());
                connection.rollback();
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            return moneyAccountDao.getNumberOfRecords();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

    public long getNumberOfRecordsByUserId(int userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            MoneyAccountDao moneyAccountDao = daoFactory.createMoneyAccountDao(connection);
            return moneyAccountDao.getNumberOfRecordsByUserId(userId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

    public Map<Integer, MoneyAccountMonitor> getMoneyAccountMonitorsMap() {
        return moneyAccountMonitorsMap;
    }
}
