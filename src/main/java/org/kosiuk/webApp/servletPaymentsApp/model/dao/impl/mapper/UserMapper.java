package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserMapper implements ObjectMapper<User>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(rb.getString("field.user.id")));
        user.setUsername(rs.getString(rb.getString("field.user.username")));
        user.setEmail(rs.getString(rb.getString("field.user.email")));
        user.setPassword(rs.getString(rb.getString("field.user.password")));
        user.setActive(rs.getBoolean(rb.getString("field.user.active")));
        user.setHasOrderOnCheck(rs.getBoolean(rb.getString("field.user.hasOrderOnCheck")));
        user.setHasBlockedAccount(rs.getBoolean(rb.getString("field.user.hasBlockedAccount")));
        System.err.println("orderMapper");
        return user;
    }
}
