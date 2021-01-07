import java.lang.reflect.Field;

import dao.Search;
import entity.Item;
import entity.OrderItem;

public class Main {

	
	public static void main(String[] args){
		
	}
	
	
	private static void getSearchableFields(String fieldName, Field[] fields) {
		
		for (Field field : fields) {
			if (field.isAnnotationPresent(Search.class)) {
				
				String s = String.format("%s.%s", fieldName, field.getName());
				if (field.getAnnotation(Search.class).getMarckedFields()) {
					getSearchableFields(s, field.getType().getDeclaredFields());
				
				}else {
					System.out.println(s);
				}
			}
		}
	}
	
}
