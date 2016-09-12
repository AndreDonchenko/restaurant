<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="header.jsp"/>
<body>

    <table width="100%">
        <th>
            <h3>${dish.dishName}</h3>
            <p><b>Price:  </b><i>${dish.price}</i></p>
            <p><b>Weight: </b><i>${dish.weight}</i></p>
        </th>
        <th>
            <img src="${pageContext.request.contextPath}/resources/dish/${dish.photoFn}" height="120">
        </th>
    </table>

    <table class="table table-striped">
        <tr>
            <th>Ingredients</th>
            <th>Qty</th>
        </tr>
        <c:forEach var="recipe" items="${recipeList}">
            <tr>
                <td>${recipe.ingredient}</td>
                <td>${recipe.qty}</td>
            </tr>
        </c:forEach>
    </table>

    </body>
    </html>
