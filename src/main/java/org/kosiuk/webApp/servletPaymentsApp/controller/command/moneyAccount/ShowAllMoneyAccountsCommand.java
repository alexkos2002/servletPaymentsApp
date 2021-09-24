package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.MoneyAccountWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllMoneyAccountsCommand implements Command {

    private final MoneyAccountService moneyAccountService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("moneyAccount.page.size"));

    public ShowAllMoneyAccountsCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        long totalItems = moneyAccountService.getNumberOfRecords();
        List<MoneyAccountWithUserDto> moneyAccounts = moneyAccountService.getAllMoneyAccountPage(0, "none");
        request.setAttribute("totalItems", totalItems);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", 1);
        request.setAttribute("moneyAccounts", moneyAccounts);

        request.setAttribute("sortParameter", "no");

        return Path.SHOW_ALL_MONEY_ACCOUNTS_JSP_PATH;
    }
}
