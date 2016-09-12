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
    function delPosition(positionName) {
        if (confirm("Delete position?") == true) {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "${pageContext.request.contextPath}/admin/positions/delete/" + positionName, false);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("");
            if (xhttp.status == 400) {
                window.alert("Position could not be deleted!!!");
            } else {
                location.reload();
            }
            location.reload();
        }
    }
</script>


<form action="${pageContext.request.contextPath}/admin/positions" method="post">
    <input type="text" name="positionName" id="positionName" required value=${positionName}>
    <input type="submit" value="Add Position">
</form>

    <table class="table table-striped">
        <tr>
            <th>Position</th>
            <th></th>
        </tr>
        <c:forEach var="position" items="${positions}">
            <tr>
                <td>${position.positionName}</td>
                <td>
                    <button class="btn btn-danger" onclick="delPosition('${position.positionName}')">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>



</body>
</html>
