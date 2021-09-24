package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;

import javax.servlet.http.HttpServletRequest;

public class OrderRejectionFormCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("message", request.getParameter("message"));
        request.setAttribute("userId", request.getParameter("userId"));

        return Path.ORDER_REJECTION_FORM_JSP_PATH;
    }
}
