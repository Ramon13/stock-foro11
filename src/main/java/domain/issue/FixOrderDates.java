package domain.issue;

import java.util.List;

import org.hibernate.Session;

import br.com.javamon.service.ServiceFactory;
import br.com.javamon.util.HibernateUtil;
import domain.OrderStatus;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import service.ItemService;
import service.OrderItemService;

public class FixOrderDates {

	public static void main(String[] args) {

		try {
			HibernateUtil.beginTransaction();
			
			Session session = HibernateUtil.getCurrentSession();
			List<Order> orders = session.createQuery("from Order o", Order.class).getResultList();
			
			ItemService itemSvc = ServiceFactory.getInstance().getService(ItemService.class);
			Item item;
			for (Order o : orders) {
				for (OrderItem oi : o.getOrderItems()) {
					item = oi.getItem();
					item.setAmount(itemSvc.getItemCurrentAmount(item));
					session.update(item);
				}
				
				if (OrderStatus.getByValue(o.getStatus()).equals(OrderStatus.FINALIZED)) {
					o.setReleaseDate(o.getFinalDate());
					o.setRequestDate(o.getFinalDate());
					session.update(o);
				}
				
				if (OrderStatus.getByValue(o.getStatus()).equals(OrderStatus.RELEASED)) {
					o.setReleaseDate(o.getFinalDate());
					o.setRequestDate(o.getFinalDate());
					o.setStatus(OrderStatus.FINALIZED.getValue());
					session.update(o);
				}
			}
			
			HibernateUtil.commitTransaction();
		}catch(Exception e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	}
