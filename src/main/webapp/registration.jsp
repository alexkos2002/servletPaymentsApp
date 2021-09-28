<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Registration</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="get" action="/servletPaymentsApp/registration/auth"  class="form-group">
        <c:set var="userRegDto" value="${requestScope.userRegDto}"/>
        <label for="username" ><fmt:message key="label.username"/></label>
        <input type="text" name="username" class="form-control ${requestScope.errors.get('usernameErrors') != null ? 'is-invalid' : 'is-valid'}"
               id="username" value="${userRegDto.username}">
        <c:if test="${requestScope.errors.get('usernameErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('usernameErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <label for="email" ><fmt:message key="label.email"/></label>
        <input type="text" name="email" class="form-control ${requestScope.errors.get('emailErrors') != null ? 'is-invalid' : 'is-valid'}"
               id="email" value="${userRegDto.email}">
        <c:if test="${requestScope.errors.get('emailErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('emailErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <label for="password" ><fmt:message key="label.password"/></label>
        <input type="text" name="password" class="form-control ${requestScope.errors.get('passwordErrors') != null ? 'is-invalid' : 'is-valid'}"
               id="password" value="${userRegDto.password}">
        <c:if test="${requestScope.errors.get('passwordErrors') != null}">
            <div class="alert alert-danger" role="alert">
                <c:forEach var="error" items="${requestScope.errors.get('passwordErrors')}">
                    ${error}<br>
                </c:forEach>
            </div>
        </c:if>
        <br/>
        <c:if test="${requestScope.usernameNotUniqueError != null}">
            <div class="alert alert-danger" role="alert">
                <p>${requestScope.usernameNotUniqueError}</p>
            </div>
        </c:if>
        <input type="submit" class="btn btn-info" value="<fmt:message key="auth.register"/>">
    </form>
</div>
</body>
</html>
