package org.kosiuk.webApp.servletPaymentsApp.model.service;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserBasicDto;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserRegistrationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.exception.NotCompatibleRolesException;
import org.kosiuk.webApp.servletPaymentsApp.exception.UsernameNotUniqueException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoConnection;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoFactory;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.UserDao;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;

import java.sql.SQLException;
import java.util.*;

public class UserService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.valueOf(rb.getString("user.page.size"));
    private final Set<Set<Role>> notCompatibleRoles = new HashSet<>();
    private static final Logger logger = Logger.getLogger(UserService.class);

    public UserService() {
        notCompatibleRoles.add(Set.of(Role.ADMIN, Role.USER));
    }

    public void registerUser(UserRegistrationDto userRegDto) throws UsernameNotUniqueException {
        User user = User.builder().
                initRegistrationDetails(userRegDto.getUsername(), userRegDto.getEmail(), userRegDto.getPassword())
                .initFlagsDefault()
                .roles(Role.USER)
                .build();

        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.create(user);
        } catch (DaoException e) {
            if (e.getCause() instanceof SQLException) {
                SQLException causeException = (SQLException) e.getCause();
                if (causeException.getSQLState().equals("23000") &&
                        e.getMessage().equals(ExceptionMessages.CANT_CREATE_USER)) {
                    logger.warn(e.getMessage());
                    throw new UsernameNotUniqueException();
                }
            }
            logger.warn(e.getMessage());
        }
    }

    public Optional<User> getUserById(int id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUsername(String username) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<UserBasicDto> getUserByIdAsBasicDto(int id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findByIdAsUserBasicDto(id);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    @Deprecated
    public List<User> getAllUsers() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAll();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<User> getAllUsersPage(int pageNumber) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAllPageable(pageNumber);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.getNumberOfRecords();
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return 0;
        }
    }

    public void changeRoles(int userId, Set<Role> newRoles) throws NotCompatibleRolesException {
        if (!isRolesCompatible(newRoles)) {
            throw new NotCompatibleRolesException();
        }
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.changeRoles(userId, newRoles);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public Optional<User> banUser(int userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.banUser(userId);
            return getUserById(userId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<User> unbanUser(int userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.unbanUser(userId);
            return getUserById(userId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public void deleteUser(int userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(userId);
        } catch (DaoException e) {
            logger.warn(e.getMessage());
        }
    }

    public boolean isRolesCompatible(Set<Role> roles) {
        for (Set<Role> notCompRoles : notCompatibleRoles) {
            if (roles.equals(notCompRoles)) {
                return false;
            }
        }
        return true;
    }

}
