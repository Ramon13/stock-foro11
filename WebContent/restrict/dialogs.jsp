<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="loadSubCategoryURL" 
	value="/restrict/subcategory/ListByCategory.action" />
<c:url var="savePacketURL" value="/restrict/packet/Save.action" />
<c:url var="saveCategoryURL" value="/restrict/category/Save.action" />
<c:url var="saveSubCategoryURL" value="/restrict/subcategory/Save.action" />

<div id="createPacketDlg" class="jqDialog" hidden="hidden"
	title="Criar nova unidade">
	<form id="newPacketForm">
		<div id="pgeneralErrorDiv"></div> 
		<label for="pname">Nome</label> <br />
		<div id="pnameErrorDiv"></div>
		<input type="text" name="pname" id="pname" class="text ui-widget-content ui-corner-all">
	</form>
</div>

<div id="createCategoryDlg" class="jqDialog" hidden="hidden"
	title="Criar novo subitem">
	<form id="newCategoryForm">
		<div id="cgeneralErrorDiv"></div>
		 
		<label for="cname">Nome</label> <br />
		<div id="cnameErrorDiv"></div>
		<input type="text" name="cname" id="cname" class="text ui-widget-content ui-corner-all">
		<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
	</form>
</div>

<div id="createSubCategoryDlg" class="jqDialog" hidden="hidden"
	title="Criar nova categoria">
	<form id="newSubCategoryForm">
		<div id="scgeneralErrorDiv"></div>
		 
		<label for="scname">Nome</label> <br />
		<div id="scNameErrorDiv"></div>
		<input type="text" name="scName" id="scName" class="text ui-widget-content ui-corner-all"><br />
		
		<label for="icategory">Subitem *</label>
		<div id="scCategoryErrorDiv"></div>
		<select id="scCategory" name="scCategory">
			<option selected="selected">Selecione...</option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.id}">${category.name }</option>
			</c:forEach>
		</select>
	</form>
</div>

<div id="simpleModalDialog" hidden="hidden" title="">
	<p></p>
</div>

<div id="simpleConfirmationDialog" hidden="hidden" title="">
	<span class="ui-icon ui-icon-alert"></span>
	<p></p>
</div>

<script>
	var savePacketURL = '${savePacketURL}';
	var saveCategoryURL = '${saveCategoryURL}';
	var saveSubCategoryURL = '${saveSubCategoryURL}';
	var loadSubCategoryURL = '${loadSubCategoryURL}';
	
	var dialog, form;
	
	function simpleConfirmationDialog(title, messageBody, ajaxSubmitFunction){
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
		        	ajaxSubmitFunction();
		        },
		        Cancel: function() {
		          $( this ).dialog("destroy");
		        }
		      }
	    });
		
		return dialog; 
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
	
	function dialogForm(dialog, submitFunction){
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
		        }
		      },
		      close: function() {
		        dialog.find("form")[ 0 ].reset();
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
	
	$(function() {

		$("#addPacketBtn").button().on("click", function() {
			dialog = smallDialogForm($("#createPacketDlg"), addPacket, $("#newPacketForm"));
			
			form = dialog.find("#newPacketForm").on("submit", function(event) {
				event.preventDefault();
				addPacket();
			});
			
			dialog.dialog("open");
		});

		$("#addCategoryBtn").button().on("click", function() {
			
			dialog = smallDialogForm($("#createCategoryDlg"), addCategory, $("#newCategoryForm"));

			form = dialog.find("#newCategoryForm").on("submit", function(event) {
				event.preventDefault();
				addCategory();
			});

			dialog.dialog("open");
		});

		$("#addSubCategoryBtn").button().on("click", function() {
			dialog = smallDialogForm($("#createSubCategoryDlg"), addSubCategory, $("#newSubCategoryForm"));

			form = dialog.find("#newSubCategoryForm").on("submit", function(event) {
				event.preventDefault();
				addSubCategory();
			});

			dialog.dialog("open");
		});
	});
	
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
				$("#packet").append(option);
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
				$("#category, #scCategory").append(option);
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
</script>