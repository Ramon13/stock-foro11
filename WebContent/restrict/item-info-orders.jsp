<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>

<style>
	.dropdown button{
		padding: 0px;
	}
</style>

<c:url var="listOrdersURL" value="/restrict/order/List.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

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
								<div class="dropdown-content" data-sortURL="${listOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="order.id" data-order="asc">Classificar em ordem crescente</span>
									<span class="sortBy" data-property="order.id" data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
							<span>Código</span>
						</th>
						<th>
							<div class="dropdown">
								<button class="dropdown-btn">
									<span class="ui-icon ui-icon-caret-1-s"></span>
								</button>
								<div class="dropdown-content" data-sortURL="${listOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="order.finalDate" data-order="asc">Classificar em ordem crescente</span>
									<span class="sortBy" data-property="order.finalDate" data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
							<span>Data</span>
						</th>
						<th>
							<div class="dropdown">
								<button class="dropdown-btn">
									<span class="ui-icon ui-icon-caret-1-s"></span>
								</button>
								<div class="dropdown-content" data-sortURL="${listOrdersURL}">
									<button class="ui-button ui-widget ui-corner-all close-dropdown">
										<img class="ui-icon ui-icon-closethick"></img>
									</button>
									<br />
									<span class="sortBy" data-property="order.customer.locale.name" data-order="asc">Classificar em ordem crescente</span>
									<span class="sortBy" data-property="order.customer.locale.name" data-order="desc">Classificar em ordem decrescente</span>
								</div>
							</div>
							<span>Local</span>
						</th>
						
						<th>
							<span>Pedido por:</span>
						</th>
						
						<th>
							<span>Entregue a:</span>
						</th>
						
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
							
							<td>
								<span>
									<c:out value="${order.receivedPersonName}"></c:out>
								</span>
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