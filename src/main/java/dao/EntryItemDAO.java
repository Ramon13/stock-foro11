package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Entry;
import entity.EntryItem;
import entity.Item;
import entity.PaginationFilter;

public class EntryItemDAO extends ApplicationDAO<EntryItem>{

	public EntryItemDAO() {
		super(EntryItem.class);
	}
	
	public List<EntryItem> listByEntry(Entry entry) throws DAOException{
		String hql = "from EntryItem ei where ei.entry = :entry";
		Query<EntryItem> query = createQuery(hql, EntryItem.class);
		query.setParameter("entry", entry);
		return query.list();
	}
	
	public List<EntryItem> listByItem(Item item) throws DAOException{
		String hql = "from EntryItem ei where ei.item = :item order by ei.entry.date desc";
		Query<EntryItem> query = createQuery(hql, EntryItem.class);
		query.setParameter("item", item);
		return query.list();
	}
	
	@SuppressWarnings({"deprecation", "unchecked"})
	public List<EntryItem> listByItem(Item item, PaginationFilter filter){
		Criteria criteria = getSession().createCriteria(EntryItem.class);
		
		criteria.add(Restrictions.eq("item.id", item.getId()));
		setSortProperties(criteria, filter);
		
		return criteria.setMaxResults(filter.getMaxResults())
			.setFirstResult(filter.getFirstResultPage())
			.list();
	}
	

	public List<EntryItem> searchByItem(String word, Item item) throws DAOException{
		StringBuilder hql = new StringBuilder();
		hql.append("from EntryItem o where o.item.id = :itemId and ( o.id < 0 ");
		
		bfsFields(hql, getAllFields());
		
		hql.append(" )");
		System.out.println(hql.toString());
		Query<EntryItem> query = createQuery(hql.toString(), EntryItem.class);
		query.setParameter("itemId", item.getId());
		
		setParams(word, query);
		
		return query.list();
	}
}
	
