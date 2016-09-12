<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 30.07.2016
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="header.jsp"/>
<body>
    <h1>Restaurant. Now: ${currentTime} </h1>
    <a href="admin/menu">Menus</a>
    <br>
    <hr>
    <a href="admin/dish">Dishes</a>
    <br>
    <a href="admin/dish/categories">Categories of dishes</a>
    <br>
    <hr>
    <a href="/admin/employees">Employees</a>
    <br>
    <a href="/admin/positions">Positions</a>
    <hr>
    <br>
    <a href="admin/stock">Stock</a>
    <br>
    <a href="admin/dish/ingredient">Ingredients</a>
    <br>
    <hr>
    <a href="admin/orders">Orders</a>
    <br>
</body>
</html>
