package dao;

import org.hibernate.query.Query;

import br.com.javamon.dao.DAO;
import br.com.javamon.exception.DAOException;
import entity.Invoice;

public class InvoiceDAO extends DAO<Invoice>{

	public InvoiceDAO() {
		super(Invoice.class);
	}
	
	public Invoice findByInvoiceNumber(String invoiceNumber) throws DAOException{
		String hql = "from Invoice i where i.invoiceIdNumber = :invoiceNumber";
		Query<Invoice> query = createQuery(hql, Invoice.class);
		query.setParameter("invoiceNumber", invoiceNumber);
		return query.uniqueResult();
	}
}
