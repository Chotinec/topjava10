
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
            <td><h2>ID</h2></td>
            <td><h2>Description</h2></td>
            <td><h2>Date</h2></td>
            <td><h2>Calories</h2></td>
            <td><h2>Exceed</h2></td>
            <th colspan=2><h2>Action</h2></th>
        </tr>
        <c:set var="count" value="0" scope="page" />
        <c:forEach var="meal" items="${requestScope.mealList}">
            <tr style="${meal.exceed ? 'color: red;' : 'color :green;'}">
                <!--<c:set var="count" value="${count + 1}" scope="page"/>-->
                <td>${meal.id}</td>
                <td>${meal.description}</td>
                <td><javatime:format value="${meal.dateTime}" style="MS" /></td>
                <td>${meal.calories}</td>
                <td>${meal.exceed}</td>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

    <p><a href="meals?action=insert">Add User</a></p>
</body>
</html>
