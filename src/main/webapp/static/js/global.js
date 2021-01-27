const ERROR_230 = 230;
const SUCCESS_200 = 200;

var scrolledPage = true;

$(document).ready(function(){
	$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
			
	$('textarea').each(function(){
        $(this).val($(this).val().trim());
    });
	
	$("body").on("click", ".dropdown-btn", function(e){
		var dropdownContent = $(this).parent().find('.dropdown-content');
		dropdownContent.toggleClass('dropdown-content-show');
		$("#content").find("thead th").toggleClass("fixTableHead");
	});
	
	$("body").on("click", ".close-dropdown", function(){
		var dropdownContent = $(this).parent().parent().find('.dropdown-content');
		dropdownContent.toggleClass('dropdown-content-show');
		$("#content").find("thead th").toggleClass("fixTableHead");
	});
	
	$("#searchForm").on("submit", function(event){	
		var url = window.location.href;
		url = addParams(url, decodeURIComponent($("#searchForm").serialize()));
	
		location.href = url;
	});
	
	var scrollHeight = 0;
	$('#content').on('scroll', function() {
		
		var element = $(this);
        if(scrollHeight < getScrollHeight(element) && isMaxScrollHeight(element) && scrolledPage == true) {
    		scrollHeight = getScrollHeight(element) + 500;
        
			var url = window.location.href;
		
			var tbodyCount = element.find("table tbody").length;
			var param = [{name: "firstResultPage", value: tbodyCount}];
			
			ajaxCall("get", url, param, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					var table = $(data).find("table")[0];
					var tbodyList = $(table).find("tbody");
    				$.each(tbodyList, function(){
    					$("table").append($(this));
    				});	
				}
			});
        }
    });

	$("body").on("click",".sortBy", function(){
		var sortParams = [
			{name: "sortBy", value: $(this).attr("data-property")},
			{name: "order", value: $(this).attr("data-order")},
			{name: "appSortBy", value: $(this).attr("data-app-property")}];
		
		var url = window.location.href;
		url = addParams(url, decodeURIComponent($.param(sortParams)));
		
		location.href = url;
	});
});

function loadContentOnEndPage(choice){
	scrolledPage = choice;	
}

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

function addParams(url, param){
	var urlArr = url.split("?");
	
	if (urlArr.length >= 2){
		var treeMap = new TreeMap();
		
		var urlParams = urlArr[1].split("&");
		for (var i = 0; i < urlParams.length; i++){
			var urlParam = urlParams[i].split("=");
			treeMap.set(urlParam[0], urlParam[1]);
		}
		
		var paramArr = param.split("&");
		for (var i = 0; i < paramArr.length; i++){
			var urlParam = paramArr[i].split("=");
			treeMap.set(urlParam[0], urlParam[1]);
		}
		
		var params = "";
		treeMap.each(function (value, key){
			params += key + "=" + value + "&";
		});
		
		return urlArr[0] + "?" + params;
	}
	
	return urlArr[0] + "?" + param;
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

function getScrollHeight(e){
	return e.scrollTop() + e.innerHeight();
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
			$("#cartCount").html(data);
		}
	});
}

function getURLParameter(sParam){
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}