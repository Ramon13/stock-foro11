//loads and append the JSON data to select html element
function loadSubCategory(url){
	
	//gets the isubcategory options based on icategory id
	ajaxCall("get", url, $("#category").serialize(), function(data, textStatus, xhr){
		//clear all options
		$("#subCategory").empty();
		
		//adds subcategory name and value to option
		$.each(jQuery.parseJSON(data), function(){
			$("#subCategory").append(new Option(this.name, this.id));
		});
	});
}

function setInputError(input){
	input.addClass("inputError");
}



