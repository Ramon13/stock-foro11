package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Category;

public class CategoryDAO extends DAO<Category>{

	public CategoryDAO() {
		super(Category.class);
	}
	
	public Category findByName(String name) throws DAOException{
		String hql = "from Category c where lower(c.name) like :name";
		Query<Category> query = createQuery(hql, Category.class);
		query.setParameter("name", name.toLowerCase());
		return query.uniqueResult();
	}
	
	//TODO Remove
	@Deprecated
	public Category findByName(Integer name) throws DAOException{
		String hql = "from Category c where c.name like :name";
		Query<Category> query = createQuery(hql, Category.class);
		query.setParameter("name", name);
		return query.uniqueResult();
	}
	
	public List<Category> list() throws DAOException{
		String hql = "from Category";
		return list(hql);
	}

}
