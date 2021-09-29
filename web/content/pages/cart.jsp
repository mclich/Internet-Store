<%@ include file="imports.jsp" %>
<html>
	<head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Cart</title>
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Cart Products</h2>
			<c:if test="${purchase!=null&&!purchase.isEmpty()}">
				<div class="warn">
					<div>${purchase}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="purchase" value="" scope="session"/>
            </c:if>
			<c:if test="${cartProducts.isEmpty()}">
				<div class="msg">Cart is currently empty</div>
			</c:if>
			<c:set var="totalPrice" value="${0.0}"/>
			<c:forEach var="products" items="${cartProducts}">
				<div class="m-cart">
					<form>
						<label>Name:</label>
						<input type="text" value="${products.key.name}" disabled/>
						<label>Category:</label>
						<input type="text" value="${products.key.category.name}" disabled/>
						<label>Price:</label>
						<input type="text" value="${products.key.price}" disabled/>
						<label>Count:</label>
						<table class="counter">
							<thead>
								<tr>
									<th class="decrease">-</th>
									<th>${products.value}</th>
									<th class="increase">+</th>
									<th hidden="">${products.key.id}</th>
								</tr>
							</thead>
						</table>
					</form>
					<div>
						<div class="delete">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="p-price">${priceFormatter.format(products.key.price*products.value)}</div>
						<c:set var="totalPrice" value="${totalPrice+products.key.price*products.value}"/>
					</div>
				</div>
				<h2 class="hl"></h2>
			</c:forEach>
			<c:if test="${!cartProducts.isEmpty()}">
				<div class="m-cart-sum">
					<label>Total price:</label>
					<label>${priceFormatter.format(totalPrice)}</label>
				</div>
				<c:set var="onBuy" value="cart"/>
				<c:set var="method" value="post"/>
				<c:if test="${user==null}">
					<c:set var="onBuy" value="sign-in"/>
					<c:set var="method" value="get"/>
					<c:set var="logMsg" value="You are not signed in" scope="session"/>
				</c:if>
				<form id="m-cart-submit" action="${onBuy}" method="${method}"><input class="submit-button" type="submit" value="Purchase"/></form>
			</c:if>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/initCart.js"></script>
	</body>
</html>