package dao;

import java.util.List;

import br.com.javamon.exception.DAOException;
import entity.Entry;
import entity.PaginationFilter;
import entity.PaginationFilter.orders;

public class EntryDAO extends ApplicationDAO<Entry>{

	public EntryDAO() {
		super(Entry.class);
	}
	
	public List<Entry> list() throws DAOException {
		PaginationFilter filter = new PaginationFilter();
		filter.setSortProperty("date");
		filter.setOrder(orders.DESC);
		filter.setFirstResultPage(1);
		return list(filter);
	}
	
}
