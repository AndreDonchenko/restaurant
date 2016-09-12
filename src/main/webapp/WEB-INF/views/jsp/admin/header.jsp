<head>
<title>Restaurant Admin Panel</title>

<link href="${pageContext.request.contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet" />
<link href=${pageContext.request.contextPath}/resources/core/css/hello.css" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">Official Restaurant</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/admin/menu">Menu</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/dish">Dishes</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/employees">Employee</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/stock">Stock</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/orders">Orders</a></li>
			</ul>
		</div>
	</div>
</nav>