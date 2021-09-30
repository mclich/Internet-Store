<%@ include file="imports.jsp" %>
<%@ page isErrorPage="true" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-r">Internet Store - Register</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="rr">Register</h2>
			<form action="register" method="post">
				<label class="required" data-i18n="li_">Login:</label>
				<input type="text" name="login" pattern="[a-z]{4,8}" title="Must contain from 4 to 8 only lowercase letters" required/>
				<label class="required" data-i18n="pw_">Password:</label>
				<input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required/>
				<label class="required" data-i18n="em_">E-mail:</label>
				<input type="email" name="email" required/>
				<label class="required" data-i18n="n_">Name:</label>
				<input type="text" name="firstName" required/>
				<label class="required" data-i18n="sn_">Surname:</label>
				<input type="text" name="lastName" required/>
				<label class="required" data-i18n="g_">Gender:</label>
				<div>
					<div><input type="radio" name="gender" value="male" checked required/><p data-i18n="m">Male</p></div>
					<div><input type="radio" name="gender" value="female" required/><p data-i18n="f">Female</p></div>
				</div>
				<input class="form-button add-button" data-i18n="[value]rr" type="submit" value="Register"/>
				<a class="form-button edit-button" data-i18n="b" href="sign-in">Back</a>
				<c:if test="${existingLE!=null&&!existingLE.isEmpty()}">
					<label class="error" data-i18n="le-e">${existingLE}</label>
					<c:set var="existingLE" value="" scope="session"/>
            	</c:if>
            	<c:if test="${existingL!=null&&!existingL.isEmpty()}">
					<label class="error" data-i18n="l-e">${existing}</label>
					<c:set var="existingL" value="" scope="session"/>
            	</c:if>
            	<c:if test="${existingE!=null&&!existingE.isEmpty()}">
					<label class="error" data-i18n="e-e">${existing}</label>
					<c:set var="existingE" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>