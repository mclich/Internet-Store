<%@ include file="../imports.jsp" %>
<html>
    <head>
		<%@ include file="../head.jsp" %>
		<title>Internet Store - Edit Product</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl">Edit Product</h2>
			<form action="options?action=edit&product=${currProduct.id}" method="post">
				<label class="required">Name: </label>
				<input type="text" value="${currProduct.name}" name="name" required/>
				<label class="required">Price:</label>
				<input type="number" min="0.10" step="0.01" value="${currProduct.price}" name="price" required/>
				<label class="required">Category:</label>
				<select name="category" required>
					<c:if test="${categories!=null}">
						<c:forEach var="category" items="${categories}">
							<option value="${category.name}" ${category.name.equals(currProduct.category.name)?'selected':''}>${category.name}</option>
						</c:forEach>
					</c:if>
				</select>
				<input class="form-button edit-button" type="submit" value="Edit"/>
				<a class="form-button add-button" href="catalog">Back</a>
				<c:if test="${noChanges!=null}">
					<label class="error">${noChanges}</label>
					<c:set var="noChanges" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="../footer.jsp" %>
	</body>
</html>