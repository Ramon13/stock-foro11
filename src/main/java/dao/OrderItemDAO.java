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
import entity.Item;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;
import entity.ShoppingCart;

public class OrderItemDAO extends ApplicationDAO<OrderItem>{

	public OrderItemDAO() {
		super(OrderItem.class);
	}
	
	public List<OrderItem> listAll() throws DAOException{
		return createQuery("from OrderItem", OrderItem.class).getResultList();
	}
	
	public List<OrderItem> listOrdersByItem(Item item) throws DAOException{
		String hql = "from OrderItem o where o.item.id = :itemId order by o.order.id desc";
		Query<OrderItem> query = createQuery(hql, OrderItem.class);
		query.setParameter("itemId", item.getId());
		
		return query.list();
	}
	
	@SuppressWarnings({"deprecation", "unchecked"})
	public List<OrderItem> list(Item item, PaginationFilter filter){
		Criteria criteria = getSession().createCriteria(OrderItem.class);
		criteria.add(Restrictions.eq("item.id", item.getId()));
		setSortProperties(criteria, filter);
		
		return criteria.setMaxResults(filter.getMaxResults())
			.setFirstResult(filter.getFirstResultPage())
			.list();
	}
	
	public List<OrderItem> list(Item item, PaginationFilter filter, OrderStatus status){
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = builder.createQuery(OrderItem.class);
		
		Root<OrderItem> orderItemRoot = criteriaQuery.from(OrderItem.class);
		
		Predicate predicateForItemId = builder.equal(orderItemRoot.get("item").get("id"), item.getId());
		Predicate predicateForOrderStatus = builder.equal(orderItemRoot.get("order").get("status"), status.getValue());
		
		Predicate finalPredicate = builder.and(predicateForItemId, predicateForOrderStatus);
		
		setSortProperties(builder, criteriaQuery, orderItemRoot, filter);
		criteriaQuery.where(finalPredicate);
		
		Query<OrderItem> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
	

	public List<OrderItem> listOrderItemsByOrder(Order order) throws DAOException{
		String hql = "from OrderItem i where i.order = :order";
		Query<OrderItem> query = createQuery(hql, OrderItem.class);
		query.setParameter("order", order);
		return query.list();
	}
	
	public List<OrderItem> searchByItem(Item item, PaginationFilter filter) throws DAOException{
		getSearchableFields(clazz.getSimpleName(), getClassFields(clazz));
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = builder.createQuery(clazz);
		
		Root<OrderItem> root = criteriaQuery.from(clazz);
		
		Predicate predicateForItemId = builder.equal(root.get("item").get("id"), item.getId());
		Predicate[] predicates = getSearchPredicates(builder, root, filter);
		
		Predicate andPredicate = builder.and(predicateForItemId);
		Predicate orPredicate = builder.or(predicates);
		
		criteriaQuery.where(andPredicate, orPredicate);
		
		setSortProperties(builder, criteriaQuery, root, filter);
		
		Query<OrderItem> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
	
	
	public List<OrderItem> listByShoppingCart(ShoppingCart shoppingCart) throws DAOException{
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = builder.createQuery(OrderItem.class);
		
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		
		Predicate predicateForCartId = builder.equal(root.get("cart").get("id"), shoppingCart.getId());
		criteriaQuery.where(predicateForCartId);
		
		Query<OrderItem> query = getSession().createQuery(criteriaQuery);
		return query.list();
	}
}
