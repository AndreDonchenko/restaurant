<head>
<title>GoIT Restaurant</title>

<link href="${pageContext.request.contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet" />
<link href=${pageContext.request.contextPath}/resources/core/css/hello.css" rel="stylesheet" />
</head>
<style>
	p {
		padding: 1px 10px
	}
</style>
<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Admin panel</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/main">Main</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/map">Map</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/personel">Staff</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/contacts">Contacts</a></li>
			</ul>
		</div>
	</div>
</nav>
<a href="/">
	<img src="${pageContext.request.contextPath}/resources/logo.png">
</a>