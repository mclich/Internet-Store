var totalPrice=$(".m-cart-sum label:nth-child(2)");
console.log(totalPrice)
$(".m-cart .delete").click(function()
{
	var id=parseInt($(this).parents(".m-cart").find("th:nth-child(4)").text(), 10);
	var xhttp=new XMLHttpRequest();
	xhttp.open("GET", "cart?delete="+id, true);
	xhttp.send();
	document.location.replace("cart");
});
$(".m-cart .counter .increase").click(function()
{
	var flag=true;
	var id=parseInt($(this).siblings(":nth-child(4)").text(), 10);
	$(this).siblings(":nth-child(2)").text(function()
	{
		var value=parseInt($(this).text(), 10);
		if(value>=100)
		{
			flag=false;
			return value;
		}
		else
		{
			var form=$(this).parents("form");
			var price=parseFloat(form.find("input:nth-child(6)").val());
			form.siblings("div").find(".p-price").text(function()
			{
				return (parseFloat($(this).text())+price).toFixed(2);
			});
			totalPrice.text(function()
			{
				return (parseFloat($(this).text())+price).toFixed(2);
			});
		}
		return value+1;
	});
	if(flag)
	{
		var xhttp=new XMLHttpRequest();
		xhttp.open("GET", "cart?add="+id, true);
		xhttp.send();
	}
});
$(".m-cart .counter .decrease").click(function()
{
	var flag=true;
	var id=parseInt($(this).siblings(":nth-child(4)").text(), 10);
	$(this).siblings(":nth-child(2)").text(function()
	{
		var value=parseInt($(this).text(), 10);
		if(value<=1)
		{
			flag=false;
			return value;
		}
		else
		{
			var form=$(this).parents("form");
			var price=parseFloat(form.find("input:nth-child(6)").val());
			form.siblings("div").find(".p-price").text(function()
			{
				return (parseFloat($(this).text())-price).toFixed(2);
			});
			totalPrice.text(function()
			{
				return (parseFloat($(this).text())-price).toFixed(2);
			});
		}
		return value-1;
	});
	if(flag)
	{
		var xhttp=new XMLHttpRequest();
		xhttp.open("GET", "cart?remove="+id, true);
		xhttp.send();
	}
});