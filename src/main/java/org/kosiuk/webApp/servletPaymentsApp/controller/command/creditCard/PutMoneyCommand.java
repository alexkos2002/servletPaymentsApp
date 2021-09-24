package org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PutMoneyCommand implements Command {

    private final CreditCardService creditCardService;
    private ResourceBundle rb;

    public PutMoneyCommand(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        String sumString = request.getParameter("sumString");
        int cardId = Integer.parseInt(request.getParameter("cardId"));

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(sumString);

        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("sumString", sumString);
            request.setAttribute("cardId", cardId);
            request.setAttribute("errors", validationErrorsMap);
            return Path.MONEY_PUT_FORM_JSP_PATH;
        }

        int userId = creditCardService.putMoneyWithUserIdReturn(cardId, sumString);

        return Path.REDIRECT + Path.SHOW_ALL_USERS_CREDIT_CARDS_PATH + "?userId=" + userId;
    }

    private Map<String, String[]> getValidationErrorsMap(String sumString) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> sumStringValidator = new CompositeValidator<>(
                new IsMoneySumValidator(rb.getString("validation.payment.payedSum.isMoneySum")),
                new NotBlankValidator(rb.getString("validation.payment.putSum.notBlank"))
        );

        Result result = sumStringValidator.validate(sumString);
        if (!result.isValid()) {
            validationErrorsMap.put("sumErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
