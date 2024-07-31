<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<%@ include file="/WEB-INF/views/includes/cacheControl.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 메인화면</title>

</head>
<body>
<c:import url = "/top.jsp" />

<header>
<h1>Welcome to the Dashboard</h1>
    <c:if test="${sessionScope.loggedInMember != null}">
        <p>${sessionScope.loggedInMember.mname}님, 안녕하세요!!</p>
    </c:if>
<a href="<%= request.getContextPath() %>/member/view?mid=${sessionScope.loggedInMember.mid}">
    <button>개인정보</button>
</a>
    <a href="logout"><button>로그아웃</button></a>
</header>

<c:import url = "/bottom.jsp" />