<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@include file="header.jsp" %>
<c:url var="newOrder" value="/common/order/new.action" />
<c:url var="cartCountURL" value="/common/cart/count.action" />
<c:url var="orderURL" value="/common/order.action" />

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
		width: 80% !important;
		margin: auto;
	}
	
	table tbody:hover {
	    background-color: #ffffff !important;
	}
	
	.ui-selectmenu-button.ui-button{
		width: 3em;
	}
	
	#amountSlctDiv{
		display: inline;
	}
	
	.gtTenInput{
		width: 80px !important;
		text-align: center;
	}
}
</style>

<script>
	$(function(){
		$(".amountSlct").selectmenu();
		updateCartCount('${cartCountURL}');
		loadContentOnEndPage(false);
	});
</script>

<div>
	<c:choose>
		<c:when test="${empty orderItems}">
			<h3>Não há produtos para serem exibidos</h3>
		</c:when>
		
		<c:otherwise>
			<fieldset>
				<legend>Carrinho</legend>
				<table>
					<c:forEach items="${orderItems}" var="orderItem">
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
								<span>Quantidade: </span>
								<div id="amountSlctDiv">
									<select class="amountSlct" name="itemAmount">
										<c:forEach begin="1" end="9" varStatus="loop">
											<option value="${loop.index}">${loop.index}</option>
										</c:forEach>
										<option class="gtTen">10+</option>
									</select>
								</div>
							</td>
							<td>
								<c:url var="deleteCartItem" value="/common/cart/delete.action">
									<c:param name="orderItem" value="${orderItem.id}" />
								</c:url>
								<button type="button" class="ui-button ui-widget ui-corner-all rm-cart-btn"
									data-url="${deleteCartItem}">
									Remover
								</button>
							</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="3">
							<button type="button" class="ui-button ui-widget ui-corner-all saveBtn"
								data-url="${newOrder}">
								Finaizar Pedido
							</button>
						</td>
					</tr>
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
			
			simpleConfirmationDialog("Abir pedido", "Prosseguir com a abertura do pedido no sistema?", function(){
				ajaxCall("get", url, data, function(data, textStatus, xhr){
					if (isSuccessRequest(xhr)){
						location.href = "${orderURL}?order=" + data;
					}else{
						simpleModalDialog("Falha ao finalizar pedido", jQuery.parseJSON(data)[0].message);
					}
				});
			});
		});
	});
</script>