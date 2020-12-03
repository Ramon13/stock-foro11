<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="cancelOrderURL" value="/restrict/order/Cancel.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="comeBackOrderURL" value="/restrict/order/Pending.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="releaseOrderURL" value="/restrict/order/Release.action">
	<c:param name="order" value="${order.id}"/>
</c:url>
<c:url var="finishOrderURL" value="/restrict/order/Finish.action">
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
	button.releaseBtn{
		background-color: #ffa64d;
		color: white;
	}

	button.cancelBtn{
		background-color: #ff4d4d;
		color: white;
	}
	
	button.comeBackBtn{
		background-color: #003399;
		color: white;
	}
	
	button.comeBackBtn:hover{
		background-color: #000099;
		color: white;
	}
	button.releaseBtn:hover{
		background-color: #ff8c1a;
		color: white;
	}
	
	button.cancelBtn:hover{
		background-color: #ff0000;
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
	
	img{
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
									<span class="amount">
										<fmt:formatNumber value="${orderItem.amount}" type="number"/>
									</span>
									<br />
									<c:if test="${superAdmin}">
										<button class="editAmountBtn" type="button" 
											data-url="${editItemAmountURL}"
											data-orderItem-id="${orderItem.id}">
											Editar
											
										</button>
									</c:if>
								</div>
							</td>				
							<td>
								<span>
									<c:out value="${item.amount}" />
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
			
			<c:if test="${superAdmin}">
				<div id="buttons">
					<button type="button" class="ui-button ui-widget ui-corner-all cancelBtn"
						data-cancel-action="${cancelOrderURL}" 
						data-success-url="${ordersURL}">
						Cancelar
					</button>
					<button type="button" class="ui-button ui-widget ui-corner-all releaseBtn"
						data-release-action="${releaseOrderURL}" 
						data-success-url="${ordersURL}">
						Liberar pedido
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
			
			<c:if test="${status == RELEASED}">
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
						<button type="button" class="ui-button ui-widget ui-corner-all comeBackBtn"
							data-return-action="${comeBackOrderURL}" 
							data-success-url="${ordersURL}">
							Retornar para a fase anterior
						</button>
						<button type="button" class="ui-button ui-widget ui-corner-all finishBtn saveBtn"
							data-action="${finishOrderURL}" 
							data-success-url="${ordersURL}">
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
			var url = $(this).attr("data-action");
			var successURL = $(this).attr("data-success-url");
			var receivedPerson = $(this).parent().parent().find("#receivedPerson");
			
			url += "&receivedPerson=" + receivedPerson.val();
			finishOrder(url, successURL);	
		});
		
		$(".comeBackBtn").on("click", function(){
			var url = $(this).attr("data-return-action");
			var successURL = $(this).attr("data-success-url");
			
			dialogTitle = "Retornar para a fase anterior";
			dialogMessage = "O pedido irá retornar para a fase de pendência. Deseja prosseguir com a operação?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				comeBackOrder(url, successURL);
				dialog.dialog("destroy");
			});
		});
		
		$(".cancelBtn").on("click", function(){
			var url = $(this).attr("data-cancel-action");
			var successURL = $(this).attr("data-success-url");
			
			dialogTitle = "Cancelar Pedido";
			dialogMessage = "O cancelamento do pedido é irreversível. Deseja prosseguir com o cancelamento?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				cancelOrder(url, successURL);
				dialog.dialog("close");
			});
		});
		
		$(".releaseBtn").on("click", function(){
			var url = $(this).attr("data-release-action");
			var successURL = $(this).attr("data-success-url");
			
			dialogTitle = "Liberar Pedido";
			dialogMessage = "Deseja prosseguir com a liberação do pedido?";
			var dialog = simpleConfirmationDialog(dialogTitle, dialogMessage, function(){
				releaseOrder(url, successURL);
				dialog.dialog("close");
			});
		});
		
		$(".editAmountBtn").on("click", function(event){
			event.stopPropagation();
			var dialogDiv = $("#editAmountDlg");
			var url = $(this).attr("data-url");

			var amountInput = $(this).parent().find(".amount");
			var currentAmount = amountInput.text();
			var orderItemId = $(this).attr("data-orderItem-id");
			
			dialogDiv.find("#amount").val(currentAmount.trim());
			dialogDiv.find("#orderItem").val(orderItemId.trim());
			
			var editAmountDialog = dialogForm($("#editAmountDlg"), function(){
				changeAmount(url, editAmountDialog, amountInput);
			}).dialog("open");
		});
	});
	
	function comeBackOrder(url, successURL){
		ajaxCall("post", url, null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				simpleConfirmationDialog("Pedido pendente", "O pedido foi retornado para a fase anterior.", function(){
					location.href = successURL;
					dialog.dialog("close");
				});
			}	
		});
	}
	
	function finishOrder(url, successURL){
		ajaxCall("post", url, null, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				console.log(JSON);
				showDivErrors(JSON);
				showInputErrors(JSON);
			}else{
				loadPage(successURL);	
				simpleModalDialog("Pedido Finalizado", "O pedido foi finalizado com sucesso.");
			}	
		});
	}
	
	function releaseOrder(url, successURL){
		ajaxCall("post", url, null, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				simpleModalDialog("Não foi possível liberar o pedido", JSON[0].message);
			}else{
				simpleConfirmationDialog("Pedido Liberado", "Pedido liberado para entrega.", function(){
					location.href = successURL;
					dialog.dialog("destroy");
				});
			}	
		});
	}
	
	function cancelOrder(url, successURL){
		ajaxCall("post", url, null, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				simpleModalDialog("Erro ao cancelar o Pedido", JSON[0].message);
			}else{
				loadPage(successURL);
				simpleModalDialog("Pedido Cancelado", "O pedido foi cancelado.");
			}
		});
	}
	
	function changeAmount(url, dialog, amountSpan){
		
		var formData = $("#newAmountForm").serialize();
		console.log(formData);
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