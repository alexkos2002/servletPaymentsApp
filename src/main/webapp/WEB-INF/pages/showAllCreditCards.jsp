<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 03.09.2021
  Time: 3:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>All Your Credit Cards</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/style.css">
    <style>
        .roundButton {
            width: 50px;
            height: 50px;
            border-radius: 20px;
            background-size: cover;
            border: 1px solid #788;
        }

        .paymentSysLogo{
            width: 90px;
            height: 56px;
            background-size: cover;
        }
    </style>
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
        <a class="nav-link text-white" href="/servletPaymentsApp/creditCard/page?pageNum=${curPage}&userId=${sessionScope.authUser.id}&lang=en">
            <fmt:message key="lang.en"/>
        </a>
        <a class="nav-link text-white" href="/servletPaymentsApp/creditCard/page?pageNum=${curPage}&userId=${sessionScope.authUser.id}&lang=ua">
            <fmt:message key="lang.ukr"/>
        </a>
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
<div class="container" style="margin-top: 6.5em">
    <div class="d-flex flex-row justify-content-start mt-2">
        <c:forEach items="${requestScope.creditCards}" var="creditCard">
        <div class="container mt-2 mb-2 mr-2 ml-2 pt-1 pb-1" style="width: 22em">
            <div class="card pr-2 pl-2 pt-1 pb-2 mt-2 mb-2" style="background-image: url(/images/space.png); background-size: cover;width: 20em">
                <p class="mt-2 mr-1 ml-1 crCardText">${creditCard.number}</p>
                <div class="row justify-content-between mt-2 mr-1 ml-1 crCardText">
                    <p>${creditCard.cvv}</p>
                    <p>${creditCard.expireDateString}</p>
                </div>
                <div class="row justify-content-between align-items-center mt-2 mr-1 ml-1 crCardText">
                    <p>${creditCard.availableSumInt}.
                        <c:if test="${creditCard.availableSumDec < 10}">0</c:if>${creditCard.availableSumDec}UAH</p>
                    <c:if test="${creditCard.paymentSystem.name().equals('VISA')}">
                        <div class="paymentSysLogo mb-1" style="background-image: url(/images/visaLogo.png)"></div>
                    </c:if>
                    <c:if test="${creditCard.paymentSystem.name().equals('MASTERCARD')}">
                        <div class="paymentSysLogo mb-1" style="background-image: url(/images/masterCardLogo.png)"></div>
                    </c:if>
                </div>
            </div>
            <div class="row justify-content-center align-items-center mt-2 mb-2 crCardText">
                <a class="btn btn-success" href="/servletPaymentsApp/creditCard/putMoney?cardId=${creditCard.id}">
                    <fmt:message key="action.putMoney"/>
                </a>
                <a class="btn btn-primary ml-1" href="/servletPaymentsApp/moneyAccount/distinct?id=${creditCard.moneyAccountId}&userId=${creditCard.accountId}">
                    <fmt:message key="creditCard.relAcc"/>
                </a>
                <a href="/servletPaymentsApp/payment/getToCardPaymentForm?senderMoneyAccId=${creditCard.moneyAccountId}">
                    <div class="roundButton ml-1 mr-1" style="background-image: url(/images/creditCardLogo.png)"></div>
                </a>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
<div class="container mt-3 mb-2" style="max-width:1140px; border: 2px solid #777; border-radius: 5px;
     display: flex; align-items: center">
    <div class="row col-sm-12" style="font-size:20px">
        <div class="col-sm-3">
            <span><fmt:message key="label.totalCards"/></span> ${requestScope.totalItems}
        </div>
        <c:if test="${requestScope.totalPages > 1}">
            <div class="col-sm-3">
                <c:forEach begin="1" end="${requestScope.totalPages}" step="1" var="i">
                <span>
                    <c:if test="${requestScope.curPage != i}">
                    <a href="/servletPaymentsApp/creditCard/page?pageNum=${i}&userId=${requestScope.userId}">
                            ${i}
                    </a>
                    </c:if>
                    <c:if test="${requestScope.curPage == i}">
                        <span>${i}</span> &nbsp; &nbsp;
                    </c:if>
                </span>
                </c:forEach>
            </div>
            <div class="col-sm-1">
                <c:if test="${requestScope.curPage < requestScope.totalPages}">
                    <a href="/servletPaymentsApp/creditCard/page?pageNum=${curPage + 1}&userId=${requestScope.userId}" >
                        <fmt:message key="link.next"/>
                    </a>
                </c:if>
                <c:if test="${requestScope.curPage == requestScope.totalPages}">
                    <span><fmt:message key="link.next"/></span>
                </c:if>
            </div>
            <div class="col-sm-1">
                <c:if test="${requestScope.curPage < requestScope.totalPages}">
                    <a href="/servletPaymentsApp/creditCard/page?pageNum=${requestScope.totalPages}&userId=${requestScope.userId}" >
                        <fmt:message key="link.last"/>
                    </a>
                </c:if>
                <c:if test="${requestScope.curPage == requestScope.totalPages}">
                    <span><fmt:message key="link.last"/></span>
                </c:if>
            </div>
            <br/>
        </c:if>
    </div>
</div>
</body>
</html>
