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
                if (tmp2[0]=='filterEmployee') {
                    document.getElementById('filterEmployee').value =  decodeURI(tmp2[1]);
                }
                if (tmp2[0]=='filterDate') {
                    document.getElementById('filterDate').value =  decodeURI(tmp2[1]);
                }
                if (tmp2[0]=='filterTable') {
                    document.getElementById('filterTable').value =  decodeURI(tmp2[1]);
                }
            }
        }
    }

    function applyFilter() {
        filterEmployee = document.getElementById('filterEmployee').value;
        filterDate = document.getElementById('filterDate').value;
        filterTable = document.getElementById('filterTable').value;
        if (filterEmployee != "" | filterDate != "" | filterTable != "") {
            location.replace("?filterEmployee=" + filterEmployee +
                             "&filterDate=" + filterDate +
                             "&filterTable=" + filterTable)
        } else {
            location.replace(location.href.replace(location.search, ''));
        }
    }

    function addOrder() {
        employeeId = document.getElementById('employeeId').value;
        dateOrder = document.getElementById('dateOrder').value;
        tableNum = document.getElementById('tableNum').value;
        isOpen = document.getElementById('open').checked;
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "${pageContext.request.contextPath}/admin/orders", false);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send( "employeeId=" + employeeId +
                    "&dateOrder=" + dateOrder +
                    "&tableNum=" + tableNum +
                    "&isOpen=" + isOpen);
        location.reload();
    }

    function delOrder(orderId) {
        if (confirm("Delete order?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "${pageContext.request.contextPath}/admin/orders/delete/" + orderId, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            if (xhttp.status == 400) {
                window.alert("Order could not be deleted!!!");
            }
            location.reload();
        }
    }
</script>

<h3>Add order</h3>
    <label for="employeeId">Employee:</label>
    <select id="employeeId" name="employeeId">
        <c:forEach var="employee" items="${employeeList}">
            <option value="${employee.key}">${employee.value}</option>
        </c:forEach>
    </select>
    &emsp;
    <label for="dateOrder">DateOrder:</label>
    <input type="date" name="Order date" id="dateOrder" required>
    <br>

    <label for="tableNum">Table number:</label>
    <input type="number" name="tableNum" id="tableNum" required>
    &emsp;
    <label for="open">Open:</label>
    <input type="checkbox" name="open" id="open" checked/>
    <br>
    <button class="btn btn-primary" onclick="addOrder()">Add order</button>


<hr>
<table class="table table-striped">
    <tr>
        <th>Employee &nbsp <input type="search" name="filterEmployee" id="filterEmployee" placeholder="Поиск" onchange="applyFilter()"></th>
        <th>Date &nbsp <input type="date" name="filterDate" id="filterDate" placeholder="Поиск" onchange="applyFilter()"></th>
        <th>Table &nbsp <input type="number" name="filterTable" id="filterTable" placeholder="Поиск" onchange="applyFilter()"></th>
        <th>Open</th>
        <th>Action</th>
    </tr>
    <c:forEach var="order" items="${ordersList}">
        <tr>
            <td>${order.employee.name} ${order.employee.surname}</td>
            <td>${order.dateOrder}</td>
            <td>${order.tableNum}</td>
            <td>${order.open}</td>
            <td>
                <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/admin/orders/editDetail/${order.id}'">Edit</button>
                <button class="btn btn-danger" onclick="delOrder('${order.id}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
