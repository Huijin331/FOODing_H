<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>FOODing 내 초대 관리</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/inviteManage.css">
</head>
<body>
<c:import url="/top.jsp"/>
<section>
    <div class="inviteManage-box">
        <div class="inviteManage-list-area">
            <h1>내 초대 관리</h1>
            <table class="inviteManage-table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>초대한 회원</th>
                    <th>초대하는 모임명</th>
                    <th>초대 유형</th>
                    <th>수락 / 거절</th>
                    <th>삭제</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>번호</td>
                    <td>초대자</td>
                    <td>모임명</td>
                    <td>초대 유형</td>
                    <td>수락 버튼 / 거절 버튼</td>
                    <td>삭제 버튼</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</section>
<c:import url="/bottom.jsp"/>
