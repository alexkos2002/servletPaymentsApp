package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;

import javax.servlet.http.HttpServletRequest;

public class CancelOrderConfirmationCommand implements Command {

    final CreditCardOrderService orderService;

    public CancelOrderConfirmationCommand(CreditCardOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        orderService.cancelCreditCardOrderConfirmation();
        return Path.REDIRECT + Path.SHOW_ALL_ORDERS_PATH;
    }
}
