<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Put Money Form</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form action="/servletPaymentsApp/creditCard/putMoney/post" method="get">
        <h2><fmt:message key="creditCard.putMoney"/></h2>
        <label for="sumStringInput"><fmt:message key="label.sum"/></label>
        <input type="hidden" name="cardId" value="${requestScope.cardId}" id="sumStringInput">
        <input type="text" name="sumString" value="${requestScope.sumString}" class="form-control
        ${requestScope.errors.get('sumErrors') != null ? 'is-invalid' : 'is-valid'}">
        <c:if test="${requestScope.errors.get('sumErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('sumErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <input class="btn btn-success mt-2" type="submit" value="<fmt:message key="action.put"/>">
    </form>
</div>
</body>
</html>
