$("#lang-s").change(function()
{
	var xhttp=new XMLHttpRequest();
	xhttp.open("POST", "lang?lang="+$(this).val(), true);
	xhttp.send();
	document.location.reload();
});