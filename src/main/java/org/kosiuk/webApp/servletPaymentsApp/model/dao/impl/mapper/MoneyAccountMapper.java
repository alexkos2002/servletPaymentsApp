package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccountActStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MoneyAccountMapper implements ObjectMapper<MoneyAccount>{

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    @Override
    public MoneyAccount extractFromResultSet(ResultSet rs) throws SQLException {
        MoneyAccount moneyAccount = new MoneyAccount();
        moneyAccount.setId(rs.getInt(rb.getString("field.moneyAccount.id")));
        moneyAccount.setNumber(rs.getLong(rb.getString("field.moneyAccount.number")));
        moneyAccount.setName(rs.getString(rb.getString("field.moneyAccount.name")));
        moneyAccount.setSumInt(rs.getLong(rb.getString("field.moneyAccount.sumInt")));
        moneyAccount.setSumDec(rs.getInt(rb.getString("field.moneyAccount.sumDec")));
        moneyAccount.setActive(MoneyAccountActStatus.getActStatusByName(rs.getString(rb.getString("field.moneyAccount.active"))));
        moneyAccount.setCurSumAvailableDec(rs.getInt(rb.getString("field.moneyAccount.curSumAvailableDec")));
        moneyAccount.setCurSumAvailableInt(rs.getLong(rb.getString("field.moneyAccount.curSumAvailableInt")));
        moneyAccount.setCanBeLocked(!rs.getBoolean(rb.getString("field.user.hasBlockedAccount")));
        return moneyAccount;
    }

}
