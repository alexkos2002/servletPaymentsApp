package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;

public class CancelPaymentCommand implements Command {

    private final PaymentService paymentService;

    public CancelPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int senderMoneyAccountId = Integer.parseInt(request.getParameter("senderMoneyAccId"));
        long paymentNumber = Long.parseLong(request.getParameter("paymentNumber"));
        String totalString = request.getParameter("totalString");

        paymentService.cancelPayment(senderMoneyAccountId, paymentNumber, totalString);

        return Path.REDIRECT + Path.MAIN_PAGE_PATH;
    }
}
