<%@ include file="../imports.jsp" %>
<html>
    <head>
		<%@ include file="../head.jsp" %>
		<title>Internet Store - Create Product</title>
	</head>
    <body>
		<%@ include file="../header.jsp" %>
		<main>
			<h2 class="hl">Create Product</h2>
			<form action="options?action=create" method="post">
				<label class="required">Name: </label>
				<input type="text" name="name" required/>
				<label class="required">Price:</label>
				<input type="number" min="0.10" step="0.01" name="price" required/>
				<label class="required">Category:</label>
				<select name="category" required>
					<option value="no-option" selected hidden="">Select category</option>
					<c:if test="${categories!=null}">
						<c:forEach var="category" items="${categories}">
    						<option value="${category.name}">${category.name}</option>
						</c:forEach>
					</c:if>
				</select>
				<input class="form-button submit-button" type="submit" value="Create"/>
				<a class="form-button add-button" href="catalog">Back</a>
				<c:if test="${noCategory!=null}">
					<label class="error">${noCategory}</label>
					<c:set var="noCategory" value="" scope="session"/>
            	</c:if>
			</form>
		</main>
		<%@ include file="../footer.jsp" %>
	</body>
</html>