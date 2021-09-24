package org.kosiuk.webApp.servletPaymentsApp.controller;

import org.apache.log4j.Logger;
import org.kosiuk.webApp.servletPaymentsApp.constants.Path;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.ShowMoneyAccountCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.auth.*;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.Command;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.basic.MainPageCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard.PutMoneyCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard.PutMoneyFormCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard.ShowAllUsersCreditCardPageCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.creditCard.ShowAllUsersCreditCardsCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.moneyAccount.*;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.order.*;

import org.kosiuk.webApp.servletPaymentsApp.controller.command.payment.*;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.personalRoom.ShowPersonalRoomCommand;
import org.kosiuk.webApp.servletPaymentsApp.controller.command.user.*;
import org.kosiuk.webApp.servletPaymentsApp.model.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = Logger.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {

        final UserService userService = new UserService();
        final CreditCardOrderService orderService = new CreditCardOrderService();
        final MoneyAccountService moneyAccountService = MoneyAccountService.getInstance();
        final CreditCardService creditCardService = new CreditCardService();
        final PaymentService paymentService = new PaymentService();
        final TransactionService transactionService = new TransactionService();

        commands.put(Path.MAIN_PAGE_PATH, new MainPageCommand());
        commands.put(Path.LOGIN_FORM_PATH, new LoginFormCommand());
        commands.put(Path.LOGIN_PATH, new LoginCommand(userService));
        commands.put(Path.REGISTRATION_FORM_PATH, new RegistrationFormCommand());
        commands.put(Path.REGISTRATION_PATH, new RegistrationCommand(userService));
        commands.put(Path.LOGOUT_PATH, new LogOutCommand());
        commands.put(Path.SHOW_ALL_USERS_PATH, new ShowAllUsersCommand(userService));
        commands.put(Path.CHANGE_ROLES_PATH, new ChangeRolesCommand(userService));
        commands.put(Path.BAN_USER_PATH, new BanUserCommand(userService));
        commands.put(Path.UNBAN_USER_PATH, new UnbanUserCommand(userService));
        commands.put(Path.SHOW_ALL_USERS_PAGE_PATH, new ShowAllUsersPageCommand(userService));
        commands.put(Path.DELETE_USER_PATH, new DeleteUserCommand(userService));
        commands.put(Path.SHOW_PERSONAL_ROOM_PATH, new ShowPersonalRoomCommand(userService, orderService));
        commands.put(Path.NEW_ORDER_PATH, new NewOrderCommand(userService, orderService));
        commands.put(Path.SHOW_ALL_ORDERS_PATH, new ShowAllOrdersCommand(orderService));
        commands.put(Path.SHOW_ALL_ORDERS_PAGE_PATH, new ShowAllOrdersPageCommand(orderService));
        commands.put(Path.ORDER_REJECTION_FORM_PATH, new OrderRejectionFormCommand());
        commands.put(Path.REJECT_ORDER_PATH, new RejectOrderCommand(orderService));
        commands.put(Path.ORDER_CONFIRMATION_FORM_PATH, new OrderConfirmationFormCommand(orderService,
                moneyAccountService, creditCardService));
        commands.put(Path.CANCEL_ORDER_CONFIRMATION_PATH, new CancelOrderConfirmationCommand(orderService));
        commands.put(Path.CONFIRM_ORDER_PATH, new ConfirmOrderCommand(orderService));
        commands.put(Path.SHOW_ALL_USERS_CREDIT_CARDS_PATH, new ShowAllUsersCreditCardsCommand(creditCardService));
        commands.put(Path.SHOW_ALL_USERS_CREDIT_CARDS_PAGE_PATH, new ShowAllUsersCreditCardPageCommand(creditCardService));
        commands.put(Path.PUT_MONEY_FORM_PATH, new PutMoneyFormCommand());
        commands.put(Path.PUT_MONEY_PATH, new PutMoneyCommand(creditCardService));
        commands.put(Path.SHOW_ALL_MONEY_ACCOUNTS_PATH, new ShowAllMoneyAccountsCommand(moneyAccountService));
        commands.put(Path.SHOW_ALL_MONEY_ACCOUNTS_PAGE_PATH, new ShowAllMoneyAccountsPageCommand(moneyAccountService));
        commands.put(Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PATH, new ShowAllUsersMoneyAccountsCommand(moneyAccountService));
        commands.put(Path.SHOW_ALL_USERS_MONEY_ACCOUNTS_PAGE_PATH, new ShowAllUsersMoneyAccountsPageCommand(moneyAccountService));
        commands.put(Path.BLOCK_MONEY_ACCOUNT_PATH, new BlockMoneyAccountCommand(moneyAccountService));
        commands.put(Path.ASK_TO_UNLOCK_MONEY_ACCOUNT_PATH, new AskToUnlockMoneyAccountCommand(moneyAccountService));
        commands.put(Path.UNLOCK_MONEY_ACCOUNT_PATH, new UnlockMoneyAccountCommand(moneyAccountService));
        commands.put(Path.DELETE_MONEY_ACCOUNT_PATH, new DeleteMoneyAccountCommand(moneyAccountService));
        commands.put(Path.SHOW_MONEY_ACCOUNT_PATH, new ShowMoneyAccountCommand(moneyAccountService));
        commands.put(Path.SHOW_TO_CARD_PAYMENT_FORM_PATH, new ShowToCardPaymentFormCommand());
        commands.put(Path.PREPARE_TO_CARD_PAYMENT_PATH, new PrepareToCardPaymentCommand(paymentService));
        commands.put(Path.SEND_PAYMENT_PATH, new SendPaymentCommand(paymentService, moneyAccountService));
        commands.put(Path.CANCEL_PAYMENT_PATH, new CancelPaymentCommand(paymentService));
        commands.put(Path.SHOW_TO_MONEY_ACCOUNT_PAYMENT_FORM_PATH, new ShowToMoneyAccountPaymentFormCommand());
        commands.put(Path.PREPARE_TO_MONEY_ACCOUNT_PAYMENT_PATH, new PrepareToMoneyAccountPaymentCommand(paymentService));
        commands.put(Path.SHOW_ALL_PAYMENTS_PATH, new ShowAllPaymentsCommand(paymentService));
        commands.put(Path.SHOW_ALL_PAYMENTS_PAGE_PATH, new ShowAllPaymentsPageCommand(paymentService));
        commands.put(Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PATH, new ShowAllPaymentsOnMoneyAccountCommand(paymentService, transactionService));
        commands.put(Path.SHOW_ALL_PAYMENTS_ON_MONEY_ACCOUNT_PAGE_PATH, new ShowAllPaymentsOnMoneyAccountPageCommand(paymentService, transactionService));
        commands.put(Path.SHOW_PAYMENT_DETAILS_PATH, new ShowPaymentDetailsCommand(paymentService));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response, false);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response, true);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info("Path: " + path);
        path = path.replaceAll(Path.APPLICATION_ROOT_PATH, "");
        logger.info("Path from context root: " + path);
        if (isPost) {
            path = path + "_POST";
        }
        Command command = commands.getOrDefault(path, commands.get(Path.MAIN_PAGE_PATH));
        String pagePath = command.execute(request);
        if(pagePath.contains(Path.REDIRECT)){
            response.sendRedirect(request.getContextPath() +
                    request.getServletPath() + pagePath.replace(Path.REDIRECT, ""));
            logger.info("Page path: " + pagePath);
        }else {
            request.getRequestDispatcher(pagePath).forward(request, response);
        }
    }
}
