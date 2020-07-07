<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--Actions URL's --%>
<c:url var="listItems" value="/restrict/ListItems.action" />
<c:url var="listEntriesURL" value="/restrict/ListEntries.action" />

<%--Css and Javascript URL's --%>
<c:url var="globalCss" value="global.css"/>
<c:url var="restrictCss" value="/restrict/style.css"/>
<c:url var="globalJs" value="global.js"/>
<c:url var="restrictJs" value="/restrict/application.js"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Controle De Estoque - Foro Da 11ª</title>
		<link rel="stylesheet" type="text/css" href="${restrictCss}">
		<link rel="stylesheet" type="text/css" href="${globalCss }">
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script src="${globalJs }"></script>
		<script src="${restrictJs }"></script>
	</head>
	
	<body>
		<header>
			<div id="topMenu">
		
				<div><h1>Controle De Estoque - Foro Da 11ª</h1></div>
				
				<div>
					<input type="text" id="searchInput" placeholder="Pesquisar item">
				</div>
				
				<div id="userProfile"> 
					<label>admin</label>
				</div>
			</div>
			
			<div>
				<ul id="menu">
					<li>
						<div onclick="location.href='${loadItems}'"><span class="ui-icon ui-icon-home"></span>Início</div>
					</li>
					<li>
						<div><span class="ui-icon ui-icon-circle-arrow-n"></span>Pedidos</div>
						<ul>
							<li>
								<div>		
									Pendentes
								</div>
							</li>
							<li>
								<div>		
									Autorizados
								</div>
							</li>
							<li>
								<div>		
									Finalizados
								</div>
							</li>
							<li>
								<div>		
									Cancelados Pelo Administrador
								</div>
							</li>
							<li>
								<div>		
									Cancelados Pelo Usuário
								</div>
							</li>
						</ul>
					</li>
					<li>
						<div onclick="loadPage('${listEntriesURL}')"><span class="ui-icon ui-icon-circle-arrow-s"></span>Entradas</div>
					</li>
					<li>
						<div><span class="ui-icon ui-icon-person"></span>Usuários do sistema</div>
					</li>
					<li>
						<div><span class="ui-icon ui-icon-document"></span>Relatórios</div>
						<ul>
							<li>
								<div>		
									Necessidade de Aquisição
								</div>
							</li>
						</ul>
					</li>
					<li>
						<div>
							Sair 
						</div>
					</li>
				</ul>
			</div>
		</header>
		
		<div id="content">