var flag=-1;
var prev="";
$("th.sortable").click(function()
{
	if(prev!=$(this).text()) flag=-1;
	flag*=-1;
	$("th.sortable").not($(this)).removeClass("up");
	$("th.sortable").not($(this)).removeClass("down");
	if(!$(this).hasClass("up"))
	{
		$(this).removeClass("down");
		$(this).addClass("up");
	}
	else
	{
		$(this).removeClass("up");
		$(this).addClass("down");
	}
	sortTable(flag, $(this).prevAll().length);
	prev=$(this).text();
});

function sortTable(f, n)
{
	var rows=$("table tbody tr").get();
	rows.sort(function(row1, row2)
	{
		var x=getValue(row1);
		var y=getValue(row2);
		if(x<y) return -1*f;
		if(x>y) return 1*f;
		return 0;
	});
	function getValue(row)
	{
		var v=$(row).children("td").eq(n).text().toUpperCase();
		if(/\d{4}-\d{2}-\d{2}/.test(v)) v=new Date(v);
		if($.isNumeric(v)) v=parseInt(v, 10);
		return v;
	}
	$.each(rows, function(index, row)
	{
		$("table").children("tbody").append(row);
	});
}