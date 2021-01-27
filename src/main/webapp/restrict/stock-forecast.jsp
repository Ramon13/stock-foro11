<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>

<%@ include file="header.jsp" %>
<fmt:setLocale value="pt_BR"/>

<c:url var="staticImages" value="/static/images"/>

<style>
	span.negativeStock{
		color: #ff0000;
	}
		
	.baseAvg{
		background-color: #ece797;
	}
	
	.forecastAmount{
		background-color: #8ddaf5;
	}
	
	.bidAmount{
		background-color: #a7ffa2;
	}
	
	.itemInfo{
		background-color: #cccccc !important;
	}
	
	.myHeader tr th{
		border: none;
    	padding-bottom: 4px !important;
	}
</style>

<table id="stockForecastTable">
	<thead class="myHeader">
		<tr>
			<th colspan="4" class="itemInfo"></th>
			<th colspan="3" class="baseAvg">
				<span>Consumo</span>
			</th>
			<th colspan="2" class="forecastAmount">
				<span>Previsão</span>
			</th>
			<th colspan="2" class="bidAmount">
				<span>Estimativa de despesa</span>
			</th>
			<th>
				<div id="pageMenu">
					<a class="reportLink" data-report-type="pdf">
						<img src="${staticImages}/pdf-icon-512x512.png">
					</a>
					<a class="reportLink" data-report-type="xls">
						<img src="${staticImages}/excel-icon.png">
					</a>
				</div>
			</th>
		</tr>
	</thead>
	
	<thead class="myHeader2">
		<tr>
			<th class="itemInfo">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Código</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="itemInfo">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Descrição</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="itemInfo">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Unidade</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="packet.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="packet.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="itemInfo">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Categoria</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="subCategory.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="subCategory.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="baseAvg">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Estoque em:</span>
						<br/>
						<input type="text" id="startDate" class="date" name="date" 
							value="<cfmt:formatDate value="${startDate}" locale="ptBR"/>" />
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="amount" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="amount" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="baseAvg">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Média Anual</span>
						<br/>
						<span>(últimos 2 anos)</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="twoYearsAverage" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="twoYearsAverage" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="baseAvg">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Média Mensal</span>
						<br/>
						<span>(últimos 12 meses)</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="twelveMonthsAverage" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="twelveMonthsAverage" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="forecastAmount">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Estoque</span>
						<br/>
						<span>Mínimo</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="minRecommendedStock" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="minRecommendedStock" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="forecastAmount">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Estoque em:</span>
						<br/>
						<input type="text" id="endDate" class="date" name="date" 
							value="<cfmt:formatDate value="${endDate}" locale="ptBR"/>"/>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="forecastedStock" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="forecastedStock" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="bidAmount">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Licitar</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="bidAmount" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="bidAmount" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="bidAmount">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Valor Unitário</span>
						<br/>
						<span>(ultima aquisição)</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="lastEntryValue" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="lastEntryValue" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<th class="bidAmount">
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Valor total</span>
					</div>
					<div class="dropdown-content">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-app-property="total" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-app-property="total" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
		</tr>
	</thead>
	
	<c:forEach items="${itemStockForecasts}" var="itemStockForecast">
		<c:set var="item" value="${itemStockForecast.item}" />
		<tbody>	
			<tr>
				<td class="itemInfo"><c:out value="${item.id}"></c:out></td>
				
				<td class="itemInfo"><c:out value="${item.name}"></c:out></td>
				
				<td class="itemInfo"><c:out value="${item.packet.name}"></c:out></td>
				
				<td class="itemInfo"><c:out value="${item.subCategory.name}"></c:out></td>
				
				<td class="baseAvg"><fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.amount}" /></td>
				
				<td class="baseAvg"><fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.twoYearsAverage}" /></td>
					
				<td class="baseAvg"><fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.twelveMonthsAverage}" /></td>
				
				<td class="forecastAmount"><fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.minRecommendedStock}" /></td>
				
				<td class="forecastAmount">
					<span class=${itemStockForecast.forecastedStock lt 0 ? 'negativeStock' : ''}>
						<fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.forecastedStock}" />
					</span>
				</td>					
				
				<td class="bidAmount"><fmt:formatNumber type="number" maxFractionDigits="0" value="${itemStockForecast.bidAmount}" /></td>
				
				<td class="bidAmount"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${itemStockForecast.lastEntryValue}" /></td>
				
				<td class="bidAmount"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${itemStockForecast.total}" /></td>
			</tr>
		</tbody>
	</c:forEach>
</table>

<script>
	$(document).ready(function(){
		$(".date").datepicker();
		loadContentOnEndPage(false);
		
		$(".date").on("change", function(){
			var url = window.location.href;
			
			var primaryDateParam = [{name: "startDate", value: formatDate($("#startDate").datepicker("getDate"))}];
			var secondDateParam = [{name: "endDate", value: formatDate($("#endDate").datepicker("getDate"))}];
			
			url = addParams(url, $.param(primaryDateParam));
			url = addParams(url, $.param(secondDateParam));
			
			console.log(url);
			location.href = url;
		});
			
		$(".reportLink").on("click", function(){
			var url = window.location.href;
			
			var primaryDateParam = [{name: "startDate", value: formatDate($("#startDate").datepicker("getDate"))}];
			var secondDateParam = [{name: "endDate", value: formatDate($("#endDate").datepicker("getDate"))}];
			var reportTypeParam = [{name: "reportType", value: $(this).attr("data-report-type")}];
			
			url = addParams(url, $.param(primaryDateParam));
			url = addParams(url, $.param(secondDateParam));
			url = addParams(url, $.param(reportTypeParam));
			window.open(url, '_blank');
		});
	});
</script>