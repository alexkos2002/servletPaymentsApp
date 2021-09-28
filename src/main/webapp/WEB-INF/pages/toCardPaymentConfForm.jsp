<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Payment Form(To Card)</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/style.css">
    <style>
        .emphText {
            font-weight: 700;
        }
    </style>
</head>
<body>
<div class="container mt-4 pr-2 pl-2 pt-1 pb-1" style="width: 30em; border: 2px solid #999; border-radius: 5px">
    <h2><fmt:message key="payment.toCreditCard"/></h2>
    <div class="row">
        <div class="col">
        <form method="get" action="/servletPaymentsApp/payment/send">
            <input type="hidden" name="senderMoneyAccId" value="${requestScope.cardPaymentConfDto.senderMoneyAccountId}">
            <input type="hidden" name="receiverMoneyAccId" value="${requestScope.cardPaymentConfDto.receiverMoneyAccountId}">

            <input type="hidden" name="paymentNum" value="${requestScope.cardPaymentConfDto.paymentNumber}">
            <p class="emphText"><fmt:message key="label.paymentNumber"/></p>
            <p>${requestScope.cardPaymentConfDto.paymentNumber}</p>

            <p class="emphText"><fmt:message key="label.toCard"/></p>
            <p>${requestScope.cardPaymentConfDto.receiverRequisitesNumber}</p>

            <p class="emphText"><fmt:message key="label.recMoneyAcc"/></p>
            <p>${requestScope.cardPaymentConfDto.receiverMoneyAccountName}</p>

            <input type="hidden" name="payedSumString" value="${requestScope.cardPaymentConfDto.payedSumInt}.${requestScope.cardPaymentConfDto.payedSumDec}">
            <p class="emphText"><fmt:message key="label.sumToPay"/></p>
            <p>${requestScope.cardPaymentConfDto.payedSumInt}.${requestScope.cardPaymentConfDto.payedSumDec}</p>

            <p class="emphText"><fmt:message key="label.senderPaysComLabel"/></p>
            <p>${requestScope.cardPaymentConfDto.paymentComissionInt}.${requestScope.cardPaymentConfDto.paymentComissionDec}</p>

            <input type="hidden" name="totalString"
                       value="${requestScope.cardPaymentConfDto.totalInt}.${requestScope.cardPaymentConfDto.totalDec}">
            <p class="emphText"><fmt:message key="label.totalToPay"/></p>
            <p>${requestScope.cardPaymentConfDto.totalInt}.${requestScope.cardPaymentConfDto.totalDec}</p>

            <p class="emphText"><fmt:message key="label.assignment"/></p>
            <p>${requestScope.cardPaymentConfDto.assignment}</p>

            <input class="btn btn-success" type="submit" value="<fmt:message key="action.confirm"/>">
        </form>
        </div>
        <div class="col mt=12">
            <c:if test="${requestScope.paymentPrepMessage == null}">
                <form method="GET" action="/servletPaymentsApp/payment/cancel">
                    <input type="hidden" name="senderMoneyAccId" value="${requestScope.cardPaymentConfDto.senderMoneyAccountId}">
                    <input type="hidden" name="paymentNumber" value="${requestScope.cardPaymentConfDto.paymentNumber}">
                    <input type="hidden" name="totalString" value="${requestScope.cardPaymentConfDto.totalInt}.${requestScope.cardPaymentConfDto.totalDec}">
                    <input class="btn btn-warning" type="submit" value="<fmt:message key="action.cancel"/>">
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
