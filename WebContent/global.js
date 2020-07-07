$(document).ready(function(){
	jqueryMenu();
});

function loadPage(URL){
	
	$.get(URL, function(data){
		$("#content").html(data);
	});
}

function jqueryMenu(){
	$( "#menu" ).menu();
}

function jqueryTabs(){
	$("#tabs").tabs();
}

function ajaxCall(method, url, data, success){
	$.ajax({
        type: method,
        url: url,
        data: data,
        success: success 
	});
}