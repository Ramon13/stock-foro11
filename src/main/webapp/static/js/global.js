const ERROR_230 = 230;
const SUCCESS_200 = 200;

var scrolled = false;
var sortBy, order;

$(document).ready(function(){
	$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
			
	$('textarea').each(function(){
        $(this).val($(this).val().trim());
    });
	
	$("body").on("click", ".dropdown-btn", function(e){
		var dropdownContent = $(this).parent().find('.dropdown-content');
		dropdownContent.toggleClass('dropdown-content-show');
	});
	
	$("body").on("click", ".close-dropdown", function(){
		var dropdownContent = $(this).parent().parent().find('.dropdown-content');
		dropdownContent.toggleClass('dropdown-content-show');
	});
	
	$("#searchForm").on("submit", function(event){	
		var url = $(this).attr("action");
		var param = {search:$("#searchInput").val()};
		
		ajaxCall("get", url, param, function(data, textStatus, xhr){	
			if (isSuccessRequest(xhr)){
				$("table tbody").remove();
				var tbodyList = $(data).find("tbody");
				$.each(tbodyList, function(){
					$("table").append($(this));	
				});
			}
		});
	});
	
	$('#content').on('scroll', function() {
		var url = $(this).attr("data-pagination-url");
		
        if(scrolled == false && isMaxScrollHeight($(this)) && url != "") {
        	scrolled = true;
        	var tbodyCount = $(this).find("table tbody").length;
			
			var param = [
				{name: "sortBy", value: sortBy},
				{name: "order", value: order},
				{name: "firstResultPage", value: tbodyCount},
				{name: "search", value: $("#searchInput").val()}];
			
			ajaxCall("get", url, param, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					var tbodyList = $(data).find("tbody");
					
    				$.each(tbodyList, function(){
    					$("table").append($(this));
    				});	
				}
			});
        }
        
		scrolled = false;
    });

	$("body").on("click",".sortBy", function(){
		sortBy = $(this).attr("data-property");
		order = $(this).attr("data-order");
		
		var sortParams = [
			{name: "sortBy", value: sortBy},
			{name: "search", value: $("#searchForm").find("#searchInput").val() },
			{name: "order", value: order}];
		
		var url = $(this).parent().attr("data-sortURL");
		
		ajaxCall("get", url, sortParams, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				$("table tbody").remove();
				var tbodyList = $(data).find("tbody");
				$.each(tbodyList, function(){
					$("table").append($(this));	
				});
			}
		});
		$(this).parent().find(".close-dropdown").click();
	});
});

function loadPage(URL){
	$("#searchForm").find("#searchInput").val("");
	$("#dinamicContent").attr("class", "disableDiv");
	$("#searchForm").removeAttr("data-on-div");
	
	ajaxCall("get", URL, null, function(data, textStatus, xhr){
		if (sessionHasEnded(data)){
			location.reload();
		
		}else{
			$("#dinamicContent").html(data);
			$("#dinamicContent").removeClass("disableDiv");			
		}
	});
}

function sessionHasEnded(data){
	return $(data).find("#loginPage").val() === "true";
}

function addParam(url, data){
	if (! $.isEmptyObject(data)){
		url += (url.indexOf('?') >= 0 ? '&' : '?') + $.param(data);
	}
	
	return url;
}

function loadFoldTable(url, view){
	event.stopPropagation();
	var url = $(view).attr("data-url");
	var td = $(view).parent().find(".fold").find("td");
	
	if ($(td).html() == ''){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$(td).html(data);
		});	
	}
	
	$(view).toggleClass("open-view").next(".fold").toggleClass("open-fold");
	
	var spanIcon = $(view).find(".ui-icon");
	if ($(view).attr(".open-view"))
		spanIcon.attr("class", "ui-icon ui-icon-triangle-1-n")
	else
		spanIcon.attr("class", "ui-icon ui-icon-triangle-1-s")
}

function ajaxCall(method, url, data, success){
	$.ajax({
		async: true,
        type: method,
        url: url,
        data: data,
        success: success 
	});
}

function isNumber(charCode){
	return charCode >= 48 && charCode <= 57 || charCode == 44 || charCode == 8;
}

