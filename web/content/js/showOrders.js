$(".hidden").hide();
$(".show-orders").click(function()
{
	var i=$(this).parents("tr").index()/2;
	$("#tr"+i).toggle();
});