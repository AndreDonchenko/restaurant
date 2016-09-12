
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="header.jsp"/>
<body>
    <h3> Наш персонал</h3>

    <table class="table table-striped">
        <tr>
            <th>Имя</th>
            <th>Фото</th>
        </tr>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td>${employee.name}</td>
                <td><img src="/resources/employee/${employee.photoFn}" height="120"></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
