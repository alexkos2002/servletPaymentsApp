package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllPaymentsOnMoneyAccountPageCommand implements Command {

    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("payment.page.halfSize"));

    public ShowAllPaymentsOnMoneyAccountPageCommand(PaymentService paymentService, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int moneyAccountId = Integer.parseInt(request.getParameter("moneyAccId"));
        int receivedPageNumber = Integer.parseInt(request.getParameter("receivedPageNum"));
        int sentPageNumber = Integer.parseInt(request.getParameter("sentPageNum"));
        String sortParameter = request.getParameter("sortParam");

        long totalReceivedItems = transactionService.getNumberOfRecordsByReceiverMonAccId(moneyAccountId);
        List<Payment> receivedPayments = paymentService.
                getAllSortedPageableReceivedOnMoneyAccount(moneyAccountId, receivedPageNumber - 1, sortParameter);
        long totalSentItems = paymentService.getNumberOfSentFromMonAccRecords(moneyAccountId);
        List<Payment> sentPayments = paymentService.getAllSortedPageableSentFromMoneyAccount(moneyAccountId, sentPageNumber - 1, sortParameter);
        request.setAttribute("totalReceivedItems", totalReceivedItems);
        request.setAttribute("totalSentItems", totalSentItems);
        long totalReceivedPages = totalReceivedItems % pageSize == 0 ? totalReceivedItems / pageSize :
                totalReceivedItems / pageSize + 1;
        long totalSentPages = totalSentItems % pageSize == 0 ? totalSentItems / pageSize :
                totalSentItems / pageSize + 1;
        request.setAttribute("totalReceivedPages", totalReceivedPages);
        request.setAttribute("curReceivedPage", receivedPageNumber);
        request.setAttribute("receivedPayments", receivedPayments);

        request.setAttribute("totalSentPages", totalSentPages);
        request.setAttribute("curSentPage", sentPageNumber);
        request.setAttribute("sentPayments", sentPayments);
        request.setAttribute("moneyAccountId", moneyAccountId);
        request.setAttribute("sortParameter", sortParameter);

        return Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_JSP_PATH;
    }
}
