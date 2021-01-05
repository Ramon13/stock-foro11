package dao;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;

import br.com.javamon.convert.DateConvert;
import br.com.javamon.dao.DAO;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.DAOException;
import br.com.javamon.validation.StringValidator;
import entity.PaginationFilter;
import entity.PaginationFilter.orders;

public class ApplicationDAO<T> extends DAO<T>{

	private Class<T> clazz;
	private StringBuilder queryFieldName = new StringBuilder();
	private List<Class<?>> types = new ArrayList<Class<?>>();
	private int paramCount = 0;
	
	protected ApplicationDAO(Class<T> clazz) {
		super(clazz);
		this.clazz = clazz;
	}

	/** 
	 * Search the word pattern on fields annotated with @Search
	 * @param word the pattern to be search
	 */
	public List<T> search(PaginationFilter filter) throws DAOException{
		StringBuilder hql = new StringBuilder();
		hql.append(String.format("from %s o where o.id < 0 ", clazz.getSimpleName()));
		bfsFields(hql, getAllFields());
		Query<T> query = createQuery(hql.toString(), clazz);	
		setParams(filter.getSearchWord(), query);
		
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		return query.list();
	}
	
	private Stack<String> parentNames = new Stack<String>();
	private String path;
	protected void bfsFields(StringBuilder hql, List<Field> fields) {
		
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
				
				types.add(field.getType());
				queryFieldName = new StringBuilder();
			
			}
		}
		popParentName();
	}
	
	private void popParentName() {
		if (!parentNames.isEmpty())
			parentNames.pop();
	}
	
	private String getParentsPath() {
		if (parentNames.isEmpty())
			return null;
		
		StringBuilder sb = new StringBuilder();
		for (String parentName : parentNames) {
			sb.append("." + parentName);
		}
		
		return sb.toString();
	}
	
	protected void setParams(String word, Query<T> query) throws DAOException {
		String paramName;
		for (int i = 0; i < paramCount; i++) {
			paramName = "param" + i;
			bindParameter(paramName, word, types.get(i), query);
		}
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
	
	protected List<Field> getAllFields() {
		List<Field> fields = new ArrayList<Field>(); 
		fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
	    return fields;
	}
	
	private static List<Field> getSearchableFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Search.class))
				fields.add(field);
		}
		
		return fields;
	}
	
	/**
	 * bind parameters on the hibernate query. Convert numeric types. Set % on String fields to apply
	 * like operator  
	 * @param paramName
	 * @param paramValue
	 * @param type
	 * @param query
	 * @throws DAOException
	 */
	protected void bindParameter(String paramName, String paramValue, Class<?> type, Query<T> query){
		try {
			if (type.equals(LocalDate.class)) {
				try {
					LocalDate date = DateConvert.stringToLocalDate(paramValue, "dd/MM/yyyy");
					query.setParameter(paramName, date);
				} catch (ConvertException e) {
					query.setParameter(paramName, null);
				}
			
			}else if (type.equals(Long.class)) {
				if (StringValidator.isValidLongParse(paramValue))
					query.setParameter(paramName, Long.parseLong(paramValue));
				
			}else if (type.equals(Integer.class)) {
				if (StringValidator.isValidIntegerParse(paramValue))
					query.setParameter(paramName, Integer.parseInt(paramValue));
				
			}else if (type.equals(String.class)) {
				query.setParameter(paramName, "%" + paramValue + "%");
			
			}
			
		} catch (Exception e) {}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<T> list(PaginationFilter filter)throws DAOException{
		
		Criteria criteria = getSession().createCriteria(clazz);
		setSortProperties(criteria, filter);
		
		return criteria.setMaxResults(filter.getMaxResults())
			.setFirstResult(filter.getFirstResultPage())
			.list();
	}
	
	protected void setSortProperties(Criteria criteria, PaginationFilter filter) {
		String sortProperty = filter.getSortProperty();
		
		String[] properties = sortProperty.split("\\.");
		Criteria c1 = criteria;
		
		if (properties.length >= 2) {
			
			int numProperties = properties.length;
			for (int i = 0; i < properties.length -1; i++) {
				c1 = c1.createCriteria(properties[i]);
			}
			
			sortProperty = properties[numProperties-1];
		}
		Order order = (filter.getOrder().toString() == orders.ASC.toString()) ? Order.asc(sortProperty) : Order.desc(sortProperty);
		c1.addOrder(order);
	}

	protected void setSortProperties(CriteriaBuilder builder,CriteriaQuery<T> query, Root<T> root, PaginationFilter filter) {
		String sortProperty = filter.getSortProperty();
		
		String[] properties = sortProperty.split("\\.");
		Path<Object> path = root.get(properties[0]);
		
		if (properties.length >= 2) {
			
			for (int i = 1; i < properties.length -1; i++) {
				path = path.get(properties[i]);
			}
			
		}
		
		if (filter.getOrder().toString() == orders.ASC.toString()) {
			query.orderBy(builder.asc(path));
		
		}else {
			query.orderBy(builder.desc(path));
		}
	}
}

