<%@ include file="imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is">Internet Store</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="mp">Main Page</h2>
			<c:if test="${loggedAs!=null&&!loggedAs.isEmpty()}">
				<div class="warn">
					<div>
						<div data-i18n="l-as">You are logged in as</div>
						<div>&nbsp;${loggedAs}</div>
					</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="loggedAs" value="" scope="session"/>
            </c:if>
			<p data-i18n="dc">
				Description...
			</p>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>