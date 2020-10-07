package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;

public class OrderItemDAO extends ApplicationDAO<OrderItem>{

	public OrderItemDAO() {
		super(OrderItem.class);
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
	

	public List<OrderItem> listOrderItemsByOrder(Order order) throws DAOException{
		String hql = "from OrderItem i where i.order = :order";
		Query<OrderItem> query = createQuery(hql, OrderItem.class);
		query.setParameter("order", order);
		return query.list();
	}
	
	public List<OrderItem> searchByItem(Item item, String word) throws DAOException{
		StringBuilder hql = new StringBuilder("from OrderItem o where o.item.id = :itemId and (o.id < 0 ");
		bfsFields(hql, getAllFields());
		hql.append(")");
		System.out.println(hql.toString());
		Query<OrderItem> query = createQuery(hql.toString(), OrderItem.class);
		setParams(word, query);
		query.setParameter("itemId", item.getId());
		
		return query.list();
	}
}
