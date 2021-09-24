package org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCard;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllUsersCreditCardsCommand implements Command {

    private final CreditCardService creditCardService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("creditCard.page.size"));

    public ShowAllUsersCreditCardsCommand(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int userId = Integer.parseInt(request.getParameter("userId"));
        long totalItems = creditCardService.getNumberOfRecordsByUserId(userId);

        List<CreditCard> creditCards = creditCardService.getAllUsersCreditCardPage(userId, 0);
        request.setAttribute("totalItems", totalItems);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", 1);
        request.setAttribute("creditCards", creditCards);
        request.setAttribute("userId", userId);

        return Path.SHOW_ALL_CREDIT_CARDS_JSP;
    }
}
