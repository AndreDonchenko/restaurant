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
    function reloadPhoto() {
        document.getElementById('photo').src = "${pageContext.request.contextPath}/resources/employee/" + document.getElementById('photoFn').value;
    }
</script>
<h3>Edit Employee</h3>
<form:form action="${pageContext.request.contextPath}/admin/employees" commandName="employee" method="post">
    <input type="hidden" name="id" id="id" value=${employee.id}>

    <label for="name">First Name:</label>
    <input type="text" name="name" id="name" required value=${employee.name}>
    <br>
    <label for="surname">Last Name:</label>
    <input type="text" name="surname" id="surname" required value=${employee.surname}>
    <br>
    <label for="birthDay">Birthday:</label>
    <input type="date" name="birthDay" id="birthDay" required value=${employee.birthDay}>
    <br>
    <label for="phone">Phone:</label>
    <input type="text" name="phone" id="phone" required value=${employee.phone}>
    <br>
    <label for="salary">Salary:</label>
    <input type="number" name="salary" id="salary" required value=${employee.salary}>
    <br>
    <a href="${pageContext.request.contextPath}/admin/positions">Position:</a>
    <form:select path="position">
        <form:options items="${positionList}" />
    </form:select>
    <br>
    <form:label path="photoFn">Photo:</form:label>
    <form:select path="photoFn" onchange="reloadPhoto()">
        <form:option value="NONE" label="--- Select ---" />
        <form:options items="${photoFiles}" />
    </form:select>
    <br>
    <img name="photo" id="photo" src="${pageContext.request.contextPath}/resources/employee/${employee.photoFn}" height="120">
    <br>
    <input type="submit" value="Submit">
    </form:form>

    </body>
    </html>
