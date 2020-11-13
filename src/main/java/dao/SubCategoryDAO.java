package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Category;
import entity.SubCategory;

public class SubCategoryDAO extends ApplicationDAO<SubCategory>{

	public SubCategoryDAO() {
		super(SubCategory.class);
	}
	
	public SubCategory findByName(String name) throws DAOException{
		String hql = "from SubCategory sc where sc.name like :name";
		Query<SubCategory> query = createQuery(hql, SubCategory.class);
		query.setParameter("name", name);
		return query.uniqueResult();
	}
	
	public List<SubCategory> list() throws DAOException{
		String hql = "from SubCategory";
		return list(hql);
	}

	public List<SubCategory> listSubCategoryByCategory(Category category) throws DAOException{
		String hql = "from SubCategory sc where sc.category = :category";
		Query<SubCategory> query = createQuery(hql, SubCategory.class);
		query.setParameter("category", category);
		return query.getResultList();
	}
}
