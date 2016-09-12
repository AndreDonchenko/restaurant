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

    <h3>Add new menu</h3>
    <form action="menu" method="post">
        <input type="text" name="menuName" id="menuName" required value=${menuName}>
        <input type="submit" value="Add Menu">
    </form>
    <hr>

    <table class="table table-striped">
        <tr>
            <th>Menu</th>
            <th>Action</th>
        </tr>
        <c:forEach var="menuItem" items="${menu}">
            <tr>
                <td>${menuItem.menuName}</td>
                <td>
                    <button class="btn btn-primary" onclick="location.href='/admin/menu/edit/${menuItem.id}'">Edit</button>
                    <button class="btn btn-danger" onclick="location.href='/admin/menu/delete/${menuItem.id}'">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>


</body>
</html>
