<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="saveSubCategoryURL" value="/restrict/subcategory/Save.action" />

<div class="jqDialog" title="Criar nova categoria">
	<form id="newSubCategoryForm" onsubmit="return false;">
	 
		<label for="scname">Nome</label> <br />
		<div id="scNameErrorDiv"></div>
		<input type="text" name="name" id="scName" class="text ui-widget-content ui-corner-all"><br />
		
		<label for="icategory">Subitem *</label>
		<div id="scCategoryErrorDiv"></div>
		<select id="scCategory" name="category">
			<option selected="selected">Selecione...</option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.id}">${category.name }</option>
			</c:forEach>
		</select>
		
		<button type="button" onclick="saveNewSubCategory('${saveSubCategoryURL}')" 
			class="ui-button ui-widget ui-corner-all saveBtn"
			style="margin-top: 30px">
			Salvar
		</button>
	</form>
	
</div>