<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@include file="header.jsp" %>
<c:url var="newOrder" value="/common/order/new.action" />
<c:url var="cartCountURL" value="/common/cart/count.action"></c:url>

<style>

	#contentDiv{
		width: 85%;
		height: 98vh;
		float: right;
		overflow: auto;	
	}	
	
	img{
		width: 100px;
	    height: 100px;
	    border: 1px solid #d2d2d2;
	}
	
	table tr td{
		text-align: left !important;
		border: none !important;
	}
	
	.item-name {
		font-weight: bolder;
	}
	
	table{
		width: 60% !important;
		margin: auto;
	}
	
	table tbody:hover {
	    background-color: #ffffff !important;
	}
	
	input.cancelBtn{
		margin-top: 40px;
	    background-color: #de1414;
	    color: #ffffff;
	    padding: .6em 2em;
	}
	
	input.cancelBtn:hover{
		background: #b51616 !important;
    	color: #ffffff !important;
	}
}
</style>

<script>
	$(function(){
		$(".amountSlct").selectmenu();
		loadContentOnEndPage(false);
		updateCartCount('${cartCountURL}');
	});
</script>

<div>
	<c:choose>
		<c:when test="${empty order.orderItems}">
			<h3>Não há produtos para serem exibidos</h3>
		</c:when>
		
		<c:otherwise>
			<fieldset>
				<legend>Pedido Cód:${order.id}</legend>
				<table>
					<c:forEach items="${order.orderItems}" var="orderItem">
						<tr>
							<td width="100px">
								<c:url var="loadItemImageURL" value="/common/item/LoadImage.action">
									<c:param name="item" value="${orderItem.item.id}"/>
									<c:param name="image" value="${orderItem.item.mainImage}" />
								</c:url>		
								<img src="${loadItemImageURL}">
							</td>
							
							<td>
								<span class="item-name">${orderItem.item.name}</span>
								<br/>
								<span>Quantidade: ${orderItem.amount}</span>
							</td>
						</tr>
					</c:forEach>
					
					<c:if test="${order.status eq PENDING}">
						<c:url var="cancelOrderURL" value="/common/order/cancel.action"/>
						<tr>
							<td colspan="3">
								<form method="post" action="${cancelOrderURL}">
									<input type="hidden" name="order" value="${order.id}">
									<input type="submit" class="ui-button ui-widget ui-corner-all cancelBtn" value="Cancelar Pedido">
								</form>
							</td>
						</tr>
					</c:if>	
				</table>
			</fieldset>		
		</c:otherwise>
	</c:choose>
</div>

<jsp:include page="/public/dialogs.jsp" />

<script>
	$(document).ready(function(){
		$(".rm-cart-btn").on("click", function(){
			var url = $(this).attr("data-url");
			
			ajaxCall("post", url, null, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					location.reload(true);
				
				}else{
					simpleModalDialog("Falha ao remover produto", jQuery.parseJSON(data)[0].message);
				}
			});
		});
		
		$(".amountSlct").selectmenu({
			change: function(event, data){
				if (data.item.value === '10+'){
					$(this).parent().html("<input type='text' class='gtTenInput' name='itemAmount' value='10'/>")	
				}
			}
		});
		
		$(".saveBtn").on("click", function(){
			var url = $(this).attr("data-url");
			var data = $("fieldset").serialize();
			
			ajaxCall("get", url, data, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					location.reload(true);
				
				}else{
					simpleModalDialog("Falha ao finalizar pedido", jQuery.parseJSON(data)[0].message);
				}
			});
		});
	});
</script>