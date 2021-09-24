package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreditCardOrderMapper implements ObjectMapper<CreditCardOrder>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public CreditCardOrder extractFromResultSet(ResultSet rs) throws SQLException {
        CreditCardOrder order = new CreditCardOrder();
        order.setId(rs.getInt(rb.getString("field.order.id")));
        order.setDesPaymentSystem(PaymentSystem.getPaymentSystemByName(rs.getString(rb.getString("field.order.desPaymentSystem"))));
        order.setMessage(rs.getString(rb.getString("field.order.message")));
        order.setStatus(OrderStatus.getStatusByName(rs.getString(rb.getString("field.order.orderStatus"))));
        order.setUserId(rs.getInt(rb.getString("field.order.userId")));
        return order;
    }

}
