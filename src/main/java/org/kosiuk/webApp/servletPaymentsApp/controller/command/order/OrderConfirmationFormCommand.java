package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardConfirmationDto;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.exception.UnsafeOrderConfirmationException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.OrderStatus;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.PaymentSystem;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class OrderConfirmationFormCommand implements Command {

    private final CreditCardOrderService orderService;
    private final MoneyAccountService moneyAccountService;
    private final CreditCardService creditCardService;
    private ResourceBundle rb;
    public static final Logger logger = Logger.getLogger(OrderConfirmationFormCommand.class);

    public OrderConfirmationFormCommand(CreditCardOrderService orderService, MoneyAccountService moneyAccountService,
                                        CreditCardService creditCardService) {
        this.orderService = orderService;
        this.moneyAccountService = moneyAccountService;
        this.creditCardService = creditCardService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        int id = Integer.parseInt(request.getParameter("id"));
        String message = request.getParameter("message");
        OrderStatus orderStatus = OrderStatus.getStatusByName(request.getParameter("statusName"));
        PaymentSystem paymentSystem = PaymentSystem.getPaymentSystemByName(request.getParameter("paymentSystemName"));

        int userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");

        try {
            orderService.prepareCreditCardOrderConfirmation();
        } catch (UnsafeOrderConfirmationException e) {
            logger.warn(ExceptionMessages.UNSAFE_ORDER_CONFIRMATION);
            request.setAttribute("orderPreparingMessage", rb.getString("verification.order.unsafe.confirmation"));
            return Path.SHOW_ALL_ORDERS_JSP_PATH;
        }

        request.setAttribute("orderConfDto", new CreditCardOrderWithUserDto(id, orderStatus, message, paymentSystem, userId, userName));
        request.setAttribute("moneyAccountNum", moneyAccountService.getCurMoneyAccountNum());
        CreditCardConfirmationDto cardConfDto = creditCardService.getCreditCardConfirmationDto(paymentSystem);
        request.setAttribute("cardConfDto", cardConfDto);

        return Path.ORDER_CONFIRMATION_FORM_JSP_PATH;
    }
}
