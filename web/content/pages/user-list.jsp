<%@ include file="imports.jsp" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Users</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">User List</h2>
			<table>
				<thead>
					<tr>
						<th class="sortable">Login</th>
						<th class="sortable">First Name</th>
						<th class="sortable">Last Name</th>
						<th class="sortable">E-mail</th>
						<th class="sortable">Gender</th>
						<th class="sortable">Roles</th>
						<th>Actions</th>
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
									<li><a class="show-orders add-button" href="user-orders?login=${cUser.login}&back=user-list">Show orders</a></li>
									<li><a class="delete-button" href="">Block user</a></li>
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