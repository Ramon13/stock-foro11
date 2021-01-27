package domain.issue;

import java.util.List;

import org.hibernate.Session;

import br.com.javamon.service.ServiceFactory;
import br.com.javamon.util.HibernateUtil;
import entity.Item;
import service.ItemService;

public class FixItemAmount {

	public static void main(String[] args) {
		try {
			HibernateUtil.beginTransaction();
			Session session = HibernateUtil.getCurrentSession();
			ItemService itemSvc = ServiceFactory.getInstance().getService(ItemService.class);
			
			List<Item> items = itemSvc.list();
			for (Item item : items) {
				item.setAmount(itemSvc.getItemCurrentAmount(item));
				session.update(item);
			}
			
			HibernateUtil.commitTransaction();
		}catch(Exception e) {
			HibernateUtil.rollbackTransaction();
		}
	}
}
