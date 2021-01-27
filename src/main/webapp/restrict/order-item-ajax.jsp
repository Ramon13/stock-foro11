<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="cancelOrderURL" value="/wpermission/order/Cancel.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="comeBackOrderURL" value="/wpermission/order/Pending.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="releaseOrderURL" value="/wpermission/order/Release.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="finishOrderURL" value="/wpermission/order/Finish.action">
	<c:param name="order" value="${order.id}"/>
</c:url>

<c:url var="ordersURL" value="/restrict/orders.action">
	<c:param name="status" value="${status}"/>
</c:url>
<c:url var="editItemAmountURL" value="/restrict/orderItem/EditAmount.action" />


<c:set var="PENDING" value="P" />
<c:set var="FINALIZED" value="F" />
<c:set var="RELEASED" value="R" />
<c:set var="SUPERADMIN" value="superAdmin" />


<style>
	button.finishBtn{
		background-color: #2ea44f !important;
		padding: .4em 1em !important;
		color: white;
	}
	
	button.releaseBtn{
		background-color: #ffa64d;
		padding: .4em 1em !important;
		color: white;
	}
	
	button.comeBackBtn{
		background-color: #003399;
		padding: .4em 1em !important;
		color: white;
	}
	
	button.comeBackBtn:hover{
		background-color: #000099;
		color: white;
	}
	
	button.releaseBtn{
		background-color: #2ea44f !important;
		padding: .4em 1em !important;
		color: white;
	}
	
	button.releaseBtn:hover{
		background-color: #247f3e !important;
		color: white;
	}
	
	button.cancelBtn{
		background-color: #ff4d4d !important;
		padding: .4em 1em !important;
		color: white;
	}
	
	button.cancelBtn:hover{
		background-color: #ff0000 !important;
		color: white;
	}
	
	div#buttons{
		float: right;
		padding: 20px;
	}
	
	div#buttons button{
		margin: 20px;
	}
	
	.releaseAdmin{
		font-style: italic;
	}

	form#newAmountForm, input[type=text]{
		text-align: center;
		width: 80%;
		margin: auto;
	}
	
	div#orderInfo{
	    width: 95%;
	    text-align: right;
	    padding-top: 30px;
	    margin: auto;
	}
	
	div#orderInfo input[type=text]{
		width: 300px;
	}
	
	div#orderInfo span{
		margin-right: 80px;
	}
	
	.fold-content img{
		width: 50px;
	    height: 50px;
	    border: 1px solid #d2d2d2;
	}
	
</style>

<c:set var="FINALIZED" value="F" />
<c:set var="RELEASED" value="R" />

