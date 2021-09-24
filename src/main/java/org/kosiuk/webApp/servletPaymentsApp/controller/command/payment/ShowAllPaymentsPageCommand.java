package org.kosiuk.webApp.servletPaymentsApp.controller.command.payment;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Payment;
import org.kosiuk.webApp.servletPaymentsApp.model.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllPaymentsPageCommand implements Command {

    private final PaymentService paymentService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("payment.page.size"));

    public ShowAllPaymentsPageCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int pageNumber = Integer.parseInt(request.getParameter("pageNum"));
        String sortParameter = request.getParameter("sortParam");

        long totalItems = paymentService.getNumberOfRecords();
        List<Payment> payments = paymentService.getAllPaymentsPage(pageNumber - 1, sortParameter);

        request.setAttribute("totalItems", totalItems);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", pageNumber);
        request.setAttribute("payments", payments);

        request.setAttribute("sortParameter", sortParameter);

        return Path.SHOW_ALL_PAYMENTS_JSP_PATH;
    }
}
