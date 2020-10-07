<%@page import="entity.EntryItem"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	$( function() {$( "#tabs" ).tabs(); });
</script>

<style>
	#tabNewEntry tbody:hover{
		background-color: unset !important;
	}
	
	#saveEntries{
		margin-right: 50px;
	}
	
	div#generalErrorDiv, #successDiv {
	    margin: auto;
	    text-align: center;
	    margin-bottom: 30px;
	    width: 35%;
	    background-color: #ff0000;
	    border-radius: 50px;
	}
	
	#generalErrorDiv span, #successDiv span{
		color: white !important;
		font-size: 15px;
	}
	
	div#successDiv{
		background-color: #4e9a06;
	}
	
</style>

<c:url var="newEntryURL" value="/restrict/entry/New.action" />

<c:url var="listEntriesURL" value="/restrict/entry/List.action">
	<c:param name="listAll" value="true" />
</c:url>

<fmt:setLocale value="pt_BR"/>

<div id="tabs">
	<ul>
		<li><a href="#tabEntries" >Entradas</a></li>
		<li><a href="#tabNewEntry" onclick="loadNewEntryPage('${newEntryURL}'); return false;" >Nova Entrada</a></li>
	</ul>
	
	<div id="tabEntries" data-url="${listEntriesURL}"></div>
	
	<div id="tabNewEntry"></div>
</div>
<script>
	$(document).ready(function(){
		
		var listEntriesURL = $("#tabEntries").attr("data-url");
		loadEntries(listEntriesURL);
	});
	
	function loadEntries(url){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$("#tabEntries").html(data);
			$("#searchForm").attr("action", '${listEntriesURL}');
			$("#dinamicContent").attr("data-pagination-url", '${listEntriesURL}');
		});
	}
	
	function loadNewEntryPage(url){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$("#tabNewEntry").html(data);
		});
		return false;
	}
	
	
</script>