function fromPtBrCurrency(number){
	return number.replace(",", ".");
}

function toPtBrCurrency(number){
	return number.replace(".", ",");
}

function hasCallbackErrors(xhr){
	return xhr.status == ERROR_230;
}

function isSuccessRequest(xhr){
	return xhr.status == SUCCESS_200;
}

function showInputErrors(JSONData){
	$.each(JSONData, function(){
		setInputError($("#" + this.divName));
		setInputError($("." + this.divName));
	});
}

function showDivErrors(JSONData){
	$.each(JSONData, function(){
		var span = $("<span />").attr("class", "errorMsg").html(this.message);
		$("#" + this.divName + "ErrorDiv").html(span);
	});
}

function setInputError(input){
	input.addClass("inputError");
}

function clearErrorDivs(){
	$.each( $("div[id$='ErrorDiv']"), function(){
		$(this).empty();
	});
	
	$.each( $(".inputError"), function(){
		$(this).attr("class", "");
	});
}

	
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}

function isMaxScrollHeight(e){
	return e.scrollTop() + e.innerHeight() >= e[0].scrollHeight - 1;
}

var flag = true;
function openView(element){
	element = $(element);
	var url = element.attr("data-url");
	var td = element.parent().find(".fold").find("td");
	
	if ($(td).html() == ''){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$(td).html(data);
		});	
	}
	
	element.toggleClass("open-view").next(".fold").toggleClass("open-fold");
	
	var spanIcon = element.find(".open-fold");
	flag == true ? spanIcon.attr("class", "ui-icon ui-icon-triangle-1-n") : spanIcon.attr("class", "ui-icon ui-icon-triangle-1-s");
	flag = !flag;
}

function saveNewLocale(url){
	ajaxCall("post", url, $("#newLocaleForm").serialize(),
			function(data, textStatus, xhr){
		
		if (hasCallbackErrors(xhr)){
			var JSONData = jQuery.parseJSON(data);	
   			showInputErrors(JSONData);
   			showDivErrors(JSONData);
		}
	});
}

function saveNewPacket(url){
	clearErrorDivs();
	ajaxCall("post", url, $("#newPacketForm").serialize(), function(data, textStatus, xhr){
		var JSONData = jQuery.parseJSON(data);
		
		if (hasCallbackErrors(xhr)){
			showInputErrors(JSONData);
   			showDivErrors(JSONData);
		}
		else if (xhr.status == SUCCESS_200){
			var option = $("<option />").attr("value", JSONData.id).html(JSONData.name);
			option.attr("selected", "selected");
			$("#packet").append(option);
			dialog.dialog("destroy");
			simpleModalDialog("Sucesso", "Unidade criada com sucesso.");
		}
	});
}

function saveNewCategory(url){
	clearErrorDivs();
	ajaxCall("post", url, $("#newCategoryForm").serialize(), function(data, textStatus, xhr){
		var JSONData = jQuery.parseJSON(data);
		
		if (hasCallbackErrors(xhr)){
			showInputErrors(JSONData);
   			showDivErrors(JSONData);
		}
		else if (xhr.status == SUCCESS_200){
			var option = $("<option />").attr("value", JSONData.id).html(JSONData.name);
			option.attr("selected", "selected");
			$("#category, #scCategory").append(option);
			dialog.dialog("destroy");
			simpleModalDialog("Sucesso", "Subitem criado com sucesso.");
		}
	});
}

function saveNewSubCategory(url){
	clearErrorDivs();
	ajaxCall("post", url, $("#newSubCategoryForm").serialize(), function(data, textStatus, xhr){
		
		if (hasCallbackErrors(xhr)){
			var JSONData = jQuery.parseJSON(data);
			showInputErrors(JSONData);
   			showDivErrors(JSONData);
		}
		
		if (isSuccessRequest(xhr)){
			dialog.dialog("destroy");
			simpleModalDialog("Sucesso", "Categoria criada com sucesso.");
		}
	});
}

function updateCartCount(url){
	ajaxCall("get", url, null, function(data, textStatus, xhr){
	
		if (isSuccessRequest(xhr)){
			console.log(data);
			$("#cartCount").html(data);
		}
	});
}