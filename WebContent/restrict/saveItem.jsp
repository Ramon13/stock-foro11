<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>

<c:url var="newPacketURL" value="/restrict/packet/New.action" />
<c:url var="newCategoryURL" value="/restrict/category/New.action" />

<c:url var="saveItemURL" value="/restrict/item/Save.action" />
<c:url var="itemInfoURL" value="/restrict/item/ItemInfo.action" />
<c:url var="listByCategoryURL" value="/restrict/subcategory/ListByCategory.action" />

<div id="newItemForm">
	<form id="saveItemForm" onsubmit="return false" enctype="multipart/form-data">

		<fieldset>
			<input type="hidden" name="save" value="true" />

			<legend>
				<c:choose>
					<c:when test="${editMode }">
						Editar Produto
					</c:when>
					<c:otherwise>
						Novo Produto
					</c:otherwise>
				</c:choose>
			</legend>

			<div class="saveSuccess" hidden="hidden">Produto cadastrado no sistema com sucesso.</div><br />

			<div id="generalErrorDiv"></div>
			
			<input type="hidden" name="id" value="${item.id}">
			
			<label for="name">Descrição *</label> <br />
			<div id="nameErrorDiv"></div>
			<input type="text" id="name" name="name" value="${item.name}"placeholder="Nome do item...">
			<br /><br />


			<label for="packet">Unidade*</label> <br />
			<div id="packetErrorDiv"></div>
			<div class="selectBtn">
				<select id="packet" name="packet">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${packets}" var="packet">
						<option value="${packet.id}"
							<c:if test="${item.packet.id eq packet.id}">selected</c:if>
						>${packet.name}</option>
					</c:forEach>
				</select>
				<button id="addPacketBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span>
				</button>
			</div>
			<br /><br />

			<div class="selectBtn">
				<label for="category">Subitem *</label> <br />
				<div id="categoryErrorDiv"></div>
				<select id="category" name="category" onchange="loadSubCategory('${listByCategoryURL }')">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${categories}" var="category">
						<option value="${category.id}"
							<c:if test="${item.category.id eq category.id}">selected</c:if>
						>${category.name }</option>
					</c:forEach>
				</select>
				<button id="addCategoryBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span>
				</button>
			</div>
			<br /><br />

			<div class="selectBtn">
				<label for="subCategory">Categoria *</label> <br />
				<div id="subCategoryErrorDiv"></div>
				<select id="subCategory" name="subCategory">
					<c:forEach items="${subCategories}" var="subCategory">
						<option value="${subCategory.id}"
							<c:if test="${item.subCategory.id eq subCategory.id}">selected</c:if>
						>${subCategory.name }</option>
					</c:forEach>
				</select>
				<button id="addSubCategoryBtn" type="button" class="ui-button ui-widget ui-corner-all">
					<span class="ui-icon ui-icon-plusthick"></span>
				</button>
			</div>
			<br /><br />

			<label for="description">Especificações *</label> <br />
			<div id="descriptionErrorDiv"></div>
			<textarea name="description" style="text-align: justify;" rows="5" cols="30">
				<ccvt:blob-to-string value="${item.description}"/>
			</textarea>
			<br /><br />

			<label for="name">Imagens</label> <br />
			<input type="file" multiple="multiple" id="images" name="images"
				placeholder="enviar imagens...">
			<br /><br /><br />
			
			<button type="button" onclick="saveItem('${saveItemURL}', '${itemInfoURL }')"
			 class="ui-button ui-widget ui-corner-all saveBtn">
			Salvar
			</button>
		</fieldset>
	</form>
</div>
<jsp:include page="dialogs.jsp" />

<script>
	var dialog;

	$(document).ready(function(){
		//print the successful message if the server return success as true on url
		const queryString = window.location.href;
		var url = new URL(queryString);
		if (url.searchParams.get("success") != null)
			$(".saveSuccess").show();
		
		$("textarea").jqte();
		
		$("#addPacketBtn").on("click", function(){
			ajaxCall("get", '${newPacketURL}', null, function(data, textStatus, xhr){
				dialog = dialogForm($(data), null);
				dialog.dialog("open");
			});
		});
		
		$("#addCategoryBtn").on("click", function(){
			ajaxCall("get", '${newCategoryURL}', null, function(data, textStatus, xhr){
				dialog = dialogForm($(data), null);
				dialog.dialog("open");
			});
		});
	});

	function saveItem(saveURL, successURL){

		clearErrorDivs();
		$(":input[type='submit']").prop("disabled", true);

		var form = $("#saveItemForm")[0];
		var data = new FormData(form);

		$.ajax({
	        type: "post",
	        enctype: 'multipart/form-data',
	        url: saveURL,
	        processData: false, //prevent JQuery transforming the data into a query string
	        contentType: false,
	        data: data,
	        success: function(data, textStatus, xhr){

	        	var JSONData = jQuery.parseJSON(data);
				if (hasCallbackErrors(xhr)){
	  	   			showInputErrors(JSONData);
	  	   			showDivErrors(JSONData);
	  	   			$(":input[type='submit']").prop("disabled", false);
	  	   		}
	  	   		else{
	  	   			window.location.href = successURL + "?itemId=" + JSONData.id;
	  	   		}
	        }
		});

		return false;
	}
</script>
