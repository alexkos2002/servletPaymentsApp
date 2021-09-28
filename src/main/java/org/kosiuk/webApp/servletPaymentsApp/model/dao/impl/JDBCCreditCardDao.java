package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import java.sql.*;
import java.util.*;

import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.CreditCardDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.CreditCardMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;

public class JDBCCreditCardDao implements CreditCardDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.valueOf(rb.getString("creditCard.page.size"));

    JDBCCreditCardDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(CreditCard creditCard) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.creditCard.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillCreditCardStatement(creditCard, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_CREATE_CREDIT_CARD);
        }
    }

    @Override
    public long getNumberOfRecordsByUserId(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.count.rows.byUserId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_CREDIT_CARD_RECORDS, e);
        }
    }

    @Override
    public List<CreditCard> findAllByUserIdPageable(int userId, int pageNumber) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.findAll.byUserId.pageable"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, pageSize);
            ps.setInt(3, pageNumber * pageSize);
            ResultSet rs = ps.executeQuery();
            Map<Integer, CreditCard> creditCardMap = extractCreditCardsFromResultSet(rs);
            return new ArrayList<>(creditCardMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_GET_USERS_CARD_PAGE, e);
        }
    }

    @Override
    public Optional<CreditCard> findCreditCardByNumber(long number) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.creditCard.find.byNumber"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, number);
            CreditCardMapper creditCardMapper = new CreditCardMapper();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(creditCardMapper.extractFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_CREDIT_CARD_BY_NUMBER, e);
        }
    }

    @Override
    public Optional<CreditCard> findByMoneyAccountId(int moneyAccountId) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.creditCard.find.byMoneyAccountId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, moneyAccountId);
            CreditCardMapper creditCardMapper = new CreditCardMapper();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(creditCardMapper.extractFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_CREDIT_CARD_BY_MONEY_ACC_ID, e);
        }
    }

    @Deprecated
    public void updateWithAddAvailableSum(int cardId, long availableSumInt, int availableSumDec) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.updateWithAdd.SumAvailable"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, availableSumInt);
            ps.setInt(2, availableSumDec);
            ps.setInt(3, cardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_CREDIT_CARD_SUM, e);
        }
    }

    @Override
    public void updateAvailableSum(int cardId, long availableSumInt, int availableSumDec) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query,creditCard.update.sumAvailable"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, availableSumInt);
            ps.setInt(2, availableSumDec);
            ps.setInt(3, cardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_CREDIT_CARD_SUM, e);
        }
    }

    @Override
    public int selectMoneyAccountId(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.select.moneyAccountId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_ID, e);
        }
    }

    @Override
    public int selectAccountId(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.select.accountId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_USER_ID, e);
        }
    }

    @Override
    public long selectSumAvailableInt(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.select.availableSumInt"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_CREDIT_CARD_SUM_INT, e);
        }
        return 0;
    }

    @Override
    public int selectSumAvailableDec(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.creditCard.select.availableSumDec"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_CREDIT_CARD_SUM_DEC, e);
        }
        return 0;
    }

    private Map<Integer, CreditCard> extractCreditCardsFromResultSet(ResultSet rs) throws DaoException {
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        Map<Integer, CreditCard> creditCardMap = new LinkedHashMap<>();
        CreditCard curCreditCard;
        try {
            while (rs.next()) {
                curCreditCard = creditCardMapper.extractFromResultSet(rs);
                creditCardMap.put(curCreditCard.getId(), curCreditCard);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return creditCardMap;
    }

    public void fillCreditCardStatement(CreditCard creditCard, PreparedStatement ps) throws SQLException {
        ps.setLong(1, creditCard.getNumber());
        ps.setInt(2, creditCard.getCvv());
        ps.setString(3, creditCard.getExpireDateString());
        ps.setString(4, creditCard.getPaymentSystem().name());
        ps.setLong(5, creditCard.getAvailableSumInt());
        ps.setInt(6, creditCard.getAvailableSumDec());
        ps.setInt(7, creditCard.getAccountId());
        ps.setInt(8, creditCard.getMoneyAccountId());
    }

}
