<%@ include file="../imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="../head.jsp" %>
		<title data-i18n="is-epd">Internet Store - Edit Product</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="epd">Edit Product</h2>
			<form action="options?action=edit&product=${currProduct.id}" method="post">
				<label class="required" data-i18n="n_">Name: </label>
				<input type="text" value="${currProduct.name}" name="name" required/>
				<label class="required" data-i18n="p_">Price:</label>
				<input type="number" min="0.10" step="0.01" value="${currProduct.price}" name="price" required/>
				<label class="required" data-i18n="cg_">Category:</label>
				<select name="category" required>
					<c:if test="${categories!=null}">
						<c:forEach var="category" items="${categories}">
							<option value="${category.name}" ${category.name.equals(currProduct.category.name)?'selected':''}>${category.name}</option>
						</c:forEach>
					</c:if>
				</select>
				<input class="form-button edit-button" data-i18n="[value]e" type="submit" value="Edit"/>
				<a class="form-button add-button" data-i18n="b" href="catalog">Back</a>
				<c:if test="${noChanges!=null}">
					<label data-i18n="n-ch" class="error">${noChanges}</label>
					<c:set var="noChanges" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="../footer.jsp" %>
	</body>
</html>