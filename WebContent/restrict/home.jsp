<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp" />

<c:url var="saveItemURL" value="/restrict/SaveItem.action" />
<c:url var="downArrowImg" value="/images/down-arrow.png"/>

<table id="tableHome">
	<thead>
		<tr>
			<th colspan="5"></th>
			<th colspan="7">Consumo ano atual</th>
			<th></th>
			<th colspan="7">Consumo entre datas</th>
			<th>
				<input type="button" value="Novo Item" onclick="loadPage('${saveItemURL}')">
			</th>
		</tr>
		<tr>
			<th>
				Código
				<div class="dropdown">
					<img src="${downArrowImg }" class="dropbtn"/>
					<div class="dropdown-content">
						<span>Classificar em ordem crescente</span>
						<span>Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th style="width: 30%">
				Descrição
				<div class="dropdown">
					<img src="${downArrowImg}" class="dropbtn"/>
					<div class="dropdown-content">
						<span>Classificar em ordem crescente</span>
						<span>Classificar em ordem decrescente</span>
					</div>
				</div>
			</th>
			<th>
				Unidade
				<div class="dropdown">
					<img src="${downArrowImg}" class="dropbtn"/>
					<div class="dropdown-content">
						<span>Classificar em ordem crescente</span>
						<span>Classificar em ordem decrescente</span>
						<br />
						<span>Filtros</span>
						<input type="checkbox"/> Filtro 00 <br />
						<input type="checkbox"/> Filtro 01
					</div>
				</div>
			</th>
			<th>
				Subitem
				<div class="dropdown">
					<img src="${downArrowImg}" class="dropbtn"/>
					<div class="dropdown-content">
						<span>Classificar em ordem crescente</span>
						<span>Classificar em ordem decrescente</span>
						<br />
						<span>Filtros</span>
						<input type="checkbox"/> Filtro 00 <br />
						<input type="checkbox"/> Filtro 01
					</div>
				</div>	
			</th>
			<th>
				Categoria
				<div class="dropdown">
					<img src="${downArrowImg}" class="dropbtn"/>
					<div class="dropdown-content">
						<span>Classificar em ordem crescente</span>
						<span>Classificar em ordem decrescente</span>
						<br />
						<span>Filtros</span>
						<input type="checkbox"/> Filtro 00 <br />
						<input type="checkbox"/> Filtro 01
					</div>
				</div>
			</th>
			
			<c:forEach items="${locales}" var="locale">
				<th><div  class="verticalTableHeader"><c:out value="${locale.name}" /></div></th>
			</c:forEach>
			
			<th>
				Estoque Anterior
				<br />
				<input type="date" value="${applicationScope.firstDayOfYear}"/>
			</th>
			
			<c:forEach items="${locales}" var="locale">
				<th><div  class="verticalTableHeader"><c:out value="${locale.name}" /></div></th>
			</c:forEach>
			
			<th>
				Estoque Atual
				<br />
				<input type="date" value="${applicationScope.today}"/>
			</th>
		</tr>
	</thead>
	<c:forEach items="${itemLocales}" var="itemLocale">
		<tbody>	
			<tr>
				<td>
					<c:out value="${itemLocale.item.id}"></c:out>
				</td>
				<td>
					<c:out value="${itemLocale.item.name}"></c:out>
				</td>
				<td>
					<c:out value="${itemLocale.item.packet.name}"></c:out>
				</td>
				<td>
					<c:out value="${itemLocale.item.category.name}"></c:out>
				</td>
				<td>
					<c:out value="${itemLocale.item.subCategory.name}"></c:out>
				</td>
				
				<c:forEach items="${itemLocale.sumLocales}" var="sumLocale" end="${splitIndex}">
					<td>
						<c:out value="${sumLocale }"></c:out>
					</td>
				</c:forEach>
				
				<td>100</td>
				
				<c:forEach items="${itemLocale.sumLocales}" var="sumLocale" begin="${splitIndex + 1}">
					<td>
						<c:out value="${sumLocale }"></c:out>
					</td>
				</c:forEach>		
			</tr>
		</tbody>
	</c:forEach>
</table>

<jsp:include page="footer.jsp" />