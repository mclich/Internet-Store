<%@ include file="imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-u">Internet Store - Users</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="ul">User List</h2>
			<table>
				<thead>
					<tr>
						<th class="sortable" data-i18n="li">Login</th>
						<th class="sortable" data-i18n="n">Name</th>
						<th class="sortable" data-i18n="sn">Surname</th>
						<th class="sortable" data-i18n="em">E-mail</th>
						<th class="sortable" data-i18n="g">Gender</th>
						<th class="sortable" data-i18n="r">Roles</th>
						<th data-i18n="as">Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach varStatus="iteration" var="cUser" items="${userList}">
						<tr>
							<td><p>${cUser.login}</p></td>
							<td><p>${cUser.firstName}</p></td>
							<td><p>${cUser.lastName}</p></td>
							<td><p>${cUser.email}</p></td>
							<td><p>${cUser.gender?'Male':'Female'}</p></td>
							<td><p>${cUser.getRolesString()}</p></td>
							<td>
								<ul class="actions">
									<li><a class="show-orders add-button" data-i18n="so" href="user-orders?login=${cUser.login}&back=user-list">Show orders</a></li>
									<li><a class="delete-button" data-i18n="bu" href="">Block user</a></li>
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/setSorting.js"></script>
	</body>
</html>