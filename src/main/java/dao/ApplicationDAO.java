package dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.PaginationFilter;
import entity.PaginationFilter.orders;

public class ApplicationDAO<T> extends DAO<T>{

	protected Class<T> clazz;
	
	protected ApplicationDAO(Class<T> clazz) {
		super(clazz);
		this.clazz = clazz;
	}

	
	public List<T> search(PaginationFilter filter) throws DAOException{
		getSearchableFields(clazz.getSimpleName(), getClassFields(clazz));
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
		
		Root<T> root = criteriaQuery.from(clazz);
		
		Predicate[] predicates = getSearchPredicates(builder, root, filter);
		
		Predicate finalPredicate = builder.or(predicates);
		
		criteriaQuery.where(finalPredicate);
		
		setSortProperties(builder, criteriaQuery, root, filter);
		
		Query<T> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
	
	private List<String> fieldsPath = new ArrayList<>();
	
	protected Predicate[] getSearchPredicates(CriteriaBuilder builder, Root<T> root, PaginationFilter filter) {
		Predicate[] predicates = new Predicate[fieldsPath.size()];
		for (int i = 0; i < fieldsPath.size(); i++) {
			String[] fields = fieldsPath.get(i).split("\\.");

			Path<String> path = root.get(fields[1]);
			for (int j = 2; j < fields.length; j++) {
				path = path.get(fields[j]);
			}
			
			predicates[i] = builder.like(path.as(String.class), "%" + filter.getSearchWord() + "%");
		}
		
		return predicates;
	}
	
	protected void getSearchableFields(String fieldName, Field[] fields) {
		
		for (Field field : fields) {
			if (field.isAnnotationPresent(Search.class)) {
				
				String s = String.format("%s.%s", fieldName, field.getName());
				if (field.getAnnotation(Search.class).getMarckedFields()) {
					getSearchableFields(s, getClassFields(field.getType()));
				
				}else {
					fieldsPath.add(s);
				}
			}
		}
	}
	
	protected Field[] getClassFields(Class<?> clasz){
		List<Field> fields = new ArrayList<>();
		
		for (Field field : clasz.getSuperclass().getDeclaredFields()) {
			fields.add(field);
		}
		
		for (Field field : clasz.getDeclaredFields()) {
			fields.add(field);
		}
		
		return fields.toArray(new Field[0]);
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

