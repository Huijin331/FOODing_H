<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                    <td colspan="2" align="">현재 모임장 : ${memberGroup.member.mnick}</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <c:forEach var="rg" items="${regularMembers}">
                            <p>${rg.memberNick}</p>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        모임장 권한 위임 회원 : <input type="text" id="transferJauth" name="memberNick" required/>
                    </td>
                </tr>
            </table>
                <input type="submit" value="위임"/>
            </form>
        </div>
    </section>
</body>
</html>