<div class="fold-content">
	<c:choose>
		<c:when test="${status == PENDING}">
		
			<table class="fold-table">
				<thead>
					<tr>
						<th colspan="5"></th>
						<th colspan="12">
							<span>(<c:out value="${order.customer.locale.name}"/>)</span>
							<br />
							<span>Consumo nos últimos 12 meses</span>
						</th>
					</tr>
					<tr>
						<th></th>
						<th>
							<span>Descrição</span>
						</th>	
						<th>
							<span>Quantidade</span>
						</th>
						<th>
							<span>Estoque Atual</span>
						</th>
						<th>	
							<span>(<c:out value="${order.customer.locale.name}"/>)</span>
							<br />
							
							<span>consumo de: </span>
							<br />
							
							<span>
								<cfmt:formatDate locale="pt_BR" value="${firstDayOfCurrentYear}"/>
							</span>
							
							<span>até:</span>
							 
							<br />
							<span><cfmt:formatDate locale="pt_BR" value="${today}"/></span>
						</th>
						
						<c:forEach var="monthName" items="${monthNames}">
							<th>
								<span>
									<c:out value="${monthName}" />
								</span>
							</th>
						</c:forEach>
					</tr>
				</thead>
				 
				<c:forEach var="orderItem" items="${orderItems}">
				
					<c:set var="item" value="${orderItem.item}"/>
					
					<tbody>
						<tr>
							<td>
								<c:url var="loadItemImageURL" value="/restrict/item/LoadImage.action">
									<c:param name="item" value="${item.id}"/>
									<c:param name="image" value="${item.mainImage}" />
								</c:url>			
								<img src="${loadItemImageURL}">
							</td>
							<td>
								<span>
									<c:out value="${item.name}" />
								</span>
							</td>
							<td>
								<div id="editAmount">
									<span class="orderAmount">
										<fmt:formatNumber value="${orderItem.amount}" type="number"/>
									</span>
									<br />
									<c:if test="${hasWritePermission}">
										<a href="#" class="editAmountBtn" data-url="${editItemAmountURL}"
											data-orderItem-id="${orderItem.id}">editar</a>
									</c:if>
								</div>
							</td>				
							<td>
								<span>
									<fmt:formatNumber value="${item.amount}" type="number"/>
								</span>
							</td>
							<td>
								<span>
									<c:out value="${item.currentYearAmount}" />
								</span>
							</td>
							<c:forEach var="sum" items="${item.sumByMonth}">
								<td>
									<span>
										<c:out value="${sum}"/>
									</span>
								</td>
							</c:forEach>
						</tr>
					</tbody>
				</c:forEach>
			</table>
			<br /><br />
			
			<c:if test="${hasWritePermission}">
				<div id="buttons">		
					<button data-url="${cancelOrderURL}" class="ui-button ui-widget ui-corner-all cancelBtn">
						Cancelar
					</button>
					
					<button data-url="${releaseOrderURL}" class="ui-button ui-widget ui-corner-all releaseBtn">
						Liberar Pedido
					</button>
				</div>
			</c:if>
		</c:when>
		
		<c:otherwise>
			<table class="fold-table">
				<thead>
					<tr>
						<th></th>
						<th>
							<span>Descrição</span>
						</th>	
						<th>
							<span>Quantidade</span>
						</th>			
					</tr>
				</thead>
				 
				<c:forEach var="orderItem" items="${orderItems}">
				
					<c:set var="item" value="${orderItem.item}"/>
					
					<tbody>
						<tr>
							<td>
								<c:url var="loadItemImageURL" value="/restrict/item/LoadImage.action">
									<c:param name="item" value="${item.id}"/>
									<c:param name="image" value="${item.mainImage}" />
								</c:url>			
								<img class="itemImage" src="${loadItemImageURL}">
							</td>
							<td>
								<span>
									<c:out value="${item.name}" />
								</span>
							</td>
							<td>
								<span>
									<fmt:formatNumber value="${orderItem.amount}" type="number"/>
								</span>
							</td>
							
						</tr>
					</tbody>
				</c:forEach>
			</table>
			
			<c:if test="${status == RELEASED and hasWritePermission eq true}">
				<div>
					<div id="orderInfo">
						<c:if test="${ not empty order.releaseAdministrator}">
							<span class="releaseAdmin">
								Liberado por: 
								<c:out value="${order.releaseAdministrator.name}" />
							</span>						
						</c:if>
						<br /><br />
						
						<span>Pedido entregue à:</span>
						<br />
						<div id="receivedPersonErrorDiv"></div>
						<input type="text" name="receivedPerson" id="receivedPerson"/>					
					</div>
					
					<br />
					
					<div id="buttons">
						<button type="button" class="ui-button ui-widget ui-corner-all comeBackBtn" data-url="${comeBackOrderURL}">
							Retornar para a fase anterior
						</button>
						<button type="button" class="ui-button ui-widget ui-corner-all finishBtn saveBtn" data-url="${finishOrderURL}">
							Finalizar pedido
						</button>
					</div>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>	


<div id="editAmountDlg" class="jqDialog" hidden="hidden" title="Editar Quantidade">
	<form id="newAmountForm" onsubmit="return false">
		<input type="hidden" id="orderItem" name="orderItem"/> 
		<label for="amount">Nova quantidade:</label> <br />
		<div id="generalErrorDiv"></div>
		<div id="amountErrorDiv"></div>
		<input type="text" name="amount" id="amount"/>
	</form>
