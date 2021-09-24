package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RejectOrderCommand implements Command {

    private final CreditCardOrderService orderService;
    private ResourceBundle rb;

    public RejectOrderCommand(CreditCardOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        int id = Integer.parseInt(request.getParameter("id"));
        String rejectMessage = request.getParameter("rejectMessage");
        int ownerId = Integer.parseInt(request.getParameter("userId"));

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(rejectMessage);

        if(!validationErrorsMap.isEmpty()) {
            request.setAttribute("message", rejectMessage);
            request.setAttribute("errors", validationErrorsMap);
            return Path.ORDER_REJECTION_FORM_JSP_PATH;
        }

        orderService.rejectCreditCardOrder(id, rejectMessage, ownerId);

        return Path.REDIRECT + Path.SHOW_ALL_ORDERS_PATH;
    }

    private Map<String, String[]> getValidationErrorsMap(String rejectMessage) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> messageValidator = new CompositeValidator<>(
                new SizeValidator(0, 100, rb.getString("validation.order.rejectionMessage.size"))
        );
        Result result = messageValidator.validate(rejectMessage);
        if (!result.isValid()) {
            validationErrorsMap.put("messageErrors", result.getMessage().split("\n"));
        }

        return validationErrorsMap;
    }
}
