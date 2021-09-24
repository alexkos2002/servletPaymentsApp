package org.kosiuk.webApp.servletPaymentsApp.controller.command.user;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.exception.ExceptionMessages;
import org.kosiuk.webApp.servletPaymentsApp.exception.NotCompatibleRolesException;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.kosiuk.webApp.servletPaymentsApp.util.sessionUtils.SessionLocalizationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ChangeRolesCommand implements Command {

    private final UserService userService;
    private ResourceBundle rb;

    public ChangeRolesCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        rb = ResourceBundle.getBundle("i18n.messages", SessionLocalizationUtil.getLocaleFromSession(request));

        Logger logger = Logger.getLogger(ChangeRolesCommand.class);
        int userId = Integer.parseInt(request.getParameter("userId"));
        Set<Role> newRoles = new HashSet<>();

        for (Role curRole : Role.values()) {
            String isRole = request.getParameter(curRole.name());
            if (isRole != null && Boolean.valueOf(isRole)) {
                newRoles.add(curRole);
            }
        }

        try {
            userService.changeRoles(userId, newRoles);
        } catch (NotCompatibleRolesException e) {
            logger.warn(ExceptionMessages.NOT_COMPATIBLE_ROLES);
            request.setAttribute("rolesChangeMessage", rb.getString("verification.role.notCompatible"));
            return Path.SHOW_ALL_USERS_JSP_PATH;
        }


        return Path.REDIRECT + Path.SHOW_ALL_USERS_PATH;
    }
}
