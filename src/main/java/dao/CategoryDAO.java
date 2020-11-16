package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Category;

public class CategoryDAO extends ApplicationDAO<Category>{

	public CategoryDAO() {
		super(Category.class);
	}
	
	public Category findByName(String name) throws DAOException{
		String hql = "from Category c where c.name = :name";
		Query<Category> query = createQuery(hql, Category.class);
		query.setParameter("name", Integer.parseInt(name));
		return query.uniqueResult();
	}
	
	public List<Category> list() throws DAOException{
		String hql = "from Category";
		return list(hql);
	}

}
