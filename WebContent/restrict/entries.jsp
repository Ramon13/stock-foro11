<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	jqueryTabs();
</script>

<div id="tabs">
	<ul>
		<li><a href="#tabEntries">Entradas</a></li>
		<li><a href="#tabNewEntry">Nova Entrada</a></li>
	</ul>
	<div id="tabEntries">
		<table>
			<thead>
				<tr>
					<th>Código</th>
					<th>Data</th>
					<th>Fornecedor</th>
					<th>Nº Documento</th>
				</tr>
			</thead>
			<c:forEach items="${entries}" var="entry">
				<tbody>
					<tr class="view">
						<td>
							<span class="ui-icon ui-icon-triangle-1-s"></span>
							<c:out value="${entry.id}"></c:out>
						</td>
						<td>
							<c:out value="${entry.date}"></c:out>
						</td>
						<td>
							<c:out value="${entry.provider.name}"></c:out>
						</td>
						<td>
							<c:out value="${entry.invoice.invoiceIdNumber}"></c:out>
						</td>
					</tr>
					<tr class="fold">
						<td colspan="4">
							<div class="fold-content">
								<h3>Aqui vai uma a lista de itens</h3>
								<table>
									<thead>
										<tr>
											<th>Item</th>
											<th>Quantidade</th>
											<th>Valor Unitário</th>
											<th>Valor Total</th>
										</tr>
									</thead>
									
										<tbody>
											<tr>
												<td>
													item
												</td>
												<td>
													0
												</td>
												<td>
													0
												</td>
												<td>
													0
												</td>
											</tr>
										</tbody>
									
								</table>
							</div>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	<div id="tabNewEntry">
		<h1>New entry</h1>
	</div>
</div>

<script>
	$(document).ready(function(){
		var flag = true;
		$("tr.view").on("click", function(){
			$(this).toggleClass("open-view").next(".fold").toggleClass("open-fold");
			
			var spanIcon = $(this).find(".ui-icon");
			flag == true ? spanIcon.attr("class", "ui-icon ui-icon-triangle-1-n") : spanIcon.attr("class", "ui-icon ui-icon-triangle-1-s");
			flag = !flag;
		});
	});
</script>
