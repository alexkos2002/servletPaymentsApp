package org.kosiuk.webApp.servletPaymentsApp.controller.command.user;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {

    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userService.deleteUser(userId);
        return Path.REDIRECT + Path.SHOW_ALL_USERS_PATH;
    }

}
