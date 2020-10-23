<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="saveLocaleURL" value="/restrict/locale/Save.action"></c:url>

<div class="jqDialog" hidden="hidden" title="Criar novo local">

	<form id="newLocaleForm" onsubmit="return false" style="text-align: center">
		<div id="generalErrorDiv"></div>
		 
		<label for="name">Nome</label> <br />
		<div id="lnameErrorDiv"></div>
		<input type="text" name="name" id="lname" class="text ui-widget-content ui-corner-all"><br />
		
		<button type="button" onclick="saveNewLocale('${saveLocaleURL}')" 
			class="ui-button ui-widget ui-corner-all saveBtn"
			style="margin-top: 30px">
			Salvar
		</button>
	</form>
</div>