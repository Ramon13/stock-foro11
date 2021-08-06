<%@page import="entity.EntryItem"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="header.jsp"%>

<script type="text/javascript">
	$( function() {
		$( "#tabs" ).tabs();
		$(".chosen-select").chosen({width: "60%"});
	});
</script>

<style>
	#tabNewEntry tbody:hover{
		background-color: unset !important;
	}
	
	#saveEntries{
		margin-right: 50px;
	}
	
	div#generalErrorDiv, #successDiv {
	    margin: auto;
	    text-align: center;
	    margin-bottom: 30px;
	    width: 35%;
	    background-color: #ff0000;
	    border-radius: 50px;
	}
	
	#generalErrorDiv span, #successDiv span{
		color: white !important;
		font-size: 15px;
	}
	
	div#successDiv{
		background-color: #4e9a06;
	}
	
	button#addRow, button#pConfig{
	    position: absolute;
	    margin: 0 0 0 10px;
	    display: inline-flex;
	}
	
	button#newProvider:hover {
		background-color: #204a87;
	}
	
	td.col20{
		width: 20%;
	}
</style>

<c:url var="saveEntryURL" value="/restrict/entry/Save.action" />
<c:url var="listProviderURL" value="/restrict/provider/List.action" />

<fmt:setLocale value="pt_BR"/>

<div id="tabs">
	<ul>
		<li><a href="#tabEntries" onclick="confEntries()">Entradas</a></li>
		<c:if test="${hasWritePermission}">
			<li><a href="#tabNewEntry" onclick="confNewEntryPage()">Nova Entrada</a></li>
		</c:if>
	</ul>
	
	<div id="tabEntries">
		<table>
			<thead>
				<tr>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Código</span>
							</div>
							<div class="dropdown-content">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Data</span>
							</div>
							<div class="dropdown-content">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="date" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="date" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Fornecedor</span>
							</div>
							<div class="dropdown-content">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="provider.name" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="provider.name" data-order="desc">Classificar em ordem decrescente</span>
							</div>
						</div>
					</th>
					<th>
						<div class="dropdown">
							<div class="dropdown-btn">
								<img class="ui-icon ui-icon-triangle-1-s"></img>
								<span>Nº Documento</span>
							</div>
							<div class="dropdown-content">
								<button class="ui-button ui-widget ui-corner-all close-dropdown">
									<img class="ui-icon ui-icon-closethick"></img>
								</button>
								<br />
								<span class="sortBy" data-property="invoice.invoiceIdNumber" data-order="asc">Classificar em ordem crescente</span>
								<span class="sortBy" data-property="invoice.invoiceIdNumber" data-order="desc">Classificar em ordem decrescente</span>
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

	<c:if test="${hasWritePermission}">	
		<div id="tabNewEntry">
			<form id="saveEntryForm" onsubmit="return false">
				<fieldset>
					<legend>Adicionar Entradas</legend>
					
					<div id="generalErrorDiv"></div>
					<div id="successDiv"></div>
					
					<table>
						<thead>
							<tr>
								<th><label for="date">Data</label></th>
								<th><label for="invoiceNumber">Nº Do Documento</label></th>
								<th><label for="providerSlct">Fornecedor</label></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="col20">
									<div id="dateErrorDiv"></div>
									<input type="text" id="date" name="date" 
										value="<cfmt:formatDate value="${today}" locale="ptBR"/>" />
								</td>
								<td class="col20">
									<div id="invoiceNumberErrorDiv"></div>
									<input type="text" name="invoiceNumber" 
										id="invoiceNumber" class="inputNumber" />
								</td>
								<td>
									<div id="providerErrorDiv"></div>
									<select name="provider" class="chosen-select" id="provider">
									 	<c:forEach items="${providers}" var="provider">
									 		<option value="${provider.id}">
									 			<c:out value="${provider.name}" />
									 		</option>
									 	</c:forEach>
									</select>
									<button id="pConfig" onclick="providerConfig('${listProviderURL}')" title="configurar fornecedores">
										<c:url var="prefIcon" value="/static/images/pref.svg" />
										<img id="" src="${prefIcon}">
									</button>
								</td>
							</tr>
						</tbody>
					</table>
					<br />
					<br />
					<table id="entryItemsTable">
						<thead>
							<tr>
								<th>Item</th>
								<th>Quantidade</th>
								<th>Valor Unitário</th>
								<th>Total (R$)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<div>
										<div id="itemErrorDiv"></div>
										<br />
										<select class="chosen-select"
											 name="item">
										 	<c:forEach items="${items}" var="item">
										 		<option value="${item.id}">
										 			<c:out value="${item.name }" />
										 		</option>
										 	</c:forEach>
										</select>
									</div>
								</td>
								<td class="col20">
									<span class="clientErrorMsg"></span>
									<div id="amountErrorDiv"></div>
									<br />
									<input type="text" name="amount" class="inputNumber amount"/>
								</td>
								<td class="col20">
									<span class="clientErrorMsg"></span>
									<div id="valueErrorDiv"></div>
									<br />
									<input type="text" name="value" class="inputNumber value"/>
								</td>
								<td class="col20">
										<br />
									<input type="text" class="inputNumber rowTotal" value="0"/>
								</td>
							</tr>
						</tbody>
						<tbody id="tableTotal">
							<tr>
								<td colspan="3"></td>
								<td class="col20">
									<input type="text" value ="0" class="inputNumber colTotal"/>
								</td>
							</tr>
						</tbody>
					</table>
					<button type="button" id="addRow" class="ui-button ui-widget ui-corner-all">
						+Inserir
					</button>
				</fieldset>
				
				<br /><br />
					<button type="button" id="saveEntries" onclick="saveEntry('${saveEntryURL}')"
					 	class="ui-button ui-widget ui-corner-all saveBtn">
						Finalizar
					</button>
			</form>
			
			<table>
			  <tbody id="baseRow" hidden=hidden>
					<tr>
						<td>
							<div>
								<select name="item">
								 	<c:forEach items="${items}" var="item">
								 		<option value="${item.id}">
								 			<c:out value="${item.name }" />
								 		</option>
								 	</c:forEach>
								</select>
							</div>
						</td>
						<td class="col20">
							<span class="clientErrorMsg"></span>
							<div id="amountErrorDiv"></div>
							<br />
							<input type="text" name="amount" class="inputNumber amount"/>
						</td>
						<td class="col20">
							<span class="clientErrorMsg"></span>
							<div id="amountErrorDiv"></div>
							<br />
							<input type="text" name="value" class="inputNumber value"/>
						</td>
						<td class="col20"><input type="text" class="inputNumber rowTotal" value="0" /></td>
					</tr>
					
				</tbody>
			</table>	
		</div>
	</c:if>
