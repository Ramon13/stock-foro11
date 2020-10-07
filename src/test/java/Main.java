import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import dao.Search;
import entity.EntryItem;
import entity.Item;

public class Main {
	private static StringBuilder sb = new StringBuilder();
	private static Stack<String> parentNames = new Stack<String>();
	
	public static void main(String[] args) throws Exception {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		ZonedDateTime now = ZonedDateTime.now();
		System.out.println(LocalDate.now());
		System.out.println(now);
		
//		Class<?> clazz = Item.class;
//		sb.append(String.format("from %s o where o.id < 0 ", clazz.getSimpleName()));
//		bfsFields(sb, getSearchableFields(clazz));
//		System.out.println(sb.toString());
	}
	
	private static int paramCount = 0;
	private static StringBuilder queryFieldName = new StringBuilder();
	private static String path;
	public static void bfsFields(StringBuilder hql, List<Field> fields) {
		
		for (Field field : fields) {
			if (hasSearchableChildrens(field)) {
				parentNames.add(field.getName());
				bfsFields(hql, getSearchableFields(field.getType()));
			
			}else if (isSearchableField(field)) {
				if ((path = getParentsPath()) != null)
					queryFieldName.append(path);
				
				queryFieldName.append("." + field.getName());
				hql.append(" or ");	
				hql.append(String.format("o%s like :%s", 
						queryFieldName.toString(), "param" + paramCount++));
				System.out.println(queryFieldName);
				queryFieldName = new StringBuilder();
			
			}
		}
		
		popParentName();
	}
	
	private static void popParentName() {
		if (!parentNames.isEmpty())
			parentNames.pop();
	}
	
	private static String getParentsPath() {
		if (parentNames.isEmpty())
			return null;
		
		StringBuilder sb = new StringBuilder();
		for (String parentName : parentNames) {
			sb.append("." + parentName);
		}
		
		return sb.toString();
	}
	
	private static boolean hasSearchableChildrens(Field field) {
		Search searchAnnotation = field.getAnnotation(Search.class);
		if (searchAnnotation != null) {
			return searchAnnotation.getMarckedFields(); 
		}
		
		return false;
	}
	
	private static boolean isSearchableField(Field field) {
		return field.isAnnotationPresent(Search.class);
	}
	
	
	
	private static List<Field> getSearchableFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Search.class))
				fields.add(field);
		}
		
		return fields;
	}
}
