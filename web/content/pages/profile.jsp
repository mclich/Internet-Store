<%@ include file="imports.jsp" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Profile</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Profile</h2>
			<c:choose>
				<c:when test="${user==null}">
					<form>
						<div class="msg form-button">You are not signed in</div>
						<a class="form-button submit-button" href="sign-in">Sign In</a>
						<!-- <a class="form-button add-button" href="register">Register</a> -->
					</form>
				</c:when>
				<c:otherwise>
					<c:if test="${updated!=null&&!updated.isEmpty()}">
						<div class="warn">
							<div>${updated}</div>
							<img src="content/images/exc-mark.png">
						</div>
						<c:set var="updated" value="" scope="session"/>
            		</c:if>
					<form>
						<label>Login:</label>
						<input type="text" value="${user.login}" disabled/>
						<label>E-mail:</label>
						<input type="email" value="${user.email}" disabled/>
						<label>Name:</label>
						<input type="text" value="${user.firstName}" disabled/>
						<label>Surname:</label>
						<input type="text" value="${user.lastName}" disabled/>
						<label>Gender:</label>
						<div>${user.gender?"Male":"Female"}</div>
						<a class="form-button add-button" href="edit-profile">Edit</a>
						<a class="form-button submit-button" href="user-orders?login=${user.login}&back=profile">Show orders</a>
						<a class="form-button edit-button" href="logout">Logout</a>
					</form>
				</c:otherwise>
			</c:choose>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>