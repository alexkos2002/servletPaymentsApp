package org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;

public class AskToUnlockMoneyAccountCommand implements Command {

    private final MoneyAccountService moneyAccountService;

    public AskToUnlockMoneyAccountCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int id = Integer.parseInt(request.getParameter("id"));

        moneyAccountService.askToUnlock(id, userId);

        return Path.REDIRECT + Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PATH + "?userId=" + userId;
    }
}
