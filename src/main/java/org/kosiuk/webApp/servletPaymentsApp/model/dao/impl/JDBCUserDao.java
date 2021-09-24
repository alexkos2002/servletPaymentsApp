package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;


import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserBasicDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.UserDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.UserMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.valueOf(rb.getString("user.page.size"));

    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.create"), Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            fillUserStatement(user, ps);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            try (PreparedStatement rolePS = connection.prepareStatement(rb.getString("query.role.create"))) {
                for (Role curRole : user.getRoles()) {
                    rolePS.setInt(1, user.getId());
                    rolePS.setInt(2,curRole.ordinal() + 1);
                    rolePS.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(ExceptionMessages.CANT_CREATE_USER_ROLE, e);
            }
            connection.commit();
        } catch (SQLException e) {
           throw new DaoException(ExceptionMessages.CANT_CREATE_USER, e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.find.byUsername"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Map<Integer, User> userMap = extractUsersFromResultSet(rs);
            return userMap.values().stream().findFirst();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(int id) throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.find.byId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Integer, User> userMap = extractUsersFromResultSet(rs);
            return userMap.values().stream().findFirst();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserBasicDto> findByIdAsUserBasicDto(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.find.byId.asBasicDto"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserBasicDto userBasicDto =
                        new UserBasicDto(rs.getInt(1), rs.getString(2),
                                rs.getString(3), rs.getString(4),
                                rs.getBoolean(5), rs.getBoolean(6));
                return Optional.of(userBasicDto);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_USER_BASIC_DTO, e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.findAll"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            Map<Integer, User> userMap = extractUsersFromResultSet(rs);
            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAllPageable(int pageNumber) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.findAll.pageable"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pageSize);
            ps.setLong(2,  pageSize * pageNumber);
            ResultSet rs = ps.executeQuery();
            Map<Integer, User> userMap = extractUsersFromResultSet(rs);
            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_USER_PAGE, e);
        }
    }

    @Override
    public long getNumberOfRecords() throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.count.rows"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_USER_RECORDS, e);
        }
    }

    @Override
    public void changeRoles(int userId, Set<Role> newRoles) throws DaoException {
        try (PreparedStatement deletePs =
                     connection.prepareStatement(rb.getString("query.role.delete.byUserId"), Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            deletePs.setInt(1, userId);
            deletePs.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_DELETE_USER_ROLE, e);
        }
        try (PreparedStatement createPs =
                     connection.prepareStatement(rb.getString("query.role.create"), Statement.RETURN_GENERATED_KEYS)) {
            for (Role curRole : newRoles) {
                createPs.setInt(1, userId);
                createPs.setInt(2,curRole.ordinal() + 1);
                createPs.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(ExceptionMessages.CANT_CREATE_USER_ROLE, sqlException);
            }
            throw new DaoException(ExceptionMessages.CANT_CREATE_USER_ROLE, e);
        }
    }

    @Override
    public void banUser(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.ban"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_BAN_USER, e);
        }
    }

    @Override
    public void unbanUser(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.unban"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UNBAN_USER, e);
        }
    }

    @Override
    public void setOrderOnCheckTrue(int userId) throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.update.orderOnCheck.true"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SET_ORDER_ON_CHECK_FLAG, e);
        }
    }

    @Override
    public void setOrderOnCheckFalse(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.update.orderOnCheck.false"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SET_ORDER_ON_CHECK_FLAG, e);
        }
    }

    @Override
    public void setHasBlockedAccountTrue(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.update.hasBlockedAccount.true"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SET_HAS_BLOCKED_ACCOUNT_FLAG, e);
        }
    }

    @Override
    public void setHasBlockedAccountFalse(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.update.hasBlockedAccount.false"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SET_HAS_BLOCKED_ACCOUNT_FLAG, e);
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) throws DaoException{
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.delete.byId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_DELETE_USER, e);
        }
    }

    private void fillUserStatement(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setBoolean(4, user.isActive());
        ps.setBoolean(5, user.isHasOrderOnCheck());
        ps.setBoolean(6, user.isHasBlockedAccount());
    }

    private Map<Integer, User> extractUsersFromResultSet(ResultSet rs) throws DaoException {

        UserMapper userMapper = new UserMapper();
        Map<Integer, User> userMap = new LinkedHashMap<>();
        List<User> usersWithNoRoles = new ArrayList<>();

        User curUser;
        try {
            while (rs.next()) {
                curUser = userMapper.extractFromResultSet(rs);
                usersWithNoRoles.add(curUser);
            }

            int roleId;
            for (User user : usersWithNoRoles) {
                Set<Role> roles = new HashSet<>();
                PreparedStatement rolePs = connection.prepareStatement(rb.getString("query.user_has_role.findByAccountId"),
                        Statement.RETURN_GENERATED_KEYS);
                rolePs.setInt(1, user.getId());
                ResultSet roleRs = rolePs.executeQuery();
                while (roleRs.next()) {
                    roleId = roleRs.getInt(rb.getString("field.user_has_role.roleId"));
                    try {
                        roles.add(Role.getRoleById(roleId - 1));
                        user.setRoles(roles);
                        userMap.put(user.getId(), user);
                    } catch (NoSuchElementException e) {
                       throw new DaoException(e);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return userMap;
    }

}
