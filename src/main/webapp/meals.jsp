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
<table border="1" cellpadding="10" cellspacing="0" >
    <tr>
        <td><h3>Date</h3></td>
        <td><h3>Description</h3></td>
        <td><h3>Calories</h3></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr>
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
</table>
<h3><a href="index.html">Home</a></h3>
</body>
</html>
