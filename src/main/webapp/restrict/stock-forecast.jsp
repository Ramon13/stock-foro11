<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table id="stockForecastTable">
	<thead class="myHeader">
		<tr>
			<th colspan="5"></th>
			<th colspan="2">
				<span>Consumo anual</span>
			</th>
			<th>
				<span>Previsão</span>
			</th>
			<th></th>
			<th colspan="2">
				<span>Estimativa de despesa</span>
			</th>
		</tr>
	</thead>
	
	<thead class="myHeader2">
		<tr>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="#HERE-GOES-SORT-URL#">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
					<span>Código</span>
				</div>
			</th>
			
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="#HERE-GOES-SORT-URL#">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Descrição</span>
			</th>
			
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${homeURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="packet.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="packet.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Unidade</span>
			</th>
			
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${homeURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="category.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="category.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Subitem</span>
			</th>
			
			<th>
				<span>Estoque em:</span>
				<br/>
				<input type="text" id="previousDate" class="date" name="date" value=""/>
			</th>
			
			<th>
				<span>Média 2 anos</span>
			</th>
			
			<th>
				<span>Média 12 meses</span>
			</th>
			
			<th>
				<span>Estoque em:</span>
				<br/>
				<input type="text" id="forecastDate" class="date" name="date" value=""/>
			</th>
			
			<th>
				<span>Licitar</span>
			</th>
			
			<th>
				<span>Valor Unitário (ultima aquisição)</span>
			</th>
			
			<th>
				<span>Valor total</span>
			</th>
		</tr>
	</thead>
	
	<c:forEach items="${items}" var="item">
		<tbody>	
			<tr>
				<td><c:out value="${item.id}"></c:out></td>
				
				<td><c:out value="${item.name}"></c:out></td>
				
				<td><c:out value="${item.packet.name}"></c:out></td>
				
				<td><c:out value="${item.category.name}"></c:out></td>		
			</tr>
		</tbody>
	</c:forEach>
</table>

<script>
	$(document).ready(function(){
		$(".date").datepicker();
		
		
	});
</script>