<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>

<c:url var="saveUserURL" value="/restrict/user/Save.action" />
<c:url var="listUserURL" value="/restrict/user/List.action"/>
<c:url var="newLocaleURL" value="/restrict/locale/New.action"/>

<style>
	#newUserForm{
		margin: auto;
		text-align: center;
	}
	
	input[type="text"], select {
		width: 300px;
	}
	
}
</style>

<div id="newUserForm">
	<form id="saveUserForm" onsubmit="return false">

		<fieldset>
			<input type="hidden" name="save" value="true" />

			<legend>Novo Usuário</legend>

			<div id="generalErrorDiv"></div>
			
			<label for="name">Usuário*</label> <br />
			<div id="nameErrorDiv"></div>
			<input type="text" id="name" name="name"
			placeholder="Nome de usuário para login...">
			<br /><br />


			<label for="permission">Permissão*</label> <br />
			<div id="permissionErrorDiv"></div>
			<div class="selectBtn">
				<select id="permission" name="permission">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${permissions}" var="permission">
						<option value="${permission.id}">
							${permission.name}
						</option>
					</c:forEach>
				</select>
			</div>
			<br /><br />
			
			<label for="locale">Local*</label> <br />
			<div id="localeErrorDiv"></div>
			<div class="selectBtn">
				<select id="locale" name="locale">
					<option selected="selected">Selecione...</option>
					<c:forEach items="${locales}" var="locale">
						<option value="${locale.id}">
							${locale.name}
						</option>
					</c:forEach>
				</select>
				<button type="button" class="ui-button ui-widget ui-corner-all addLocale">
					<span class="ui-icon ui-icon-plusthick"></span>
				</button>
			</div>
			<br /><br />
			
			<label for="password">Senha Temporária *</label> <br />
			<div id="passwordErrorDiv"></div>
			<input type="text" id="password" name="password" value="${tmpPass}">
			<br /><br />
			
			<button type="button" class="ui-button ui-widget ui-corner-all saveBtn">
				Salvar
			</button>
			
		</fieldset>
	</form>
</div>



<jsp:include page="dialogs.jsp" />

<script>
	$(document).ready(function(){
		$(".saveBtn").on("click", function(){
			ajaxCall("post", '${saveUserURL}', $("#saveUserForm").serialize(),
					function(data, textStatus, xhr){
				clearErrorDivs();
				
				if (hasCallbackErrors(xhr)){
					var JSONData = jQuery.parseJSON(data);
					console.log(JSONData);	
	  	   			showInputErrors(JSONData);
	  	   			showDivErrors(JSONData);
	  	   		
				}else{
					alert("Usuário cadastrado.");
					loadPage("${listUserURL}");
				}
			});
		});
		
		$(".addLocale").on("click", function(){
			ajaxCall("get", '${newLocaleURL}', null, function(data, textStatus, xhr){
				var dialog = dialogForm($(data), null);
				dialog.dialog("open");
			});
		})
	});
</script>
