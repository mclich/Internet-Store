<%@ include file="imports.jsp" %>
<%@ page isErrorPage="true" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Register</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Register</h2>
			<form action="register" method="post">
				<label class="required">Login:</label>
				<input type="text" name="login" pattern="[a-z]{4,8}" title="Must contain from 4 to 8 only lowercase letters" required/>
				<label class="required">Password:</label>
				<input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required/>
				<label class="required">E-mail:</label>
				<input type="email" name="email" required/>
				<label class="required">Name:</label>
				<input type="text" name="firstName" required/>
				<label class="required">Surname:</label>
				<input type="text" name="lastName" required/>
				<label class="required">Gender:</label>
				<div>
					<p><input type="radio" name="gender" value="male" checked required/> Male</p>
					<p><input type="radio" name="gender" value="female" required/> Female</p>
				</div>
				<input class="form-button add-button" type="submit" value="Register"/>
				<a class="form-button edit-button" href="sign-in">Back</a>
				<c:if test="${existing!=null&&!existing.isEmpty()}">
					<label class="error">${existing}</label>
					<c:set var="existing" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>