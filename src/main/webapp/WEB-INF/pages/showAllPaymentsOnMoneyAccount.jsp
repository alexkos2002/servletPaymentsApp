<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n/messages"/>

<html lang="en">
<head>
    <title>All Payments On Account</title>
    <script>
        function submitForm(formId) {
            document.getElementById(formId).submit();
            return false;
        }
    </script>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/style.css">
    <style>
        .roundButton {
            width: 50px;
            height: 50px;
            border-radius: 20px;
            background-size: cover;
            border: 1px solid #788;
        }
        .emphText {
            font-weight: 700;
        }

        .cRed {
            font-weight: 500;
            color: red;
        }

        .cGreen {
            font-weight: 500;
            color: #283;
        }
    </style>
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
        <a class="nav-link text-white" href="/servletPaymentsApp/payment/onAccount/page?moneyAccId=${requestScope.moneyAccountId}&sentPageNum=${requestScope.curSentPage}&receivedPageNum=${requestScope.curReceivedPage}&sortParam=${requestScope.sortParameter}&lang=en">
            <fmt:message key="lang.en"/>
        </a>
        <a class="nav-link text-white" href="/servletPaymentsApp/payment/onAccount/page?moneyAccId=${requestScope.moneyAccountId}&sentPageNum=${requestScope.curSentPage}&receivedPageNum=${requestScope.curReceivedPage}&sortParam=${requestScope.sortParameter}&lang=ua"">
            <fmt:message key="lang.ukr"/>
        </a>
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

