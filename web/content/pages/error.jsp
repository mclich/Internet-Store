<%@ include file="imports.jsp" %>
<%@ page isErrorPage="true" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-err">Internet Store - Error</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl-error" data-i18n="err">An error occurred while processing a request:</h2>
			<div class="warn">
				<div>${exception!=null?exception.getMessage():pageContext.exception.message}</div>
				<img src="content/images/exc-mark.png">
			</div>
			<p class="add-button" data-i18n="st">Show stack trace</p>
			<c:set var="trace" value=""/>
			<c:set var="size" value="${0}"/>
			<c:forEach var="msg" items="${exception!=null?exception.getStackTrace():pageContext.exception.stackTrace}">
				<c:set var="trace" value="${trace}&#10;${msg}"/>
				<c:set var="size" value="${size+1}"/>
			</c:forEach>
			<textarea id="trace" rows="${size}">${trace}</textarea>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/initTrace.js"></script>
	</body>
</html>