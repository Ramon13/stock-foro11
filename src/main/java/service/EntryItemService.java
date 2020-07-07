package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.EntryItemDAO;
import domain.util.ExceptionMessageUtil;
import entity.Entry;
import entity.EntryItem;

public class EntryItemService extends Service{

	public List<EntryItem> listByEntry(Entry entry) throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryItemDAO.class).listByEntry(entry);
		}catch(DAOException ex) {
			ex.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST, ex);
		}
	}
}
