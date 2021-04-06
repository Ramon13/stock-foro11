<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="saveProviderURL" value="/restrict/provider/Save.action" />

<div class="jqDialog" title="Criar novo fornecedor">
	<form id="newProviderForm" style="text-align: center" onsubmit="return false;">
	 
		<label for="pname">Nome*</label> <br />
		<div id="pnameErrorDiv"></div>
		<input type="text" name="pname" id="pname" class="text ui-widget-content ui-corner-all"><br />
		
		<label for="pcnpj">CNPJ*</label> <br />
		<div id="pcnpjErrorDiv"></div>
		<input type="text" name="pcnpj" id="pcnpj" class="text ui-widget-content ui-corner-all"><br />
				
		<button type="button" data-url="${saveProviderURL}" class="ui-button ui-widget ui-corner-all saveProviderBtn" style="margin-top: 30px">
			Salvar
		</button>
	</form>
	
	<script>
		$(document).ready(function(){
			$("button.saveProviderBtn").on("click", function(){
				var url = '${saveProviderURL}';
				var data = $("#newProviderForm").serialize();
				console.log(data)
				ajaxCall("post", url, data, function(data, textStatus, xhr){
					var JSONData = jQuery.parseJSON(data);
					if (isSuccessRequest(xhr)){
						var option = $("<option>foo</option>");
						option.attr("value", JSONData.id);
						option.text(JSONData.name);
						option.attr("selected", "selected");
						$("#provider").append(option);
						$("#provider").trigger("chosen:updated");
						dialog.dialog("destroy");
					}else{
						showDivErrors(JSONData)
						showInputErrors(JSONData);
					}
				});
			});
		});
	</script>
</div>