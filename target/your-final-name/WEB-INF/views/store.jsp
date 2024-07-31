<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/top.jsp" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
<section class = "content">
 <ul>
  <c:forEach var="store" items="${stores}">
   <li>${store.sname} - ${store.saddr}</li>
  </c:forEach>
 </ul>
</section>
<c:import url="/bottom.jsp" />