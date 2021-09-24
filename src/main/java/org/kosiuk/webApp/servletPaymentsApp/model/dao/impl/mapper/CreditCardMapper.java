package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreditCardMapper implements ObjectMapper<CreditCard>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public CreditCard extractFromResultSet(ResultSet rs) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(rs.getInt(rb.getString("field.creditCard.id")));
        creditCard.setNumber(rs.getLong(rb.getString("field.creditCard.number")));
        creditCard.setCvv(rs.getInt(rb.getString("field.creditCard.cvv")));
        creditCard.setExpireDateString(rs.getDate(rb.getString("field.creditCard.expireDate")).toString());
        creditCard.setPaymentSystem(PaymentSystem.getPaymentSystemByName(rs.getString(rb.getString("field.creditCard.paymentSystem"))));
        creditCard.setAvailableSumInt(rs.getLong(rb.getString("field.creditCard.availableSumInt")));
        creditCard.setAvailableSumDec(rs.getInt(rb.getString("field.creditCard.availableSumDec")));
        creditCard.setAccountId(rs.getInt(rb.getString("field.creditCard.accountId")));
        creditCard.setMoneyAccountId(rs.getInt(rb.getString("field.creditCard.moneyAccountId")));
        return creditCard;
    }
}
