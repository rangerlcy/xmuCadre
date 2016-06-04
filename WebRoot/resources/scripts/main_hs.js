$(document).ready(function () {	
	$(".fun_list").hover(function(){
		$(".fun_list").each(function(){
			$(this).addClass("hide_list");});
		$(this).removeClass("hide_list");},		
		function(){$(this).addClass("hide_list");
	});	
	
	$(".second_menu_hover").hover(function(){
		$(".last_menu").each(function(){
			$(this).addClass("hide_list");
		});
		$(this).children('.last_menu').removeClass("hide_list");},		
		function(){$(this).children('.last_menu').addClass("hide_list");
	});
});
function changeURL(url){
	var type=".html";
	var desURL=url+type;
	$(".content iframe").attr("src",desURL);
}