</div>

<jsp:include page="/public/dialogs.jsp"/>

<script>
	$(document).ready(function(){
		var dialogTitle, dialogMessage;
		
		$(".finishBtn").on("click", function(){
			var receivedPerson = $(this).parent().parent().find("#receivedPerson").val();
			
			var url = $(this).attr("data-url");
			var params = [{name:"receivedPerson", value: receivedPerson}]
			
			dialogTitle = "Finalizar Pedido";
			dialogMessage = "Após finalizar o pedido não será mais possível altera-lo. Deseja prosseguir com a operação?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				
				ajaxCall("post", url, params, function(data, textStatus, xhr){
					if (hasCallbackErrors(xhr)){
						var JSON = jQuery.parseJSON(data);
						simpleModalDialog("Alerta", JSON[0].message);
					}else{
						location.reload();
					}	
				});
			});
		});
		
		$(".comeBackBtn").on("click", function(){
			var url = $(this).attr("data-url");
			
			dialogTitle = "Retornar para a fase anterior";
			dialogMessage = "O pedido irá retornar para a fase de pendência. Deseja prosseguir com a operação?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				
				ajaxCall("post", url, null, function(data, textStatus, xhr){
					if (hasCallbackErrors(xhr)){
						var JSON = jQuery.parseJSON(data);
						simpleModalDialog("Alerta", JSON[0].message);
					}else{
						location.reload();
					}	
				});
			});
		});
		
		$(".cancelBtn").on("click", function(){		
			var url = $(this).attr("data-url");
			
			dialogTitle = "Cancelar Pedido";
			dialogMessage = "O cancelamento do pedido é irreversível. Deseja prosseguir com o cancelamento?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				
				ajaxCall("post", url, null, function(data, textStatus, xhr){
					if (hasCallbackErrors(xhr)){
						var JSON = jQuery.parseJSON(data);
						simpleModalDialog("Alerta", JSON[0].message);
					}else{
						location.reload();
					}	
				});
			});
		});
		
		$(".releaseBtn").on("click", function(){
			var url = $(this).attr("data-url");
			
			dialogTitle = "Liberar Pedido";
			dialogMessage = "Deseja prosseguir com a liberação do pedido?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
		
				ajaxCall("post", url, null, function(data, textStatus, xhr){
					if (hasCallbackErrors(xhr)){
						var JSON = jQuery.parseJSON(data);
						simpleModalDialog("Alerta", JSON[0].message);
					}else{
						location.reload();
					}	
				});
			});
		});
		
		$(".editAmountBtn").on("click", function(event){
			event.stopPropagation();
			var dialogDiv = $("#editAmountDlg");
			var url = $(this).attr("data-url");

			var amountInput = $(this).parent().find(".orderAmount");
			var currentAmount = amountInput.text();
			var orderItemId = $(this).attr("data-orderItem-id");
			
			dialogDiv.find("#amount").val(currentAmount.trim());
			dialogDiv.find("#orderItem").val(orderItemId.trim());
			
			var dialog = smallDialogForm(dialogDiv, dialogDiv.find("form"), function(){
				changeAmount(url, dialog, amountInput);
			}).dialog("open");
		});
	});
	
	function changeAmount(url, dialog, amountSpan){
		var formData = $("#newAmountForm").serialize();
		ajaxCall("post", url, formData, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				showDivErrors(JSON);
				showInputErrors(JSON);
			
			}else{
				simpleModalDialog("Editar quantidade", "Quantidade modificada com sucesso.");
				setNewAmount($("#newAmountForm").find("#amount"), amountSpan);
				dialog.dialog("destroy");
			}
		});
	}
	
	function setNewAmount(inputForm, spanTable){
		spanTable.text(inputForm.val());
	}
	
	
</script>