<div class="container" style="width: 80em; margin-top: 6.5em;">
    <div class="row justify-content-start">
        <h2 class="col-8"><fmt:message key="payment.allOnMoneyAcc"/></h2>
        <div class="col">
            <form class="row justify-content-end" th:method="get"
                  action="/servletPaymentsApp/payment/onAccount/page">
                <div class="col-7" style="display: flex; justify-content: space-between; align-items: center">
                    <label for="sortParamId"><fmt:message key="action.sortBy"/></label>
                    <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                    <input type="hidden" name="receivedPageNum" value="1">
                    <input type="hidden" name="sentPageNum" value="1">
                    <select name="sortParam" class="form-control ml-1" id="sortParamId" style="width: 14em">
                        <option value="number"><fmt:message key="sortParam.number"/></option>
                        <option value="timeAsc"><fmt:message key="sortParam.timeAsc"/></option>
                        <option value="timeDesc"><fmt:message key="sortParam.timeDesc"/></option>
                    </select>
                </div>
                <div class="col-2 mr-1 mt-2">
                    <input type="submit" value="<fmt:message key="action.apply"/>" class="btn btn-success">
                </div>
            </form>
        </div>
    </div>
    <h3><fmt:message key="label.sentPayments"/></h3>
    <div class="row justify-content-start align-items-center ml-1 mt-2 mb-2">
        <c:forEach var="payment" items="${requestScope.sentPayments}">
            <div class="col-3 mt-2 mb-2 pr-2 pl-2" style="border: 1px solid #666;
                 box-sizing: border-box; border-radius: 3px">
                <h4><fmt:message key="payment.ofNumber"/>${payment.number}</h4>
                <div class="row pl-1 pr-1 mt-1 mb-1">
                    <div class="col">
                        <p class="emphText"><fmt:message key="label.status"/></p>
                        <p>${payment.status.name()}</p>
                    </div>
                    <div class="col">
                        <a href="/servletPaymentsApp/payment/details?paymentNum=${payment.number}">
                            <div class="roundButton ml-1 mr-1" style="background-image: url(/images/paymentDetLogo.jpg)"></div>
                        </a>
                    </div>
                </div>
                <div class="row pl-1 pr-1 mt-1 mb-1">
                    <div class="col">
                        <p class="emphText"><fmt:message key="label.payedSum"/></p>
                        <p class="cRed">${payment.payedSumInt}.
                            <c:if test="${payment.payedSumDec < 10}">0</c:if>${payment.payedSumDec}UAH</p>
                    </div>
                    <div class="col">
                        <p class="emphText"><fmt:message key="label.comission"/></p>
                        <p class="cRed">${payment.comissionInt}.
                            <c:if test="${payment.comissionDec < 10}">0</c:if>${payment.comissionDec}UAH</p>
                    </div>
                </div>
                <div class="row pl-1 pr-1 mt-1 mb-1">
                    <div class="col">
                        <p class="emphText"><fmt:message key="label.assignment"/></p>
                        <p>${payment.assignment}</p>
                    </div>
                    <div class="col">
                        <p class="emphText"><fmt:message key="label.time"/></p>
                        <p>${payment.timeString}</p>
                    </div>
                </div>

            </div>
        </c:forEach>
    </div>
    <div class="container mt-3 mb-2" style="max-width:1140px; border: 2px solid #777; border-radius: 5px;
     display: flex; align-items: center">
        <div class="row col-sm-12" style="font-size:20px">
            <div class="col-sm-3">
                <span><fmt:message key="label.totalSentPayments"/></span> ${requestScope.totalSentItems}
            </div>
            <c:if test="${requestScope.totalSentPages > 1}">
                <div class="col-sm-3">
                    <c:forEach begin="1" end="${requestScope.totalSentPages}" step="1" var="i">
                    <span>
                        <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="sentPaymentPage${i}">
                            <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                            <input type="hidden" name="sentPageNum" value="${i}">
                            <input type="hidden" name="receivedPageNum" value="${requestScope.curReceivedPage}">
                            <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                        </form>
                        <c:if test="${requestScope.curSentPage != i}">
                            <input type="button" onclick="submitForm('sentPaymentPage${i}')" value="${i}"></a>
                        </c:if>
                        <c:if test="${requestScope.curSentPage == i}">
                            <span>${i}</span> &nbsp; &nbsp;
                        </c:if>
                    </span>
                    </c:forEach>
                </div>
                <div class="col-sm-1">
                    <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="nextSentPayment">
                        <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                        <input type="hidden" name="sentPageNum" value="${requestScope.curSentPage + 1}">
                        <input type="hidden" name="receivedPageNum" value="${requestScope.curReceivedPage}">
                        <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                    </form>
                    <c:if test="${requestScope.curSentPage < requestScope.totalSentPages}">
                        <input type="button" onclick="submitForm('nextSentPayment')" value="<fmt:message key="link.next"/>">
                    </c:if>
                    <c:if test="${requestScope.curSentPage == requestScope.totalSentPages}">
                        <span><fmt:message key="link.next"/></span>
                    </c:if>
                </div>
                <div class="col-sm-1">
                    <c:if test="${requestScope.curSentPage < requestScope.totalSentPages}">
                        <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="lastSentPayment">
                            <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                            <input type="hidden" name="sentPageNum" value="${requestScope.totalSentPages}">
                            <input type="hidden" name="receivedPageNum" value="${requestScope.curReceivedPage}">
                            <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                        </form>
                        <input type="button" onclick="submitForm('lastSentPayment')" value="<fmt:message key="link.last"/>">
                    </c:if>
                    <c:if test="${requestScope.curSentPage == requestScope.totalSentPages}">
                        <span><fmt:message key="link.last"/></span>
                    </c:if>
                </div>
                <br/>
            </c:if>
        </div>
    </div>
    <h3><fmt:message key="label.receivedPayments"/></h3>
    <div class="row justify-content-start align-items-center ml-1 mt-2 mb-2">
        <c:forEach var="payment" items="${requestScope.receivedPayments}">
        <div class="col-3 mt-2 mb-2 pr-2 pl-2" style="border: 1px solid #666;
                 box-sizing: border-box; border-radius: 3px">
            <h4><fmt:message key="payment.ofNumber"/>${payment.number}</h4>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.status"/></p>
                    <p>${payment.status.name()}</p>
                </div>
                <div class="col">
                    <a href="/servletPaymentsApp/payment/details?paymentNum=${payment.number}">
                        <div class="roundButton ml-1 mr-1" style="background-image: url(/images/paymentDetLogo.jpg)"></div>
                    </a>
                </div>
            </div>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.payedSum"/></p>
                    <p class="cGreen">${payment.payedSumInt}.
                        <c:if test="${payment.payedSumDec < 10}">0</c:if>${payment.payedSumDec}UAH</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.comission"/></p>
                    <p class="cGreen">${payment.comissionInt}.
                        <c:if test="${payment.comissionDec < 10}">0</c:if>${payment.comissionDec}UAH</p>
                </div>
            </div>
            <div class="row pl-1 pr-1 mt-1 mb-1">
                <div class="col">
                    <p class="emphText"><fmt:message key="label.assignment"/></p>
                    <p>${payment.assignment}</p>
                </div>
                <div class="col">
                    <p class="emphText"><fmt:message key="label.time"/></p>
                    <p>${payment.timeString}</p>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>

    <div class="container mt-3 mb-2" style="max-width:1140px; border: 2px solid #777; border-radius: 5px;
     display: flex; align-items: center">
        <div class="row col-sm-12" style="font-size:20px">
            <div class="col-sm-3">
                <span><fmt:message key="label.totalReceivedPayments"/></span> ${requestScope.totalReceivedItems}
            </div>
            <c:if test="${requestScope.totalReceivedPages > 1}">
                <div class="col-sm-3">
                    <c:forEach begin="1" end="${requestScope.totalReceivedPages}" step="1" var="i">
                    <span>
                        <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="receivedPaymentPage${i}">
                            <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                            <input type="hidden" name="sentPageNum" value="${requestScope.curSentPage}">
                            <input type="hidden" name="receivedPageNum" value="${i}">
                            <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                        </form>
                        <c:if test="${requestScope.curReceivedPage != i}">
                            <input type="button" onclick="submitForm('receivedPaymentPage${i}')" value="${i}"></a>
                        </c:if>
                        <c:if test="${requestScope.curReceivedPage == i}">
                            <span>${i}</span> &nbsp; &nbsp;
                        </c:if>
                    </span>
                    </c:forEach>
                </div>
                <div class="col-sm-1">
                    <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="nextReceivedPayment">
                        <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                        <input type="hidden" name="sentPageNum" value="${requestScope.curSentPage}">
                        <input type="hidden" name="receivedPageNum" value="${requestScope.curReceivedPage + 1}">
                        <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                    </form>
                    <c:if test="${requestScope.curReceivedPage < requestScope.totalReceivedPages}">
                        <input type="button" onclick="submitForm('nextReceivedPayment')" value="<fmt:message key="link.next"/>">
                    </c:if>
                    <c:if test="${requestScope.curReceivedPage == requestScope.totalReceivedPages}">
                        <span><fmt:message key="link.next"/></span>
                    </c:if>
                </div>
                <div class="col-sm-1">
                    <c:if test="${requestScope.curReceivedPage < requestScope.totalReceivedPages}">
                        <form action="/servletPaymentsApp/payment/onAccount/page" method="get" style="display: none" id="lastReceivedPayment">
                            <input type="hidden" name="moneyAccId" value="${requestScope.moneyAccountId}">
                            <input type="hidden" name="sentPageNum" value="${requestScope.curSentPage}">
                            <input type="hidden" name="receivedPageNum" value="${requestScope.totalReceivedPages}">
                            <input type="hidden" name="sortParam" value="${requestScope.sortParameter}">
                        </form>
                        <input type="button" onclick="submitForm('lastReceivedPayment')" value="<fmt:message key="link.last"/>">
                    </c:if>
                    <c:if test="${requestScope.curReceivedPage == requestScope.totalReceivedPages}">
                        <span><fmt:message key="link.last"/></span>
                    </c:if>
                </div>
                <br/>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
