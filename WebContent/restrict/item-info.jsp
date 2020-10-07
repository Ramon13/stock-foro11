<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt_BR"/>

<c:url var="loadItemImageURL" value="/restrict/item/LoadImage.action">
	<c:param name="item" value="${item.id}"/>
	<c:param name="image" value="${item.mainImage }"/>
</c:url>

<c:url var="editItemURL" value="/restrict/item/Edit.action">
	<c:param name="item" value="${item.id }"/>
</c:url>

<c:url var="changeMainImageURL" value="/restrict/item/ChangeMainImage.action">
	<c:param name="item" value="${item.id }"/>
</c:url>
<c:url var="itemInfoURL" value="/restrict/item/ItemInfo.action">
	<c:param name="itemId" value="${item.id}"/>
</c:url>
<c:url var="listEntriesURL" value="/restrict/entry/List.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

<c:url var="listOrdersURL" value="/restrict/order/List.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

<c:url var="searchEntryByItemURL" value="/restrict/entry/List.action">
	<c:param name="item" value="${item.id}" />
</c:url>

<script type="text/javascript">
	$( function() {$( "#tabs" ).tabs(); });
</script>

<style>
	img{
		width: 50px;
	    height: 50px;
	    border: 1px solid #d2d2d2;
	}
</style>


<h2><c:out value="${item.name}"/></h2>

<div id="tabs">
	<ul>
		<li><a href="#info">Geral</a></li>
		<li><a href="#entries" onclick="loadEntries(); return false;">Entradas</a></li>
		<li><a href="#orders" onclick="loadOrders(); return false;">Saídas</a></li>
	</ul>
	
	<div id="info">
		<div id="itemAttr">
			<div>
				<button type="button" onclick="location.href='${editItemURL}'"
					 class="ui-button ui-widget ui-corner-all editBtn">
					<span class="ui-icon ui-icon-pencil"></span> Editar
				</button><br /><br /><br />
			</div>
			
			<label class="tittle-label">Nome</label> <br />
			<span id="nameSpan"><c:out value="${item.name }"/></span>
			<br /><br />
			
			<label class="tittle-label">Imagem principal</label> <br />
			<c:choose>
				<c:when test="${empty item.mainImage}">
					<span class="errorMsg">Este produto não possui imagens.</span> <br /> 
				</c:when>
				<c:otherwise>
					<img alt="imagem do produto" src="${loadItemImageURL}">
					<br />
					<a id="imageMngmt" href="#">Trocar/Deletar</a>
				</c:otherwise>
			</c:choose>
			<br /> <br />
				
			<label class="tittle-label">Unidade</label> <br />
			<span id="packetSpan"><c:out value="${item.packet.name }"/></span>
			<br /><br />
			
			<label class="tittle-label">Subitem</label> <br />
			<span id="categorySpan"><c:out value="${item.category.name }"/></span>
			<br /><br />
			
			<label class="tittle-label">Categoria</label> <br />
			<span id="subCategorySpan"><c:out value="${item.subCategory.name }"/></span>
			<br /><br />
			
			<label class="tittle-label">Especificações</label> <br />
			<span id="descriptionSpan">
				<ccvt:blob-to-string value="${item.description }"/>
			</span>
		</div>
	</div>
  
	<div id="entries" data-url="${listEntriesURL}"></div>
	
	<div id="orders" data-url="${listOrdersURL}"></div>
	
</div>

<div id="imageManagerDlg" title="Imagens" hidden="hidden">
	<div id="generalErrorDiv"></div>
	
	<div class="gallery">
		<c:forEach items="${item.images}" var="image">
			<c:url var="loadImagesURL" value="/restrict/item/LoadImage.action">
				<c:param name="item" value="${item.id}"/>
				<c:param name="image" value="${image.id}"/>
			</c:url>
			
			<img src="${loadImagesURL}"
				<c:if test="${image.id eq item.mainImage }">class='selectedImage'</c:if>
			>
			<span hidden="hidden"><c:out value="${image.id}"/></span>
			
		</c:forEach>
	</div>
	
	<div>
		<button id="newMainImageBtn" type="button" data-url="${changeMainImageURL}"
			data-success="${itemInfoURL}" class="ui-button ui-widget ui-corner-all saveBtn">
			Salvar
		</button><br /><br /><br />
	</div>
</div>    

<script>
	$(document).ready(function(){
		$("#imageManagerDlg").hide();
		$("#newMainImageBtn").hide();
		var dialog;
		
		$("#imageMngmt").on("click", function(){
			dialog = $("#imageManagerDlg").dialog();
			dialog.dialog( "option", "minWidth", 450 );
			dialog.dialog("open");
		});
		
		$(".gallery img").on("click", function(){
			$(".selectedImage").removeClass();
			$(this).attr("class", "selectedImage");
			$("#newMainImageBtn").show();
		});
		
		$("#newMainImageBtn").on("click", function(){
			var url = $(this).attr("data-url");
			url += "&image=" + $(".selectedImage").next().text();
			
			var successUrl = $(this).attr("data-success");
			
			ajaxCall("POST", url, null, function(data, textStatus, xhr){
				if (hasCallbackErrors(xhr)){
					var JSONData = jQuery.parseJSON(data);
	  	   			showDivErrors(JSONData);
	  	   			$("#newMainImageBtn").prop("disabled", false);
	  	   		}
	  	   		else{
	  	   			loadPage(successUrl);
	  	   			dialog.dialog("destroy");
	  	   		}	
			});
		});
		
	});
	
	function loadEntries(){
		var url = $("#entries").attr("data-url");
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				$("#dinamicContent").attr("data-pagination-url", '${listEntriesURL}');
				$("#searchForm").attr("action", '${listEntriesURL}');
				$("#entries").html($(data).find("table"));	
			}
			$("#entries").html(data);
		});
	}
	
	function loadOrders(){	
		var url = $("#orders").attr("data-url");
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				$("#dinamicContent").attr("data-pagination-url", '${listOrdersURL}');
				$("#searchForm").attr("action", '${listOrdersURL}');
				$("#orders").html($(data).find("table"));	
			}
		});
	}
</script>