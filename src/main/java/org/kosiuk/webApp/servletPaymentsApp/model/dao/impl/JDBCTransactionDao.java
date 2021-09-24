package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.TransactionDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.TransactionMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JDBCTransactionDao implements TransactionDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    public JDBCTransactionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Transaction> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Transaction> findAllByReceiverMoneyAccountId(int receiverMoneyAccountId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.transaction.find.ByRecMonAccId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receiverMoneyAccountId);
            ResultSet rs = ps.executeQuery();
            return extractTransactionsFromResultSet(rs);
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_TRANSACTIONS_BY_REC_MON_ACC_ID, e);
        }
    }

    @Override
    public void create(Transaction transaction) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.transaction.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillTransactionStatement(transaction, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_CREATE_TRANSACTION, e);
        }
    }

    @Override
    public void update(Transaction entity) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }

    @Override
    public long getNumberOfRecordsByReceiverMonAccId(int receiverMoneyAccountId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.transaction.count.rows.byRecMonAccId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receiverMoneyAccountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_TRANSACTION_RECORDS_BY_REC_MON_ACC_ID, e);
        }
    }

    private List<Transaction> extractTransactionsFromResultSet(ResultSet rs) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();
        TransactionMapper transactionMapper = new TransactionMapper();
        Transaction curTransaction;
        while (rs.next()) {
            curTransaction = transactionMapper.extractFromResultSet(rs);
            transactionList.add(curTransaction);
        }

        return transactionList;
    }

    private void fillTransactionStatement(Transaction transaction, PreparedStatement ps) throws SQLException {
        ps.setLong(1, transaction.getMovedSumInt());
        ps.setInt(2, transaction.getMovedSumDec());
        ps.setInt(3, transaction.getReceiverMoneyAccountId());
        ps.setLong(4, transaction.getPaymentNumber());
        ps.setInt(5, transaction.getSenderMoneyAccountId());
    }
}
