<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403 Forbidden</title>
</head>
<body>
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4"><fmt:message key="error.403"/></h1>
            <h1 class="display-4" ><fmt:message key="error.403.message"/></h1>
        </div>
    </div>
</body>
</html>
