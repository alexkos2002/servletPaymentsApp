<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>Personal Room</title>
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
    <h3 class="text-danger">${requestScope.orderPreparingMessage}</h3>
    <c:if test="${requestScope.orderPreparingMessage == null}">
    <h2><fmt:message key="label.allOrders"/></h2>
    <div class="row">
    <c:forEach items="${requestScope.orderDtos}" var="orderDto">
        <div class="card pr-2 pl-2 col-3" style="height: 100%; border:2px solid #777">
            <p>${orderDto.ownerName}</p>
            <p>${orderDto.orderStatus.name()}</p>
            <h6><fmt:message key="label.message"/></h6>
            <p>${orderDto.message}</p>
            <p>${orderDto.paymentSystem.name()}</p>
            <div class="row justify-content-between">
                <div class="col">
                    <c:if test="${orderDto.orderStatus.name() == 'ON_CHECK'}">
                    <form method="get" action="/servletPaymentsApp/order/reject">
                        <input type="hidden" name="id" value="${orderDto.id}">
                        <input type="hidden" name="message" value="${orderDto.message}">
                        <input type="hidden" name="userId" value="${orderDto.ownerId}">
                        <input class="btn btn-danger" type="submit" value="<fmt:message key="action.reject"/>">
                    </form>
                </div>
                <div class="col" style="display: flex; justify-content: end;">
                    </c:if>
                    <c:if test="${orderDto.orderStatus.name() == 'ON_CHECK'}">
                        <form method="get" action="/servletPaymentsApp/order/confirm">
                            <input type="hidden" name="id" value="${orderDto.id}">
                            <input type="hidden" name="message" value="${orderDto.message}">
                            <input type="hidden" name="statusName" value="${orderDto.orderStatus.name()}">
                            <input type="hidden" name="paymentSystemName" value="${orderDto.paymentSystem.name()}">
                            <input type="hidden" name="userId" value="${orderDto.ownerId}">
                            <input type="hidden" name="userName" value="${orderDto.ownerName}">
                            <input class="btn btn-success" type="submit" value="<fmt:message key="action.confirm"/>">
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
    </div>
    </c:if>
</div>
<div class="container mt-3 mb-2" style="max-width:1140px; border: 2px solid #777; border-radius: 5px;
     display: flex; align-items: center">
    <div class="row col-sm-12" style="font-size:20px">
        <div class="col-sm-3">
            <span><fmt:message key="label.totalOrders"/></span> ${requestScope.totalItems}
        </div>
        <c:if test="${requestScope.totalPages > 1}">
        <div class="col-sm-3">
            <c:forEach begin="1" end="${requestScope.totalPages}" step="1" var="i">
                <span>
                    <c:if test="${requestScope.curPage != i}">
                    <a href="/servletPaymentsApp/order/page?pageNum=${i}">
                            ${i}
                    </a>
                    </c:if>
                    <c:if test="${requestScope.curPage == i}">
                        <span>${i}</span> &nbsp; &nbsp;
                    </c:if>
                </span>
            </c:forEach>
        </div>
        <div class="col-sm-1">
                <c:if test="${requestScope.curPage < requestScope.totalPages}">
                    <a href="/servletPaymentsApp/order/page?pageNum=${curPage + 1}" >
                        <fmt:message key="link.next"/>
                    </a>
                </c:if>
                <c:if test="${requestScope.curPage == requestScope.totalPages}">
                    <span><fmt:message key="link.next"/></span>
                </c:if>
        </div>
        <div class="col-sm-1">
            <c:if test="${requestScope.curPage < requestScope.totalPages}">
                <a href="/servletPaymentsApp/order/page?pageNum=${requestScope.totalPages}" >
                    <fmt:message key="link.last"/>
                </a>
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