$(function()
{
	var cLink=window.location.href.split("InternetStore/").pop();
	if(cLink=="") cLink="InternetStore";
	$("nav>ul>li").each(function()
	{
		var div=null;
		$(this).find("div:first-child").each(function()
		{
			div=$(this);
		})
		$(this).find("a").each(function()
		{
			var mLink=$(this).attr("href").split("/").pop();
			if(cLink.includes("options")) cLink="catalog";
			if(cLink.includes("logout")) cLink="sign-in";
			if(cLink.includes(mLink))
			{
				if(div==null) $(this).addClass("active");
				if(div!=null) div.addClass("active");
			}
		})
	});
});