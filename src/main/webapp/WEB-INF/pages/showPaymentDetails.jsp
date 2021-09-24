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
</head>
<body>
<div class="container mt-2" style="width: 30em; border: 1px solid #666; border-radius: 3px">
    <c:set var="paymentDetails" value="${requestScope.paymentDetails}"/>
            <h4><fmt:message key="payment.ofNumber"/>${paymentDetails.number}<fmt:message key="payment.details"/></h4>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.sender"/></p>
                    <p>${paymentDetails.senderName}</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.receiver"/></p>
                    <p>${paymentDetails.receiverName}</p>
                </div>
            </div>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.status"/></p>
                    <p>${paymentDetails.paymentStatus.name()}</p>
                </div>
            </div>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.payedSum"/></p>
                    <p>${paymentDetails.movedSumInt}.
                        <c:if test="${paymentDetails.movedSumDec < 10}">0</c:if>${paymentDetails.movedSumDec}UAH</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.comission"/></p>
                    <p>${paymentDetails.comissionInt}.
                        <c:if test="${paymentDetails.comissionDec < 10}">0</c:if>${paymentDetails.comissionDec}UAH</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.total"/></p>
                    <p>${paymentDetails.payedSumInt}.
                        <c:if test="${paymentDetails.payedSumDec < 10}">0</c:if>${paymentDetails.payedSumDec}UAH</p>
                </div>
            </div>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.assignment"/></p>
                    <p>${paymentDetails.assignment}</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.time"/></p>
                    <p>${paymentDetails.timeString}</p>
                </div>
            </div>
</div>

</body>
</html>
