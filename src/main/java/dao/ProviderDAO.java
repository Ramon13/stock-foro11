package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Provider;

public class ProviderDAO extends ApplicationDAO<Provider>{

	public ProviderDAO() {
		super(Provider.class);
	}

	public List<Provider> list() throws DAOException{
		String hql = "from Provider p order by p.id desc";
		return list(hql);
	}
	
	public Provider findByName(String name) throws DAOException{
		Query<Provider> query = createQuery("from Provider p where p.name like :name", Provider.class);
		query.setParameter("name", name);
		return query.uniqueResult();
	}
	
	public Provider findByCNPJ(String cnpj) throws DAOException{
		Query<Provider> query = createQuery("from Provider p where p.cnpj like :cnpj", Provider.class);
		query.setParameter("cnpj", cnpj);
		return query.uniqueResult();
	}
}
