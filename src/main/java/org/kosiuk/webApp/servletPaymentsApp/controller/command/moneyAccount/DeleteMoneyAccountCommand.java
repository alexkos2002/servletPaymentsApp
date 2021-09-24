package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;

public class DeleteMoneyAccountCommand implements Command {

    private final MoneyAccountService moneyAccountService;

    public DeleteMoneyAccountCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        moneyAccountService.delete(id, userId);

        return Path.REDIRECT + Path.SHOW_ALL_MONEY_ACCOUNTS_PATH;
    }
}
