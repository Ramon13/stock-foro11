<%@page import="entity.EntryItem"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	$( function() {$( "#tabs" ).tabs(); });
</script>

<style>
	
</style>

<c:url var="listUsersURL" value="/restrict/user/List.action">
	<c:param name="listAll" value="true"/>
</c:url>
<c:url var="newUserURL" value="/restrict/user/New.action"/>

<div id="tabs">
	<ul>
		<li><a href="#tabUsers" >Usuários</a></li>
		<li><a href="#tabNewUser" onclick="loadNewUserPage('${newUserURL}') ; return false;" >Cadastrar</a></li>
	</ul>
	
	<div id="tabUsers" data-url="${listUsersURL}"></div>
	
	<div id="tabNewUser"></div>
</div>
<script>
	$(document).ready(function(){
		
		var listUsersURL = $("#tabUsers").attr("data-url");
		loadUsers(listUsersURL);
	});
	
	function loadUsers(url){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$("#tabUsers").html(data);
			$("#searchForm").attr("action", url);
			$("#dinamicContent").attr("data-pagination-url", url);
		});
	}
	
	function loadNewEntryPage(url){
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$("#tabNewEntry").html(data);
		});
		return false;
	}
	
	function loadNewUserPage(url){
		
		ajaxCall("get", url, null, function(data, textStatus, xhr){
			$("#tabNewUser").html(data);
			$("#searchForm").attr("action", "");
			$("#dinamicContent").attr("data-pagination-url", "");
		});
		return false;
	}
	
</script>