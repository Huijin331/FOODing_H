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
<c:if test="${not empty error}">
<script>
    alert('${error}');
</script>
</c:if>
<section>
    <div class = "show-area1">
        <div class="slider-div">
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
    </div>
    <div class="show-area2">
    <div class="category-area">
        <table>
            <tr>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/korean_food_icon.png" href="${pageContext.request.contextPath}/storeList?scate=한식">
                    <button href="${pageContext.request.contextPath}/storeList?scate=한식">한식</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/japanese_food_icon.png" href="${pageContext.request.contextPath}/storeList?scate=일식">
                    <button href="${pageContext.request.contextPath}/storeList?scate=일식">일식</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/chinese_food_icon.png" href="${pageContext.request.contextPath}/storeList?scate=중식">
                    <button href="${pageContext.request.contextPath}/storeList?scate=중식">중식</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/western_food_icon.png" href="${pageContext.request.contextPath}/storeList?scate=양식">
                    <button href="${pageContext.request.contextPath}/storeList?scate=한식">양식</button>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/global_food_icon.png">
                    <button>세계요리</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/dessert_icon.png">
                    <button>빵/디저트</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/coffee_icon.png">
                    <button>차/커피</button>
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/resources/store_images/pub_icon2.png">
                    <button>술집</button>
                </td>
            </tr>
        </table>
    </div>
    <div class="groupshow-area">
        <table>
            <tr>
                <td>모임방1</td>
            </tr>
            <tr>
                <td>모임방2</td>
            </tr>
        </table>
    </div>
    </div>
</section>
<c:import url = "/bottom.jsp" />