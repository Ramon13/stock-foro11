<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>

<style>
	input.date {
    	font-size: 12px;
    	width: 120px;
	}
	
	thead tr th{
		background-color: #ffffff;
		padding-bottom: 20px;
	}
	
	#myHeader tr th{
		top: 0;
    position: sticky;
	}
	
	#myHeader2 tr th{
		top: 20px;
    position: sticky;
    padding-top: 50px;
	}
</style>

<c:url var="newItemURL" value="/restrict/item/New.action" />
<c:url var="searchItemsURL" value="/restrict/item/List.action" />
<c:url var="changeFilterDate" value="/restrict/date/ChangeHomeFilterDate.action"/>
<c:url var="homeURL" value="/restrict/item/List.action" />


<table id="tableHome">
	<thead id="myHeader">
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
			<!--
				<input type="button" value="Novo Item" 
					onclick="window.location.href='${newItemURL}'">
					-->
			</th>
			 
		</tr>
	</thead>
	<thead  id="myHeader2">
		<tr>
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
						<span class="sortBy" data-property="id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Código</span>
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
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${homeURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="subCategory.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="subCategory.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Categoria</span>
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
		
		initContent();
		$("#dinamicContent").attr("data-pagination-url", '${homeURL}');
		$("#searchForm").attr("action", '${homeURL}');
		
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
					loadPage('${homeURL}');
				}
			});
		});
	});
	
	function initContent(){
		ajaxCall("get", '${homeURL}', null, function(data, textStatus, xhr){
			if (isSuccessRequest(xhr)){
				var tbodyList = $(data).find("tbody");
				$.each(tbodyList, function(){
					$("table").append($(this));	
				});
			}
		});
	}
	
</script>