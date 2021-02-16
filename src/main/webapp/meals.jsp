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
</head>
<body>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="10" cellspacing="0">
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
                <fmt:formatDate pattern="dd-MM-YYYY HH:mm" value="${parsedDateTime}"/>
            </td>
            <td><h4>${meal.description}</h4></td>
            <td><h4>${meal.calories}</h4></td>
        </tr>
    </c:forEach>
</table>
<h3><a href="index.html">Home</a></h3>
</body>
</html>
