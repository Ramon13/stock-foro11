package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.LocaleDAO;
import entity.Locale;

public class LocaleService extends Service{

	public List<Locale> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(LocaleDAO.class).listLocales();
		}catch(DAOException ex) {
			throw new ServiceException("Error in attempt of retrieve Locale list", ex);
		}
	}
}
