<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Personal Room</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg bg-dark fixed-top text-white" style="height:5em">
    <a href="/servletPaymentsApp" class="navbar-brand">
        <img src="/images/logo.jpg" style="width: 80px; height:70px" alt="logo" style="width: 80px; height:70px">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#my-nav">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" style="font-size: 20px" id="my-nav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a href="/servletPaymentsApp" class="nav-link text-white" ><fmt:message key="menu.link.home"/></a>
            </li>
            <c:if test="${sessionScope.authUser != null}">
                <li class="nav-item">
                    <a href="/servletPaymentsApp/personalRoom?userId=${sessionScope.authUser.id}"
                       class="nav-link text-white"><fmt:message key="menu.link.personalRoom"/></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.authUser != null && sessionScope.authUser.hasRole('ADMIN')}">
                <li class="nav-item">
                    <a href="/servletPaymentsApp/user" class="nav-link text-white"><fmt:message key="menu.link.users"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/moneyAccount" class="nav-link text-white"><fmt:message key="menu.link.moneyAccounts"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/order" class="nav-link text-white"><fmt:message key="menu.link.orders"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/payment" class="nav-link text-white"><fmt:message key="menu.link.payments"/></a>
                </li>
            </c:if>
        </ul>
        <a class="nav-link text-white" href="/servletPaymentsApp/personalRoom?userId=${sessionScope.authUser.id}&lang=en"><fmt:message key="lang.en"/></a>
        <a class="nav-link text-white" href="/servletPaymentsApp/personalRoom?userId=${sessionScope.authUser.id}&lang=ua"><fmt:message key="lang.ukr"/></a>
        <c:if test="${sessionScope.authUser == null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/registration"><fmt:message key="menu.button.signUp"/></a>
        </c:if>
        <c:if test="${sessionScope.authUser == null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/login"><fmt:message key="menu.button.signIn"/></a>
        </c:if>
        <c:if test="${sessionScope.authUser != null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/logout"><fmt:message key="menu.button.logOut"/></a>
        </c:if>
    </div>
</nav>
<div class="container" style="width: 85em; border: 2px solid #999; border-radius: 5px; margin-top: 6.5em">
    <h2>Personal room</h2>
    <div class="row">
        <div class="col">
            <h4><fmt:message key="label.user"/></h4>
            <table class="table table-bordered table-striped table-hover">
                <tbody>
                <tr>
                    <td><fmt:message key="label.username"/></td>
                    <td>${requestScope.userBasicDto.username}</td>
                </tr>
                <tr>
                    <td><fmt:message key="label.email"/></td>
                    <td>${requestScope.userBasicDto.email}</td>
                </tr>
                <tr>
                    <td><fmt:message key="label.password"/></td>
                    <td>${requestScope.userBasicDto.password}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col">
            <h4><fmt:message key="menu.link.orders"/></h4>
            <c:forEach items="${requestScope.creditCardOrders}" var="order">
                <div class="card pr-2 pl-2 mt-2 mb-2" >
                    <h5><fmt:message key="order"/></h5>
                    <p>${order.status}</p>
                    <h6><fmt:message key="label.message"/></h6>
                    <p>${order.message}</p>
                    <p>${order.desPaymentSystem.name()}</p>
                </div>
            </c:forEach>
        </div>
        <div class="col">
            <c:if test="${!requestScope.userBasicDto.hasOrderOnCheck}">
            <h4><fmt:message key="label.newOrder"/></h4>
            <p class="text-danger">${requestScope.orderCreationMessage}</p>
            <form method="get" action="/servletPaymentsApp/order/new" class="form-group">
                <input type="hidden" name="userId" value="${requestScope.userBasicDto.id}">
                <h6><fmt:message key="label.entWishes"/></h6>
                <input type="text" name="message" class="${requestScope.errors.get('messageErrors') != null ? 'is-invalid' : 'is-valid'}">
                <c:if test="${requestScope.errors.get('messageErrors') != null}">
                    <div class="alert alert-danger" role="alert">
                        <c:forEach var="error" items="${requestScope.errors.get('messageErrors')}">
                            ${error}<br>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="row">
                    <div class="col-4">
                        <label for="visa">VISA</label>
                        <input type="checkbox" class="${requestScope.errors.get('paymentSystemErrors') != null ? 'is-invalid' : 'is-valid'}"
                               name="VISA" value="true" id="visa">
                    </div>
                    <div class="col-8">
                        <label for="mastercard">MASTERCARD</label>
                        <input type="checkbox" name="MASTERCARD" class="${requestScope.errors.get('paymentSystemErrors') != null ? 'is-invalid' : 'is-valid'}"
                        value="true" id="mastercard">
                    </div>
                    <c:if test="${requestScope.errors.get('paymentSystemErrors') != null}">
                        <div class="alert alert-danger" role="alert">
                            <c:forEach var="error" items="${requestScope.errors.get('paymentSystemErrors')}">
                                ${error}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                <input class="btn btn-success" type="submit" value="<fmt:message key="action.create"/>">
            </form>
            </c:if>
            <c:if test="${requestScope.userBasicDto.hasOrderOnCheck}">
                <h5><fmt:message key="order.ordInProc"/></h5>
            </c:if>
        </div>
        <div class="col-3">
            <a class="btn btn-primary" href="/servletPaymentsApp/creditCard?userId=${requestScope.userBasicDto.id}"><fmt:message key="creditCard.all"/></a>
            <a class="btn btn-primary mt-2" href="/servletPaymentsApp/moneyAccount/ofUser?userId=${requestScope.userBasicDto.id}"><fmt:message key="moneyAccount.all"/></a>
        </div>
    </div>
</div>
</body>
</html>