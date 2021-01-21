package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import domain.OrderStatus;
import entity.Order;
import entity.PaginationFilter;
import entity.User;

public class OrderDAO extends ApplicationDAO<Order>{

	public OrderDAO() {
		super(Order.class);
	}

	@SuppressWarnings(value = {"deprecation", "unchecked"})
	public List<Order> listByStatus(OrderStatus status, PaginationFilter filter) throws DAOException{
		Criteria criteria = getSession().createCriteria(Order.class);
		criteria.add(Restrictions.eq("status", status.getValue()));
		setSortProperties(criteria, filter);
		
		return criteria.setMaxResults(filter.getMaxResults())
			.setFirstResult(filter.getFirstResultPage())
			.list();
	}
	
	public List<Order> listByUser(User user, PaginationFilter filter) throws DAOException{
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		
		Root<Order> root = criteriaQuery.from(Order.class);
		Predicate predicateForCustomerID = builder.equal(root.get("customer").get("id"), user.getId());
		setSortProperties(builder, criteriaQuery, root, filter);
		criteriaQuery.where(predicateForCustomerID);
		
		Query<Order> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
	
	public List<Order> search(OrderStatus status, PaginationFilter filter) throws DAOException{
		getSearchableFields(clazz.getSimpleName(), getClassFields(clazz));
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = builder.createQuery(clazz);
		
		Root<Order> root = criteriaQuery.from(clazz);
		Predicate predicateForStatus = builder.equal(root.get("status"), status.getValue());
		
		Predicate[] predicates = getSearchPredicates(builder, root, filter);
		
		Predicate predicateForProperties = builder.or(predicates);
		Predicate finalPredicate = builder.and(predicateForStatus, predicateForProperties);
		
		criteriaQuery.where(finalPredicate);
		
		setSortProperties(builder, criteriaQuery, root, filter);
		
		Query<Order> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
	
	public Order findByUser (Long orderId, User user) throws DAOException{
		Query<Order> query = createQuery("from Order o where o.id = :orderId and o.customer = :user", Order.class);
		query.setParameter("orderId", orderId);
		query.setParameter("user", user);
		return query.uniqueResult();
	}
}
