<%@ include file="imports.jsp" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Main Page</h2>
			<c:if test="${loggedAs!=null&&!loggedAs.isEmpty()}">
				<div class="warn">
					<div>${loggedAs}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="loggedAs" value="" scope="session"/>
            </c:if>
			<p>
				Description...
			</p>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>