<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>

<style>
	#statusPath{
		margin-bottom: 30px;
	}

	#statusPath a{
		color: #00000080;
	}
	
	#statusPath span{
		font-size: 14px;
	}
	
	a.highlight{
		color: black !important;
		font-weight: bold;
	}
	
	a{
		text-decoration: none;
	}
	
	.itemImage img{
		width: 50px;
	    height: 50px;
	    border: 1px solid #d2d2d2;
	}
	
	h3{
		text-align: center;
	    color: gray;
	    margin-top: 100px;
	    font-family: Helvetica, Arial, sans-serif;
	}
	
	form#newDateForm, input[type=text]{
		text-align: center;
	}
	
	td span{
		font-size: 14px;
	}
	
	.dropdown button{
		padding: 0px;
	}
</style>

<c:set var="PENDING" value="P" />
<c:set var="FINALIZED" value="F" />
<c:set var="RELEASED" value="R" />

<c:url var="editFinalDateURL" value="/restrict/order/ChangeOrderFinalDate.action" />
<c:url var="listOrdersURL" value="${listOrderURL}">
	<c:param name="status" value="${status}"/>
</c:url>

<c:url var="listOrders" value="/restrict/order/List.action" />


<div id="statusPath">
	<a href="#" onclick="loadPage('${listOrders}?status=P')" 
		<c:if test="${status == PENDING}">class="highlight"</c:if> >Pendente</a> 
	
	<span>></span>
	 
	<a href="#" onclick="loadPage('${listOrders}?status=R')" 
		<c:if test="${status == RELEASED}">
			class="highlight"
		</c:if> >
		Liberado
	</a>
	 
	<span>></span>
	
	<a href="#" onclick="loadPage('${listOrders}?status=F')" 
	<c:if test="${status == FINALIZED}">class="highlight"</c:if> >Finalizado</a>	
</div>

<div id="ordersDiv">
	<c:choose>
		<c:when test="${empty orders}">
			<h3>Não há saídas para serem exibidas</h3>
		</c:when>
		<c:otherwise>
			<table>
				<thead>
					<tr>
						<th>
							<div class="dropdown">
								<button class="dropdown-btn">
									<span class="ui-icon ui-icon-caret-1-s"></span>
								</button>
								<div class="dropdown-content" data-sortURL="${sortDataURL}">
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
							Data
						</th>
						<th>Local</th>
						<th>Pedido por:</th>
						
						<c:if test="${status == FINALIZED}">
							<th>Entregue a:</th>
						</c:if>
					</tr>
				</thead>
				
				<c:forEach items="${orders}" var="order">
					<tbody>
					
						<c:url var="loadItemImageURL" value="/restrict/orderItem/ListByOrder.action" >
							<c:param name="order" value="${order.id}"/>
						</c:url>
			
						<tr class="view" onclick="openView(this)" data-url="${loadItemImageURL}">
							<td>
								<span class="open-fold ui-icon ui-icon-triangle-1-s"></span>
								<span class="orderId">
									<c:out value="${order.id}"></c:out>
								</span>
								
							</td>
							<td>
								<span>
									<cfmt:formatDate locale="pt_BR" value="${order.finalDate}"/>
								</span>
								
								<c:if test="${superAdmin and status eq PENDING}">
									<button class="editDateBtn" type="button" 
										data-url="${editFinalDateURL}" 
										data-success-url="${listOrdersURL}"
										data-order-id="${order.id}">
										Editar
										<span class="ui-icon ui-icon-pencil"></span>
									</button>
								</c:if>
							</td>
							<td>
								<c:out value="${order.customer.locale.name}"></c:out>
							</td>
							<td>
								<c:out value="${order.customer.name}"></c:out>
							</td>
							
							<c:if test="${status == FINALIZED}">
								<td>
									<c:out value="${order.receivedPersonName}"></c:out>
								</td>
							</c:if>
						</tr>
		  
						<tr class="fold">
							<td colspan="10"></td>
						</tr>				
					</tbody>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</div>

<div id="editDateDlg" class="jqDialog" hidden="hidden"
	title="Editar data de solicitação">
	<form id="newDateForm">
		
		<input type="hidden" id="order" name="order"/> 
		<label for="date">Nova data:</label> <br />
		<div id="dateErrorDiv"></div>
		<input type="text" name="date" id="date"/>
	</form>
</div>

<jsp:include page="dialogs.jsp"/>

<c:url var="listOrderItemsURL" value="/restrict/orderItem/ListByOrder.action" />
<script>
	$(document).ready(function(){
		$("#date").datepicker();
		$("#date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
		
		$(".editDateBtn").on("click", function(event){
			event.stopPropagation();
			var dialogDiv = $("#editDateDlg");
			var url = $(this).attr("data-url");
			var successURL = $(this).attr("data-success-url");

			var currentDate = $(this).parent().find("span").text();
			var orderId = $(this).attr("data-order-id");
			
			dialogDiv.find("#date").val(currentDate.trim());
			dialogDiv.find("#order").val(orderId.trim());
			
			var editDateDialog = dialogForm($("#editDateDlg"), function(){
				changeDate(url, successURL, editDateDialog);
			}).dialog("open");
		});
		
		$("body").on("click", ".sortBy", function(){
			
		});
	});
	
	function changeDate(url, successURL, dialog){
		var formData = $("#newDateForm").serialize();
		ajaxCall("post", url, formData, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				console.log(JSON);
				showDivErrors(JSON);
				showInputErrors(JSON);
				
			}else{
				loadPage(successURL);
				simpleModalDialog("Editar data de solicitação", "A data foi modificada com sucesso.");
				dialog.dialog("destroy");
			}
		});
	}
</script>
