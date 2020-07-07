package dao;

import java.util.List;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Entry;

public class EntryDAO extends DAO<Entry>{

	public EntryDAO() {
		super(Entry.class);
	}
	
	public List<Entry> list() throws DAOException{
		String hql = "from Entry e order by e.date desc";
		return list(hql);
	}

}
