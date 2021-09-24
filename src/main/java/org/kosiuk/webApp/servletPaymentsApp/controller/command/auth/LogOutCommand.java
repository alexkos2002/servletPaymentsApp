package org.kosiuk.webApp.servletPaymentsApp.controller.command.auth;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("logoutMessage", "You have been successfully logged out.");
        return Path.LOGIN_JSP_PATH;
    }
}
