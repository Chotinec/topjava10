<%--
  Created by IntelliJ IDEA.
  User: Artiom
  Date: 26.03.2017
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals List</title>
    <style>
        table {
            border: 1px solid black;
            width: 80%;
            border-spacing: 0;
        }

        td {
            border: 1px solid black;
            height: 25px;
            text-align: center;
        }
    </style>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meals list</h2>
    <table>
        <tr>
            <td></td>
            <td><h2>Description</h2></td>
            <td><h2>Date</h2></td>
            <td><h2>Calories</h2></td>
            <td><h2>Exceed</h2></td>
        </tr>
        <c:set var="count" value="0" scope="page" />
        <c:forEach var="meal" items="${requestScope.mealList}">
            <tr style="${meal.exceed ? 'color: red;' : 'color :green;'}">
                <c:set var="count" value="${count + 1}" scope="page"/>
                <td>${count}</td>
                <td>${meal.description}</td>
                <td><javatime:format value="${meal.dateTime}" style="MS" /></td>
                <td>${meal.calories}</td>
                <td>${meal.exceed}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
