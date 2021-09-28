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
</head>
<body>
    <div class="container mt-4" style="width: 80em; border: 2px solid #999; border-radius: 5px">
        <div class="row">
            <div class="col">
                <h2><fmt:message key="order"/></h2>
                <p>${requestScope.orderConfDto.ownerName}</p>
                <p>${requestScope.orderConfDto.orderStatus.name()}</p>
                <h6><fmt:message key="label.message"/></h6>
                <p>${requestScope.orderConfDto.message}</p>
            </div>
            <div class="col">
                <h2><fmt:message key="label.card"/></h2>
                <p>${requestScope.cardConfDto.number}</p>
                <p>${requestScope.cardConfDto.cvv}</p>
                <p>${requestScope.cardConfDto.expireDateString}</p>
                <p>${requestScope.cardConfDto.paymentSystem.name()}</p>
            </div>
            <div class="col">
                <h2><fmt:message key="label.moneyAccount"/></h2>
                <p>${requestScope.moneyAccountNum}</p>
                <form method="get" action="/servletPaymentsApp/order/confirm/post">
                    <input type="hidden" name="orderId" value="${requestScope.orderConfDto.id}">
                    <input type="hidden" name="userId" value="${requestScope.orderConfDto.ownerId}">
                    <input type="hidden" name="userName" value="${requestScope.orderConfDto.ownerName}">
                    <input type="hidden" name="message" value="${requestScope.orderConfDto.message}">
                    <input type="hidden" name="creditCardNum" value="${requestScope.cardConfDto.number}">
                    <input type="hidden" name="CVV" value="${requestScope.cardConfDto.cvv}">
                    <input type="hidden" name="expireDateString" value="${requestScope.cardConfDto.expireDateString}">
                    <input type="hidden" name="paymentSystem" value="${requestScope.cardConfDto.paymentSystem.name()}">
                    <input type="hidden" name="moneyAccountNum" value="${requestScope.moneyAccountNum}">
                    <label for="moneyAccountNameId" ><fmt:message key="label.name"/></label>
                    <input type="text" name="moneyAccountName" id="moneyAccountNameId"
                           class="form-control ${requestScope.errors.get('nameErrors') != null ? 'is-invalid' : 'is-valid'}">
                    <c:if test="${requestScope.errors.get('nameErrors') != null}">
                        <div class="alert alert-danger" role="alert">
                            <c:forEach var="error" items="${requestScope.errors.get('nameErrors')}">
                                ${error}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                    <p></p>
                    <input class="btn btn-success" type="submit" value="<fmt:message key="action.crtCrdMonAcc"/>">
                </form>
            </div>
            <div class="col-2 mt-2">
                <form method="get"
                      action="/servletPaymentsApp/order/cancelConfirmation">
                    <input class="btn btn-warning" type="Submit" value="<fmt:message key="action.cancel"/>">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
