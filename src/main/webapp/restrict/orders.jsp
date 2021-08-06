<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>

<%@include file="header.jsp" %>

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
	
	form#newDateForm, input[type=text]{
		text-align: center;
	}
	
	.reportLink img{
		width: 20px;
		margin-right: 15px;
	}
	
	input[type=text]#requestDate{
		width: auto;
		padding: 0px;
	}
	
	a.editDateBtn img{
		width: 13px;
		margin-left: 7px;
	}
</style>


<c:url var="editFinalDateURL" value="/restrict/order/ChangeOrderFinalDate.action" />

<c:choose>
	<c:when test="${not empty item}">
		<c:url var="ordersURL" value="/restrict/orders.action">
			<c:param name="item" value="${item.id}"/>
		</c:url>
		<c:url var="selectedOrdersURL" value="/restrict/orders.action">
			<c:param name="item" value="${item.id}"/>
		</c:url>
	</c:when>
	<c:otherwise>
		<c:url var="selectedOrdersURL" value="/restrict/orders.action">
			<c:param name="status" value="${status}"></c:param>
		</c:url>
		<c:url var="ordersURL" value="/restrict/orders.action" />
	</c:otherwise>
</c:choose>

<c:set var="PENDING" value="P" />
<c:set var="FINALIZED" value="F" />
<c:set var="RELEASED" value="R" />

<div id="statusPath">
	<a href="${ordersURL}?status=P" ${status eq PENDING ? "class='highlight'" : ""}>Pendente</a> 
	<span>></span>
	 
	<a href="${ordersURL}?status=R" ${status eq RELEASED ? "class='highlight'" : ""}>Liberado</a>
	<span>></span>
	
	<a href="${ordersURL}?status=F" ${status eq FINALIZED ? "class='highlight'" : ""}>Finalizado</a>
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
								<div class="dropdown-btn">
									<img class="ui-icon ui-icon-triangle-1-s"></img>
									<span>Código</span>
								</div>
								<div class="dropdown-content" data-sortURL="${selectedOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="${fieldName}id" data-order="asc">Classificar em ordem crescente</span>
									<span class="sortBy" data-property="${fieldName}id" data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
						</th>
						<th>
							<div class="dropdown">
								<div class="dropdown-btn">
									<img class="ui-icon ui-icon-triangle-1-s"></img>		
									<span>Data</span>
								</div>
								<div class="dropdown-content" data-sortURL="${selectedOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="${fieldName}finalDate" data-order="asc">Classificar em ordem crescente</span>
									<span class="sortBy" data-property="${fieldName}finalDate" data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
						</th>
						<th>
							<div class="dropdown">
								<div class="dropdown-btn">
									<img class="ui-icon ui-icon-triangle-1-s"></img>
									<span>Local</span>
								</div>
								<div class="dropdown-content" data-sortURL="${selectedOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="${fieldName}customer.locale.name" 
										data-order="asc">Classificar em ordem crescente</span>
									
									<span class="sortBy" data-property="${fieldName}customer.locale.name" 
										data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
						</th>
						<th><div class="dropdown">
								<div class="dropdown-btn">
									<img class="ui-icon ui-icon-triangle-1-s"></img>
									<span>Pedido por:</span>
								</div>
								<div class="dropdown-content" data-sortURL="${selectedOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="${fieldName}customer.name" 
										data-order="asc">Classificar em ordem crescente</span>
									
									<span class="sortBy" data-property="${fieldName}customer.name" 
										data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div></th>
						
						<c:if test="${status == FINALIZED}">
							<th>
								<span>
									Entregue a:
								</span>
							</th>
						</c:if>
					</tr>
				</thead>
				
				<c:url var="editOrderDateURL" value="/restrict/order/ChangeOrderDate.action"></c:url>
				<c:forEach items="${orders}" var="order">
					<tbody>
					
						<c:url var="loadItemImageURL" value="/restrict/orderItem/ListByOrder.action" >
							<c:param name="order" value="${order.id}"/>
						</c:url>
						<c:url var="listOrderItemByOrderURL" value="/restrict/orderItem/ListByOrder.action" >
							<c:param name="order" value="${order.id}"/>
						</c:url>
						
						<tr class="view" onclick="openView(this)" data-url="${listOrderItemByOrderURL}">
							<td>
								<span class="open-fold ui-icon ui-icon-triangle-1-s"></span>
								<span class="orderId">
									<c:out value="${order.id}"></c:out>
								</span>
								
							</td>
							<td>
								<span>
									<cfmt:formatDate locale="pt_BR" value="${order.requestDate}"/>
								</span>
								<c:if test="${status == PENDING and hasWritePermission}">
									<a href="#" class="editDateBtn" data-url="${editOrderDateURL}" data-order-id="${order.id}">
										<img src="${staticImages}/edit.svg">
									</a>
								</c:if>	
							</td>
							<td>
								<span>
									<c:out value="${order.customer.locale.name}"></c:out>
								</span>
							</td>
							<td>
								<span>
									<c:out value="${order.customer.name}"></c:out>
								</span>
							</td>
							
							<c:if test="${status == FINALIZED}">
								<td>
									<span>
										<c:out value="${order.receivedPersonName}"></c:out>
									</span>
								</td>
							</c:if>
							
							<td>								
								<c:url var="orderReportURL" value="/restrict/orderItem/pdf_report/ListByOrder.action">
									<c:param name="order" value="${order.id}"/>
								</c:url>
								<a class="reportLink" target="_blank" href="${orderReportURL}">
									<img src="${staticImages}/pdf-icon-512x512.png">	
								</a>
							</td>
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
		<input id="orderId" type="hidden" name="order">
		<label for="date">Nova data:</label> <br />
		<div id="dateErrorDiv generalErrorDiv"></div>
		<div id="generalErrorDiv"></div>
		<input class="date" type="text" name="date" id="date"/>
	</form>
