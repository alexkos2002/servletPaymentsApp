package org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;

import javax.servlet.http.HttpServletRequest;

public class PutMoneyFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        String cardId = request.getParameter("cardId");

        request.setAttribute("cardId", cardId);
        return Path.MONEY_PUT_FORM_JSP_PATH;
    }
}
