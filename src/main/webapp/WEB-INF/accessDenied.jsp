<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <title>Access Denied!!!</title>
</head>
<body>
Sorry, ${user.username}, your status is ${user.role} and you cant visit this page!
<br>
<form action="/helloUser" method="get">
    <input type="submit" value="назад">
</form>
</body>
</html>

