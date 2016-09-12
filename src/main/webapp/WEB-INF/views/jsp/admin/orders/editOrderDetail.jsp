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
    function addOrderDetail() {
        orderId = document.getElementById('orderId').value;
        dishId = document.getElementById('dishId').value;
        qty = document.getElementById('qty').value;
        if (qty<=0) {
            alert ("Quntity must be > 0")
        } else {
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "${pageContext.request.contextPath}/admin/orders/addDetail/", false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send( "orderId=" + orderId +
                    "&dishId=" + dishId +
                    "&qty=" + qty);
            location.reload();
        }
    }

    function delOrderDetail(orderDetailId) {
        if (window.confirm("Delete dish from order?")) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "${pageContext.request.contextPath}/admin/orders/deleteDetail/" + orderDetailId, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
        }
        location.reload();
    }

    function prepareDish(dishId) {
        orderId = document.getElementById('orderId').value;
        dateOrder = document.getElementById('dateOrder').value;
        coockerId = document.getElementById('coockerId').value;
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "${pageContext.request.contextPath}/admin/dish/prepareDish", false);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("dishId=" + dishId +
                "&employeeId=" + coockerId +
                "&orderId=" + orderId +
                "&dateOrder=" + dateOrder);
        if (xhttp.status == 400) {
            window.alert("Not enough ingredients for prepare Dish!!!");
        }
        if (xhttp.status == 405) {
            window.alert("No recipe for prepare dish!!!");
        }
        location.reload();
    }
</script>

<h3>Edit order</h3>
<input type="hidden" name="id" id="id" value=${orderNum.id}>
<label for="employee">Waiter:</label>
<input type="text" name="employee" id="employee" value=${orderNum.employee.name} disabled>
&emsp;
<label for="dateOrder">Order date:</label>
<input type="date" name="Order date" id="dateOrder" value=${orderNum.dateOrder} disabled>
<label for="tableNum">Table number:</label>
<input type="number" name="tableNum" id="tableNum" value=${orderNum.tableNum} disabled>
&emsp;
<label for="open">Open:</label>
<input type="checkbox" name="open" id="open" value=${orderNum.open} disabled>
<hr>
<h3>Add Dish to Order</h3>
<input type="hidden" name="orderId" id="orderId" value=${orderNum.id}>
<label for="dishId">Dish:</label>
<select id="dishId" name="dishId">
    <c:forEach var="list" items="${dishList}">
        <option value="${list.key}">${list.value}</option>
    </c:forEach>
</select>
<label for="qty">Qty:</label>
<input type="number" name="qty" id="qty" required value=0>
<button class="btn btn-primary" onclick="addOrderDetail()">Add to order</button>

    <table class="table table-striped">
        <tr>
            <th>Dish name</th>
            <th>Qty</th>
            <th>Action</th>
        </tr>
        <c:forEach var="orderDetail" items="${orderNum.orderDetails}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/admin/dish/${orderDetail.dish.id}">${orderDetail.dish}</a></td>
                <td>${orderDetail.qty}</td>
                <td>
                    <button class="btn btn-primary" onclick="prepareDish(${orderDetail.dish.id})">Prepare</button>
                    <button class="btn btn-danger" onclick="delOrderDetail(${orderDetail.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
<hr>
<h3>Prepared Dishes for order</h3>
<label for="coockerId">Coocker:</label>
<select id="coockerId" name="coockerId">
    <c:forEach var="employee" items="${coockerList}">
        <option value="${employee.key}">${employee.value}</option>
    </c:forEach>
</select>

<table class="table table-striped">
    <tr>
        <th>Dish name</th>
        <th>Coocker</th>
        <th>Date</th>
    </tr>
    <c:forEach var="preparedDish" items="${preparedDishList}">
        <tr>
            <td>${preparedDish.dish}</td>
            <td>${preparedDish.employee}</td>
            <td>${preparedDish.prepareDate}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
