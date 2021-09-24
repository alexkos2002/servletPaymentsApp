package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentDetailsDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.PaymentDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.MoneyAccountMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.PaymentMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentStatus;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class JDBCPaymentDao implements PaymentDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("payment.page.size"));
    private final int halfPageSize = Integer.parseInt(rb.getString("payment.page.halfSize"));

    JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Payment> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> findByNumber(long number) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.find.ByNumber"),
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, number);
            ResultSet rs = ps.executeQuery();
            Map<Long, Payment> paymentMap = extractPaymentsFromResultSet(rs);
            return paymentMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_PAYMENT_BY_NUMBER, e);
        }
    }

    @Override
    public List<Payment> findAllSortedPageable(int pageNumber, String sortParameter) throws DaoException {
        String query;
        String numberSortParam = rb.getString("field.payment.number");
        String timeAscSortParam = rb.getString("sortParam.payment.time.asc");
        String timeDescSortParam = rb.getString("sortParam.payment.time.desc");

        if (sortParameter.equals(numberSortParam)) {
            query = rb.getString("query.payment.findAll.pageable.sortBy.number");
        } else if (sortParameter.equals(timeAscSortParam)) {
            query = rb.getString("query.payment.findAll.pageable.sortBy.time.asc");
        } else if (sortParameter.equals(timeDescSortParam)) {
            query = rb.getString("query.payment.findAll.pageable.sortBy.time.desc");
        } else {
            query = rb.getString("query.payment.findAll.pageable");
        }

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, pageNumber * pageSize);
            ResultSet rs = ps.executeQuery();
            Map<Long, Payment> paymentMap = extractPaymentsFromResultSet(rs);
            return new ArrayList<>(paymentMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_PAYMENT_PAGE, e);
        }
    }

    @Override
    public List<Payment> findAllBySenderMonAccIdSortedPageable(int moneyAccountId, int pageNumber, String sortParameter) throws DaoException {
        String query;
        String numberSortParam = rb.getString("field.payment.number");
        String timeAscSortParam = rb.getString("sortParam.payment.time.asc");
        String timeDescSortParam = rb.getString("sortParam.payment.time.desc");

        if (sortParameter.equals(numberSortParam)) {
            query = rb.getString("query.payment.find.BySenderMoneyAccountId.pageable.sortBy.number");
        } else if (sortParameter.equals(timeAscSortParam)) {
            query = rb.getString("query.payment.find.BySenderMoneyAccountId.pageable.sortBy.time.asc");
        } else if (sortParameter.equals(timeDescSortParam)) {
            query = rb.getString("query.payment.find.BySenderMoneyAccountId.pageable.sortBy.time.desc");
        } else {
            query = rb.getString("query.payment.find.BySenderMoneyAccountId.pageable");
        }

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, moneyAccountId);
            ps.setInt(2, halfPageSize);
            ps.setInt(3, pageNumber * halfPageSize);
            ResultSet rs = ps.executeQuery();
            Map<Long, Payment> paymentMap = extractPaymentsFromResultSet(rs);
            return new ArrayList<>(paymentMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_SENT_ON_ACCOUNT_PAYMENT_PAGE, e);
        }
    }

    @Override
    public Optional<PaymentDetailsDto> findPaymentDetails(long paymentNumber) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.find.paymentDetails"),
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, paymentNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new PaymentDetailsDto(rs.getLong(1), rs.getLong(2), rs.getInt(3), rs.getLong(4), rs.getInt(5),
                        rs.getString(6), rs.getTimestamp(7).toString().replaceFirst("\\.[\\d]*", ""),
                        PaymentStatus.getStatusByName(rs.getString(8)), rs.getLong(9), rs.getInt(10), rs.getString(11),
                        rs.getString(12)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_PAYMENT_DETAILS, e);
        }
    }

    @Override
    public void create(Payment payment) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillPaymentStatement(payment, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_CREATE_PAYMENT, e);
        }
    }

    @Override
    public void update(Payment entity) throws DaoException {

    }

    @Override
    public void updateStatus(long number, PaymentStatus status) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.update.status"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, status.name());
            ps.setLong(2, number);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_PAYMENT_STATUS, e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {

    }

    @Override
    public void deleteByNumber(long number) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.delete.byNumber"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, number);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_DELETE_PAYMENT, e);
        }
    }

    @Override
    public long getNumberOfRecords() throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.count.rows"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_PAYMENT_RECORDS, e);
        }
    }

    @Override
    public long getNumberOfRecordsBySenderMonAccId(int moneyAccountId) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.payment.count.rows.bySenderMonAccId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, moneyAccountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_PAYMENT_RECORDS_BY_SENDER_MON_ACC_ID, e);
        }
    }

    private Map<Long, Payment> extractPaymentsFromResultSet(ResultSet rs) throws DaoException {
        PaymentMapper paymentMapper = new PaymentMapper();
        Map<Long, Payment> paymentMap = new LinkedHashMap<>();
        Payment curPayment;
        try {
            while (rs.next()) {
                curPayment = paymentMapper.extractFromResultSet(rs);
                paymentMap.put(curPayment.getNumber(), curPayment);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return paymentMap;
    }

    private void fillPaymentStatement(Payment payment, PreparedStatement ps) throws SQLException {
        ps.setLong(1, payment.getNumber());
        ps.setLong(2, payment.getPayedSumInt());
        ps.setInt(3, payment.getPayedSumDec());
        ps.setLong(4, payment.getComissionInt());
        ps.setInt(5, payment.getComissionDec());
        ps.setString(6, payment.getAssignment());
        ps.setString(7, payment.getStatus().name());
        ps.setInt(8, payment.getSenderMoneyAccountId());
    }
}
