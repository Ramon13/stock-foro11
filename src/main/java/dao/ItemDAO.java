package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Item;
import entity.PaginationFilter;
import entity.SubCategory;

public class ItemDAO extends ApplicationDAO<Item>{

	public ItemDAO() {
		super(Item.class);
	}
	
	public List<Item> list() throws DAOException{
		String hql = "from Item i";
		return list(hql);
	}
	
	public Item findItemByName(String name) throws DAOException{
		String hql = "from Item i where i.name like :name";
		Query<Item> query = createQuery(hql, Item.class);
		query.setParameter("name", name);
		
		return query.uniqueResult();
	}
	
	public Item merge(Item item) throws DAOException{
		return (Item) getSession().merge(item);
	}
	
	public Long sumOrdersAmount(Item item) throws DAOException{
		String hql = "sum (amount) from Item";
		return createQuery(hql, Long.class).getSingleResult();
	}
	
	@SuppressWarnings("deprecation")
	public List<Item> listBySubCategory(PaginationFilter filter, SubCategory...subCategories) throws DAOException{
		Criteria criteria = getSession().createCriteria(Item.class);
		
		SimpleExpression[] expressions = new SimpleExpression[subCategories.length];
		for (int i = 0; i < subCategories.length; i++) {
			expressions[i] = Restrictions.eq("subCategory", subCategories[i]);
		}
		
		Disjunction orExp = Restrictions.or(expressions);
		criteria.add(orExp);
		criteria.setMaxResults(filter.getMaxResults());
		criteria.setFirstResult(filter.getFirstResultPage());
		return criteria.list();
	}
	
	public Long getLastItemId() throws DAOException{
		Query<Long> query = createQuery("select i.id from Item i order by i.id desc", Long.class);
		query.setMaxResults(1);
		return query.uniqueResult();
	}
}