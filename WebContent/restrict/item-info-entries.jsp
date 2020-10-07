<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="listEntriesURL" value="/restrict/entry/List.action">
	<c:param name="item" value="${item.id}"/>
</c:url>

<table>
	<thead>
		<tr>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="entry.id" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="entry.id" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Entrada</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="entry.date" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="entry.date" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>
				<span>Data</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="entry.provider.name" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="entry.provider.name" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>	
				<span>Fornecedor</span>
			</th>
			<th>
				<div class="dropdown">
					<button class="dropdown-btn">
						<span class="ui-icon ui-icon-caret-1-s"></span>
					</button>
					<div class="dropdown-content" data-sortURL="${listEntriesURL}">
						<button class="ui-button ui-widget ui-corner-all close-dropdown">
							<img class="ui-icon ui-icon-closethick"></img>
						</button>
						<br />
						<span class="sortBy" data-property="entry.invoice.invoiceIdNumber" data-order="asc">Classificar em ordem crescente</span>
						<span class="sortBy" data-property="entry.invoice.invoiceIdNumber" data-order="desc">Classificar em ordem decrescente</span>
					</div>
				</div>	
				<span>Nº Documento</span>
			</th>
		</tr>
	</thead>
	<c:forEach items="${entries}" var="entry">
		<tbody>
			<c:url var="listEntryItemURL" value="/restrict/entryItem/List.action">
				<c:param name="entry" value="${entry.id}"/>
			</c:url>
			<tr class="view" onclick="openView(this)" data-url="${listEntryItemURL}">
				<td>
					<span>
						<span class="ui-icon ui-icon-triangle-1-s"></span>
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
