jQuery(function($)
{
	$.i18n().load(
	{
		"en": "./content/lang/en.json",
		"ru": "./content/lang/ru.json"
	})
	.done(function()
	{
		$("html").i18n();
	});
});