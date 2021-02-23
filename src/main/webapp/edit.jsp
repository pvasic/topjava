<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 19.02.2021
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<hr>
<h2><a href="index.html">Home</a></h2>
<br>
<h2>Edit Meal</h2>
<br>
<form method="post" action="${pageContext.request.contextPath}/meals">
    <jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.Meal" />
    <table >
        <tr>
            <td><h3>Date</h3></td>
            <td><input type="datetime-local" name="date" value="${meal.dateTime}" maxlength="50" size="20"></td>
        </tr>
        <tr>
            <td><h3>Description</h3></td>
            <td><input type="text" name="description" value="${meal.description}" maxlength="50" size="20"></td>
        </tr>
        <tr>
            <td><h3>Calories</h3></td>
            <td><input type="text" name="calories" value="${meal.calories}" maxlength="50" size="20"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"></td>
            <td><input type="button" value="Cancel" onclick="history.back()"></td>
        </tr>
    </table>
</form>
</body>
</html>
