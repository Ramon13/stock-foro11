<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="header.jsp" %>

<style>
	td.sum-locale{
		width: 30px;
	    margin: 0px;
	    padding: 0px;
	}
	
	.myHeader tr th{
		border: none;
    	padding-bottom: 4px !important;
	}
</style>

<c:url var="homeActionURL" value="/restrict/home.action" />
<c:url var="newItemURL" value="/wpermission/item/New.action" />

<table id="tableHome" >
	<thead class="myHeader">
		<tr>
			<th colspan="5"></th>
			<th colspan="7">
				<span>Consumo por seção - ${lastYear}</span>
			</th>
			<th></th>
			<th colspan="7">
				<span>Consumo entre datas</span>
			</th>	 
			<th>
				<div id="pageMenu">
					<a class="reportLink" data-report-type="pdf">
						<img src="${staticImages}/pdf-icon-512x512.png">
					</a>
					<a class="reportLink" data-report-type="xls">
						<img src="${staticImages}/excel-icon.png">
					</a>
					<c:if test="${hasWritePermission}">
						<a href="${newItemURL}">
							<img src="${staticImages}/new-item.png">
						</a>
					</c:if>
				</div>
			</th>
		</tr>
	</thead>

	<thead  class="myHeader2">
		<tr>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Código</span>
					</div>
					<div class="dropdown-content" data-sortURL="${loadTableContentURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Descrição</span>
					</div>
					<div class="dropdown-content" data-sortURL="${loadTableContentURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Unidade</span>
					</div>
					<div class="dropdown-content" data-sortURL="${loadTableContentURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="packet.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="packet.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Subitem</span>
					</div>
					<div class="dropdown-content" data-sortURL="${loadTableContentURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="category.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="category.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Categoria</span>
					</div>
					<div class="dropdown-content" data-sortURL="${loadTableContentURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="subCategory.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="subCategory.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			
			<c:forEach begin="0" end="6" varStatus="loop">
				<th><div  class="verticalTableHeader"><c:out value="${locales[loop.index].name}" /></div></th>
			</c:forEach>
			
			<th>
				<span class="dateErrorMsg"></span>
				<div id="dateErrorDiv"></div>
				<span>Estoque em:</span>
				<br />
				<input type="text" id="primaryDate" class="date" name="date" 
							value="<cfmt:formatDate value="${primaryDate}" locale="ptBR"/>" />
			</th>
			
			<c:forEach begin="0" end="6" varStatus="loop">
				<th><div  class="verticalTableHeader"><c:out value="${locales[loop.index].name}" /></div></th>
			</c:forEach>
			
			<th>
				<span>Estoque em:</span>
				<br />
				<input type="text" id="secondDate" class="date" name="date" 
							value="<cfmt:formatDate value="${secondDate}" locale="ptBR"/>" />
			</th>
		</tr>
	</thead>
	
	<c:forEach items="${items}" var="item" varStatus="loop">
		<c:url var="itemInfoURL" value="/restrict/item/ItemInfo.action">
			<c:param name="itemId" value="${item.id}"/>
		</c:url>
			
		<tbody>	
			<tr class="itemRow" onclick="location.href='${itemInfoURL}'">
				<td><c:out value="${item.id}"></c:out></td>
				
				<td><c:out value="${item.name}"></c:out></td>
				
				<td><c:out value="${item.packet.name}"></c:out></td>
				
				<td><c:out value="${item.category.name}"></c:out></td>
				
				<td><c:out value="${item.subCategory.name}"></c:out></td>
				
				<c:forEach begin="0" end="6" varStatus="j">
					<td class="sum-locale"><c:out 
						value="${itemLocaleFromPreviousYear.itemLocales[loop.index].sumLocales[j.index].sum}"></c:out></td>
				</c:forEach>
			
				<td><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].startDateAmount}" /></td>
			
				<c:forEach begin="0" end="6" varStatus="j">
					<td class="sum-locale"><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].sumLocales[j.index].sum}"></c:out></td>
				</c:forEach>
				
				<td><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].endDateAmount}" /></td>		
			</tr>
		</tbody>
	</c:forEach>
		
</table>
	
<script>
	$(document).ready(function(){	
		const PRIMARY_DATE = "primaryDate";
		const SECOND_DATE = "secondDate";
		
		$(".date").datepicker();
		$(".date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		
		$(".date").on("change", function(){
			var url = window.location.href;
			
			var primaryDateParam = [{name: "primaryDate", value: formatDate($("#primaryDate").datepicker("getDate"))}];
			var secondDateParam = [{name: "secondDate", value: formatDate($("#secondDate").datepicker("getDate"))}];
			
			url = addParams(url, $.param(primaryDateParam));
			url = addParams(url, $.param(secondDateParam));
			
			console.log(url);
			location.href = url;
		});
		
		$(".reportLink").on("click", function(){
			var url = window.location.href;
			
			var primaryDateParam = [{name: "primaryDate", value: formatDate($("#primaryDate").datepicker("getDate"))}];
			var secondDateParam = [{name: "secondDate", value: formatDate($("#secondDate").datepicker("getDate"))}];
			var reportTypeParam = [{name: "reportType", value: $(this).attr("data-report-type")}];
			
			url = addParams(url, $.param(primaryDateParam));
			url = addParams(url, $.param(secondDateParam));
			url = addParams(url, $.param(reportTypeParam));
			window.open(url, '_blank');
		});
	});
</script>