package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllUsersMoneyAccountsPageCommand implements Command {

    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("moneyAccount.page.size"));

    private final MoneyAccountService moneyAccountService;

    public ShowAllUsersMoneyAccountsPageCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int userId = Integer.parseInt(request.getParameter("userId"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNum"));
        String sortParameter = request.getParameter("sortParam");
        long totalItems = moneyAccountService.getNumberOfRecordsByUserId(userId);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        List<MoneyAccount> moneyAccounts = moneyAccountService.getAllUsersMoneyAccountPage(userId, pageNumber - 1, sortParameter);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("moneyAccounts", moneyAccounts);
        request.setAttribute("curPage", pageNumber);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("userId", userId);
        request.setAttribute("sortParameter", sortParameter);

        return Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_JSP_PATH;
    }
}
