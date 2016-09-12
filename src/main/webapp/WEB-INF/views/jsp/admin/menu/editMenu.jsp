<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 31.07.2016
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../header.jsp"/>
<body>

<script>
    function addDishToMenu(menuId) {
        dishId = document.getElementById('dishId').value;
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "${pageContext.request.contextPath}/admin/menu/addDish", false);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("menuId=" + menuId + "&dishId=" + dishId);
        location.reload();
    }

    function delDishFromMenu(dishId) {
        if (window.confirm("Delete Dish From Menu?")) {
            menuId = document.getElementById('id').value;
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "${pageContext.request.contextPath}/admin/menu/delDish" , false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("menuId=" + menuId + "&dishId=" + dishId);
            location.reload();
        }
    }

    function updateMenu() {
        menuId = document.getElementById('id').value;
        menuName = document.getElementById('menuName').value;
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "${pageContext.request.contextPath}/admin/menu/update", false);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("menuId=" + menuId + "&menuName=" + menuName);
        location.reload();
    }
</script>

<h2>Edit Menu</h2>
<hr>
    <input type="hidden" name="id" id="id" value=${menu.id}>
    <label for="menuName">Menu Name:</label>
    <input type="text" name="menuName" id="menuName" required value='${menu.menuName}'>
    <button class="btn btn-primary" onclick="updateMenu()">Update</button>
    <hr>
    <h3>Add Dish to Menu</h3>
    <label for="dishId">Dish:</label>
    <select id="dishId" name="dishId">
        <c:forEach var="list" items="${dishList}">
            <option value="${list.key}">${list.value}</option>
        </c:forEach>
    </select>
    <button class="btn btn-primary" onclick="addDishToMenu(${menu.id})">Add Dish</button>

    <hr>
    <table class="table table-striped">
        <tr>
            <th>Dishes in Menu</th>
            <th>Action</th>
        </tr>
        <c:forEach var="dish" items="${menu.dishes}">
            <tr>
                <td>${dish.dishName}</td>
                <td>
                    <button class="btn btn-danger" onclick="delDishFromMenu(${dish.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>

    </body>
    </html>
