<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ccvt" uri="/WEB-INF/tag/custom-convert.tld" %>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="fold-content">						
	<table>
		<thead>
			<tr>
				<th>Item</th>
				<th>Quantidade</th>
				<th>Valor Unitário (R$)</th>
				<th>Valor Total (R$)</th>
			</tr>
		</thead>
		<c:forEach items="${entryItems}" var="entryItem">
			<tbody>
				<tr>
					<td>
						<c:out value="${entryItem.item.name}" />	
					</td>
					<td>
						<fmt:formatNumber value="${entryItem.amount}" type="number"/>
					</td>
					<td>
						<fmt:formatNumber value="${entryItem.value}" type="number" minFractionDigits="2"/>
					</td>
					<td>
						<fmt:formatNumber value="${entryItem.amount * entryItem.value}" type="number" minFractionDigits="2"/>
					</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
</div>