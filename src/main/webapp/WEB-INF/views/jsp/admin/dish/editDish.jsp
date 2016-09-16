<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 31.07.2016
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../header.jsp"/>
<body>


<script>
    function addIngredientToDish() {
        dishId = document.getElementById('id').value;
        qty = document.getElementById('qty').value;
        if (qty > 0) {
            ingredientName = document.getElementById('ingredient').value;
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "${pageContext.request.contextPath}/admin/dish/edit/addIngredient", false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("dishId=" + dishId + "&ingredientName=" + ingredientName + "&qty=" + qty);
            if (xhttp.status == 400) {
                window.alert("Ingredient could not be added!!!");
            } else {
                location.reload();
            }
        } else {
            alert("Quntity must be greater than '0'")
        }
    }

    function delIngredientFromDish(recipeId) {
        if (confirm("Delete ingredient from dish?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "${pageContext.request.contextPath}/admin/dish/edit/deleteIngedient/" + recipeId, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            location.reload();
        }
    }

</script>

<h3>Edit dish</h3>
<script>
    function reloadPhoto() {
        document.getElementById('photo').src = "${pageContext.request.contextPath}/resources/dish/" + document.getElementById('photoFn').value;
    }
</script>
<form:form action="${pageContext.request.contextPath}/admin/dish" commandName="dish" method="post">
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
    <a href="${pageContext.request.contextPath}/admin/dish/categories">Category:</a>
    <form:select path="categoryDish">
        <form:options items="${categoryList}" />
    </form:select>
    &emsp;
    <img name="photo" id="photo" src="${pageContext.request.contextPath}/resources/dish/${dish.photoFn}" height="120">
    <br>
    <input type="submit" value="Update">
</form:form>

    <hr>
    <h3>Add ingredient to dish</h3>
    <a href="${pageContext.request.contextPath}/admin/dish/ingredient">Ingredient:</a>
    <select id="ingredient" name="ingredient" required>
        <c:forEach var="list" items="${ingredientList}">
            <option value='${list.ingredientName}'>${list.ingredientName}</option>
        </c:forEach>
    </select>
    <label for="qty">Qty:</label>
    <input type="number" name="qty" id="qty" required value="0">
    <button class="btn btn-default" onclick="addIngredientToDish()">Add Ingredient</button>

    <hr>
    <table class="table table-striped">
        <tr>
            <th>Ingredients</th>
            <th>Qty</th>
            <th>Action</th>
        </tr>
        <c:forEach var="recipe" items="${recipeList}">
            <tr>
                <td>${recipe.ingredient}</td>
                <td>${recipe.qty}</td>
                <td>
                    <button class="btn btn-danger" onclick="delIngredientFromDish(${recipe.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>

    </body>
    </html>
