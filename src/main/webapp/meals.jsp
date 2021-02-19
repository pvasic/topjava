<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 10.02.2021
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body>
<hr>
<h2>Meals</h2>
<hr>
<h3><a href="edit.jsp" >Add</a></h3>
<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <td><h3>Date</h3></td>
        <td><h3>Description</h3></td>
        <td><h3>Calories</h3></td>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.excess == true ? 'red' : 'green'}">
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"/>
                <h4><fmt:formatDate pattern="dd-MM-YYYY HH:mm" value="${parsedDateTime}"/></h4>
            </td>
            <td><h4>${meal.description}</h4></td>
            <td><h4>${meal.calories}</h4></td>
            <td><h4><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=${meal.id}">Update</a></h4></td>
            <td><h4><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=${meal.id}">Delete</a></h4></td>
        </tr>
    </c:forEach>
</table>
<h3><a href="index.html">Home</a></h3>
</body>
</html>
