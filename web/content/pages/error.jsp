<%@ include file="imports.jsp" %>
<%@ page isErrorPage="true" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Error</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl-error">An error occurred while processing a request:</h2>
			<div class="warn">
				<div>
					<c:choose>
						<c:when test="${exception!=null}">${exception.getMessage()}</c:when>
						<c:otherwise>${pageContext.exception.message}</c:otherwise>
					</c:choose>
				</div>
				<img src="content/images/exc-mark.png">
			</div>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>