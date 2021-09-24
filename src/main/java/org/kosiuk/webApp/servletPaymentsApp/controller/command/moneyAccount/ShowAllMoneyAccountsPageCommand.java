package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.MoneyAccountWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllMoneyAccountsPageCommand implements Command {

    private final MoneyAccountService moneyAccountService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("moneyAccount.page.size"));

    public ShowAllMoneyAccountsPageCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int pageNumber = Integer.parseInt(request.getParameter("pageNum"));
        String sortParameter = request.getParameter("sortParam");

        long totalItems = moneyAccountService.getNumberOfRecords();
        List<MoneyAccountWithUserDto> moneyAccounts = moneyAccountService.getAllMoneyAccountPage(pageNumber - 1, sortParameter);
        request.setAttribute("totalItems", totalItems);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", pageNumber);
        request.setAttribute("moneyAccounts", moneyAccounts);

        request.setAttribute("sortParameter", sortParameter);

        return Path.SHOW_ALL_MONEY_ACCOUNTS_JSP_PATH;
    }
}
