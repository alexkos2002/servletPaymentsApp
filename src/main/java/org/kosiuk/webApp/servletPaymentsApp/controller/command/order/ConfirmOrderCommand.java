package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.CompositeValidator;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.NotBlankValidator;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.Result;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.SizeValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ConfirmOrderCommand implements Command {

    private final CreditCardOrderService orderService;
    private ResourceBundle rb;

    public ConfirmOrderCommand(CreditCardOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int ownerId = Integer.parseInt(request.getParameter("userId"));
        String ownerName = request.getParameter("userName");
        String message = request.getParameter("message");
        long creditCardNumber = Long.parseLong(request.getParameter("creditCardNum"));
        long moneyAccountNumber = Long.parseLong(request.getParameter("moneyAccountNum"));
        String moneyAccountName = request.getParameter("moneyAccountName");
        int cvv = Integer.parseInt(request.getParameter("CVV"));
        String paymentSystemName = request.getParameter("paymentSystem");
        String expireDateString = request.getParameter("expireDateString");
        PaymentSystem paymentSystem = PaymentSystem.getPaymentSystemByName(request.getParameter("paymentSystem"));

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(moneyAccountName);

        if (!validationErrorsMap.isEmpty()) {
            CreditCardOrderWithUserDto orderConfDto = new CreditCardOrderWithUserDto(orderId, OrderStatus.ON_CHECK, message,
                    paymentSystem, ownerId, ownerName);
            request.setAttribute("orderConfDto", orderConfDto);
            request.setAttribute("moneyAccountNum", moneyAccountNumber);
            CreditCardConfirmationDto cardConfDto = new CreditCardConfirmationDto(creditCardNumber, cvv, expireDateString,
                    paymentSystem);
            request.setAttribute("cardConfDto", cardConfDto);
            request.setAttribute("errors", validationErrorsMap);
            return Path.ORDER_CONFIRMATION_FORM_JSP_PATH;
        }

        orderService.confirmCreditCardOrder(orderId, ownerId, creditCardNumber, cvv, expireDateString, moneyAccountNumber,
                moneyAccountName, paymentSystem);

        return Path.REDIRECT + Path.SHOW_ALL_ORDERS_PATH;
    }

    private Map<String, String[]> getValidationErrorsMap(String moneyAccountName) {

        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> monAccNameValidator = new CompositeValidator<>(
                new NotBlankValidator(rb.getString("validation.moneyAccount.name.notBlank")),
                new SizeValidator(0, 45, rb.getString("validation.moneyAccount.name.size"))
        );
        Result result = monAccNameValidator.validate(moneyAccountName);
        if (!result.isValid()) {
            validationErrorsMap.put("nameErrors", result.getMessage().split("\n"));
        }

        return validationErrorsMap;
    }
}
