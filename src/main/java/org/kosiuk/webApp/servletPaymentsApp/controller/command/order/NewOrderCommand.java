package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserBasicDto;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class NewOrderCommand implements Command {

    private final UserService userService;
    private final CreditCardOrderService orderService;
    private ResourceBundle rb;

    public NewOrderCommand(UserService userService, CreditCardOrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        int userId = Integer.parseInt(request.getParameter("userId"));
        String message = request.getParameter("message");

        String isVisa = request.getParameter("VISA");
        String isMasterCard = request.getParameter("MASTERCARD");

        PaymentSystem paymentSystem;

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(message, List.of(isVisa != null, isMasterCard != null));

        if (!validationErrorsMap.isEmpty()) {
            Optional<UserBasicDto> userBasicDtoOptional = userService.getUserByIdAsBasicDto(userId);
            UserBasicDto userBasicDto = userBasicDtoOptional.isEmpty() ? new UserBasicDto() : userBasicDtoOptional.get();
            request.setAttribute("userBasicDto", userBasicDto);
            List<CreditCardOrder> creditCardOrders = orderService.getAllUsersCreditCardOrders(userId);
            request.setAttribute("creditCardOrders", creditCardOrders);
            request.setAttribute("errors", validationErrorsMap);
            return Path.PERSONAL_ROOM_JSP_PATH;
        }

        if (isVisa != null) {
            paymentSystem = PaymentSystem.VISA;
        } else {
            paymentSystem = PaymentSystem.MASTERCARD;
        }

        orderService.create(userId, paymentSystem, message);

        return Path.REDIRECT + Path.SHOW_PERSONAL_ROOM_PATH + "?userId=" + userId;
    }

    private Map<String, String[]> getValidationErrorsMap(String message, List<Boolean> paymentSystemFlags) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> messageValidator = new CompositeValidator<>(
                new SizeValidator(0, 100, rb.getString("validation.order.message.size"))
        );
        CompositeValidator<List<Boolean>> paymentSystemValidator = new CompositeValidator<>(
                new NotAllFlagsFalseValidator(rb.getString("validation.order.noPaymentSystem")),
                new NotMoreThanOneFlagTrueValidator(rb.getString("validation.order.notDistinctPaymentSystem"))
        );
        Result result = messageValidator.validate(message);
        if (!result.isValid()) {
            validationErrorsMap.put("messageErrors", result.getMessage().split("\n"));
        }
        result = paymentSystemValidator.validate(paymentSystemFlags);
        if (!result.isValid()) {
            validationErrorsMap.put("paymentSystemErrors", result.getMessage().split("\n"));
        }

        return validationErrorsMap;
    }
}
