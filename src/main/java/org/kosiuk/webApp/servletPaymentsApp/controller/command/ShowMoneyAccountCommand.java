package org.kosiuk.webApp.servletPaymentsApp.controller.command;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.MoneyAccount;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ShowMoneyAccountCommand implements Command {

    private final MoneyAccountService moneyAccountService;

    public ShowMoneyAccountCommand(MoneyAccountService moneyAccountService) {
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        String userId = request.getParameter("userId");
        Optional<MoneyAccount> moneyAccount = moneyAccountService.getMoneyAccountById(id);

        if (!moneyAccount.isEmpty()) {
            request.setAttribute("moneyAccount", moneyAccount.get());
        }
        request.setAttribute("userId", userId);

        return Path.SHOW_MONEY_ACCOUNT_JSP_PATH;
    }
}
