<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>

<c:url var="saveUserURL" value="/restrict/item/Save.action" />

<style>
	#newUserForm{
		margin: auto;
		text-align: center;
	}
	
	#saveUserForm{
		text-align: center;
	}
	
	input[type="text"], select {
		width: 30px;
	}
	
}
</style>

<div id="newUserForm">
	<form id="saveUserForm" onsubmit="return false" enctype="multipart/form-data">

		<fieldset>
			<input type="hidden" name="save" value="true" />

			<legend>Novo Usuário</legend>

			<div id="generalErrorDiv"></div>
			
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
					<span class="ui-icon ui-icon-plusthick"></span> adicionar
				</button>
			</div>
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

	});
</script>
