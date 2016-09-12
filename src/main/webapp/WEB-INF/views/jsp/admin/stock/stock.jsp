<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 07.08.2016
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="../header.jsp"/>
<body>

<script>

    document.addEventListener("DOMContentLoaded", ready);

    function ready() {
        var tmp = new Array();      // два вспомагательных
        var tmp2 = new Array();     // массива
        var param = new Array();

        var get = location.search;  // строка GET запроса
        if(get != '') {
            tmp = (get.substr(1)).split('&');   // разделяем переменные
            for(var i=0; i < tmp.length; i++) {
                tmp2 = tmp[i].split('=');       // массив param будет содержать
                param[tmp2[0]] = tmp2[1];       // пары ключ(имя переменной)->значение
                if (tmp2[0]='filter') {
                    document.getElementById('filter').value =  decodeURI(tmp2[1]);
                }
            }
        }
    }

    function addIngredientToStock() {
        qty = document.getElementById('qty').value;
        if (qty >= 0) {
            ingredientName = document.getElementById('ingredient').value;
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "/admin/stock/addIngredient", false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("ingredientName=" + ingredientName + "&qty=" + qty);
            if (xhttp.status == 400) {
                window.alert("Ingredient could not be added!!!");
            } else {
                location.reload();
            }
        } else {
            alert("Quantity must be >= '0'");
        }
    }

    function delIngredientFromStock(ingredientName) {
        if (confirm("Delete ingredient from dish?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/admin/stock/delIngredient/" + ingredientName, false);
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

    function updateStock(ingredientName) {
        qty = prompt("Enter new Qty:");
        if ( qty >= 0 ) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "/admin/stock/addIngredient", false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("ingredientName=" + ingredientName + "&qty=" + qty);
            if (xhttp.status == 400) {
                window.alert("Ingredient could not be added!!!");
            } else {
                location.reload();
            }
            location.reload();
        } else {
            alert("Quantity must be >= '0'");
        }
    }

    function applyFilter() {
        if (document.getElementById('filter').value != "") {
            location.replace("?filter=" + document.getElementById('filter').value)
        } else {
            location.replace(location.href.replace(location.search, ''));
        }
    }
</script>

    <h3>Add Ingredient to Stock</h3>
    <a href="/admin/dish/ingredient">Ingredient:</a>
    <select id="ingredient" name="ingredient">
        <c:forEach var="list" items="${emptyIngredients}">
            <option value="${list.toString()}">${list.toString()}</option>
        </c:forEach>
    </select>
    <label for="qty">Qty:</label>
    <input type="number" name="qty" id="qty" required value="0">
    <button class="btn btn-default" onclick="addIngredientToStock()">Add ingredient</button>

    <hr>

    <h3>Stock balance</h3>
<label for="filter">Filter by:</label>
<input type="text" name="filter" id="filter" onchange="applyFilter()">
    <table class="table table-striped">
        <tr>
            <th>
                Ingredient
            </th>
            <th>Qty</th>
            <th>Action</th>
        </tr>
        <c:forEach var="stockItem" items="${stockList}">
            <tr>
                <td>${stockItem.ingredient}</td>
                <td>${stockItem.qty}</td>
                <td>
                    <button class="btn btn-primary" onclick="updateStock('${stockItem.ingredient}')">Edit</button>
                    <button class="btn btn-danger" onclick="delIngredientFromStock('${stockItem.ingredient}')">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>



</body>
</html>
