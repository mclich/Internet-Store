<%@ include file="imports.jsp" %>
<html lang="${lang}">
	<head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-cl">Internet Store - Catalog</title>
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="cl">Catalog</h2>
			<c:set var="createNew" value="${false}"/>
			<c:if test="${createMsg!=null&&!createMsg.isEmpty()}">
				<div class="warn">
					<div data-i18n="pcs">${createMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="createMsg" value="" scope="session"/>
            </c:if>
            <c:if test="${editMsg!=null&&!editMsg.isEmpty()}">
				<div class="warn">
					<div data-i18n="pec">${editMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="editMsg" value="" scope="session"/>
            </c:if>
            <c:if test="${deleteMsg!=null&&!deleteMsg.isEmpty()}">
				<div class="warn">
					<div data-i18n="pds">${deleteMsg}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="deleteMsg" value="" scope="session"/>
            </c:if>
			<table>
				<thead>
					<tr>
						<th class="sortable" data-i18n="n">Name</th>
						<th class="sortable" data-i18n="p">Price</th>
						<th class="sortable" data-i18n="ad">Addition Date</th>
						<th class="sortable" data-i18n="cg">Category</th>
						<th data-i18n="as">Actions</th>
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
									<li><a class="add-button" data-i18n="ac" href="options?action=add&product=${product.id}">Add to cart</a></li>
									<c:if test="${user!=null}">
										<c:forEach var="role" items="${user.getRoles()}">
  											<c:if test="${role==admin}">
  												<c:set var="createNew" value="${true}"/>
    											<li><a class="edit-button" data-i18n="e" href="options?action=edit&product=${product.id}">Edit</a></li>
												<li><a class="delete-button" data-i18n="d" href="options?action=delete&product=${product.id}">Delete</a></li>
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
				<a class="submit-button" data-i18n="np" href="options?action=create">Create new product</a>
			</c:if>
			<c:set var="catalog" value="" scope="request"/>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/setSorting.js"></script>
	</body>
</html>