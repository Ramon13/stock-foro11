package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import dao.PermissionDAO;
import entity.Permission;

public class PermissionService extends ApplicationService<Permission, PermissionDAO>{

	public PermissionService() {
		super(PermissionDAO.class);
	}
	
	public List<Permission> listLessOrEqualsLevel(Integer level) throws ServiceException{
		try {
			return getDAO().listLessOrEqualsLevel(level);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
