<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <title>Solo5- Hello, ${user.username} !</title>
</head>
<body>
Hello , ${user.username}!
<br>
<form action="/helloUser" method="post">
    <input type="submit" value="Выйти">
</form>
</body>
</html>

