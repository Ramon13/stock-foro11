package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.EntryDAO;
import domain.util.ExceptionMessageUtil;
import entity.Entry;

public class EntryService extends Service{

	public List<Entry> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
}
