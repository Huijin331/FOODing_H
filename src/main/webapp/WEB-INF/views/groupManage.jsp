<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 모임 관리</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/groupManage.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/alertHandler.js"></script>
</head>
<body>
<c:import url="/top.jsp"/>
<section>
    <div class = "groupManage-box">
        <div class="groupManage-list-area">
            <h1>관리할 모임 리스트</h1>
            <table class="groupManage-table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th colspan="2" align="center">모임명</th>
                    <th>모임 생성 날짜</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="group" items="${leaderGroups}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${group.gname}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/updateGroupName" method="post">
                                    <input type="hidden" name="gno" value="${group.gno}">
                                    <input type="text" name="newGname" value="${group.gname}" required>
                                    <input type="submit" value="수정">
                                </form>
                            </td>
                            <td>${group.gdate}</td>
                        </tr>
                        <c:set var="mnickString" value=""/>
                        <c:forEach var="memberGroup" items="${allMemberGroups}">
                            <c:choose>
                                <c:when test="${memberGroup.group.gno == group.gno}">
                                    <c:set var="mnickString" value="${mnickString}${memberGroup.member.mnick} "/>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <tr>
                            <td colspan="4" align="center">
                                    ${mnickString}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty error}">
                <p id="temporaryText" class="error-message">
                    <c:out value="${error}" />
                </p>
            </c:if>
        </div>
        <div class="groupManage-delete-area">
            <h1>모임 회원 삭제</h1>
            <form action="${pageContext.request.contextPath}/deleteMemberToGroup" method="post">
                <table class="groupManage-delete-table">
                    <tr>
                        <td>모임명</td>
                        <td>
                            <select name="gno" id="deleteGroup">
                                <c:forEach var="group" items="${leaderGroups}">
                                    <option value="${group.gno}">${group.gname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>회원ID</td>
                        <td><input type="text" id="deleteMid" name="mid" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="삭제"/>
                        </td>
                    </tr>
                </table>
            </form>
            <c:if test="${not empty errorMessage}">
                <p id="temporaryText" class="error-message">
                    <c:out value="${errorMessage}" />
                </p>
            </c:if>
        </div>
        <div class="groupManage-add-area">
            <h1>모임 회원 추가</h1>
            <form action="${pageContext.request.contextPath}/addMemberToGroup" method="post">
                <table class="groupManage-add-table">
                    <tr>
                        <td>모임명</td>
                        <td>
                            <select name="gno" id="addGroup">
                                <c:forEach var="group" items="${leaderGroups}">
                                    <option value="${group.gno}">${group.gname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>회원ID</td>
                        <td><input type="text" id="addMid" name="mid" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="추가"/>
                        </td>
                    </tr>
                </table>
            </form>
            <c:if test="${not empty errorMessage2}">
                <p id="temporaryText" class="error-message">
                    <c:out value="${errorMessage2}" />
                </p>
            </c:if>
        </div>
    </div>
</section>
<c:import url="/bottom.jsp"/>
