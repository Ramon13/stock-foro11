package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Permission;

public class PermissionDAO extends ApplicationDAO<Permission>{

	public PermissionDAO() {
		super(Permission.class);
	}

	public List<Permission> listLessOrEqualsLevel(Integer level) throws DAOException{
		String hql = "from Permission p where p.level <= :level";
		Query<Permission> query = createQuery(hql, Permission.class);
		query.setParameter("level", level);
		
		return query.list();
	}
}
