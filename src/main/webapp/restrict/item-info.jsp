<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt_BR"/>

<%@ include file="header.jsp"%>

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
<c:url var="entriesURL" value="/restrict/entries.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

<c:url var="ordersURL" value="/restrict/orders.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

<c:url var="searchEntryByItemURL" value="/restrict/entry/List.action">
	<c:param name="item" value="${item.id}" />
</c:url>

<script type="text/javascript">
	$( function() {
		$( "#tabs" ).tabs();
		openSelectedTab();
	});
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
				<button type="button" onclick="location.href = '${editItemURL}'"
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
  
	<div id="entries">
		<table>
			<thead>
				<tr>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Código</span>
							</div>
							<div class="dropdown-content" data-sortURL="${listEntriesURL}">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="${fieldName}id" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="${fieldName}id" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Data</span>
							</div>
							<div class="dropdown-content" data-sortURL="${listEntriesURL}">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="${fieldName}date" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="${fieldName}date" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Fornecedor</span>
							</div>
							<div class="dropdown-content" data-sortURL="${listEntriesURL}">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="${fieldName}provider.name" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="${fieldName}provider.name" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Nº Documento</span>
							</div>
							<div class="dropdown-content" data-sortURL="${listEntriesURL}">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="${fieldName}invoice.invoiceIdNumber" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="${fieldName}invoice.invoiceIdNumber" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
				</tr>
			</thead>
			
			<c:forEach items="${entries}" var="entry">
				<tbody>
					<c:url var="entryItemsURL" value="/restrict/entryItem/List.action">
						<c:param name="entry" value="${entry.id}"/>
					</c:url>
					<tr class="view" onclick="openView(this)" data-url="${entryItemsURL}">
						<td>
							<span class="ui-icon ui-icon-triangle-1-s"></span>
							<span>
								<c:out value="${entry.id}"></c:out>
							</span>
						</td>
						<td>
							<span>
								<cfmt:formatDate value="${entry.date}" locale="ptBR"/>
							</span>
						</td>
						<td>
							<span>
								<c:out value="${entry.provider.name}"></c:out>
							</span>
						</td>
						<td>
							<span>
								<c:out value="${entry.invoice.invoiceIdNumber}"></c:out>
							</span>
						</td>
					</tr>
					<tr class="fold">
						<td colspan="4"></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	
	<div id="orders" data-url="${ordersURL}"></div>
	
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
	
	function openSelectedTab(){
		var prop = getURLParameter("listProp");
		if (prop === "entry"){
			$("#tabs").tabs({active: 1});
		}
	}
	
	function loadEntries(){
		if ($("div#entries table tbody").html() == undefined){
			var param = [{name:"listProp", value:"entry"}];
			var url = '${itemInfoURL}&' + $.param(param);
			location.href = url;
		}
	}
	
	function loadOrders(){	
		var url = $("#orders").attr("data-url");
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				$("#content").attr("data-pagination-url", '${ordersURL}');
				$("#searchForm").attr("action", '${ordersURL}');
				$("#orders").html($(data).find("table"));	
			}
		});
	}
</script>