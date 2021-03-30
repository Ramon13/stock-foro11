package service;

import java.util.ArrayList;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.EntryDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Invoice;
import entity.Item;
import entity.PaginationFilter;

public class EntryService extends ApplicationService<Entry, EntryDAO>{

	public EntryService() {
		super(EntryDAO.class);
	}

	public List<Entry> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public List<Entry> listByItem(Item item, PaginationFilter filter) throws ServiceException{
		
		List<EntryItem> entryItems = getServiceFactory()
				.getService(EntryItemService.class)
				.listByItem(item, filter);
		
		return listFromEntryItems(entryItems);
	}
	
	public void saveEntry(Entry entry, List<EntryItem> entryItems) throws ServiceException, ValidationException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		validateInvoiceNumber(entry.getInvoice().getInvoiceIdNumber());
		Invoice invoice = getServiceFactory()
				.getService(InvoiceService.class)
				.save(entry.getInvoice());
		
		entry.setInvoice(invoice);
		
		try {
			getDaoFactory().getDAO(EntryDAO.class).save(entry);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e);
		}
		
		for (EntryItem entryItem : entryItems) {
			entryItem.setEntry(entry);
			entryItem.calcTotal();
			
			getServiceFactory().getService(EntryItemService.class).save(entryItem);
			itemSvc.updateItemCurrentAmount(entryItem.getItem());
		}
	}
	
	private void validateInvoiceNumber(String invoiceNumber) throws ValidationException, ServiceException{
		if (getServiceFactory()
				.getService(InvoiceService.class)
				.findByInvoiceNumber(invoiceNumber) != null)
			throw new ValidationException(ValidationMessageUtil.INVOICE_NUMBER_EXISTS);
	}
	
	public List<Entry> searchOnEntry(PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(EntryDAO.class).search(filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<Entry> listFromEntryItems(List<EntryItem> entryItems){
		List<Entry> entries = new ArrayList<Entry>(0);

		for (EntryItem ei : entryItems) {
			entries.add(ei.getEntry());
		}
		
		return entries;
	}
	
	public List<Entry> searchByItem(Item item, PaginationFilter filter) throws ServiceException{
		return listFromEntryItems(getServiceFactory()
				.getService(EntryItemService.class)
				.searchByItem(filter, item));
	}
}
