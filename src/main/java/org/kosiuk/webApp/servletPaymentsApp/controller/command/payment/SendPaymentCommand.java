package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.concurrent.MoneyAccountMonitor;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.PaymentSendingDto;
import org.kosiuk.webApp.servletPaymentsApp.model.service.MoneyAccountService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SendPaymentCommand implements Command {

    private final PaymentService paymentService;
    private final MoneyAccountService moneyAccountService;
    Logger logger = Logger.getLogger(SendPaymentCommand.class);

    public SendPaymentCommand(PaymentService paymentService, MoneyAccountService moneyAccountService) {
        this.paymentService = paymentService;
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int senderMoneyAccId = Integer.parseInt(request.getParameter("senderMoneyAccId"));
        int receiverMoneyAccId = Integer.parseInt(request.getParameter("receiverMoneyAccId"));
        long paymentNum = Long.parseLong(request.getParameter("paymentNum"));
        String payedSumString = request.getParameter("payedSumString");
        String totalString = request.getParameter("totalString");

        PaymentSendingDto paymentSendingDto = new PaymentSendingDto(senderMoneyAccId, receiverMoneyAccId, paymentNum, payedSumString, totalString);

        Map<Integer, MoneyAccountMonitor> moneyAccountMonitorsMap = moneyAccountService.getMoneyAccountMonitorsMap();

        MoneyAccountMonitor monitor1 = receiverMoneyAccId < senderMoneyAccId ?
                moneyAccountMonitorsMap.get(senderMoneyAccId) : moneyAccountMonitorsMap.get(receiverMoneyAccId);

        MoneyAccountMonitor monitor2 = receiverMoneyAccId > senderMoneyAccId ?
                moneyAccountMonitorsMap.get(senderMoneyAccId) : moneyAccountMonitorsMap.get(receiverMoneyAccId);

        logger.info("Ready to sendPayment payment №" + paymentSendingDto.getPaymentNumber() + " from money account №" +
                paymentSendingDto.getSenderMoneyAccId() + " to money account №" + paymentSendingDto.getReceiverMoneyAccId());

        synchronized (monitor1) {
            logger.info("Paayment №" + paymentSendingDto.getPaymentNumber() + " has occupied first of sender-receiver pair accounts.");
            synchronized (monitor2) {
                logger.info("Paayment №" + paymentSendingDto.getPaymentNumber() + " has occupied second of sender-receiver pair accounts.");
                paymentService.sendPayment(paymentSendingDto);
                logger.info("Paayment №" + paymentSendingDto.getPaymentNumber() + " was sent.");
            }
        }

        return Path.REDIRECT + Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PATH + "?moneyAccId=" + senderMoneyAccId;
    }
}
