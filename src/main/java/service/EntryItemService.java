package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.EntryItemDAO;
import domain.util.ExceptionMessageUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Item;
import entity.PaginationFilter;

public class EntryItemService extends Service{

	public List<EntryItem> listByEntry(Entry entry) throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryItemDAO.class).listByEntry(entry);
		}catch(DAOException ex) {
			ex.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST, ex);
		}
	}
	
	public List<EntryItem> listByItem(Item item, PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryItemDAO.class).listByItem(item, filter);
		}catch(DAOException ex) {
			ex.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST, ex);
		}
	}
	
	public EntryItem findById(Long id) throws ServiceException {
		try {
			return getDaoFactory().getDAO(EntryItemDAO.class).load(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e);
		}
	}
	
	public EntryItem save(EntryItem entryItem) throws ServiceException{
		try {
			Long id = (Long) getDaoFactory().getDAO(EntryItemDAO.class).save(entryItem);
			return findById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public List<EntryItem> searchByItem(PaginationFilter filter, Item item) throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryItemDAO.class).searchByItem(item, filter);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
