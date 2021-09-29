<%@ include file="imports.jsp" %>
<html>
    <head>
		<%@ include file="head.jsp" %>
		<title>Internet Store - Orders</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl">Orders</h2>
			<c:choose>
         		<c:when test="${orders==null||orders.isEmpty()}">
         			<form>
						<div class="msg form-button">${fullName} does not have any orders yet</div>
						<a class="form-button add-button" href="${param['back']}">Back</a>
					</form>
         		</c:when>
				<c:otherwise>
					<table>
						<thead>
							<tr>
								<th>Orderer</th>
								<th>Name</th>
								<th>Count</th>
								<th>Price</th>
								<th>Total Price</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<c:set var="totalPrice" value="${0.0}"/>
								<c:set var="statusClass" value="processed"/>
								<c:forEach var="pTmp" items="${order.products}">
									<c:set var="totalPrice" value="${totalPrice+pTmp.key.price*pTmp.value}"/>
								</c:forEach>
								<c:forEach varStatus="i" var="product" items="${order.products}">
									<tr>
										<c:if test="${i.index==0}"><td rowspan="${order.products.size()}"><p>${order.orderer.firstName} ${order.orderer.lastName}</p></td></c:if>
										<td><p>${product.key.name}</p></td>
										<td><p>${product.value}</p></td>
										<td><p>${product.key.price}</p></td>
										<c:if test="${i.index==0}"><td rowspan="${order.products.size()}"><p>${priceFormatter.format(totalPrice)}</p></td></c:if>
										<c:if test="${i.index==0}">
											<c:if test="${order.status.toString().equals('Paid')}"><c:set var="statusClass" value="paid"/></c:if>
											<c:if test="${order.status.toString().equals('Cancelled')}"><c:set var="statusClass" value="cancelled"/></c:if>
											<td rowspan="${order.products.size()}"><p class="${statusClass}">${order.status.toString()}</p></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<a class="add-button" href="${param['back']}">Back</a>
				</c:otherwise>
			</c:choose>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>