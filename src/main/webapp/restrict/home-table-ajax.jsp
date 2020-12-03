<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cfmt" uri="/WEB-INF/tag/custom-fmt.tld" %>

<table>	
	<c:forEach items="${items}" var="item" varStatus="loop">
		<c:url var="itemInfoURL" value="/restrict/item/ItemInfo.action">
			<c:param name="itemId" value="${item.id}"/>
		</c:url>
		<tbody>	
			<tr class="itemRow" onclick="location.href='${itemInfoURL}'">
				<td><c:out value="${item.id}"></c:out></td>
				
				<td><c:out value="${item.name}"></c:out></td>
				
				<td><c:out value="${item.packet.name}"></c:out></td>
				
				<td><c:out value="${item.category.name}"></c:out></td>
				
				<td><c:out value="${item.subCategory.name}"></c:out></td>
				
				<c:forEach items="${locales}" var="locale">
					<td class="sum-locale"><c:out 
						value="${itemLocaleFromPreviousYear.itemLocales[loop.index].sumLocales[locale.id]}"></c:out></td>
				</c:forEach>
			
				<td><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].startDateAmount}" /></td>
			
				<c:forEach items="${locales}" var="locale">
					<td class="sum-locale"><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].sumLocales[locale.id]}"></c:out></td>
				</c:forEach>
				
				<td><c:out value="${itemLocaleBetweenDates.itemLocales[loop.index].endDateAmount}" /></td>		
			</tr>
		</tbody>
	</c:forEach>
</table>
