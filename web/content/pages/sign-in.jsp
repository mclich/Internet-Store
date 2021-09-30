<%@ include file="imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-si">Internet Store - Sign In</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="si">Sign In</h2>
			<c:if test="${cannotLogin!=null&&!cannotLogin.isEmpty()}">
				<div class="warn">
					<div data-i18n="n-si">${cannotLogin}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="cannotLogin" value="" scope="session"/>
            </c:if>
			<c:if test="${logMsg!=null&&!logMsg.isEmpty()}">
				<div class="warn">
					<div data-i18n="s-lo">${logMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="logMsg" value="" scope="session"/>
            </c:if>
			<form action="sign-in" method="post">
				<label data-i18n="li_">Login:</label>
            	<input type="text" name="login" required/>
            	<label data-i18n="pw_">Password:</label>
            	<input type="password" name="password" required/>
            	<input class="form-button submit-button" data-i18n="[value]li" type="submit" value="Login"/>
            	<a class="form-button add-button" data-i18n="rr" href="register">Register</a>
            	<c:if test="${userNotExist!=null&&!userNotExist.isEmpty()}">
					<label class="error" data-i18n="u-ne">${userNotExist}</label>
					<c:set var="userNotExist" value="" scope="session"/>
            	</c:if>
            	<c:if test="${wrongPw!=null&&!wrongPw.isEmpty()}">
					<label class="error" data-i18n="w-pw">${userNotExist}</label>
					<c:set var="wrongPw" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>