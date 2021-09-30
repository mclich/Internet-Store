<%@ include file="imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-p">Internet Store - Profile</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="pr">Profile</h2>
			<c:choose>
				<c:when test="${user==null}">
					<form>
						<div class="msg form-button" data-i18n="n-si">You are not signed in</div>
						<a class="form-button submit-button" data-i18n="si" href="sign-in">Sign In</a>
					</form>
				</c:when>
				<c:otherwise>
					<c:if test="${updated!=null&&!updated.isEmpty()}">
						<div class="warn">
							<div data-i18n="u-us">${updated}</div>
							<img src="content/images/exc-mark.png">
						</div>
						<c:set var="updated" value="" scope="session"/>
            		</c:if>
					<form>
						<label data-i18n="li_">Login:</label>
						<input type="text" value="${user.login}" disabled/>
						<label data-i18n="em_">E-mail:</label>
						<input type="email" value="${user.email}" disabled/>
						<label data-i18n="n_">Name:</label>
						<input type="text" value="${user.firstName}" disabled/>
						<label data-i18n="sn_">Surname:</label>
						<input type="text" value="${user.lastName}" disabled/>
						<label data-i18n="g_">Gender:</label>
						<div>${user.gender?"Male":"Female"}</div>
						<a class="form-button add-button" data-i18n="e" href="edit-profile">Edit</a>
						<a class="form-button submit-button" data-i18n="so" href="user-orders?login=${user.login}&back=profile">Show orders</a>
						<a class="form-button edit-button" data-i18n="lo" href="logout">Logout</a>
					</form>
				</c:otherwise>
			</c:choose>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>