<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Payment Form(To Money Account)</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<div class="container mt-4 pr-2 pl-2 pt-1 pb-1" style="width: 30em; border: 2px solid #999; border-radius: 5px">
    <p class="text-danger">${requestScope.paymentPrepMessage}</p>
    <h2><fmt:message key="payment.toMoneyAccount"/></h2>
    <div class="row">
        <div class="col">
            <form method="POST" action="/servletPaymentsApp/payment">
                <input type="hidden" name="senderMoneyAccId" value="${requestScope.moneyAccPaymentConfDto.senderMoneyAccountId}">
                <input type="hidden" name="receiverMoneyAccId" value="${requestScope.moneyAccPaymentConfDto.receiverMoneyAccountId}">

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <input type="hidden" name="paymentNum" value="${requestScope.moneyAccPaymentConfDto.paymentNumber}">
                    <p class="emphText"><fmt:message key="label.paymentNumber"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.paymentNumber}</p>
                </c:if>
                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <p class="emphText"><fmt:message key="label.recMoneyAccNum"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.receiverRequisitesNumber}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <p class="emphText"><fmt:message key="label.recMoneyAcc"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.receiverMoneyAccountName}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <input type="hidden" name="payedSumString" value="${requestScope.moneyAccPaymentConfDto.payedSumInt}.${requestScope.moneyAccPaymentConfDto.payedSumDec}">
                    <p class="emphText"><fmt:message key="label.sumToPay"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.payedSumInt}.${requestScope.moneyAccPaymentConfDto.payedSumDec}</p>
                </c:if>
                <c:if test="${requestScope.paymentPrepMessage.equals('You dont have enough money for this payment.')}">
                    <p class="emphText cRed"><fmt:message key="label.sumToPay"/></p>
                    <p class="text-danger">${requestScope.notEnoughSumString}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <p class="emphText"><fmt:message key="label.senderPaysComLabel"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.paymentComissionInt}.${requestScope.moneyAccPaymentConfDto.paymentComissionDec}</p>
                </c:if>
                <c:if test="${requestScope.paymentPrepMessage.equals('You dont have enough money for this payment.')}">
                    <p class="emphText cRed"><fmt:message key="label.senderPaysComLabel"/></p>
                    <p class="text-danger">${requestScope.notEnoughSumComission}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <input type="hidden" name="totalString"
                           value="${requestScope.moneyAccPaymentConfDto.totalInt}.${requestScope.moneyAccPaymentConfDto.totalDec}">
                    <p class="emphText"><fmt:message key="label.totalToPay"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.totalInt}.${requestScope.moneyAccPaymentConfDto.totalDec}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <p class="emphText"><fmt:message key="label.assignment"/></p>
                    <p>${requestScope.moneyAccPaymentConfDto.assignment}</p>
                </c:if>

                <c:if test="${requestScope.paymentPrepMessage == null}">
                    <input class="btn btn-success" type="submit" value="<fmt:message key="action.confirm"/>">
                </c:if>
            </form>
        </div>
        <div class="col mt=12">
            <c:if test="${requestScope.paymentPrepMessage == null}">
                <form method="GET" action="/servletPaymentsApp/payment/cancel">
                    <input type="hidden" name="senderMoneyAccId" value="${requestScope.moneyAccPaymentConfDto.senderMoneyAccountId}">
                    <input type="hidden" name="paymentNumber" value="${requestScope.moneyAccPaymentConfDto.paymentNumber}">
                    <input type="hidden" name="totalString" value="${requestScope.moneyAccPaymentConfDto.totalInt}.${requestScope.moneyAccPaymentConfDto.totalDec}">
                    <input type="hidden" name="payedSumString" value="${requestScope.moneyAccPaymentConfDto.payedSumInt}.${requestScope.moneyAccPaymentConfDto.payedSumDec}">
                    <input class="btn btn-warning" type="submit" value="<fmt:message key="action.cancel"/>">
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>