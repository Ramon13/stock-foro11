<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--Css and Javascript URL's --%>
<c:url var="css" value="/static/css"/>
<c:url var="js" value="/static/js"/>
<c:url var="resetPasswordURL" value="/public/auth/ResetPass.action"/>
<c:url var="staticImages" value="/static/images"/>

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
				width: 100% !important;
				margin: auto;
				text-align: center;
				background-color: #ffffff;
				margin-top: 50px;
			}
			
			.resetPassBtn{
				border-radius: 3px !important;
			    text-align: center !important;
			    border: none !important;
			   	padding: 10px 10px 5px 10px;
			    color: #ffffff !important;
				background-color: #1a88ff !important;
				font-size: 14px !important;
			}
			
			.resetPassBtn:hover{
				background-color: #007bff !important;
			}
			
			input[type=text], input[type=password]{
				width: 300px;
				text-align: center;
			}

			.resetPassBtn img{
				width: 20px; 
			}
			
			.resetPassBtn span{
				vertical-align: super;
				margin-right: 5px;
				font-size: 14px !important;
			}
		</style>
	</head>
	
	<script>
		$(function(){
			$(".resetPassBtn").find("img").hide();
			
			$(".resetPassBtn").on("click", function(){
				$(this).find("img").show();
				$("#resetPasswordForm").submit();
			});
		});
	</script>
	
	<body>
		<div id="topMenu">
			<div class="title">
				<h1>Controle De Estoque - Foro Da 11ª</h1>
			</div>
		</div>
		<div id="content">
			<fieldset>
				<legend>Definir nova senha</legend>
				<form id="resetPasswordForm" method="post" action="${resetPasswordURL}">
					
					<div class="errorMsg">${generalError}</div>
					<input type="hidden" id="loginPage" value="true"/>
									
					<label for="currentPass">Senha Atual</label>
					<br />
					<div class="errorMsg">${incorrectPass}</div>
					<input id="currentPass" name="currentPass" autocomplete="current-password" type="password" 
						${empty incorrectPass ? "" : "class='inputError'"}/>
					
					<br /><br />
					
					<label for="newPass">Nova Senha</label>
					<br />
					<div class="errorMsg">${invalidNewPassLen}</div>
					<input id="newPass" name="newPass" autocomplete="new-password" type="password"
						${empty invalidNewPassLen ? "" : "class='inputError'"}/>
					
					<br /><br />
					
					<label for="newPassTwice">Repita a Senha</label>
					<br />
					<div class="errorMsg">${invalidNewPassTwiceLen}</div>
					<input id="newPassTwice" name="newPassTwice" autocomplete="new-password-twice" type="password"
						${empty invalidNewPassTwiceLen ? "" : "class='inputError'"}/>
					
					<br /><br />
									
					<button type="submit" class="ui-button ui-widget ui-corner-all resetPassBtn">
						<span>Entrar</span>
						<img src="${staticImages}/load-circle.gif">
					</button>
				</form>
				</fieldset>
		</div>
	</body>

</html>