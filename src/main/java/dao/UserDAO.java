package dao;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.User;

public class UserDAO extends ApplicationDAO<User>{

	public UserDAO() {
		super(User.class);
	}

	public User findByNameAndPassword(String username, String password) throws DAOException {
		String hql = "from User u where u.name like :username and u.password like :password";
		Query<User> query = createQuery(hql, User.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		
		return query.uniqueResult();
	}
	
	public User findByUserName(String username) throws DAOException{
		String hql = "from User u where u.name like :userName";
		Query<User> query = createQuery(hql, User.class);
		query.setParameter("userName", username);
		return query.uniqueResult();
	}
}
