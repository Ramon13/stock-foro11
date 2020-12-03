package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.UserDAO;
import domain.LoggedUser;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.PaginationFilter;
import entity.User;

public class UserService extends ApplicationService<User, UserDAO>{

	public UserService() {
		super(UserDAO.class);
	}
	
	public void saveUser(User user) throws ServiceException{
		user.setActive(true);
		user.setResetPassword(true);
		save(user);
	}
	
	public boolean isValidLogin(User user) throws ServiceException{
		try {
			return getDAO().findByNameAndPassword(user.getName(), user.getPassword()) != null;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public boolean isValidToBlock(LoggedUser loggedUser, User userToBlock) {
		return loggedUser.getUser().getPermission().getLevel() >= userToBlock.getPermission().getLevel();
	}
	
	public List<User> searchOnUser(PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(UserDAO.class).search(filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void blockUser(User user) throws ServiceException {
		user.setActive(!user.getActive());
		update(user);
	}
	
	public User findByUserName(String userName) throws ServiceException{
		try {
			return getDAO().findByUserName(userName);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public boolean isValidNewUserName(String userName) throws ServiceException {
		return findByUserName(userName) == null;
	}
	
	public boolean hasCart(User user) throws ServiceException{
		return user.getCart() != null;
	}
}
