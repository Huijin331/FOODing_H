<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<%@ include file="/WEB-INF/views/includes/cacheControl.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 메인화면</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css">

</head>
<body>
<c:import url = "/topNoneNav.jsp" />

<section>
    <div class="login-container">
        <div class="form-container">
            <h2>로그인</h2>

            <%-- 에러 메시지가 있을 경우에만 표시 --%>
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
            <form id="login-form" action="${pageContext.request.contextPath}/login" method="post" >
                <div>
                    <label for="mid">ID</label>
                    <input type="text" id="mid" name="id" required >
                    <label for="mpass">비밀번호</label>
                    <input type="password" id="mpass" name="password" required>
                </div>
                <div>
                    <input class="button" type="submit" value="로그인">
                </div>
                <div class="find-links">
                    <a href="${pageContext.request.contextPath}/find-id">회원ID 찾기</a>
                    <span>|</span>
                    <a href="${pageContext.request.contextPath}/find-pass">비밀번호 찾기</a>
                </div>
            </form>
        </div>
    </div>
</section>
<c:import url = "/bottom.jsp" />
