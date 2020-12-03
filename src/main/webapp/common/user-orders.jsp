<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>

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
	
	.status-p{
		color: #0033cc;
	}
	
	.status-r{
		color: #c17d11;
	}	
	
	.status-f{
		color: #009933;
	}
	
	.status-c{
		color: #ff0000;
	}
	
	table{
		width: 80% !important;
		margin: auto;
	}
</style>

<div>
	<c:choose>
		<c:when test="${empty orders}">
			<h3>Não há pedidos para serem exibidos</h3>
		</c:when>
		
		<c:otherwise>
			<fieldset>
				<legend>Pedidos</legend>
				<table>
					<thead>
						<tr>
							<th><span>Código</span></th>
							<th><span>Data</span></th>
							<th><span>Status do pedido</span></th>
						</tr>
					</thead>
					<c:forEach items="${orders}" var="order">
						<c:url var="listOrderItems" value="/common/order.action">
							<c:param name="order" value="${order.id}" />
						</c:url>
						<tbody>
							<tr onclick="location.href='${listOrderItems}'">
								<td>
									<span>${order.id}</span>
								</td>
								<td>
									<span>
										<c:choose>
											<c:when test="${order.status eq PENDING}">
												<c:set var="displayDate" value="${order.requestDate}"/>
											</c:when>
											<c:when test="${order.status eq RELEASED}">
												<c:set var="displayDate" value="${order.releaseDate}"/>
											</c:when>
											<c:otherwise>
												<c:set var="displayDate" value="${order.finalDate}"/>
											</c:otherwise>
										</c:choose>
										<cfmt:formatDate locale="pt_BR" value="${displayDate}"/>
									</span>
								</td>
								<td>	
									<c:choose>
										<c:when test="${order.status eq PENDING}">
											<span class="status-p">Pendente</span>
										</c:when>
										<c:when test="${order.status eq RELEASED}">
											<span class="status-r">Liberado</span>
										</c:when>
										<c:when test="${order.status eq FINALIZED}">
											<span class="status-f">Finalizado</span>
										</c:when>
										<c:when test="${order.status eq CANCELED_BY_ADMIN}">
											<span class="status-c">Cancelado pelo administrador</span>
										</c:when>
										<c:when test="${order.status eq CANCELED_BY_USER}">
											<span class="status-c">Cancelado pelo usuário</span>
										</c:when>
									</c:choose>
									
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</fieldset>		
		</c:otherwise>
	</c:choose>
</div>

<jsp:include page="/public/dialogs.jsp" />

<script>
	$(document).ready(function(){
		$("#contentDiv").attr("data-pagination-url", '${ordersURL}');
		updateCartCount('${cartCountURL}');
	});
</script>