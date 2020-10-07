package domain.util;

public class ValidationMessageUtil {

	public static final String EMPTY_ITEM_NAME = "É necessário uma descrição para o cadastro de novos itens.";
	public static final String EMPTY_PACKET_NAME = "É necessário uma descrição para o cadastro de novas unidades.";
	public static final String EMPTY_CATEGORY_NAME = "É necessário uma descrição para o cadastro de novos subitens.";
	public static final String EMPTY_SUBCATEGORY_NAME = "É necessário uma descrição para o cadastro de novas categorias.";
	public static final String EMPTY_PERSON_NAME = "É necessário o nome da pessoa que recebeu o pedido.";
	
	public static final String ITEM_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String ITEM_DESCRIPTION_MAX_LEN = "A especificação não pode ter mais do que 1000 caracteres.";
	public static final String PACKET_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String CATEGORY_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String SUBCATEGORY_MAX_LEN = "A descrição não pode ter mais do que 255 caracteres.";
	public static final String INVALID_CATEGORY_TYPE = "A categoria precisa ser um número compreendido entre 0 e 1000";
	
	public static final String INVALID_ITEM_ID = "Item inválido ou inexistente";
	public static final String INVALID_ORDER_ID = "Pedido inválido ou inexistente";
	public static final String INVALID_IMAGE_ID = "Imagem inválida ou inexistente";
	public static final String INVALID_PACKET_ID = "Unidade inválida ou inexistente";
	public static final String INVALID_PROVIDER_ID = "Fornecedor inválido ou inexistente";
	public static final String INVALID_CATEGORY_ID = "Subitem inválido ou inexistente";
	public static final String INVALID_SUBCATEGORY_ID = "Categoria inválida ou inexistente"; 
	
	public static final String ITEM_NAME_EXISTS = "Esta descrição já está cadastrada no sistema.";
	public static final String PACKET_NAME_EXISTS = "Esta unidade já está cadastrada no sistema.";
	public static final String CATEGORY_NAME_EXISTS = "Este subitem já está cadastrada no sistema.";
	public static final String SUBCATEGORY_NAME_EXISTS = "Esta categoria já está cadastrada no sistema.";
	
	public static final String ID_NOT_FOUND = "A propriedade enviada não existe ou não é reconhecida pelo sistema.";
	public static final String SAVE_ITEM_SUCCESS = "Item salvo com sucesso.";
	
	public static final String INVALID_DATE = "Data inválida";
	public static final String INVALID_DATE_FUTURE = "A data não pode ser após a data atual";
	public static final String EMPTY_DATE = "È necessário uma data para o cadastro de novas entradas";
	
	public static final String EMPTY_INVOICE = "O número do documento é necessário para o cadastro de novas entradas.";
	public static final String INVOICE_NUMBER_EXISTS = "Este número de documento já está cadastrado no sistema";
	public static final String INVOICE_MAX_LEN = "O número do documento não pode ter mais do que 25 caracteres.";
	public static final String INVALID_INTEGER_CONVERSION = "O campo requer um número inteiro menor 10⁶ e sem casas decimais";
	public static final String INVALID_DOUBLE_CONVERSION = "O campo requer um número inteiro menor 10⁶ com no máximo duas casas decimais.";
	
	public static final String ROLE_PERMISSION_DENIED = "Seu usuário não tem permissão para executar esta ação.";
	
	public static final String GENERIC_EMPTY_FIELD = "O campo não pode ser vazio.";
	public static final String GENERIC_MAX_LEN_255 = "O campo não pode conter mais que 255 caracteres.";
	public static final String ILLEGAL_CREDENTIALS = "Usuário e/ou senha incorretos.";
	
	public static final String EMPTY_STOCK = "Quantidade não disponível em estoque";
	
}
