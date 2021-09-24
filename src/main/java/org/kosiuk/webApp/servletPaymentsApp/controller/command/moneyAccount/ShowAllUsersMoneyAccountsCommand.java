package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllUsersMoneyAccountsCommand implements Command {

    private final MoneyAccountService moneyAccountService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("moneyAccount.page.size"));

    public ShowAllUsersMoneyAccountsCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int userId = Integer.parseInt(request.getParameter("userId"));
        long totalItems = moneyAccountService.getNumberOfRecordsByUserId(userId);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        List<MoneyAccount> moneyAccounts = moneyAccountService.getAllUsersMoneyAccountPage(userId, 0, "none");
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("moneyAccounts", moneyAccounts);
        request.setAttribute("curPage", 1);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("userId", userId);
        request.setAttribute("sortParameter", "none");
        return Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_JSP_PATH;
    }
}
