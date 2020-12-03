<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="header.jsp" %>

<style>
	thead tr th{
		padding-bottom: 0px;
	}
	
</style>

<c:url var="changeFilterDate" value="/restrict/date/ChangeHomeFilterDate.action"/>
<c:url var="loadTableContentURL" value="/restrict/home.action">
	<c:param name="loadTableContent" value="true" />
</c:url>


<table id="tableHome" >
	<thead class="myHeader">
		<tr>
			<th colspan="5"></th>
			<th colspan="${fn:length(locales)}">
				<span>Consumo por seção - ${lastYear}</span>
			</th>
			<th></th>
			<th colspan="${fn:length(locales)}">
				<span>Consumo entre datas</span>
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
			
			<c:forEach items="${locales}" var="locale">
				<th><div  class="verticalTableHeader"><c:out value="${locale.name}" /></div></th>
			</c:forEach>
			
			<th>
				<span class="dateErrorMsg"></span>
				<div id="dateErrorDiv"></div>
				<span>Estoque em:</span>
				<br />
				<input type="text" id="primaryDate" class="date" name="date" 
							value="<cfmt:formatDate value="${primaryDate}" locale="ptBR"/>" 
							data-url="${changeFilterDate}"/>
			</th>
			
			<c:forEach items="${locales}" var="locale">
				<th><div  class="verticalTableHeader"><c:out value="${locale.name}" /></div></th>
			</c:forEach>
			
			<th>
				<span>Estoque em:</span>
				<br />
				<input type="text" id="secondDate" class="date" name="date" 
							value="<cfmt:formatDate value="${secondDate}" locale="ptBR"/>" 
							data-url="${changeFilterDate}"/>
			</th>
		</tr>
	</thead>
		
	</table>
	
<script>
	$(document).ready(function(){	
		const PRIMARY_DATE = "primaryDate";
		const SECOND_DATE = "secondDate";
		
		$(".date").datepicker();
		$(".date").datepicker("option", "altFormat", "(dd/mm/yyyy)");
		
		loadContent();
		$("#content").attr("data-pagination-url", '${loadTableContentURL}');
		$("#searchForm").attr("action", '${loadTableContentURL}');
		
		$(".date").on("change", function(){
			var date = formatDate($(this).datepicker("getDate"));
			var url = $(this).attr("data-url");
			var paramName = $(this).attr("id");
			
			var param = [{name: paramName, value: date}];
			ajaxCall("get", url, $.param(param), function(data, textStatus, xhr){
				if (hasCallbackErrors(xhr)){
					var JSONData = jQuery.parseJSON(data);
	  	   			showInputErrors(JSONData);
	  	   			showDivErrors(JSONData);
	  	   		
				}else{
					loadContent();
				}
			});
		});
	});
	
	function loadContent(){
		$("tbody").remove();
		ajaxCall("get", '${loadTableContentURL}', null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				var tbodyList = $(data).find("tbody");
				$.each(tbodyList, function(){
					$("table").append($(this));	
				});
			}
		});
	}
	
</script>