package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentMapper implements ObjectMapper<Payment>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public Payment extractFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setNumber(rs.getLong(rb.getString("field.payment.number")));
        payment.setPayedSumInt(rs.getLong(rb.getString("field.payment.payedSumInt")));
        payment.setPayedSumDec(rs.getInt(rb.getString("field.payment.payedSumDec")));
        payment.setComissionInt(rs.getLong(rb.getString("field.payment.comissionInt")));
        payment.setComissionDec(rs.getInt(rb.getString("field.payment.comissionDec")));
        payment.setAssignment(rs.getString(rb.getString("field.payment.assignment")));
        payment.setTimeString((rs.getTimestamp(rb.getString("field.payment.time"))).toString().
                replaceFirst("\\.[\\d]*", ""));
        payment.setStatus(PaymentStatus.getStatusByName(rs.getString(rb.getString("field.payment.status"))));
        payment.setSenderMoneyAccountId(rs.getInt(rb.getString("field.payment.senderMoneyAccId")));
        return payment;
    }
}
