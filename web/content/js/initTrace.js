$("main>textarea").hide();
$("main>.add-button").click(function()
{
	$(this).siblings("textarea").toggle();
});