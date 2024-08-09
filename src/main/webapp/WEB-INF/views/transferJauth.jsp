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
            <h1>모임장 권한 위임  for ${memberGroup.group.gname}</h1>
            <table class="transfer-table">
                <tr>
                    <td>현재 모임장</td>
                    <td>${memberGroup.member.mnick}</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">일반 회원 목록</td>
                </tr>
                <tr>
                    <td>모임장 권한 위임 회원</td>
                    <td>선택된 회원</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        위임 버튼
                    </td>
                </tr>
            </table>
        </div>
    </section>
</body>
</html>
