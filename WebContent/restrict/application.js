const ERROR_230 = 230;
const SUCCESS_200 = 200;

//loads and append the JSON data to select html element
function loadSubCategory(url){
	
	//gets the isubcategory options based on icategory id
	ajaxCall("get", url, $("#icategory").serialize(), function(data, textStatus, xhr){
		//clear all options
		$("#isubCategory").empty();
		
		//adds subcategory name and value to option
		$.each(jQuery.parseJSON(data), function(){
			$("#isubCategory").append(new Option(this.name, this.id));
		});
	});
}

function clearErrorDivs(){
	$.each( $("div[id$='ErrorDiv']"), function(){
		$(this).empty();
	});
	
	$.each( $(".inputError"), function(){
		$(this).attr("class", "");
	});
}

function saveItem(url){
	
	clearErrorDivs();
	$(":input[type='submit']").prop("disabled", true);
	
	ajaxCall("post", url, $("#saveItemForm").serialize(), function(data, textStatus, xhr){
			var JSONData = jQuery.parseJSON(data);
			if (hasCallbackErrors(xhr)){	
  	   			showErrors(JSONData);
  	   			$(":input[type='submit']").prop("disabled", false);
  	   		}
  	   		else{
  	   			var span = $('<span /> <br />').html(JSONData.message);
  	   			$(".saveSuccess").show().append(span);
  	   		}
	});
	
	return false;
}

function addPacket(){
	
	clearErrorDivs();
	ajaxCall("post", savePacketURL, $("#newPacketForm").serialize(), function(data, textStatus, xhr){
		var JSONData = jQuery.parseJSON(data);
		
		if (hasCallbackErrors(xhr)){
			showErrors(JSONData)
		}
		else if (xhr.status == SUCCESS_200){
			var option = $("<option />").attr("value", JSONData.id).html(JSONData.name);
			option.attr("selected", "selected");
			$("#ipacket").append(option);
			dialog.dialog("close");
		}
	});
}

function addCategory(){
	clearErrorDivs();
	ajaxCall("post", saveCategoryURL, $("#newCategoryForm").serialize(), function(data, textStatus, xhr){
		var JSONData = jQuery.parseJSON(data);
		
		if (hasCallbackErrors(xhr)){
			showErrors(JSONData);
		}
		else if (xhr.status == SUCCESS_200){
			var option = $("<option />").attr("value", JSONData.id).html(JSONData.name);
			option.attr("selected", "selected");
			$("#icategory").append(option);
			dialog.dialog("close");
		}
	});
}

function addSubCategory(){
	clearErrorDivs();
	ajaxCall("post", saveSubCategoryURL, $("#newSubCategoryForm").serialize(), function(data, textStatus, xhr){
		var JSONData = jQuery.parseJSON(data);
		if (hasCallbackErrors(xhr)){
			showErrors(JSONData);
		}
		else if (xhr.status == SUCCESS_200){
			//TODO change category automatically
			loadSubCategory(loadSubCategoryURL);
			dialog.dialog("close");
		}
	});
}

function smallDialogForm(dialog, submitFunction, form){
	var originalContent;
	var dialog = dialog.dialog({
	      autoOpen: false,
	      height: 300,
	      width: 350,
	      modal: true,
	      open: originalContent = dialog.html(),
	      buttons: {
	    	"Salvar": submitFunction,
	        Cancel: function() {
	          dialog.dialog( "close" );
	          dialog.html(originalContent);
	        }
	      },
	      close: function() {
	        form[ 0 ].reset();
	        dialog.html(originalContent);
	      }
	    });
	
	return dialog;
}

function setInputError(input){
	input.addClass("inputError");
}

function showDivError(obj){
	var span = $("<span />").attr("class", "errorMsg").html(obj.message);
	$("#" + obj.divName + "ErrorDiv").append(span);
}

function hasCallbackErrors(xhr){
	return xhr.status == ERROR_230;
}

function showErrors(JSONData){
	console.log(JSONData);
	$.each(JSONData, function(){
		setInputError($("#" + this.divName));
		showDivError(this);
	});
}