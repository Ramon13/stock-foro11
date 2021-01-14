import java.math.BigDecimal;

import org.hibernate.Session;

import br.com.javamon.util.HibernateUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Item;

public class Main {

	
	public static void main(String[] args){
		HibernateUtil.beginTransaction();
		Session session = HibernateUtil.getCurrentSession();
		
		Item item = session.createQuery("from Item i where i.id = 1", Item.class).uniqueResult();
		Entry entry = session.createQuery("from Entry e where e.id = 1230", Entry.class).uniqueResult();
		
		EntryItem ei = new EntryItem();
		ei.setAmount(2);
		ei.setTotal(19.0);
		ei.setValue(new BigDecimal(2));
		ei.setItem(item);
		ei.setEntry(entry);
		session.save(ei);
		HibernateUtil.commitTransaction();
		
		
	}
}
