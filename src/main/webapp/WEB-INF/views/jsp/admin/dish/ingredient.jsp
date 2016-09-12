<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 06.08.2016
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="../header.jsp"/>
<body>

<script>
    function addIngredient() {
        ingredientName = document.getElementById('ingredient').value;
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "${pageContext.request.contextPath}/admin/dish/ingredient", false);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ingredientName=" + ingredientName);
        location.reload();
    }

    function delIngredient(ingredientName) {
        if (confirm("Delete ingredient?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "${pageContext.request.contextPath}/admin/dish/ingredient/delete/" + ingredientName, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            if (xhttp.status == 400) {
                window.alert("Ingredient could not be deleted!!!");
            } else {
                location.reload();
            }
            location.reload();
        }
    }
</script>

<h3>Add Ingredient</h3>
<%--
<form action="/admin/dish/ingredient" accept-charset="UTF-8" method="post">
    <input type="text" name="ingredientName" id="ingredientName" required value=${ingredientName}>
    <input type="submit" value="Add Ingredient">
</form>
--%>

<input type="text" name="ingredient" id="ingredient" required>
<button class="btn btn-default" onclick="addIngredient()">Add ingredient</button>
<hr>
    <table class="table table-striped">
        <tr>
            <th>Ingredient</th>
            <th>Action</th>
        </tr>
        <c:forEach var="ingredient" items="${ingredients}">
            <tr>
                <td>${ingredient.ingredientName}</td>
                <td>
                    <button class="btn btn-danger" onclick="delIngredient('${ingredient.ingredientName}')">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
