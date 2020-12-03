<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
	<c:when test="${not empty item}">
		<c:url var="listEntriesURL" value="/restrict/entries.action">
			<c:param name="item" value="${item.id}"/>
		</c:url>
		<c:set var="fieldName" value="entry." />
	</c:when>
	
	
	<c:otherwise>
		<c:url var="listEntriesURL" value="/restrict/entries.action">
			<c:param name="listAll" value="true" />
		</c:url>
	</c:otherwise>
</c:choose>

<table>
	<thead>
		<tr>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Código</span>
					</div>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="${fieldName}id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="${fieldName}id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Data</span>
					</div>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="${fieldName}date" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="${fieldName}date" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Fornecedor</span>
					</div>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="${fieldName}provider.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="${fieldName}provider.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				<div class="dropdown">
					<div class="dropdown-btn">
						<img class="ui-icon ui-icon-triangle-1-s"></img>
						<span>Nº Documento</span>
					</div>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="${fieldName}invoice.invoiceIdNumber" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="${fieldName}invoice.invoiceIdNumber" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
		</tr>
	</thead>
	
	<c:forEach items="${entries}" var="entry">
		<tbody>
			<c:url var="entryItemsURL" value="/restrict/entryItem/List.action">
				<c:param name="entry" value="${entry.id}"/>
			</c:url>
			<tr class="view" onclick="openView(this)" data-url="${entryItemsURL}">
				<td>
					<span class="ui-icon ui-icon-triangle-1-s"></span>
					<span>
						<c:out value="${entry.id}"></c:out>
					</span>
				</td>
				<td>
					<span>
						<cfmt:formatDate value="${entry.date}" locale="ptBR"/>
					</span>
				</td>
				<td>
					<span>
						<c:out value="${entry.provider.name}"></c:out>
					</span>
				</td>
				<td>
					<span>
						<c:out value="${entry.invoice.invoiceIdNumber}"></c:out>
					</span>
				</td>
			</tr>
			<tr class="fold">
				<td colspan="4"></td>
			</tr>
		</tbody>
	</c:forEach>
</table>
