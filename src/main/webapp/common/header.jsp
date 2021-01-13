<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:url var="css" value="/static/css"/>
<c:url var="staticImages" value="/static/images"/>
<c:url var="js" value="/static/js"/>
<c:url var="jqteCss" value="/jquery-te-1.4.0.css"/>
<c:url var="cartURL" value="/common/cart.action"></c:url>
<c:url var="homeURL" value="/common/home.action"></c:url>
<c:url var="ordersURL" value="/common/orders.action"></c:url>
<c:url var="logoutURL" value="/public/auth/Logout.action"></c:url>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Foro Da 11ª - Controle De Estoque</title>
		<link rel="stylesheet" type="text/css" href="${css}/global.css">
		<link rel="stylesheet" type="text/css" href="${css}/restrictStyle.css">
		<link rel="stylesheet" type="text/css" href="${css}/jquery-te-1.4.0.css">
		<link rel="stylesheet" type="text/css" href="${restrictCss}">
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script type="text/javascript" src="${js}/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="${js}/jquery-ui.js"></script>
		<script type="text/javascript" src="${js}/datepicker-pt-BR.js"></script>
		<script  src="${js}/global.js"></script>
		<script src="${js}/restrictJS.js"></script>
		<script src="${js}/jquery-te-1.4.0.min.js"></script>
		
		<style>
			body{
				background-color: #66a7a7;
			}
		
			#content{
				width: 70% !important;
				height: 100vh !important;
				margin: auto;
				float: none !important;
				background-color: #ffffff;
			}
		
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
		 
			.ui-icon-cart{
				padding-right: 10px;
			}
			
			.cart-icon{
				width: 20px !important;
				height: 20px !important; 
				border: none;
			}
			
			div.loggedUser{
				padding: 0px !important;
			}
			
			div.loggedUser li a:hover{
				background-color: #1f9bbf;
			}
			
			div.loggedUser li{
				float: right;
			}
			
			div.loggedUser li a{
				display: block;
			    color: white;
			    text-align: center;
			    padding: 14px 15px;
			    text-decoration: none;
			} 
			
			div.loggedUser ul{
				overflow: hidden;
			    margin: 0;
			    padding: 0;
			    list-style-type: none;
			}
			
			h1{
				padding-left: 10px !important;
			}
			
			div#contentMenuBar{
				width: auto !important;
				float: none;
			}
			
			input#searchInput{
				width: 100%;
				padding: 12px 20px;
			}
			
		</style>
	</head>
	
	<body>
		<div id="content">
			<div id="topMenu">
				<div class="title">
					<h1 onclick="location.href='${homeURL}'">Foro Da 11ª -Controle De Estoque</h1>
				</div>
				
				<div class="loggedUser">
					<ul>
						<li>
							<a href="${logoutURL}">Sair</a>		
						</li>
						<li>
							<a href="${cartURL}">
								<img class="cart-icon" src="${staticImages}/cart_icon.png">
								<span id="cartCount"></span>
							</a>
						</li>
						<li>
							<a href="${ordersURL}">Pedidos</a>
						</li>
						<li>
							<a href="${homeURL}">Início</a>
						</li>
					</ul>
					
				</div>
			</div>
			
			<div id="contentMenuBar">
			<form id="searchForm" onsubmit="return false">
				<input id="searchInput" placeholder="Pesquisar..."  name="search" type="text" data-fields="all"/>
			</form>
		</div>
			