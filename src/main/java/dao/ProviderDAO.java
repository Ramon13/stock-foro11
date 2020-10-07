package dao;

import java.util.List;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Provider;

public class ProviderDAO extends DAO<Provider>{

	public ProviderDAO() {
		super(Provider.class);
	}

	public List<Provider> list() throws DAOException{
		String hql = "from Provider";
		return list(hql);
	}
}
