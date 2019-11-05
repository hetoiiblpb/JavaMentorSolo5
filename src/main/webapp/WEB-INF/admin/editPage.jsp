<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${empty user.name}">
        <title>Add</title>
    </c:if>
    <c:if test="${!empty user.name}">
        <title>Edit</title>
    </c:if>
</head>
<body>
<c:if test="${empty user.name}">
    <c:url value="/admin/addUser" var="var"/>
</c:if>
<c:if test="${!empty user.name}">
    <c:url value="/admin/updateUser/" var="var"/>
</c:if>
<form action="${var}" method="POST">

    <table>
        <c:if test="${!empty user.name}">
            <tr><td>Id</td></td><td><input type="hidden" name="id" value="${user.id}">${user.id}</td></tr>
        </c:if>
        <tr>
            <td>
                <label for="name">Name</label></td>
            <td>
                <input type="text" name="name" id="name" value="${user.name}">
            </td>
        </tr>
        <tr>
            <td>
                <label for="password">Password</label>
            </td>
            <td>
                <input type="password" name="password" id="password" value="${user.password}">
            </td>
        </tr>
        <tr>
            <td>
        <label for="email">E-mail</label>
            </td>
            <td>
        <input type="email" name="email" id="email" value="${user.email}">
            </td>
        </tr>
        <tr>
            <td>
        <label for="age">Age</label>
            </td>
            <td>
        <input type="number" name="age" id="age" value="${user.age}">
            </td>
        </tr>
    </table>
    <c:if test="${empty user.role}">
    <input type="hidden" name="role" value="user" >
    </c:if>
<c:if test="${empty user.name}">
    <input type="submit" value="Add User">
</c:if>
    <c:if test="${!empty user.name}">
        <input type="submit" value="Edit User">
    </c:if>
</form>
</body>
</html>

