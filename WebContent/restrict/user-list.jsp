<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="listUsersURL" value="/restrict/user/List.action">
	<c:param name="listAll" value="true" />
</c:url>

<c:url var="blockUserURL" value="/restrict/user/Block.action" />

<table>
	<thead>
		<tr>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listUsersURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Código</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listUsersURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Usuário</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listUsersURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="locale.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="locale.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Local</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listUsersURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="permission.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="permission.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Permissão</span>
				
			</th>
			<th>
				<span>Bloquear</span>
			</th>
		</tr>
	</thead>
	
	<c:forEach items="${users}" var="user">
		<tbody>
			<tr>
				<td>
					<c:out value="${user.id}"></c:out>
				</td>
				<td>
					<span>
						<c:out value="${user.name}"></c:out>
					</span>
				</td>
				<td>
					<span>
						<c:out value="${user.locale.name}"></c:out>
					</span>
				</td>
				<td>
					<span>
						<c:out value="${user.permission.name}"></c:out>
					</span>
				</td>
				<td>
					<div id="ErrorDiv"></div>
					<c:choose>
						<c:when test="${loggedUser.user.permission.level ge user.permission.level}">
						
							<input class="block-user" type="checkbox" data-username="${user.name}"
								data-userId="${user.id}"
								<c:if test="${user.active eq false}">checked="checked"</c:if>/>
							
						</c:when>
						<c:otherwise>
							<input type="checkbox" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</c:forEach>
</table>

<c:import url="dialogs.jsp" />

<script>
	$(document).ready(function(){
		$("input.block-user").on("change", function(){
			
			var msgBody;
			var successMsg;
			var dialogTitle;
			var username = $(this).attr("data-username");
			if ($(this).prop("checked") == false){
				dialogTitle = "Desbloquear usuário"
				msgBody = "O usuário (" + username + ") será desbloqueado e irá acessar o sistema normalmente. Deseja continuar?";
				successMsg = "O usuário foi desbloqueado com sucesso!";
			
			}else{
				dialogTitle = "Bloquear Usuário";
				msgBody = "O acesso do usuário (" + username + ") será bloqueado. Deseja continuar? ";
				successMsg = "O usuário foi bloqueado com sucesso.";	
			}
			
			blockUserDialog($(this), msgBody, dialogTitle);
		});
	});
	
	function blockUserDialog(inputBlock, msgBody, dialogTitle){
		var blockUsersURL = '${blockUserURL}';
		
		var dialogDiv = $("#simpleConfirmationDialog");
		dialogDiv.attr("title", dialogTitle);
		dialogDiv.find("p").html(msgBody);
		
		var userId = inputBlock.attr("data-userId");
		var param = [{name: "user", value: userId}];
		
		var dialog = dialogDiv.dialog({
		      resizable: false,
		      height: "auto",
		      width: 400,
		      modal: true,
		      buttons: {
		        "Continuar": function(){
		        	ajaxCall("get", blockUsersURL, param, function(data, textStatus, xhr){
						if (isSuccessRequest(xhr)){
							simpleModalDialog("Sucesso", "usuário bloqueado");
						}
						
						if (hasCallbackErrors(xhr)){
							inputBlock.parent().attr("id", "generalErrorDiv");
							showDivErrors(jQuery.parseJSON(data));
						}
						
						dialog.dialog("destroy");
					});
		        },
		        Cancel: function() {
		          $( this ).dialog("destroy");
		        	if (inputBlock.prop("checked") == false){
		        		inputBlock.prop("checked", true);
		        	}else{
		        		inputBlock.prop("checked", false);
		        	}
		        		
		        }
		      }
	    });
	}
</script>