
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meals list</h2>

    <form method="post" action="meals" >
        User ID : <label>
            <input type="text" name="id" readonly="readonly" name="userid" value="<c:out value="${requestScope.meal.id}" />"/>
        </label> <br/><br/>
        Description : <label>
            <input type="text" name="description" value="<c:out value="${requestScope.meal.description}" />"/>
        </label><br/><br/>
        Date : <label>
            <input type="datetime" name="dateTime" value="<javatime:format value="${requestScope.meal.dateTime}" style="MS" />" />
        </label><br/><br/>
        Calories : <label>
            <input type="text" name="calories" value="<c:out value="${requestScope.meal.calories}" />" />
        </label><br/><br/>
        Exceed : <label>
            <input type="text" name="exceed" value="<c:out value="${requestScope.meal.exceed}" />" />
        </label><br/><br/>
        <input type="submit" value="OK">
    </form>
</body>
</html>
