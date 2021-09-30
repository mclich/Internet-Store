<%@ include file="imports.jsp" %>
<html lang="${lang}">
    <head>
		<%@ include file="head.jsp" %>
		<title data-i18n="is-o">Internet Store - Orders</title>
	</head>
    <body>
		<%@ include file="header.jsp" %>
		<main>
			<h2 class="hl" data-i18n="o">Orders</h2>
			<c:choose>
         		<c:when test="${orders==null||orders.isEmpty()}">
         			<form>
						<div class="msg form-button">
							<div>${fullName}&nbsp;</div>
							<div data-i18n="no">does not have any orders yet</div>
						</div>
						<a class="form-button add-button" data-i18n="b" href="${param['back']}">Back</a>
					</form>
         		</c:when>
				<c:otherwise>
					<c:set var="isAdmin" value="${false}"/>
					<c:forEach var="role" items="${user.getRoles()}">
  						<c:if test="${role==admin}">
  							<c:set var="isAdmin" value="${true}"/>
  						</c:if>
					</c:forEach>
					<table>
						<thead>
							<tr>
								<th data-i18n="or">Orderer</th>
								<th data-i18n="n">Name</th>
								<th data-i18n="c">Count</th>
								<th data-i18n="p">Price</th>
								<th data-i18n="tp">Total Price</th>
								<th data-i18n="s">Status</th>
								<c:if test="${isAdmin}"><th data-i18n="as">Actions</th></c:if>
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
										<c:if test="${i.index==0}"><td rowspan="${order.products.size()}"><p>${floatFormatter.format(totalPrice)}</p></td></c:if>
										<c:if test="${i.index==0}">
											<c:if test="${order.status.toString().equals('Paid')}"><c:set var="statusClass" value="paid"/></c:if>
											<c:if test="${order.status.toString().equals('Cancelled')}"><c:set var="statusClass" value="cancelled"/></c:if>
											<td rowspan="${order.products.size()}"><p class="${statusClass}" data-i18n="${statusClass}">${order.status.toString()}</p></td>
										</c:if>
										<c:if test="${i.index==0&&isAdmin}">
											<td rowspan="${order.products.size()}">
												<ul class="actions">
													<li><a class="add-button" data-i18n="cs" href="user-orders?login=${param.login}&order=${order.id}&back=user-list">Change Status</a></li>
												</ul>
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<a class="add-button" data-i18n="b" href="${param['back']}">Back</a>
				</c:otherwise>
			</c:choose>
		</main>
		<%@ include file="footer.jsp" %>
	</body>
</html>