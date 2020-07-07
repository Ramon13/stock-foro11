package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Item;

public class ItemDAO extends DAO<Item>{

	public ItemDAO() {
		super(Item.class);
	}

	public List<Item> listItem()throws DAOException{
		String hql = "from Item";
		return list(hql);
	}
	
	public Item findItemByName(String name) throws DAOException{
		String hql = "from Item i where lower(i.name) like :name";
		Query<Item> query = createQuery(hql, Item.class);
		query.setParameter("name", name.toLowerCase());
		return query.uniqueResult();
	}
}