<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Order Confirmation Form</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
    <div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
        <h3><fmt:message key="moneyAccount"/></h3>
        <p>${requestScope.moneyAccount.number}</p>
        <p>${requestScope.moneyAccount.name}</p>
        <p>${requestScope.moneyAccount.sumInt}.
            <c:if test="${requestScope.moneyAccount.sumDec < 10}">
                0
            </c:if>
            ${requestScope.moneyAccount.sumDec}UAH</p>
        <p>${requestScope.moneyAccount.active.name()}</p>
        <c:set var="moneyAccount" value="${requestScope.moneyAccount}"/>
        <div class="rowStart">
            <c:if test="${moneyAccount.canBeLocked && moneyAccount.active.name().equals('ACTIVE')}">
                <div class="colAbs">
                    <a class="btn btn-danger" href="/servletPaymentsApp/moneyAccount/block?id=${requestScope.moneyAccount.id}&userId=${requestScope.userId}">
                        <fmt:message key="action.block"/>
                    </a>
                </div>
            </c:if>
            <c:if test="${moneyAccount.active.name().equals('BLOCKED')}">
                <div class="colAbs">
                    <a class="btn btn-info" href="/servletPaymentsApp/moneyAccount/askToUnlock?id=${moneyAccount.id}&userId=${requestScope.userId}">
                        <fmt:message key="action.askUnlock"/>
                    </a>
                </div>
            </c:if>
            <div class="colAbs">
                <a href="/servletPaymentsApp/payment/onAccount?moneyAccId=${moneyAccount.id}"
                class="btn btn-primary">Payments</a>
            </div>
            <c:if test="${moneyAccount.active.name().equals('ACTIVE')}">
                <div class="colAbs">
                    <a href="/servletPaymentsApp/payment/getToMoneyAccountPaymentForm?senderMoneyAccId=${moneyAccount.id}">
                        <div class="roundButton ml-1 mr-1" style="background-image: url(/images/moneyAccountLogo.png)"></div>
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>