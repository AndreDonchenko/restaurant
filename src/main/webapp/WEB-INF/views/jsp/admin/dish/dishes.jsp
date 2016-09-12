<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 31.07.2016
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../header.jsp"/>
<body>

<script>
    function delDish(dishId) {
        if (confirm("Delete dish?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/admin/dish/delete/" + dishId, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            if (xhttp.status == 400) {
                window.alert("Dish could not be deleted!!!");
            } else {
                location.reload();
            }
            location.reload();
        }
    }
</script>

<h3>Add dish</h3>
<script>
    function reloadPhoto() {
        document.getElementById('photo').src = "/resources/dish/" + document.getElementById('photoFn').value;
    }
</script>
<form:form action="/admin/dish" commandName="dish" method="post">
    <input type="hidden" name="id" id="id" value=${dish.id}>

    <label for="dishName">Dish:</label>
    &emsp;&emsp;
    <input type="text" name="dishName" id="dishName" required value='${dish.dishName}'>
    &emsp;
    <label for="price">Price:</label>
    &emsp;
    <input type="number" name="price" id="price" required value=${dish.price}>
    &emsp;
    <form:label path="photoFn">Photo:</form:label>
    <form:select path="photoFn" onchange="reloadPhoto()">
        <form:option value="NONE" label="--- Select ---" />
        <form:options items="${photoFiles}" />
    </form:select>
    <br>
    <label for="weight">Weight:</label>
    &emsp;
    <input type="number" name="weight" id="weight" required value=${dish.weight}>
    &emsp;
    <a href="/admin/dish/categories">Category:</a>
    <form:select path="categoryDish">
        <form:option value="NONE" label="--- Select ---" />
        <form:options items="${categoryList}" />
    </form:select>
    &emsp;
    <img name="photo" id="photo" src="/resources/dish/${dish.photoFn}" height="120">
    <br>
    <input type="submit" value="Add Dish">
</form:form>
<hr>
<table class="table table-striped">
    <tr>
        <th>Dish</th>
        <th>Category</th>
        <th>Price</th>
        <th>Weight</th>
        <th>Action</th>
    </tr>
    <c:forEach var="dish" items="${dishes}">
        <tr>
            <td><a href="/admin/dish/${dish.id}">${dish.dishName}</a></td>
            <td>${dish.categoryDish}</td>
            <td>${dish.price}</td>
            <td>${dish.weight}</td>
            <td>
                <button class="btn btn-primary" onclick="location.href='/admin/dish/${dish.id}'">Edit</button>
                <button class="btn btn-danger" onclick="delDish('${dish.id}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
