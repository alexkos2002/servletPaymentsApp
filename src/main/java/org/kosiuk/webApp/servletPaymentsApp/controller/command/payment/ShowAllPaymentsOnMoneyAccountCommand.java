package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllPaymentsOnMoneyAccountCommand implements Command {

    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("payment.page.halfSize"));

    public ShowAllPaymentsOnMoneyAccountCommand(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int moneyAccountId = Integer.parseInt(request.getParameter("moneyAccId"));

        long totalReceivedItems = transactionService.getNumberOfRecordsByReceiverMonAccId(moneyAccountId);
        List<Payment> receivedPayments = paymentService.
                getAllSortedPageableReceivedOnMoneyAccount(moneyAccountId, 0, "none");
        long totalSentItems = paymentService.getNumberOfSentFromMonAccRecords(moneyAccountId);
        List<Payment> sentPayments = paymentService.getAllSortedPageableSentFromMoneyAccount(moneyAccountId, 0, "none");
        request.setAttribute("totalReceivedItems", totalReceivedItems);
        request.setAttribute("totalSentItems", totalSentItems);
        long totalReceivedPages = totalReceivedItems % pageSize == 0 ? totalReceivedItems / pageSize :
                totalReceivedItems / pageSize + 1;
        long totalSentPages = totalSentItems % pageSize == 0 ? totalSentItems / pageSize :
                totalSentItems / pageSize + 1;
        request.setAttribute("totalReceivedPages", totalReceivedPages);
        request.setAttribute("curReceivedPage", 1);
        request.setAttribute("receivedPayments", receivedPayments);

        request.setAttribute("totalSentPages", totalSentPages);
        request.setAttribute("curSentPage", 1);
        request.setAttribute("sentPayments", sentPayments);

        request.setAttribute("moneyAccountId", moneyAccountId);
        request.setAttribute("sortParameter", "none");

        return Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_JSP_PATH;
    }
}
