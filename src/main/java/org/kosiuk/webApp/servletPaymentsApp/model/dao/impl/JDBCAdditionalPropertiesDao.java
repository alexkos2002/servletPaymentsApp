package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.AdditionalPropertiesDao;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCAdditionalPropertiesDao implements AdditionalPropertiesDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");

    JDBCAdditionalPropertiesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getCurVisaCardNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.get.curVisaCardNum"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_CUR_CARD_NUM, e);
        }
    }

    @Override
    public int incCurVisaCardNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.inc.curVisaCardNum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_INC_CUR_CARD_NUM, e);
        }
    }

    @Override
    public int getCurMasterCardNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.get.curMasterCardNum"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_CUR_CARD_NUM, e);
        }
    }

    @Override
    public int incCurMasterCardNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.inc.curMasterCardNum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_INC_CUR_CARD_NUM, e);
        }
    }

    @Override
    public long getCurMoneyAccountNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.get.curMoneyAccountNum"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_CUR_MONEY_ACCOUNT_NUM, e);
        }
    }

    @Override
    public long incCurMoneyAccountNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.inc.curMoneyAccountNum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_INC_CUR_MONEY_ACCOUNT_NUM, e);
        }
    }

    @Override
    public long getCurPaymentNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.get.curPaymentNum"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_CUR_PAYMENT_NUM, e);
        }
    }

    @Override
    public long incCurPaymentNum() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.addprop.inc.curPaymentNum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_INC_CUR_PAYMENT_NUM, e);
        }
    }
}
