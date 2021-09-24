package org.kosiuk.webApp.servletPaymentsApp.controller.command.personalRoom;

import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserBasicDto;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.CreditCardOrder;
import org.kosiuk.webApp.servletPaymentsApp.model.service.CreditCardOrderService;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ShowPersonalRoomCommand implements Command {

    private final UserService userService;
    private final CreditCardOrderService orderService;

    public ShowPersonalRoomCommand(UserService userService, CreditCardOrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));

        Optional<UserBasicDto> userBasicDtoOptional = userService.getUserByIdAsBasicDto(userId);

        UserBasicDto userBasicDto = userBasicDtoOptional.isEmpty() ? new UserBasicDto() : userBasicDtoOptional.get();
        request.setAttribute("userBasicDto", userBasicDto);

        List<CreditCardOrder> creditCardOrders = orderService.getAllUsersCreditCardOrders(userId);

        request.setAttribute("creditCardOrders", creditCardOrders);

        return Path.PERSONAL_ROOM_JSP_PATH;
    }
}
