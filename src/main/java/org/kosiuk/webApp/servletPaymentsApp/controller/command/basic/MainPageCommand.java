package org.kosiuk.webApp.servletPaymentsApp.controller.command.basic;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;

import javax.servlet.http.HttpServletRequest;

public class MainPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return Path.MAIN_PAGE_JSP_PATH;
    }

}
