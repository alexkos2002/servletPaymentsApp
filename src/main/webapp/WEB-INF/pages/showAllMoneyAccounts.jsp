<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Personal Room</title>
    <script>
        function submitForm(formId) {
            document.getElementById(formId).submit();
            return false;
        }
    </script>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg bg-dark fixed-top text-white" style="height:5em">
    <a href="/servletPaymentsApp" class="navbar-brand">
        <img src="/images/logo.jpg" style="width: 80px; height:70px" alt="logo" style="width: 80px; height:70px">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#my-nav">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" style="font-size: 20px" id="my-nav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a href="/servletPaymentsApp" class="nav-link text-white" ><fmt:message key="menu.link.home"/></a>
            </li>
            <c:if test="${sessionScope.authUser != null}">
                <li class="nav-item">
                    <a href="/servletPaymentsApp/personalRoom?userId=${sessionScope.authUser.id}"
                       class="nav-link text-white"><fmt:message key="menu.link.personalRoom"/></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.authUser != null && sessionScope.authUser.hasRole('ADMIN')}">
                <li class="nav-item">
                    <a href="/servletPaymentsApp/user" class="nav-link text-white"><fmt:message key="menu.link.users"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/moneyAccount" class="nav-link text-white"><fmt:message key="menu.link.moneyAccounts"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/order" class="nav-link text-white"><fmt:message key="menu.link.orders"/></a>
                </li>
                <li class="nav-item">
                    <a href="/servletPaymentsApp/payment" class="nav-link text-white"><fmt:message key="menu.link.payments"/></a>
                </li>
            </c:if>
        </ul>
        <a class="nav-link text-white" href="?lang=en"><fmt:message key="lang.en"/></a>
        <a class="nav-link text-white" href="?lang=ua"><fmt:message key="lang.ukr"/></a>
        <c:if test="${sessionScope.authUser == null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/registration"><fmt:message key="menu.button.signUp"/></a>
        </c:if>
        <c:if test="${sessionScope.authUser == null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/login"><fmt:message key="menu.button.signIn"/></a>
        </c:if>
        <c:if test="${sessionScope.authUser != null}">
            <a class="btn btn-primary mr-2 ml-2" href="/servletPaymentsApp/logout"><fmt:message key="menu.button.logOut"/></a>
        </c:if>
    </div>
</nav>
<div class="container" style="width: 70em; border: 2px solid #999; border-radius: 5px; margin-top: 6.5em">
    <h2><fmt:message key="label.allMoneyAccs"/></h2>
    <div class="row justify-content-start mt-2">
        <c:forEach items="${requestScope.moneyAccounts}" var="moneyAccount">
        <div class="col-4 pr-3 pl-3">
            <div class="container" style="border: 1px solid #666; border-radius: 3px">
                <h3><fmt:message key="moneyAccount"/></h3>
                <h5><fmt:message key="moneyAccount.ofUser"/> ${moneyAccount.username}</h5>
                <p>${moneyAccount.number}</p>
                <p>${moneyAccount.name}</p>
                <p>${moneyAccount.sumInt}.
                    <c:if test="${moneyAccount.sumDec < 10}">
                        0
                    </c:if>
                        ${moneyAccount.sumDec}UAH</p>
                <p>${moneyAccount.active.name()}</p>
                <div class="row mb-2">
                    <c:if test="${moneyAccount.active.name().equals('UNLOCK_REQUESTED')}">
                        <div class="col">
                            <a class="btn btn-primary" href="/servletPaymentsApp/moneyAccount/unlock?id=${moneyAccount.id}&userId=${moneyAccount.userId}">
                                <fmt:message key="action.unlock"/>
                            </a>
                        </div>
                    </c:if>
                    <div class="col">
                        <a class="btn btn-danger" href="/servletPaymentsApp/moneyAccount/delete?id=${moneyAccount.id}&userId=${moneyAccount.userId}">
                            <fmt:message key="action.delete"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
    <form class="row justify-content-end" method="get" action="/servletPaymentsApp/moneyAccount/page">
        <div class="col-3" style="display: flex; justify-content: space-between; align-items: center">
            <input type="hidden" name="pageNum" value="1">
            <label for="sortParamId" ><fmt:message key="action.sortBy"/></label>
            <select name="sortParam" class="form-control ml-1" id="sortParamId" style="width: 14em">
                <option value="number" ><fmt:message key="sortParam.number"/></option>
                <option value="name" ><fmt:message key="sortParam.name"/></option>
                <option value="sum"><fmt:message key="sortParam.remainedSum"/></option>
            </select>
        </div>
        <div class="col-1 mr-1 mt-2">
            <input type="submit" class="btn btn-success" value="<fmt:message key="action.apply"/>">
        </div>
    </form>
</div>
<div class="container mt-3 mb-2" style="max-width:1140px; border: 2px solid #777; border-radius: 5px;
     display: flex; align-items: center">
    <div class="row col-sm-12" style="font-size:20px">
        <div class="col-sm-3">
            <span><fmt:message key="label.totalMonAccs"/></span> ${requestScope.totalItems}
        </div>
        <c:if test="${requestScope.totalPages > 1}">
            <div class="col-sm-3">
                <c:forEach begin="1" end="${requestScope.totalPages}" step="1" var="i">
                <span>
                    <form action="/servletPaymentsApp/moneyAccount/page" method="get" style="display: none" id="monAccPage${i}">
                        <input type="hidden" name="pageNum" value="${i}">
                        <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                    </form>
                    <c:if test="${requestScope.curPage != i}">
                        <input type="button" onclick="submitForm('monAccPage${i}')" value="${i}"></a>
                    </c:if>
                    <c:if test="${requestScope.curPage == i}">
                        <span>${i}</span> &nbsp; &nbsp;
                    </c:if>
                </span>
                </c:forEach>
            </div>
            <div class="col-sm-1">
                <form action="/servletPaymentsApp/moneyAccount/page" method="get" style="display: none" id="next">
                    <input type="hidden" name="pageNum" value="${requestScope.curPage + 1}">
                    <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                </form>
                <c:if test="${requestScope.curPage < requestScope.totalPages}">
                    <input type="button" onclick="submitForm('next')" value="<fmt:message key="link.next"/>">
                </c:if>
                <c:if test="${requestScope.curPage == requestScope.totalPages}">
                    <span><fmt:message key="link.next"/></span>
                </c:if>
            </div>
            <div class="col-sm-1">
                <c:if test="${requestScope.curPage < requestScope.totalPages}">
                    <form action="/servletPaymentsApp/moneyAccount/page" method="get" style="display: none" id="last">
                        <input type="hidden" name="pageNum" value="${requestScope.totalPages}">
                        <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                    </form>
                    <input type="button" onclick="submitForm('last')" value="<fmt:message key="link.last"/>">
                </c:if>
                <c:if test="${requestScope.curPage == requestScope.totalPages}">
                    <span><fmt:message key="link.last"/></span>
                </c:if>
            </div>
            <br/>
        </c:if>
    </div>
</div>
</body>
</html>
