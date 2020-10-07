package service;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.InvoiceDAO;
import domain.util.ExceptionMessageUtil;
import entity.Invoice;

public class InvoiceService extends Service{

	public Invoice save(Invoice invoice) throws ServiceException {
		try {
			Long id = (Long) getDaoFactory().getDAO(InvoiceDAO.class).save(invoice);
			return getDaoFactory().getDAO(InvoiceDAO.class).load(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e);
		}
	}
	
	public Invoice findByInvoiceNumber(String invoiceNumber) throws ServiceException {
		try {
			return getDaoFactory().getDAO(InvoiceDAO.class).findByInvoiceNumber(invoiceNumber);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
}