</div>

<jsp:include page="/public/dialogs.jsp" />

<script>
	var lDialog;
	
	$(document).ready(function(){
		$("#date").datepicker();
		$("#date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
		
		$(document).on("keyup", ".amount,.value", function(e){
			clearErrorMessages($(this));
			var value = fromPtBrCurrency($(this).val());
			
			if (!$.isNumeric(value))
				$(this).parent().find(".clientErrorMsg").text("Não é um número válido");
			printTotalRow($(this));
			printTotal();
		});
		
		$("button#addRow").on("click", function(){
			var clonedRow = $("#baseRow").clone();
			clonedRow.attr("id", "");
			clonedRow.insertBefore($("#tableTotal"));
			clonedRow.show();
			clonedRow.find("select").attr("class", "chosen-select");
			
			$(".chosen-select").chosen({width: "60%"});
		});
		
		$("body").on("click", "#providerConfig", function(){
			ajaxCall("get", $("#providerConfig").attr("data-url"), null, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					lDialog = dialogForm($(data), null);
					lDialog.dialog({
						width: 600
					})
					lDialog.dialog("open");
				}
			});
		})
	});
	
	function providerConfig(url){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				lDialog = dialogForm($(data), null);
				lDialog.dialog({
					width: 600
				})
				lDialog.dialog("open");
			}
		});
	}
	
	function clearErrorMessages(element){
		$(element).parent().find(".clientErrorMsg").text("");
	}
	
	function printTotalRow(element){
		var parent = element.parent().parent();
		var amount = fromPtBrCurrency(parent.find(".amount").val());
		var value = fromPtBrCurrency(parent.find(".value").val());
		var total =  parseFloat(amount * value).toFixed(2);
		parent.find(".rowTotal").val(toPtBrCurrency(total));
	}
	
	function printTotal(){
		var total = 0;
		$(".rowTotal").each(function(){
			total += parseFloat($(this).val());
		});
		$(".colTotal").val(toPtBrCurrency(total.toFixed(2)));
	}
	
	function saveEntry(saveURL){
		clearErrorDivs();
		$.ajax({
	        type: "post",
	        url: saveURL,
	        data: decodeURIComponent($("#saveEntryForm").serialize()),
	        success: function(data, textStatus, xhr){

	        	var JSONData = jQuery.parseJSON(data);
				if (hasCallbackErrors(xhr)){
	  	   			showInputErrors(JSONData);
	  	   			showDivErrors(JSONData);
	  	   		}
	  	   		else{
	  	   			var span = $("<span />").html("Entrada salva com sucesso.");
	  	   			$("#successDiv").html(span);
	  	   		}
	        }
		});
	}
	
	function confNewEntryPage(){
		loadContentOnEndPage(false);
	}
	
	function confEntries(){
		loadContentOnEndPage(true);
	}
</script>