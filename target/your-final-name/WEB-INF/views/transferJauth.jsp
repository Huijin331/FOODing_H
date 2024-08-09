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
        <h1>모임장 권한 위임  for ${memberGroup.group.gname}</h1>
        <div class="transfer-area">
            <table class="transfer-table">
                <tr>
                    <td>현재 모임장: ${memberGroup.member.mid}</td>
                    <td>모임장 권한 위임할 회원</td>
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
