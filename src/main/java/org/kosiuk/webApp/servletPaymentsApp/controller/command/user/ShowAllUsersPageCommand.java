package org.kosiuk.webApp.servletPaymentsApp.controller.command.user;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllUsersPageCommand implements Command {

    private final UserService userService;
    private final ResourceBundle rb = ResourceBundle.getBundle("db/database");
    private final int pageSize = Integer.parseInt(rb.getString("user.page.size"));

    public ShowAllUsersPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int pageNumber = Integer.parseInt(request.getParameter("pageNum"));

        long totalItems = userService.getNumberOfRecords();
        List<User> users = userService.getAllUsersPage(pageNumber - 1);

        request.setAttribute("totalItems", totalItems);
        long totalPages = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("curPage", pageNumber);
        request.setAttribute("users", users);
        request.setAttribute("roleValues", Role.values());

        return Path.SHOW_ALL_USERS_JSP_PATH;
    }
}
