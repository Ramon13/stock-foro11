package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import dao.LocaleDAO;
import entity.Locale;

public class LocaleService extends ApplicationService<Locale, LocaleDAO>{

	public LocaleService() {
		super(LocaleDAO.class);
	}

	public List<Locale> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(LocaleDAO.class).listLocales();
		}catch(DAOException ex) {
			throw new ServiceException("Error in attempt of retrieve Locale list", ex);
		}
	}
	
	public Locale getLastLocaleById() throws ServiceException{
		try {
			return getDaoFactory().getDAO(LocaleDAO.class).getLastLocale();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public Locale findByName(String name) throws ServiceException{
		try {
			return getDAO().findByName(name);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public boolean isValidNewLocaleName(String newName) throws ServiceException{
		return findByName(newName) == null;
	}
}
