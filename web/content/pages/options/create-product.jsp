<%@ include file="../imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="../head.jsp" %>
		<title data-i18n="is-cp">Internet Store - Create Product</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="cp">Create Product</h2>
			<form action="options?action=create" method="post">
				<label class="required" data-i18n="n_">Name: </label>
				<input type="text" name="name" required/>
				<label class="required" data-i18n="p_">Price:</label>
				<input type="number" min="0.10" step="0.01" name="price" required/>
				<label class="required" data-i18n="cg_">Category:</label>
				<select name="category" required>
					<option data-i18n="scg" value="no-option" selected hidden="">Select category</option>
					<c:if test="${categories!=null}">
						<c:forEach var="category" items="${categories}">
    						<option value="${category.name}">${category.name}</option>
						</c:forEach>
					</c:if>
				</select>
				<input class="form-button submit-button" data-i18n="[value]cp" type="submit" value="Create Product"/>
				<a class="form-button add-button" data-i18n="b" href="catalog">Back</a>
				<c:if test="${noCategory!=null}">
					<label class="error" data-i18n="cg-ns">${noCategory}</label>
					<c:set var="noCategory" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="../footer.jsp" %>
	</body>
</html>