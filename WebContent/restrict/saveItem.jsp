<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="saveItemURL" value="/restrict/SaveItem.action" />
<c:url var="loadSubCategoryURL" value="/restrict/LoadSubCategory.action" />
<c:url var="savePacketURL" value="/restrict/SavePacket.action" />
<c:url var="saveCategoryURL" value="/restrict/SaveCategory.action" />
<c:url var="saveSubCategoryURL" value="/restrict/SaveSubCategory.action" />

<div id="newItemForm">
	<form id="saveItemForm" onsubmit="return saveItem('${saveItemURL}')">

		<fieldset>
			<input type="hidden" name="save" value="true" />

			<legend>Novo Item</legend>

			<div class="saveSuccess" hidden="hidden"></div><br />
			
			<div id="igeneralErrorDiv"></div><br />
			
			<label for="iname">Descrição *</label> <br />
			<div id="inameErrorDiv"></div>
			<input type="text" id="iname" name="iname" value="${iname}" placeholder="Nome do item..."> <br /> 
			
			<label for="ipacket">Unidade*</label>
			<div id="ipacketErrorDiv"></div>
			<div class="selectBtn">
				<select id="ipacket" name="ipacket">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${packets}" var="packet">
						<option value="${packet.id}">${packet.name}</option>
					</c:forEach>
				</select>
				<button id="addPacketBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span> adicionar unidade
				</button>
			</div>
			<br />

			<div class="selectBtn">
				<label for="icategory">Subitem *</label>
				<div id="icategoryErrorDiv"></div>
				<select id="icategory" name="icategory" onchange="loadSubCategory('${loadSubCategoryURL }')">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${categories}" var="category">
						<option value="${category.id}">${category.name }</option>
					</c:forEach>
				</select>
				<button id="addCategoryBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span> adicionar subitem
				</button>
			</div>
			<br />

			<div class="selectBtn">
				<label for="isubCategory">Categoria *</label>
				<div id="isubCategoryErrorDiv"></div>
				<select id="isubCategory" name="isubCategory">
				</select>
				<button id="addSubCategoryBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span> adicionar categoria
				</button>
			</div>
			<br /> 
			
			<input type="submit" value="Salvar item" id="submitItemBtn">
		</fieldset>
	</form>
</div>

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


<script>
	var savePacketURL = '${savePacketURL}';
	var saveCategoryURL = '${saveCategoryURL}';
	var saveSubCategoryURL = '${saveSubCategoryURL}';
	var loadSubCategoryURL = '${loadSubCategoryURL}';
	
	var dialog, form;

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
</script>