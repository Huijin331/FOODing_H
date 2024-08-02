<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix = "c"%>
<%@ include file="/WEB-INF/views/includes/cacheControl.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset = "UTF-8">
    <title>FOODing 메인화면</title>
    <link rel = "stylesheet" href = "${pageContext.request.contextPath}/resources/css/main_style_section.css" type = "text/css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/slider.js"></script>
</head>
<body>
<c:import url = "/top.jsp" />
<section>

    <div class = "section-div">
        <button class = "preBtn"></button>
        <ul class = "mainSliderList">
            <li class = "slider mainSlider1 active">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
            <li class = "slider mainSlider2">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
            <li class = "slider mainSlider3">
                <a href = "#">
                    <div class = "sliderImg">
                        <img src = "${pageContext.request.contextPath}/resources/images/chefudding.png">
                    </div>
                </a>
            </li>
        </ul>
        <button class = "nextBtn"></button>
    </div>
</section>
<c:import url = "/bottom.jsp" />