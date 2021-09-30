<%@ include file="../imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="../head.jsp" %>
		<title data-i18n="is-epf">Internet Store - Edit Profile</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="epf">Edit Profile</h2>
			<form action="profile" method="post">
				<label class="required">Login:</label>
				<input type="text" name="login" placeholder="${user.login}" pattern="[a-z]{4,8}" title="Must contain from 4 to 8 only lowercase letters" required/>
				<label class="required" data-i18n="pw_">Password:</label>
				<input type="password" name="password" placeholder="New password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required/>
				<label class="required" data-i18n="em_">E-mail:</label>
				<input type="email" name="email" placeholder="${user.email}" required/>
				<label class="required" data-i18n="n_">Name:</label>
				<input type="text" name="firstName" placeholder="${user.firstName}" required/>
				<label class="required" data-i18n="sn_">Surname:</label>
				<input type="text" name="lastName" placeholder="${user.lastName}" required/>
				<label class="required" data-i18n="g_">Gender:</label>
				
				<div>
					<div><input type="radio" name="gender" value="male" ${user.gender?"checked":""} required/><p data-i18n="m">Male</p></div>
					<div><input type="radio" name="gender" value="female" ${user.gender?"":"checked"} required/><p data-i18n="f">Female</p></div>
				</div>
				<input class="form-button edit-button" data-i18n="[value]e" type="submit" value="Edit"/>
				<a class="form-button add-button" data-i18n="b" href="profile">Back</a>
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
		<%@ include file="../footer.jsp" %>
	</body>
</html>