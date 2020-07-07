package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Item;
import entity.OrderItem;

public class OrderItemDAO extends DAO<OrderItem>{

	public OrderItemDAO() {
		super(OrderItem.class);
	}
	
	public List<OrderItem> listOrdersByItem(Item item) throws DAOException{
		String hql = "from OrderItem o where o.item.id = :itemId";
		Query<OrderItem> query = createQuery(hql, OrderItem.class);
		query.setParameter("itemId", item.getId());
		
		return query.list();
	}

}
