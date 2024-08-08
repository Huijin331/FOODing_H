<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sw.fd.entity.MemberGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 내 모임</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/groupList.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/alertHandler.js"></script>
</head>
<body>
<c:import url="/top.jsp"/>
<section>
    <div class="group-box">
        <div class="group-list-area">
            <h1>모임 리스트</h1>
            <table class="group-table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>모임명</th>
                    <th>모임장</th>
                    <th>모임 생성 날짜</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="memberGroup" items="${leaderList}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td><a href="#">${memberGroup.group.gname}</a></td>
                        <td>${memberGroup.member.mnick}</td>
                        <td>${memberGroup.group.gdate}</td>
                    </tr>
                    <c:set var="mnickString" value=""/>
                    <c:forEach var="allMemberGroup" items="${allMembers}" varStatus="status">
                        <c:choose>
                            <c:when test="${allMemberGroup.group.gno == memberGroup.group.gno}">
                                <c:set var="mnickString" value="${mnickString}${allMemberGroup.member.mnick} "/>
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
        </div>
        <form:form name="group-createForm" action="${pageContext.request.contextPath}/groupList" modelAttribute="group" method="post" onsubmit="return groupForm()">
            <div class="group-create-area">
                <h1>모임 생성</h1>
                <table class="group-create-table">
                    <tr>
                        <td><form:label path="gname">모임명</form:label></td>
                        <td><form:input path="gname" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="생성" /></td>
                    </tr>
                </table>
            </div>
        </form:form>
        <form:form name="member-addForm" action="${pageContext.request.contextPath}/addMember" modelAttribute="memberGroup" method="post" onsubmit="return memberAddForm()">
            <div class="groupMember-add-area">
                <h1>모임 회원 추가</h1>
                <table class="groupMember-table">
                    <tr>
                        <td><form:label path="group.gno">모임명</form:label></td>
                        <td>
                            <form:select path="group.gno">
                                <form:options items="${groups}" itemValue="gno" itemLabel="gname"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td><form:label path="member.mid">회원ID</form:label></td>
                        <td><form:input path="member.mid"/>
                            <form:errors path="member.mid" cssClass="error"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="추가"/>
                        </td>
                    </tr>
                </table>
                <c:if test="${not empty error}">
                    <p id="temporaryText" class="error-message">
                        <c:out value="${error}" />
                    </p>
                </c:if>
            </div>
        </form:form>
    </div>
</section>
<c:import url="/bottom.jsp"/>