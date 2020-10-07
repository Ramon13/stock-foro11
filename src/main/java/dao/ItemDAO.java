package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Item;

public class ItemDAO extends ApplicationDAO<Item>{

	public ItemDAO() {
		super(Item.class);
	}
	
	public List<Item> list() throws DAOException{
		String hql = "from Item i";
		return list(hql);
	}
	
	public Item findItemByName(String name) throws DAOException{
		String hql = "from Item i where lower(i.name) like :name";
		Query<Item> query = createQuery(hql, Item.class);
		query.setParameter("name", name.toLowerCase());
		
		return query.uniqueResult();
	}
	
	public Item merge(Item item) throws DAOException{
		return (Item) getSession().merge(item);
	}
	
	public Long sumOrdersAmount(Item item) throws DAOException{
		String hql = "sum (amount) from Item";
		return createQuery(hql, Long.class).getSingleResult();
	}
}