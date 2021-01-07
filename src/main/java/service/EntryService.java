package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import entity.Provider;

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
	
	public void saveEntry(Entry entry) throws ServiceException, ValidationException{
		
		Set<EntryItem> entryItems = entry.getEntryItems();
		entry.setEntryItems(new HashSet<EntryItem>(0));
		
		Provider provider = getServiceFactory()
			.getService(ProviderService.class)
			.findById(entry.getProvider().getId());
		
		if (provider == null)
			throw new ValidationException(ValidationMessageUtil.ID_NOT_FOUND);
		
		validateInvoiceNumber(entry.getInvoice().getInvoiceIdNumber());
		Invoice invoice = getServiceFactory()
				.getService(InvoiceService.class)
				.save(entry.getInvoice());
		
		entry.setProvider(provider);
		entry.setInvoice(invoice);
		
		try {
			Long id = (Long) getDaoFactory().getDAO(EntryDAO.class).save(entry);
			entry = getDaoFactory().getDAO(EntryDAO.class).load(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e);
		}
		
		Item item;
		for (EntryItem entryItem : entryItems) {
			item = getServiceFactory()
					.getService(ItemService.class)
					.findById(entryItem.getItem().getId());
			
			if (item == null)
				throw new ValidationException(ValidationMessageUtil.ID_NOT_FOUND);
			
			entryItem.setItem(item);
			entryItem.setEntry(entry);
			entryItem.calcTotal();
			getServiceFactory().getService(EntryItemService.class).save(entryItem);
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
