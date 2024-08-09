<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 모임장 권한 위임</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/transferJauth.css">
</head>
<body>
    <section>
        <div class="transfer-area">
            <h1>모임장 권한 위임 - ${memberGroup.group.gname}</h1>
            <form action="${pageContext.request.contextPath}/transferJauth" method="post">
            <table class="transfer-table">
                <tr>
                    <td>현재 모임장</td>
                    <td>${memberGroup.member.mnick}</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <ul>
                            <c:forEach var="member" items="${membersWithoutLeader}">
                                <li>${member.member.mnick}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>모임장 권한 위임 회원</td>
                    <td>선택된 회원</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="위임"/>
                    </td>
                </tr>
            </table>
            </form>
        </div>
    </section>
</body>
</html>
