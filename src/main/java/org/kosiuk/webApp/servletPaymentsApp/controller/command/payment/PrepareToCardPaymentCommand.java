package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.*;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;
import org.kosiuk.webApp.servletPaymentsApp.util.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrepareToCardPaymentCommand implements Command {

    private final PaymentService paymentService;
    private ResourceBundle rb;
    public static final Logger logger = Logger.getLogger(PrepareToCardPaymentCommand.class);

    public PrepareToCardPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        int senderMoneyAccId = Integer.parseInt(request.getParameter("senderMoneyAccId"));
        String receiverCardNumString = request.getParameter("receiverCardNum");
        String payedSumString = request.getParameter("payedSumString");
        String assignment = request.getParameter("assignment");

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(receiverCardNumString, payedSumString, assignment);

        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("receiverCardNumber", receiverCardNumString);
            request.setAttribute("senderMoneyAccId", senderMoneyAccId);
            request.setAttribute("sum", payedSumString);
            request.setAttribute("assignment", assignment);
            request.setAttribute("errors", validationErrorsMap);
            return Path.TO_CARD_PAYMENT_FORM_JSP_PATH;
        }

        long receiverCardNum = Long.parseLong(receiverCardNumString);

        try {
            Optional<PaymentConfirmationDto> cardPaymentConfDtoOptional =
                    paymentService.prepareToCardPayment(senderMoneyAccId, receiverCardNum, payedSumString, assignment, rb);
            if (!cardPaymentConfDtoOptional.isEmpty()) {
                request.setAttribute("cardPaymentConfDto", cardPaymentConfDtoOptional.get());
            }
        } catch (NoRequisitesByNumberException e) {
            logger.warn(ExceptionMessages.NO_CREDIT_CARD_BY_NUMBER);
            request.setAttribute("paymentPrepMessage", rb.getString("verification.payment.noCard.byNumber"));
            request.setAttribute("receiverCardNumber", receiverCardNumString);
            request.setAttribute("senderMoneyAccId", senderMoneyAccId);
            request.setAttribute("sum", payedSumString);
            request.setAttribute("assignment", assignment);
            return Path.TO_CARD_PAYMENT_FORM_JSP_PATH;
        } catch (ToOwnRequisitePaymentException e) {
            logger.warn(ExceptionMessages.NO_PAYMENTS_ON_OWN_ACCOUNT);
            request.setAttribute("paymentPrepMessage", rb.getString("verification.payment.onOwnMoneyAcc"));
            request.setAttribute("receiverCardNumber", receiverCardNumString);
            request.setAttribute("senderMoneyAccId", senderMoneyAccId);
            request.setAttribute("sum", payedSumString);
            request.setAttribute("assignment", assignment);
            return Path.TO_CARD_PAYMENT_FORM_JSP_PATH;
        } catch (BlockedAccountException e) {
            logger.warn(e.getMessage());
            request.setAttribute("paymentPrepMessage", e.getMessage());
            request.setAttribute("receiverCardNumber", receiverCardNumString);
            request.setAttribute("senderMoneyAccId", senderMoneyAccId);
            request.setAttribute("sum", payedSumString);
            request.setAttribute("assignment", assignment);
            return Path.TO_CARD_PAYMENT_FORM_JSP_PATH;
        } catch (NotEnoughMoneyOnAccountException e) {
            logger.warn(ExceptionMessages.NOT_ENOUGH_MONEY_FOR_PAYMENT);
            request.setAttribute("paymentPrepMessage", rb.getString("verification.payment.notEnoughMoney"));
            request.setAttribute("notEnoughSumString", e.getPayedSumString());
            request.setAttribute("notEnoughSumComission", e.getPaymentComissionString());
            request.setAttribute("receiverCardNumber", receiverCardNumString);
            request.setAttribute("senderMoneyAccId", senderMoneyAccId);
            request.setAttribute("sum", payedSumString);
            request.setAttribute("assignment", assignment);
            return Path.TO_CARD_PAYMENT_FORM_JSP_PATH;
        }

        return Path.TO_CARD_PAYMENT_CONF_FORM_JSP_PATH;

    }

    private Map<String, String[]> getValidationErrorsMap(String cardNumString, String sumString, String assignmebt) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> cardNumStringValidator = new CompositeValidator<>(
                new SizeValidator(16, 16, rb.getString("validation.payment.cardNum.size")),
                new IsNumberValidator(rb.getString("validation.payment.cardNum.isNumber")),
                new NotBlankValidator(rb.getString("validation.payment.cardNum.notBlank"))
        );

        CompositeValidator<String> sumStringValidator = new CompositeValidator<>(
                new IsMoneySumValidator(rb.getString("validation.payment.payedSum.isMoneySum")),
                new NotBlankValidator(rb.getString("validation.payment.payedSum.notBlank"))
        );

        CompositeValidator<String> assignmentValidator = new CompositeValidator<>(
                new SizeValidator(1, 45, rb.getString("validation.payment.assignment.size")),
                new NotBlankValidator(rb.getString("validation.payment.assignment.notBlank"))
        );

        Result result = cardNumStringValidator.validate(cardNumString);
        if (!result.isValid()) {
            validationErrorsMap.put("cardNumErrors", result.getMessage().split("\n"));
        }
        result = sumStringValidator.validate(sumString);
        if (!result.isValid()) {
            validationErrorsMap.put("sumErrors", result.getMessage().split("\n"));
        }
        result = assignmentValidator.validate(assignmebt);
        if (!result.isValid()) {
            validationErrorsMap.put("assignmentErrors", result.getMessage().split("\n"));
        }

        return validationErrorsMap;
    }
}
