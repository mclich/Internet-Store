<%@ include file="../imports.jsp" %>
<html>
    <head>
		<%@ include file="../head.jsp" %>
		<title>Internet Store - Edit Profile</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl">Edit Profile</h2>
			<form action="profile" method="post">
				<label class="required">Login:</label>
				<input type="text" name="login" placeholder="${user.login}" pattern="[a-z]{4,8}" title="Must contain from 4 to 8 only lowercase letters" required/>
				<label class="required">Password:</label>
				<input type="password" name="password" placeholder="New password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required/>
				<label class="required">E-mail:</label>
				<input type="email" name="email" placeholder="${user.email}" required/>
				<label class="required">Name:</label>
				<input type="text" name="firstName" placeholder="${user.firstName}" required/>
				<label class="required">Surname:</label>
				<input type="text" name="lastName" placeholder="${user.lastName}" required/>
				<label class="required">Gender:</label>
				<div>
					<p><input type="radio" name="gender" value="male" ${user.gender?"checked":""} required/> Male</p>
					<p><input type="radio" name="gender" value="female" ${user.gender?"":"checked"} required/> Female</p>
				</div>
				<input class="form-button edit-button" type="submit" value="Edit"/>
				<a class="form-button add-button" href="profile">Back</a>
				<c:if test="${existing!=null&&!existing.isEmpty()}">
					<label class="error">${existing}</label>
					<c:set var="existing" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="../footer.jsp" %>
	</body>
</html>