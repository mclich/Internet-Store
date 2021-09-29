<%@ include file="imports.jsp" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Sign In</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Sign In</h2>
			<c:if test="${logMsg!=null&&!logMsg.isEmpty()}">
				<div class="warn">
					<div>${logMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="logMsg" value="" scope="session"/>
            </c:if>
			<form action="sign-in" method="post">
				<label class="required">Login:</label>
            	<input type="text" name="login" required/>
            	<label class="required">Password:</label>
            	<input type="password" name="password" required/>
            	<input class="form-button submit-button" type="submit" value="Login"/>
            	<a class="form-button add-button" href="register">Register</a>
            	<c:if test="${userNotFound!=null&&!userNotFound.isEmpty()}">
					<label class="error">${userNotFound}</label>
					<c:set var="userNotFound" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>