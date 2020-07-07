package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Locale;

public class LocaleDAO extends DAO<Locale>{

	public LocaleDAO() {
		super(Locale.class);
	}

	public List<Locale> listLocales()throws DAOException{
		String hql = "from Locale l order by l.id";
		return list(hql);
	}
	
	public Locale getLastLocale() throws DAOException{
		String hql = "from Locale l order by l.id desc";
		Query<Locale> query = createQuery(hql, Locale.class);
		query.setMaxResults(1);
		return query.uniqueResult();
	}
}