package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	public List<EntryItem> listAll() throws DAOException{
		return createQuery("from EntryItem", EntryItem.class).getResultList();
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
	
	public List<EntryItem> searchByItem(Item item, PaginationFilter filter) throws DAOException{
		getSearchableFields(clazz.getSimpleName(), getClassFields(clazz));
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<EntryItem> criteriaQuery = builder.createQuery(clazz);
		
		Root<EntryItem> root = criteriaQuery.from(clazz);
		
		Predicate predicateForItemId = builder.equal(root.get("item").get("id"), item.getId());
		Predicate[] predicates = getSearchPredicates(builder, root, filter);
		
		Predicate andPredicate = builder.and(predicateForItemId);
		Predicate orPredicate = builder.or(predicates);
		
		criteriaQuery.where(andPredicate, orPredicate);
		
		setSortProperties(builder, criteriaQuery, root, filter);
		
		Query<EntryItem> query = getSession().createQuery(criteriaQuery);
		query.setMaxResults(filter.getMaxResults());
		query.setFirstResult(filter.getFirstResultPage());
		
		return query.getResultList();
	}
}
	
