package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionMapper implements ObjectMapper<Transaction>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public Transaction extractFromResultSet(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setMovedSumInt(rs.getLong(rb.getString("field.transaction.movedSumInt")));
        transaction.setMovedSumDec(rs.getInt(rb.getString("field.transaction.movedSumDec")));
        transaction.setReceiverMoneyAccountId(rs.getInt(rb.getString("field.transaction.receiverMoneyAccountId")));
        transaction.setPaymentNumber(rs.getLong(rb.getString("field.transaction.paymentNumber")));
        transaction.setSenderMoneyAccountId(rs.getInt(rb.getString("field.transaction.senderMoneyAccountId")));
        return transaction;
    }
}
