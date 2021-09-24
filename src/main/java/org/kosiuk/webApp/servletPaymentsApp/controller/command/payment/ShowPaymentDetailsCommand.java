package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentDetailsDto;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ShowPaymentDetailsCommand implements Command {

    private final PaymentService paymentService;

    public ShowPaymentDetailsCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        long paymentNumber = Long.parseLong(request.getParameter("paymentNum"));

        Optional<PaymentDetailsDto> paymentDetailsDto = paymentService.getPaymentDetails(paymentNumber);
        if(!paymentDetailsDto.isEmpty()) {
            request.setAttribute("paymentDetails", paymentDetailsDto.get());
        }

        return Path.PAYMENT_DETAILS_JSP_PATH;
    }
}
