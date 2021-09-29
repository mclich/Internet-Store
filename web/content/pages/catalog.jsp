<%@ include file="imports.jsp" %>
<html>
	<head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Catalog</title>
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Catalog</h2>
			<c:set var="createNew" value="${false}"/>
			<c:if test="${actionMsg!=null&&!actionMsg.isEmpty()}">
				<div class="warn">
					<div>${actionMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="actionMsg" value="" scope="session"/>
            </c:if>
			<table>
				<thead>
					<tr>
						<th class="sortable">Name</th>
						<th class="sortable">Price</th>
						<th class="sortable">Addition Date</th>
						<th class="sortable">Category</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${catalog}">
						<tr>
							<td><p>${product.name}</p></td>
							<td><p>${product.price}</p></td>
							<td><p>${product.additionDate}</p></td>
							<td><p>${product.category.name}</p></td>
							<td>
								<ul class="actions">
									<li><a class="add-button" href="options?action=add&product=${product.id}">Add to cart</a></li>
									<c:if test="${user!=null}">
										<c:forEach var="role" items="${user.getRoles()}">
  											<c:if test="${role==admin}">
  												<c:set var="createNew" value="${true}"/>
    											<li><a class="edit-button" href="options?action=edit&product=${product.id}">Edit</a></li>
												<li><a class="delete-button" href="options?action=delete&product=${product.id}">Delete</a></li>
  											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${createNew}">
				<a href="options?action=create" class="submit-button">Create new product</a>
			</c:if>
			<c:set var="catalog" value="" scope="request"/>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/setSorting.js"></script>
	</body>
</html>