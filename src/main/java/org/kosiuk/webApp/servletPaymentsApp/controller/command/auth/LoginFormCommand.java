package org.kosiuk.webApp.servletPaymentsApp.controller.command.auth;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;

import javax.servlet.http.HttpServletRequest;

public class LoginFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return Path.LOGIN_JSP_PATH;
    }
}
