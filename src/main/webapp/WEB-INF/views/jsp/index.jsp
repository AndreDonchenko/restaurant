
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="header.jsp"/>
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

    function applyFilter() {
        if (document.getElementById('filter').value != "") {
            location.replace("?filter=" + document.getElementById('filter').value)
        } else {
            location.replace(location.href.replace(location.search, ''));
        }
    }
</script>

    <p>г.Киев, проспект академика Палладина 46 В (Пересечение проспекта академика Палладина и ул. Плодовая)</p>
    <p>Телефон для информации и заказа столов и банкетов (096) 404-3004</p>
    <p>email:<a href="mailto:info@korchma.tk">info@korchma.tk</a></p>
<%--
    <label for="filter">Найти блюдо по названию:</label>
    <input type="text" name="filter" id="filter" onchange="applyFilter()">
--%>
    <table class="table table-striped">
        <tr>
            <th>Меню</th>
            <th>Блюда &nbsp <input type="search" name="filter" id="filter" placeholder="Поиск блюда" onchange="applyFilter()"></th>
            <th>Вес</th>
            <th>Цена</th>
        </tr>
        <c:forEach var="menu" items="${menuList}">
            <tr>
                <td>${menu.menuName}</td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <c:forEach var="dish" items="${menu.dishes}">
                <tr>
                    <td></td>
                    <td><a href="dish/${dish.id}">${dish.dishName}</a></td>
                    <td>${dish.weight}</td>
                    <td>${dish.price}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>

</body>
</html>
