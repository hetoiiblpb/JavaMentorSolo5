<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<h2>Users</h2>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Password</th>
        <th>E-Mail</th>
        <th>Age</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.password}</td>
            <td>${user.email}</td>
            <td>${user.age}</td>
            <td>${user.role}</td>
            <td>
                <a href="/admin/updateUser/${user.id}">Edit</a>
                <c:if test="${!user.role.equals('ROLE_ADMIN')}">
                <a href="/admin/deleteUser/${user.id}">Delete</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<%--<c:url value="/admin/addUser" var="add"/>--%>
<form action="/admin/addUser" method="get">
    <input type="submit" value="Add new User"/>
</form>
</body>
</html>
