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
</head>
<body>
<div class="container mt-4" style="width: 30em; border: 2px solid #999; border-radius: 5px">
    <h2><fmt:message key="payment.toMoneyAccount"/></h2>
    <form action="/servletPaymentsApp/payment/getToMoneyAccountConfPaymentForm" method="GET">
        <input type="hidden" name="senderMoneyAccId" value="${requestScope.senderMoneyAccId}">
        <label for="moneyAccNum"><fmt:message key="label.recMoneyAccNum"/></label>
        <input type="text" name="receiverMoneyAccNum" id="moneyAccNum" class="form-control
        ${requestScope.errors.get('monAccNumErrors') != null ? 'is-invalid' : 'is-valid'}"
               value="${requestScope.receiverMoneyAccNumber}">
        <c:if test="${requestScope.errors.get('monAccNumErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('monAccNumErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <label for="payedSum"><fmt:message key="label.sum"/></label>
        <input type="text" name="payedSumString" id="payedSum" class="form-control
        ${requestScope.errors.get('sumErrors') != null ? 'is-invalid' : 'is-valid'}"
               value="${requestScope.sum}">
        <c:if test="${requestScope.errors.get('sumErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('sumErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <label for="assignment"><fmt:message key="label.assignment"/></label>
        <input type="text" name="assignment" id="assignment" class="form-control
        ${requestScope.errors.get('assignmentErrors') != null ? 'is-invalid' : 'is-valid'}"
               value="${requestScope.assignment}">
        <c:if test="${requestScope.errors.get('assignmentErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('assignmentErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <c:if test="${requestScope.paymentPrepMessage != null}">
            <div class="alert alert-danger" role="alert">
                <p>${requestScope.paymentPrepMessage}</p>
                <c:if test="${requestScope.paymentPrepMessage.equals('You dont have enough money for this payment.') ||
                requestScope.paymentPrepMessage.equals('У вас недостатньо грошей для даного платежу.')}">
                    <p class="emphText cRed"><fmt:message key="label.sumToPay"/></p>
                    <p class="text-danger">${requestScope.notEnoughSumString}</p>
                </c:if>
                <c:if test="${requestScope.paymentPrepMessage.equals('You dont have enough money for this payment.') ||
                requestScope.paymentPrepMessage.equals('У вас недостатньо грошей для даного платежу.')}">
                    <p class="emphText cRed"><fmt:message key="label.senderPaysComLabel"/></p>
                    <p class="text-danger">${requestScope.notEnoughSumString}</p>
                </c:if>
            </div>=
        </c:if>
        <input class="btn btn-success" type="submit" value="<fmt:message key="action.pay"/>">
    </form>
</div>
</body>
</html>