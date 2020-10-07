<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--Css and Javascript URL's --%>
<c:url var="css" value="/static/css"/>
<c:url var="js" value="/static/js"/>
<c:url var="loginURL" value="/public/auth/Login.action"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Controle De Estoque - Foro Da 11ª</title>
		<link rel="stylesheet" type="text/css" href="${css}/global.css">
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script type="text/javascript" src="${js}/jquery-3.5.1.min.js"></script>
		<script type="text/javascript" src="${js}/jquery-ui.js"></script>
		
		
		<style>
			div#content{
				width: 300px;
				margin: auto;
				text-align: center;
				background-color: #ffffff;
				margin-top: 50px;
			}
			
			.loginBtn{
				border-radius: 3px !important;
			    text-align: center !important;
			    border: none !important;
			    padding: 10px !important;
			    color: #ffffff !important;
				background-color: #1a88ff !important;
				font-size: 14px !important;
			}
			
			.loginBtn:hover{
				background-color: #007bff !important;
			}
			
			input[type=text], input[type=password]{
				width: 300px;
				text-align: center;
			}
		</style>
	</head>
	
	<body>
		<div id="topMenu">
			<div class="title">
				<h1>Controle De Estoque - Foro Da 11ª</h1>
			</div>
		</div>
		<div id="content">
			<form id="loginForm" onsubmit="return false">
				
				<div id="generalErrorDiv"></div>
				<input type="hidden" id="loginPage" value="true"/>
								
				<label for="username">Usuário</label>
				<br />
				<div id="usernameErrorDiv" ></div>
				<input id="username" name="username" autocomplete="username" type="text"/>
				
				<br /><br />
				
				<label for="password">Senha</label>
				<br />
				<div id="passwordErrorDiv"></div>
				<input id="password" name="password" autocomplete="current-password" type="password"/>
				
				<br /><br />
				
				<input type="submit" value="Entrar" class="ui-button ui-widget ui-corner-all loginBtn"
					data-login-url="${loginURL}" />
					
			</form>
		</div>
	</body>
	
	<script src="${js}/global.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			$(".loginBtn").on("click", function(){
				var url = $(this).attr("data-login-url");
				clearErrorDivs();
				
				$.ajax({
					type: "post",
					data: $("#loginForm").serialize(),
					url: url,
					success: function(data, textStatus, xhr){
				
						if (hasCallbackErrors(xhr)){
							var JSONData = jQuery.parseJSON(data);
			  	   			showInputErrors(JSONData);
			  	   			showDivErrors(JSONData);
			  	   		}
			  	   		else{
			  	   			location.reload();
			  	   		}
					}
				});			
			});
		});
	</script>
</html>