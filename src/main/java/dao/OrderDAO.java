package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import domain.OrderStatus;
import entity.Order;

public class OrderDAO extends ApplicationDAO<Order>{

	public OrderDAO() {
		super(Order.class);
	}

	public List<Order> listByStatus(OrderStatus status) throws DAOException{
		String hql = "from Order o where o.status like :status order by o.id desc";
		Query<Order> query = createQuery(hql, Order.class);
		query.setParameter("status", status.getValue());
		return query.list();
	}
}
