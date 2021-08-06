<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>	
	<div id="saveProviderDlg">
		<form id="saveProviderForm" onsubmit="return false" style="text-align: center">
	
			<fieldset>
				<legend>${editMode ? 'Editar Fornecedor' : 'Novo Fornecedor'}</legend>
				<input id="pid" name="provId" value="${provider.id}" hidden="hidden"/>
				<input name="option" value="save" hidden="hidden"/>
				
				<div id="pgeneralErrorDiv"></div>
				
				<label for="pname">Nome*</label> <br />
				<div id="pnameErrorDiv"></div>
				<input type="text" value="${provider.name}" id="pname" name="pname" placeholder="Nome do fornecedor...">
				<br /><br />
				
				<label for="pcnpj">CNPJ*</label> <br />
				<div id="pcnpjErrorDiv"></div>
				<input type="text" value="${provider.cnpj}" id="pcnpj" name="pcnpj" placeholder="CNPJ do fornecedor...">
				
				<br /><br />
				<c:url var="saveProviderURL" value="/restrict/provider/Save.action">
					<c:param name="option" value="save"/>
				</c:url>
				<button type="button" class="ui-button ui-widget ui-corner-all saveBtn" onclick="saveEditedProvider('${saveProviderURL}')">
					${editMode ? 'Finalizar edição' : 'Salvar'}
				</button>
			</fieldset>
		</form>
	</div>
	
	<c:url var="listProviderURL" value="/restrict/provider/List.action" />
	
	<script>
		var editProviderDialog;
		
		function saveEditedProvider(url){
			var formData = $("#saveProviderForm").serialize();
			console.log(formData);
			ajaxCall("POST", url, formData, function(data, textStatus, xhr){
				
				if (isSuccessRequest(xhr)){
					providerConfig('${listProviderURL}');
					pDialog.dialog("destroy");
				}else{
					var JSONData = jQuery.parseJSON(data);
					showDivErrors(JSONData);
					showInputErrors(JSONData);
				}
			});
		}
	</script>
</div>