</div>

<jsp:include page="/public/dialogs.jsp"/>

<c:url var="listOrderItemsURL" value="/restrict/orderItem/ListByOrder.action" />
<script>
	$(document).ready(function(){
		$(".date").datepicker();
		$(".date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		
		$.datepicker.setDefaults( $.datepicker.regional[ "pt-BR" ] );
		
		$("#content").attr("data-pagination-url", '${selectedOrdersURL}');
		$("#searchForm").attr("action", '${selectedOrdersURL}');
		
		$(".editDateBtn").on("click", function(event){
			event.stopPropagation();
			var dialogDiv = $("#editDateDlg");
			var url = $(this).attr("data-url");
			var successURL = $(this).attr("data-success-url");

			var currDateSpan = $(this).parent().find("span");
			var currentDate = currDateSpan.text();
			var orderId = $(this).attr("data-order-id");
			
			dialogDiv.find("#date").val(currentDate.trim());
			dialogDiv.find("#orderId").val(orderId);
			
			console.log(url);
			smallDialogForm($("#editDateDlg"), $("#newDateForm"), function(){
				changeDate(url, $("#editDateDlg"), currDateSpan);
			}).dialog("open");
		});
		
		$("body").on("click", ".sortBy", function(){
			
		});
	});
	
	function changeDate(url, dialog, dateSpanElement){
		var formData = $("#newDateForm").serialize();
		
		ajaxCall("post", url, formData, function(data, textStatus, xhr){
			if (hasCallbackErrors(xhr)){
				var JSON = jQuery.parseJSON(data);
				console.log(JSON);
				showDivErrors(JSON);
				showInputErrors(JSON);
				
			}else{
				dateSpanElement.text($("#newDateForm").children("#date").val());
				simpleModalDialog("Editar data de solicitação", "A data foi modificada com sucesso.");
				dialog.dialog("destroy");
			}
		});
	}
</script>
