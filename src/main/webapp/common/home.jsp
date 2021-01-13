<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="header.jsp" %>

<c:url var="staticImages" value="/static/images"/>
<c:url var="homeURL" value="/common/home.action" />
<c:url var="addToCartURL" value="/common/cart/add.action" />
<c:url var="cartCountURL" value="/common/cart/count.action"></c:url>

<script type="text/javascript">    
	$(document).ready(function(){	
		$( ".subcategoryToogle" ).checkboxradio();
		$( ".shape-bar, .brand" ).controlgroup();
		$( ".toggles" ).controlgroup( {
		  direction: "vertical"
		} );
	});
</script>

<style>

	#contentDiv{
		width: 80%;
		height: 95vh;
		float: right;
		overflow: auto;	
	}

	.category-menu{
		width: 20%;
		float: left;
	}
	
	img{
		width: 150px;
	    height: 150px;
	    border: 1px solid #d2d2d2;
	}
	
	.card{
		border: 1px solid #e7e7e7;
		border-radius: 8px;
		padding-bottom: 20px;
		display: inline-table;
		text-align: center;
		margin: 20px;
	}
	
	.cardOptions{
		width: 200px;
	}
	
	.add-cart-btn{
		background-color: #007bff;		
		color: #fff !important;
		border-color: rgba(27,31,35,.15) !important;
		box-shadow: 0 1px 0 rgba(27,31,35,.1), inset 0 1px 0 hsla(0,0%,100%,.03) !important;
	}
	
	.add-cart-btn:hover, .add-cart-btn:focus{
		background-color: #196fcc !important;
	}
	
	.item-amount{
		width: 30px !important;
		padding: 10px !important;
	}
	
</style>

<div>
	<div class="toggle-wrap category-menu">
		<div class="toggles">	
			<c:forEach items="${subCategories}" var="subCategory">
				<label for="${subCategory.id}">${subCategory.name}</label>
				<input class="subcategoryToogle" type="checkbox" id="${subCategory.id}"/>	
			</c:forEach>
		</div>
	</div>
	
	<div id="contentDiv" class="cardGrid">
		<c:forEach items="${items}" var="item">
			<div class="card">
				<c:url var="loadItemImageURL" value="/common/item/LoadImage.action">
					<c:param name="item" value="${item.id}"/>
					<c:param name="image" value="${item.mainImage}" />
				</c:url>			
				<img src="${empty item.mainImage ? '../static/images/no-image-icon.jpg' : loadItemImageURL}">
				
				<div class="cardOptions">
					<span class="item-name">${item.name}</span>
					<br />
					<button type="button" class="ui-button ui-widget ui-corner-all add-cart-btn"
						data-item-id="${item.id}">
						Adicionar ao carrinho
					</button>
				</div>
			</div>	
		</c:forEach>
	</div>			
</div>

<jsp:include page="/public/dialogs.jsp" />

<script>
	$(document).ready(function(){
		$("#contentDiv").attr("data-pagination-url", '${homeURL}');
		
		updateCartCount('${cartCountURL}');
		
		$("body").on("click", ".add-cart-btn", function(){
		
			var itemId = $(this).attr('data-item-id');
			var param = [{name:"item", value: itemId}];
			ajaxCall("post", '${addToCartURL}', param, function(data, textStatus, xhr){
				if (hasCallbackErrors(xhr)){
					simpleModalDialog("Falha ao adicionar produto", data);
					
				}else{
					simpleModalDialog("Produto adicionado", "Produto adicionado ao carrinho.")	
				}
			});
		});
		
		$(".toggles input[type=checkbox]").on("change", function(){
			var url = '${homeURL}';
			url = setSubcategoriesParams(url);
			
			ajaxCall("get", url, null, function(data, textStatus, xhr){
				if (isSuccessRequest(xhr)){
					var cardList = $(data).find(".card");
					$(".cardGrid").html('');
					
    				$.each(cardList, function(){
    					$(".cardGrid").append($(this));
    				});
				}
				
			});
		});
		
		
		$('#contentDiv').on('scroll', function() {

	        if(scrolled == false && isMaxScrollHeight($(this))) {
	        	
	        	var url = setPaginationParams();
	        	url = setSubcategoriesParams(url);
	        	
				ajaxCall("get", url, null, function(data, textStatus, xhr){
					if (isSuccessRequest(xhr)){
						var cardList = $(data).find(".card");
	    				$.each(cardList, function(){
	    					$(".cardGrid").append($(this));
	    				});
					}
					
					scrolled = false;
				});	
	        }
	    });
	    
	});
	
	function setSubcategoriesParams(url){
	    var checkboxes = $(".toggles input[type=checkbox]");
			
		var data;
		
		$.each(checkboxes, function(){
			if($(this).is(":checked") ){
				data = {subcategory: $(this).attr("id")}; 
				url = addParam(url, data);
			}
		});
		
		return url;
    }
    
    function setPaginationParams(){
    	var cardsCount = $(".cardGrid").find(".card").length;
				
       	var i = 0;
       	while (i < cardsCount){
       			
       		i += 50;	
       	}
       	
       	cardsCount = i;
		var url = $("#contentDiv").attr("data-pagination-url");
		
		if (url != ""){
			url = addParam(url, {firstResultPage: cardsCount})
			url = addParam(url, {search: $("#searchInput").val()})
		}
		
		return url;
    }
    
    function addToCart(){
    
    }
</script>