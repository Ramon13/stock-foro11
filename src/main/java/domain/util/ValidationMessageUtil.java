package domain.util;

public class ValidationMessageUtil {

	public static final String EMPTY_ITEM_NAME = "É necessário uma descrição para o cadastro de novos itens.";
	public static final String EMPTY_PACKET_NAME = "É necessário uma descrição para o cadastro de novas unidades.";
	public static final String EMPTY_CATEGORY_NAME = "É necessário uma descrição para o cadastro de novos subitens.";
	public static final String EMPTY_SUBCATEGORY_NAME = "É necessário uma descrição para o cadastro de novas categorias.";
	
	public static final String ITEM_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String PACKET_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String CATEGORY_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String SUBCATEGORY_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String INVALID_CATEGORY_TYPE = "A categoria precisa ser um número compreendido entre 0 e 1000";
	
	public static final String INVALID_PACKET_ID = "Unidade inválida ou inexistente";
	public static final String INVALID_CATEGORY_ID = "Subitem inválido ou inexistente";
	public static final String INVALID_SUBCATEGORY_ID = "Categoria inválida ou inexistente"; 
	
	public static final String ITEM_NAME_EXISTS = "Esta descrição já está cadastrada no sistema.";
	public static final String PACKET_NAME_EXISTS = "Esta unidade já está cadastrada no sistema.";
	public static final String CATEGORY_NAME_EXISTS = "Este subitem já está cadastrada no sistema.";
	public static final String SUBCATEGORY_NAME_EXISTS = "Esta categoria já está cadastrada no sistema.";
	
	public static final String ID_NOT_FOUND = "A propriedade enviada não existe ou não é reconhecida pelo sistema.";
	public static final String SAVE_ITEM_SUCCESS = "Item salvo com sucesso.";
	
}
