<%--
  Created by IntelliJ IDEA.
  User: Andre
  Date: 31.07.2016
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<jsp:include page="../header.jsp"/>
<body>
<script>
    function delEmployee(employeeId) {
        if (confirm("Delete employee?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/admin/employee/delete/" + employeeId, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            if (xhttp.status == 400) {
                window.alert("Employee could not be deleted!!!");
            } else {
                location.reload();
            }
            location.reload();
        }
    }
</script>

<a href="/admin/employee/add">Add Employee</a>
<br/>
<table class="table table-striped">
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Position</th>
        <th>Action</th>
    </tr>
    <c:forEach var="employee" items="${employees}">
        <tr>
            <td>${employee.name}</td>
            <td>${employee.surname}</td>
            <td>${employee.position}</td>
            <td>
                <button class="btn btn-primary" onclick="location.href='/admin/employee/${employee.id}'">Edit</button>
                <button class="btn btn-danger" onclick="delEmployee(${employee.id})">Delete</button>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
