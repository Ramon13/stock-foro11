package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Entry;
import entity.EntryItem;

public class EntryItemDAO extends DAO<EntryItem>{

	public EntryItemDAO() {
		super(EntryItem.class);
	}
	
	public List<EntryItem> listByEntry(Entry entry) throws DAOException{
		String hql = "from EntryItem ei where ei.entry = :entry";
		Query<EntryItem> query = createQuery(hql, EntryItem.class);
		query.setParameter("entry", entry);
		return query.list();
	}
}
	
