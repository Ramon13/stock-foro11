<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="listEntriesURL" value="/restrict/entry/List.action" />

<%--Css and Javascript URL's --%>
<c:url var="css" value="/static/css"/>
<c:url var="staticImages" value="/static/images"/>
<c:url var="js" value="/static/js"/>
<c:url var="jqteCss" value="/jquery-te-1.4.0.css"/>
<c:url var="chosenCs	s" value="/js/chosen.css"/>

<c:url var="homeURL" value="/restrict/home.action"/>
<c:url var="ordersURL" value="/restrict/orders.action"/>
<c:url var="listUserURL" value="/restrict/user/List.action"/>
<c:url var="stockForecastURL" value="/restrict/stockForecast/List.action" />
<c:url var="countUnfinishedOrdersURL" value="/restrict/orders.action">
	<c:param name="countUnfinished" value="true" />
</c:url>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Controle De Estoque - Foro Da 11ª</title>
		<link rel="stylesheet" type="text/css" href="${css}/global.css">
		<link rel="stylesheet" type="text/css" href="${css}/chosen.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-te-1.4.0.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-ui.structure.min.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-ui.theme.min.css">
		<link rel="stylesheet" type="text/css" href="${restrictCss}">
		<script type="text/javascript" src="${js}/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="${js}/jquery-ui.js"></script>
		<script type="text/javascript" src="${js}/datepicker-pt-BR.js"></script>
		<script  src="${js}/global.js"></script>
		<script src="${js}/restrictJS.js"></script>
		<script src="${js}/chosen.jquery.js"></script>
		<script src="${js}/init.js"></script>
		<script src="${js}/jquery-te-1.4.0.min.js"></script>
		<script src="${js}/tree-map.js"></script>
		
	</head>
	
	<script type="text/javascript">    
		$(document).ready(function(){	
			$( "#menu" ).menu();
			getUnfinishedOrdersNum();
		});
		
		function getUnfinishedOrdersNum(){
			ajaxCall("get", '${countUnfinishedOrdersURL}', null, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					$("#unfinishedOrdersNum").html("(" + data + ")");
				}
			});
		}
	</script>
	
	<style>
		h1:hover{
			cursor: pointer;	
		}
		
		#tableOptions{
			float: right;
			margin-right: 50px;
		}
		
		#tableOptions img{
			width: 20px !important;
			height: 20px !important;
		}
		
		#tableOptions button{
			border: none;
			padding: 5px;
			width: 30px;
		}
		
		form#searchForm {
		    width: 70%;
		    float: left;
		}
		
		input#searchInput{
			text-align: left;
		}
		
		#pending-ball{
			width: 10px;
		}
		
		ul#menu span{
			font-size: 15px !important;
		}
	</style>
	
	<body>
		<div id="topMenu">
			<div class="title">
				<h1 onclick="location.href='${homeURL}'">Controle De Estoque - Foro Da 11ª</h1>
			</div>
			<div class="loggedUser">
				<label>${loggedUser.user.name}</label>
			</div>
		</div>
		
		<header>
			<div>
				<ul id="menu">
					<li>
						<div onclick="location.href = '${homeURL}'">
							<span class="ui-icon ui-icon-home"></span>
							<span>Início</span>
						</div>
					</li>
					<li>
						<div>
							<span class="ui-icon ui-icon-circle-arrow-n">		
							</span>
							<span>Pedidos</span>
							<span id="unfinishedOrdersNum"></span>
						</div>
						<ul>
							<li>
								<div onclick="location.href='${ordersURL}?status=P'">		
									<span>Pendentes</span>
								</div>
							</li>
							<li>
								<div onclick="location.href='${ordersURL}?status=R'">		
									<span>Autorizados</span>
								</div>
							</li>
							<li>
								<div onclick="location.href='${ordersURL}?status=F'">		
									<span>Finalizados</span>
								</div>
							</li>
							<li>
								<div onclick="location.href='${ordersURL}?status=C'">		
									<span>Cancelados Pelo Administrador</span>
								</div>
							</li>
							<li>
								<div onclick="location.href='${ordersURL}?status=U'">
									<span>Cancelados Pelo Usuário</span>
								</div>
							</li>
						</ul>
					</li>
					<li>
						<div onclick="location.href = '${listEntriesURL}'">
							<span class="ui-icon ui-icon-circle-arrow-s"></span>
							<span>Entradas</span>
						</div>
					</li>
					<li>
						<div onclick="location.href = '${listUserURL}'">
							<span class="ui-icon ui-icon-person"></span>
							<span>Usuários do sistema</span>
						</div>
					</li>
					<li>
						<div><span class="ui-icon ui-icon-document"></span>
							<span>Relatórios</span>
						</div>
						<ul>
							<li>
								<div onclick="location.href='${stockForecastURL}'">		
									<span>Previsão de estoque</span>
								</div>
							</li>
						</ul>
					</li>
					<li>
						<c:url var="logoutURL" value="/public/auth/Logout.action"/>
						<div onclick="location.href='${logoutURL}'">
							<span>Sair</span> 
						</div>
					</li>
				</ul>
			</div>
		</header>
			
		<div id="contentMenuBar">
			<form id="searchForm" onsubmit="return false">
				<span class="ui-icon ui-icon-search"></span>
				<input id="searchInput" placeholder="Pesquisar..."  name="search" type="text" data-fields="all"/>
			</form>
			
			<!-- 
			<div id="tableOptions">
				<button class="ui-button ui-widget ui-corner-all printPdfBtn">
					<img src="${staticImages}/pdf-icon-512x512.png">
				</button>
				<button class="ui-button ui-widget ui-corner-all">
					<img src="${staticImages}/printer-icon-512x512.png">
				</button>
				
				<div id="details" class="dropdown">
					<button class="ui-button ui-widget ui-corner-all dropdown-btn">
						<img src="${staticImages}/details-icon-512x512.png">
					</button>
					<div class="dropdown-content">
						<span id="newItemBtn" onclick="location.href='${newItemURL}'">Novo Item</span>
					</div>
				</div>
				
				
			</div>
			 -->
		</div>
			
		<div id="content">