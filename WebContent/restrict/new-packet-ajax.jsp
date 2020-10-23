<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="savePacketURL" value="/restrict/packet/Save.action" />

<div class="jqDialog" hidden="hidden" title="Criar nova unidade">
	<form id="newPacketForm" onsubmit="return false"> 
		<label for="pname">Nome</label> <br />
		<div id="pnameErrorDiv"></div>
		<input type="text" name="pname" id="pname" class="text ui-widget-content ui-corner-all">
		<button type="button" onclick="saveNewPacket('${savePacketURL}')" 
			class="ui-button ui-widget ui-corner-all saveBtn"
			style="margin-top: 30px">
			Salvar
		</button>
	</form>
</div>