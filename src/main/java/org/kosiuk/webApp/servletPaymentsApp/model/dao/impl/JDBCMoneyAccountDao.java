package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import org.kosiuk.webApp.servletPaymentsApp.controller.dto.MoneyAccountWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.MoneyAccountDao;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper.MoneyAccountMapper;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccountActStatus;

import java.sql.*;
import java.util.*;

public class JDBCMoneyAccountDao implements MoneyAccountDao {

    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("moneyAccount.page.size"));

    JDBCMoneyAccountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<MoneyAccount> findById(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.find.byId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Integer, MoneyAccount> moneyAccountMap = extractMoneyAccountsFromResultSet(rs);
            return moneyAccountMap.values().stream().findFirst();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_MONEY_ACCOUNT_BY_ID, e);
        }
    }

    @Override
    public Optional<MoneyAccount> findByNumber(long number) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.find.byNumber"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, number);
            ResultSet rs = ps.executeQuery();
            Map<Integer, MoneyAccount> moneyAccountMap = extractMoneyAccountsFromResultSet(rs);
            return moneyAccountMap.values().stream().findFirst();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_MONEY_ACCOUNT_BY_NUMBER, e);
        }
    }

    @Override
    public void create(MoneyAccount entity) throws DaoException {

    }

    @Override
    public int createWithIdReturn(MoneyAccount moneyAccount) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillMoneyAccountStatement(moneyAccount, ps);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_CREATE_MONEY_ACCOUNT, e);
        }
    }

    @Override
    public void update(MoneyAccount entity) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.delete"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_DELETE_MONEY_ACCOUNT, e);
        }
    }

    @Override
    public void updateSum(int moneyAccId, long sumInt, int sumDec) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.update.sum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, sumInt);
            ps.setInt(2, sumDec);
            ps.setInt(3, moneyAccId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_MONEY_ACCOUNT_SUM, e);
        }
    }

    @Override
    public void updateCurSumAvailable(int moneyAccId, long availableSumInt, int availableSumDec) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.update.curSumAvailable"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, availableSumInt);
            ps.setInt(2, availableSumDec);
            ps.setInt(3, moneyAccId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_MONEY_ACCOUNT_SUM, e);
        }
    }

    @Deprecated
    public void updateWithAddSum(int moneyAccId, long sumInt, int sumDec) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.updateWithAdd.Sum"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, sumInt);
            ps.setInt(2, sumDec);
            ps.setLong(3, sumInt);
            ps.setInt(4, sumDec);
            ps.setInt(5, moneyAccId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_MONEY_ACCOUNT_SUM, e);
        }
    }

    @Override
    public List<MoneyAccount> findAllSortedPageableByUserId(int userId, int pageNumber, String sortParameter) throws DaoException {
        String query;
        String numberSortParam = rb.getString("field.moneyAccount.number");
        String nameSortParam = rb.getString("field.moneyAccount.name");
        String sumSortParam = rb.getString("sortParam.moneyAccount.sum");

        if (sortParameter.equals(numberSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.byUserId.pageable.sortBy.number");
        } else if (sortParameter.equals(nameSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.byUserId.pageable.sortBy.name");
        } else if (sortParameter.equals(sumSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.byUserId.pageable.sortBy.sum");
        } else {
            query = rb.getString("query.moneyAccount.findAll.byUserId.pageable");
        }

        try (PreparedStatement ps =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, pageSize);
            ps.setInt(3, pageNumber * pageSize);
            ResultSet rs = ps.executeQuery();
            Map<Integer, MoneyAccount> moneyAccountMap = extractMoneyAccountsFromResultSet(rs);
            System.err.println("aaaaa");
            return new ArrayList<>(moneyAccountMap.values());
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_MONEY_ACCOUNTS_PAGE, e);
        }
    }

    @Override
    public List<MoneyAccountWithUserDto> findAllWithUserSortedPageable(int pageNumber, String sortParameter) throws DaoException {
        String query;
        String numberSortParam = rb.getString("field.moneyAccount.number");
        String nameSortParam = rb.getString("field.moneyAccount.name");
        String sumSortParam = rb.getString("sortParam.moneyAccount.sum");

        if (sortParameter.equals(numberSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.withUser.pageable.sortBy.number");
        } else if (sortParameter.equals(nameSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.withUser.pageable.sortBy.name");
        } else if (sortParameter.equals(sumSortParam)) {
            query = rb.getString("query.moneyAccount.findAll.withUser.pageable.sortBy.sum");
        } else {
            query = rb.getString("query.moneyAccount.findAll.withUser.pageable");
        }

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, pageNumber * pageSize);
            ResultSet rs = ps.executeQuery();

            MoneyAccountWithUserDto curMoneyAccountDto;
            List<MoneyAccountWithUserDto> moneyAccountWithUserDtos = new ArrayList<>();

            while (rs.next()) {
                curMoneyAccountDto = new MoneyAccountWithUserDto(rs.getInt(7), rs.getString(8), rs.getInt(1), rs.getLong(2),
                        rs.getString(3), MoneyAccountActStatus.getActStatusByName(rs.getString(4)), rs.getLong(5),
                        rs.getInt(6), !rs.getBoolean(9));
                moneyAccountWithUserDtos.add(curMoneyAccountDto);
            }
            return moneyAccountWithUserDtos;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_FIND_MONEY_ACCOUNTS_PAGE, e);
        }
    }

    @Override
    public void updateActive(int id, MoneyAccountActStatus active) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.update.active"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, active.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_UPDATE_MONEY_ACCOUNT_ACTIVE, e);
        }
    }

    @Override
    public Optional<MoneyAccountActStatus> selectActive(int id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.select.active"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(MoneyAccountActStatus.getActStatusByName(rs.getString(1)));
            }
            return Optional.empty();
          } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_ACTIVE, e);
        }
    }

    @Override
    public long selectCurSumAvailableInt(int id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.select.curSumAvInt"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_CUR_SUM_INT_AV, e);
        }
    }

    @Override
    public int selectCurSumAvailableDec(int id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.select.curSumAvDec"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_CUR_SUM_DEC_AV, e);
        }
    }

    @Override
    public long selectSumInt(int id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.select.sumInt"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_SUM_INT, e);
        }
    }

    @Override
    public int selectSumDec(int id) throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.moneyAccount.select.sumDec"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_SUM_DEC, e);
        }
    }

    @Override
    public List<Integer> selectAllIds() throws DaoException {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.MoneyAccount.selectAll.ids"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            List<Integer> moneyAccountIds = new ArrayList<>();
            while (rs.next()) {
                moneyAccountIds.add(rs.getInt(1));
            }
            return moneyAccountIds;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_SELECT_MONEY_ACCOUNT_IDS, e);
        }
    }

    @Override
    public long getNumberOfRecords() throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.count.rows"), Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_MONEY_ACCOUNT_RECORDS, e);
        }
    }

    @Override
    public long getNumberOfRecordsByUserId(int userId) throws DaoException {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.moneyAccount.count.rows.byUserId"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException(ExceptionMessages.CANT_COUNT_MONEY_ACCOUNT_RECORDS_BY_USER_ID, e);
        }
    }

    private Map<Integer, MoneyAccount> extractMoneyAccountsFromResultSet(ResultSet rs) throws DaoException {
        MoneyAccountMapper moneyAccountMapper = new MoneyAccountMapper();
        Map<Integer, MoneyAccount> moneyAccountMap = new LinkedHashMap<>();
        MoneyAccount curMoneyAccount;
        try {
            while (rs.next()) {
                curMoneyAccount = moneyAccountMapper.extractFromResultSet(rs);
                moneyAccountMap.put(curMoneyAccount.getId(), curMoneyAccount);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return moneyAccountMap;
    }


    public void fillMoneyAccountStatement(MoneyAccount moneyAccount, PreparedStatement ps) throws SQLException {
        ps.setLong(1, moneyAccount.getNumber());
        ps.setString(2, moneyAccount.getName());
        ps.setString(3, moneyAccount.getActive().name());
        ps.setLong(4, moneyAccount.getSumInt());
        ps.setInt(5, moneyAccount.getSumDec());
        ps.setLong(6, moneyAccount.getCurSumAvailableInt());
        ps.setInt(7, moneyAccount.getCurSumAvailableDec());
    }
}
