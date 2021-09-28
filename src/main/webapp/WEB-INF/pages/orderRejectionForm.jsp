<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Order Rejection Form</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5" style="max-width: 700px; border: 2px solid #999; border-radius: 5px">
    <h1><fmt:message key="order.reject"/></h1>
    <h3><fmt:message key="order.giveReasToCl"/></h3>
    <br/>
    <form method="get" action="/servletPaymentsApp/order/reject/post" class="form-group">
        <input type="hidden" name="id" value="${requestScope.id}">
        <input type="hidden" name="userId" value="${requestScope.userId}">
        <textarea class="form-control ${requestScope.errors.get('messageErrors') != null ? 'is-invalid' : 'is-valid'}"
                  rows="8" name="rejectMessage" placeholder="${requestScope.message}"></textarea>
        <c:if test="${requestScope.errors.get('messageErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('messageErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <div class="form-group row justify-content-end mt-2 mr-1">
            <a href="/servletPaymentsApp/order" class="btn btn-info mr-1 ml-1" ><fmt:message key="action.cancel"/></a>
            <input type="submit" value="<fmt:message key="action.reject"/>" class="btn btn-warning mr-1 ml-1">
        </div>
    </form>
</div>
</body>
</html>
