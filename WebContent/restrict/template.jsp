<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--Actions URL's --%>
<c:url var="homeURL" value="/restrict/item/List.action">
	<c:param name="loadHomePage" value="true"/>
</c:url>
<c:url var="loadEntryPage" value="/restrict/entry/LoadPage.action" />

<%--Css and Javascript URL's --%>
<c:url var="css" value="/static/css"/>
<c:url var="js" value="/static/js"/>
<c:url var="jqteCss" value="/jquery-te-1.4.0.css"/>
<c:url var="chosenCs	s" value="/js/chosen.css"/>

<c:url var="listOrdersURL" value="/restrict/order/List.action"/>
<c:url var="listUserURL" value="/restrict/user/List.action"/>
<c:url var="printPdfURL" value="restrict/item/Print.action" />
<c:url var="listStockForecastURL" value="/restrict/stockForecast/List.action"></c:url>
<c:url var="newItemURL" value="/restrict/item/New.action" />


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Controle De Estoque - Foro Da 11ª</title>
		<link rel="stylesheet" type="text/css" href="${css}/global.css">
		<link rel="stylesheet" type="text/css" href="${css}/restrictStyle.css">
		<link rel="stylesheet" type="text/css" href="${css}/chosen.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-te-1.4.0.css">
		<link rel="stylesheet" type="text/css" href="${restrictCss}">
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script type="text/javascript" src="${js}/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="${js}/jquery-ui.js"></script>
		<script type="text/javascript" src="${js}/datepicker-pt-BR.js"></script>
		<script  src="${js}/global.js"></script>
		<script src="${js}/restrictJS.js"></script>
		<script src="${js}/chosen.jquery.js"></script>
		<script src="${js}/init.js"></script>
		<script src="${js}/jquery-te-1.4.0.min.js"></script>
		
		
		<script type="text/javascript">    
			$(document).ready(function(){	
				$( "#menu" ).menu();
				loadPage('${homeURL}');
			});
		</script>
		
		<style>
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
		</style>
	</head>
	
	<body>
		<div id="topMenu">
			<div class="title">
				<h1>Controle De Estoque - Foro Da 11ª</h1>
			</div>
			<div class="loggedUser">
				<label>admin</label>
			</div>
		</div>
		<div id="content">
			<header>
				<div>
					<ul id="menu">
						<li>
							<div onclick="loadPage('${homeURL}')">
								<span class="ui-icon ui-icon-home"></span>Início
							</div>
						</li>
						<li>
							<div><span class="ui-icon ui-icon-circle-arrow-n"></span>Pedidos</div>
							<ul>
								<li>
									<div onclick="loadPage('${listOrdersURL}?status=P')">		
										Pendentes
									</div>
								</li>
								<li>
									<div onclick="loadPage('${listOrdersURL}?status=R')">		
										Autorizados
									</div>
								</li>
								<li>
									<div onclick="loadPage('${listOrdersURL}?status=F')">		
										Finalizados
									</div>
								</li>
								<li>
									<div onclick="loadPage('${listOrdersURL}?status=C')">		
										Cancelados Pelo Administrador
									</div>
								</li>
								<li>
									<div onclick="loadPage('${listOrdersURL}?status=U')">
										Cancelados Pelo Usuário
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div onclick="loadPage('${loadEntryPage}')">
							<span class="ui-icon ui-icon-circle-arrow-s"></span>Entradas</div>
						</li>
						<li>
							<div onclick="loadPage('${listUserURL}')">
								<span class="ui-icon ui-icon-person"></span>Usuários do sistema
							</div>
						</li>
						<li>
							<div><span class="ui-icon ui-icon-document"></span>Relatórios</div>
							<ul>
								<li>
									<div onclick="loadPage('${listStockForecastURL}')">		
										Necessidade de Aquisição
									</div>
								</li>
							</ul>
						</li>
						<li>
							<c:url var="logoutURL" value="/public/auth/Logout.action"/>
							<div onclick="location.href='${logoutURL}'">
								Sair 
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
				<div id="tableOptions">
					<button class="ui-button ui-widget ui-corner-all printPdfBtn">
						<img src="static/images/pdf-icon-512x512.png">
					</button>
					<button class="ui-button ui-widget ui-corner-all">
						<img src="static/images/printer-icon-512x512.png">
					</button>
					
					<div id="details" class="dropdown">
						<button class="ui-button ui-widget ui-corner-all dropdown-btn">
							<img src="static/images/details-icon-512x512.png">
						</button>
						<div class="dropdown-content">
							<span id="newItemBtn">Novo Item</span>
							
							
						</div>
					</div>
					
					
				</div>
			</div>
			
			<div id="dinamicContent">
			
			</div>
		</div>
	</body>
	
	
	<script>
		$(document).ready(function(){
			$(".printPdfBtn").on("click", function(){
				window.open('${printPdfURL}');
				
			});
			
			$("#newItemBtn").on("click", function(){
				loadPage('${newItemURL}');
			});
		});
	</script>		
</html>