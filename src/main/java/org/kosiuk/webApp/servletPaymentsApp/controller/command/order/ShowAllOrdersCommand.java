package org.kosiuk.webApp.servletPaymentsApp.controller.command.order;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.CreditCardOrderWithUserDto;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllOrdersCommand implements Command {

    private final CreditCardOrderService orderService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.valueOf(rb.getString("order.page.size"));

    public ShowAllOrdersCommand(CreditCardOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        long totalItems = orderService.getNumberOfRecords();
        List<CreditCardOrderWithUserDto> orderDtos = orderService.getAllCreditCardOrderWithUserDtosPage(0);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;

        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", 1);
        request.setAttribute("orderDtos", orderDtos);
        return Path.SHOW_ALL_ORDERS_JSP_PATH;
    }
}
