<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5" style="width: 24em; border: 2px solid #999; border-radius: 5px;">

    <form action="/servletPaymentsApp/login/auth" method="get" class="form-group">
        <div class="mt-2">
            <label for="usernameId" ><fmt:message key="label.username"/></label>
            <input type="text" name="username" class="form-control" id="usernameId" value="${requestScope.username}"/>
        </div>
        <div class="mt-2">
            <label for="passwordId"><fmt:message key="label.password"/></label>
            <input type="password" name="password" class="form-control" id="passwordId" value="${requestScope.password}"/> </div>
        <c:if test="${requestScope.loginMessage != null}">
        <div class="alert alert-danger" role="alert">
            <p>${requestScope.loginMessage}</p>
        </div>
        </c:if>
        <p>${requestScope.logoutMessage}</p>
        <div class="row mt-4">
            <div class="col-sm-3"><input type="submit" value="<fmt:message key="auth.login"/>" class="btn btn-success"/></div>
            <div class="col-sm d-flex justify-content-around align-items-center">
                <span><fmt:message key="auth.stillNotReg"/></span>
                <a href="/servletPaymentsApp/registration"><fmt:message key="auth.registerHere"/></a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
