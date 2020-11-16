package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.UserDAO;
import domain.LoggedUser;
import domain.PermissionRoles;
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
	
	public User validateCredentials(String username, String password) throws ValidationException, ServiceException {
		try {
			User user = getDAO().findByNameAndPassword(username, password);
			if (user == null)
				throw new ValidationException(ValidationMessageUtil.ILLEGAL_CREDENTIALS);
			else if (user.getActive() == false)
				throw new ValidationException(ValidationMessageUtil.BLOCKED_USER);
			
			return user;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}

	public boolean isAdminLoggedUser(LoggedUser loggedUser) {
		return loggedUser.getUser().getPermission().getLevel() == PermissionRoles.ADMIN.getValue(); 
	}
	
	public boolean isSuperAdminLoggedUser(LoggedUser loggedUser) {
		return loggedUser.getUser().getPermission().getLevel() == PermissionRoles.SUPER_ADMIN.getValue(); 
	}
	
	public boolean isUserLoggedUser(LoggedUser loggedUser) {
		return loggedUser.getUser().getPermission().getLevel() == PermissionRoles.USER.getValue(); 
	}
	
	public boolean isValidToBlock(LoggedUser loggedUser, User userToBlock) {
		return loggedUser.getUser().getPermission().getLevel() >= userToBlock.getPermission().getLevel();
	}
	
	public boolean isAnyAdmin(LoggedUser loggedUser) {
		return isSuperAdminLoggedUser(loggedUser) || isAdminLoggedUser(loggedUser);
	}
	
	public List<User> searchOnUser(PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(UserDAO.class).search(filter.getSearchWord());
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
}
