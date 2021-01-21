<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="simpleModalDialog" hidden="hidden" title="">
	<p></p>
</div>

<div id="simpleConfirmationDialog" hidden="hidden" title="">
	<span class="ui-icon ui-icon-alert"></span>
	<p></p>
</div>

<script>
	var dialog, form;
	
	function simpleConfirmationDialog(title, messageBody, processFunction){
		var dialogDiv = $("#simpleConfirmationDialog");
		dialogDiv.attr("title", title);
		dialogDiv.find("p").html(messageBody);
		
		var dialog = dialogDiv.dialog({
		      resizable: false,
		      height: "auto",
		      width: 400,
		      modal: true,
		      buttons: {
		        "Continuar": function(){
		        	processFunction();
		        	$( this ).dialog("destroy");
		        },
		        Cancel: function() {
		          $( this ).dialog("destroy");
		        }
		      }
	    });
		
		return dialog; 
	}
	
	function smallDialogForm(dialog, form, submitFunction){
		var originalContent;
		var dialog = dialog.dialog({
		      autoOpen: false,
		      height: 300,
		      width: 350,
		      modal: true,
		      open: originalContent = dialog.html(),
		      buttons: {
		    	"Salvar": function(){
		    		submitFunction();
		    	},
		    	
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
	
	function dialogForm(dialog, submitFunction){
		var dialog = dialog.dialog({
		      autoOpen: false,
		      height: 300,
		      width: 350,
		      modal: true,
		      open: originalContent = dialog.html(),
		      close: function() {
		        dialog.find("form")[ 0 ].reset();
		        dialog.dialog("destroy");
		      }
		    });
		
		return dialog;
	}
	
	function simpleModalDialog(title, message){
		var dialogDiv = $("#simpleModalDialog");
		dialogDiv.attr("title", title);
		dialogDiv.find("p").html(message);
		
		var dialog = dialogDiv.dialog({
			modal: true,
			buttons: {
				OK: function(){
					dialog.dialog("close");
				}
			}
		}).dialog("open");
		
		return dialog;
	}
	
	function simpleModalDialog(title, message, action){
		var dialogDiv = $("#simpleModalDialog");
		dialogDiv.attr("title", title);
		dialogDiv.find("p").html(message);
		
		var dialog = dialogDiv.dialog({
			modal: true,
			buttons: {
				OK: function(){
					if (action != undefined){
						action();	
					}
					dialog.dialog("close");
				}
			},
			close: function(){
				if (action != undefined){
					action();	
				}
				dialog.dialog("close");
			}
		}).dialog("open");
		
		return dialog;
	}
	
	$(function() {

		$("#addSubCategoryBtn").button().on("click", function() {
			dialog = smallDialogForm($("#createSubCategoryDlg"), addSubCategory, $("#newSubCategoryForm"));

			form = dialog.find("#newSubCategoryForm").on("submit", function(event) {
				event.preventDefault();
				addSubCategory();
			});

			dialog.dialog("open");
		});
	});

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
</script>