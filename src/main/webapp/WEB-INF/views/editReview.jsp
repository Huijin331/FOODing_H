<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/editReview.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    var selectedTags = [];

    function toggleTag(tno, button) {
        var index = selectedTags.indexOf(tno);
        if (index === -1) {
            selectedTags.push(tno);
            button.classList.add('selected');
        } else {
            selectedTags.splice(index, 1);
            button.classList.remove('selected');
        }
        document.getElementById('tnos').value = selectedTags.join(',');
    }

    function submitReview(event) {
        event.preventDefault(); // 폼 기본 제출 막기
        var formData = $("#review").serialize();
        console.log("Submitting review with data: ", formData);
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/review/update",
            data: formData,
            success: function(response) {
                console.log("AJAX success response: ", response);
                alert("수정이 완료되었습니다");
                window.opener.location.reload(); // 부모 창 새로고침
                window.close(); // 팝업 창 닫기
            },
            error: function(xhr, status, error) {
                console.log("AJAX error response: ", xhr, status, error);
                alert("리뷰 수정 중 오류가 발생했습니다: " + error);
            }
        });
    }

    $(document).ready(function() {
        $("#review").on("submit", submitReview);
    });

</script>
<div class = "edit-container">
    <h2>리뷰 수정</h2>
    <form:form method="post" action="${pageContext.request.contextPath}/review/update" modelAttribute="review" id="review">
        <input type="hidden" name="rno" value="${review.rno}" />
        <div class="form-group">
            <div class="star-rating">
                <input type="radio" name="rstar" class="star" id="star5" value="5"><label for="star5"></label>
                <input type="radio" name="rstar" class="star" id="star4" value="4"><label for="star4"></label>
                <input type="radio" name="rstar" class="star" id="star3" value="3"><label for="star3"></label>
                <input type="radio" name="rstar" class="star" id="star2" value="2"><label for="star2"></label>
                <input type="radio" name="rstar" class="star" id="star1" value="1"><label for="star1"></label>
            </div>
        </div>
        <div class="form-group">
            <form:textarea path="rcomm" id="rcomm" class="custom-textarea" placeholder="리뷰 내용을 입력하세요."></form:textarea>
        </div>
        <div class="form group">
            <p>태그를 선택하세요</p>
            <div class="tag-buttons">
                <c:forEach var="tag" items="${tags}">
                    <button type="button" class="tag-button" onclick="toggleTag(${tag.tno}, this)">${tag.ttag}</button>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="submit-button">리뷰 수정</button>
        </div>
        <input type="hidden" name="tnos" id="tnos" />

    </form:form>
</div>