<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<h1>Internet Store</h1>
	<img src="content/images/icon.png" alt="Internet Store">
</header>
<nav>
	<ul>
		<li><a href="../InternetStore">Main Page</a></li>
		<li><a href="catalog">Catalog</a></li>
		<c:if test="${user!=null}">
			<c:forEach var="role" items="${user.getRoles()}">
  				<c:if test="${role==admin}">
    				<li><a href="user-list">Users</a></li>
  				</c:if>
			</c:forEach>
		</c:if>
	</ul>
	<ul>
		<li><a href="${user==null?'sign-in':'logout'}">${user==null?"Sign In / Register":"Logout"}</a></li>
		<li class="profile"><div><a href="profile">&nbsp;</a></div></li>
		<li class="cart"><div><a href="cart">&nbsp;</a></div></li>
	</ul>
</nav>