<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>

	button#addRow{
	    background-color: #3465a4;
	    color: white;
	}
	
	button#addRow:hover {
		background-color: #204a87;
	}
</style>

<c:url var="saveEntryURL" value="/restrict/entry/Save.action" />

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
					<td>
						<div id="dateErrorDiv"></div>
						<input type="text" id="date" name="date" 
							value="<cfmt:formatDate value="${today}" locale="ptBR"/>" />
					</td>
					<td>
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
					<td>
						<span class="clientErrorMsg"></span>
						<div id="amountErrorDiv"></div>
						<br />
						<input type="text" name="amount" class="inputNumber amount"/>
					</td>
					<td>
						<span class="clientErrorMsg"></span>
						<div id="valueErrorDiv"></div>
						<br />
						<input type="text" name="value" class="inputNumber value"/>
					</td>
					<td>
							<br />
						<input type="text" class="inputNumber rowTotal" value="0"/>
					</td>
				</tr>
			</tbody>
			<tbody id="tableTotal">
				<tr>
					<td colspan="3"></td>
					<td>
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
			<td>
				<span class="clientErrorMsg"></span>
				<div id="amountErrorDiv"></div>
				<br />
				<input type="text" name="amount" class="inputNumber amount"/>
			</td>
			<td>
				<span class="clientErrorMsg"></span>
				<div id="amountErrorDiv"></div>
				<br />
				<input type="text" name="value" class="inputNumber value"/>
			</td>
			<td><input type="text" class="inputNumber rowTotal" value="0" /></td>
		</tr>
		
	</tbody>
</table>

<script>
	$(document).ready(function(){
		$("#date").datepicker();
		$("#date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
		
		$(".chosen-select").chosen();
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
			
			$(".chosen-select").chosen();
		});
	});
	
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

		console.log($("#saveEntryForm").serialize());
		clearErrorDivs();
		$.ajax({
	        type: "post",
	        url: saveURL,
	        data: $("#saveEntryForm").serialize(),
	        success: function(data, textStatus, xhr){

	        	var JSONData = jQuery.parseJSON(data);
				if (hasCallbackErrors(xhr)){
	  	   			showInputErrors(JSONData);
	  	   			showDivErrors(JSONData);
	  	   		}
	  	   		else{
	  	   			console.log(JSONData);
	  	   			var span = $("<span />").html("Entrada salva com sucesso.");
	  	   			$("#successDiv").html(span);
	  	   		}
	        }
		});
	}
</script>