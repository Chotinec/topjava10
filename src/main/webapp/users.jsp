<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>User list</h2>
<hr>
    <p>Select user ID:</p>
    <form method="post" action="users">
        <select name="userId">
            <option value="1">1</option>
            <option value="2">2</option>
        </select>
        <button type="submit">Set</button>
    </form>
</body>
</html>
