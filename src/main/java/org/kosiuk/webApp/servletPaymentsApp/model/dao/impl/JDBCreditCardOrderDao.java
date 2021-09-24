package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.CreditCardOrderDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.CreditCardOrderMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

import java.sql.*;
import java.util.*;

public class JDBCreditCardOrderDao implements CreditCardOrderDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.valueOf(rb.getString("order.page.size"));

    JDBCreditCardOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<CreditCardOrder> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<CreditCardOrder> findAllByOwnerId(int ownerId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.findAll.byUserId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            Map<Integer, CreditCardOrder> orderMap = extractOrdersFromResultSet(rs);
            return new ArrayList<>(orderMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_ORDERS_BY_OWNER_ID, e);
        }
    }

    @Override
    public List<CreditCardOrderWithUserDto> findAllWithUserPage(int pageNumber) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.findAll.withUser"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, pageNumber * pageSize);
            ResultSet rs = ps.executeQuery();
            List<CreditCardOrderWithUserDto> orderDtos = new ArrayList<>();
            CreditCardOrderWithUserDto curOrderDto;
            while (rs.next()) {
                curOrderDto = new CreditCardOrderWithUserDto(rs.getInt(1), OrderStatus.getStatusByName(rs.getString(2)),
                        rs.getString(3), PaymentSystem.getPaymentSystemByName(rs.getString(4)), rs.getInt(5),
                        rs.getString(6));
                orderDtos.add(curOrderDto);
            }
            return orderDtos;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_ORDERS_WITH_USER, e);
        }
    }

    @Override
    public void updateOrderStatus(OrderStatus orderStatus, int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.update.status"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, orderStatus.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_ORDER_STATUS);
        }
    }

    @Override
    public long getNumberOfRecords() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.count.rows"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_ORDER_RECORDS, e);
        }
    }

    @Override
    public void reject(int id, String rejectionMessage) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.select.message.byId"), Statement.RETURN_GENERATED_KEYS)) {
            String message = "";
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = rs.getString(1);
            }
            try (PreparedStatement updatePs =
                         connection.prepareStatement(rb.getString("query.order.update.message.and.status"), Statement.RETURN_GENERATED_KEYS)) {
                updatePs.setString(1, message + "\nYOUR ORDER WAS REJECTED." + "\nREASON: " + rejectionMessage);
                updatePs.setString(2, OrderStatus.REJECTED.name());
                updatePs.setInt(3, id);
                updatePs.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(ExceptionMessages.CANT_REJECT_ORDER, e);
            }
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_REJECT_ORDER, e);
        }
    }

    @Override
    public void create(CreditCardOrder order) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.order.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillOrderStatement(order, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_CREATE_ORDER, e);
        }
    }

    @Override
    public void update(CreditCardOrder entity) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }

    private void fillOrderStatement(CreditCardOrder order, PreparedStatement ps) throws SQLException {
        ps.setString(1, order.getDesPaymentSystem().name());
        String message = order.getMessage();
        if (message == null) {
            ps.setString(2, "");
        } else {
            ps.setString(2, message);
        }
        ps.setString(3, order.getStatus().name());
        ps.setInt(4, order.getUserId());
    }

    private Map<Integer, CreditCardOrder> extractOrdersFromResultSet(ResultSet rs) throws DaoException {
        CreditCardOrderMapper orderMapper = new CreditCardOrderMapper();
        Map<Integer, CreditCardOrder> orderMap = new LinkedHashMap<>();

        CreditCardOrder curOrder;
        try {
            while (rs.next()) {
                curOrder = orderMapper.extractFromResultSet(rs);
                orderMap.put(curOrder.getId(), curOrder);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return orderMap;

    }
}
