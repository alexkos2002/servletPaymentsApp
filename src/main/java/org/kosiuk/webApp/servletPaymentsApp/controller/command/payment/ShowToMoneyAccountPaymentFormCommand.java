package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;

import javax.servlet.http.HttpServletRequest;

public class ShowToMoneyAccountPaymentFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        int senderMoneyAccId = Integer.parseInt(request.getParameter("senderMoneyAccId"));

        request.setAttribute("senderMoneyAccId", senderMoneyAccId);

        return Path.TO_MONEY_ACCOUNT_PAYMENT_FORM_JSP_PATH;
    }
}
