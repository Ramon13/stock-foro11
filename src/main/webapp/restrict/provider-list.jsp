<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<div>
		<c:url var="editProviderURL" value="/restrict/provider/Save.action" />
		<a href="#" id="newProvider" data-url="${editProviderURL}">Novo fornecedor</a>
		<table>
			<thead>
				<tr>
					<th>Descrição</th>
					<th>CNPJ</th>
				</tr>
			</thead>
			<c:forEach var="provider" items="${providers}">
				<tbody>
					<tr>
						<td><c:out value="${provider.name}" /></td>
						<td><c:out value="${provider.cnpj}" /></td>
						<td>
							<c:url var="editProviderURL" value="/restrict/provider/Save.action">
								<c:param name="provId" value="${provider.id}" />
								<c:param name="option" value="edit" />
							</c:url>
							<button id="editProviderBtn" data-url="${editProviderURL}">
								<c:url var="prefIcon" value="/static/images/edit.svg"/> 
								<img src="${prefIcon}">
							</button>
						</td>
						<td>
							<button id="deleteProviderBtn">
								<c:url var="delIcon" value="/static/images/delete.svg" />
								<img src="${delIcon}">
							</button>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	
	<jsp:include page="/public/dialogs.jsp" />
	
	<script>
		var pDialog;
		$(document).ready(function(){
			$("button#editProviderBtn").on("click", function(){
				var url = $(this).attr("data-url");
				ajaxCall("get", url, null, function(data, textStatus, xhr){
					if (isSuccessRequest(xhr)){
						pDialog = dialogForm($(data), null);
						
						pDialog.dialog({
							width: 500,
							height: 400
						});
						pDialog.dialog("open");		
					}
				});
				
			});
			
			$("a#newProvider").on("click", function(){
				var url = $(this).attr("data-url");
				ajaxCall("get", url, null, function(data, textStatus, xhr){
					if (isSuccessRequest(xhr)){
						pDialog = dialogForm($(data), null);
						
						pDialog.dialog({
							width: 500,
							height: 400
						});
						pDialog.dialog("open");		
					}
				});
			})
			
			
		});
	</script>
</div>