<%@ include file="imports.jsp" %>
<html lang="${lang}">
	<head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-cr">Internet Store - Cart</title>
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="cps">Cart Products</h2>
			<c:if test="${purchase!=null&&!purchase.isEmpty()}">
				<div class="warn">
					<div data-i18n="ocs">${purchase}</div>
					<img src="content/images/exc-mark.png">
				</div>
				<c:set var="purchase" value="" scope="session"/>
            </c:if>
			<c:if test="${cartProducts.isEmpty()}">
				<div class="msg" data-i18n="ce">Cart is currently empty</div>
			</c:if>
			<c:set var="totalPrice" value="${0.0}"/>
			<c:forEach var="products" items="${cartProducts}">
				<div class="m-cart">
					<form>
						<label data-i18n="n_">Name:</label>
						<input type="text" value="${products.key.name}" disabled/>
						<label data-i18n="cg_">Category:</label>
						<input type="text" value="${products.key.category.name}" disabled/>
						<label data-i18n="p_">Price:</label>
						<input type="text" value="${products.key.price}" disabled/>
						<label data-i18n="c_">Count:</label>
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
						<div class="p-price">${floatFormatter.format(products.key.price*products.value)}</div>
						<c:set var="totalPrice" value="${totalPrice+products.key.price*products.value}"/>
					</div>
				</div>
				<h2 class="hl"></h2>
			</c:forEach>
			<c:if test="${!cartProducts.isEmpty()}">
				<div class="m-cart-sum">
					<label data-i18n="tp_">Total price:</label>
					<label>${floatFormatter.format(totalPrice)}</label>
				</div>
				<c:set var="onBuy" value="cart"/>
				<c:set var="method" value="post"/>
				<c:if test="${user==null}">
					<c:set var="onBuy" value="sign-in"/>
					<c:set var="method" value="get"/>
					<c:set var="cannotLogin" value="You are not signed in" scope="session"/>
				</c:if>
				<form id="m-cart-submit" action="${onBuy}" method="${method}"><input class="submit-button" data-i18n="[value]pch" type="submit" value="Purchase"/></form>
				<!--
				<form id="m-cart-submit" action="cart" method="post">
					<c:choose>
	         			<c:when test="${user==null}">
							<a class="submit-button" data-i18n="pch" href="sign-in?no-user">Purchase</a>
						</c:when>
						<c:otherwise>
							<input class="submit-button" data-i18n="[value]pch" type="submit" value="Purchase"/>
						</c:otherwise>
					</c:choose>
				</form>
				-->
			</c:if>
		</main>
		<%@ include file="footer.jsp" %>
		<script src="content/js/initCart.js"></script>
	</body>
</html>