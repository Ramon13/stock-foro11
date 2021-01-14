package action.restrict.entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Invoice;
import entity.Provider;
import service.EntryService;
import service.ItemService;
import service.ProviderService;

public class SaveEntry extends ApplicationAction{

	List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
	
	//TODO solve URL rewriting problems
	@Override
	public void processAction() throws Exception {					
		Entry entry = getFormEntry();
		List<EntryItem> entryItems = getFormEntryItems();
		
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}
		
		
		getServiceFactory().getService(EntryService.class).saveEntry(entry, entryItems);
		responseToClient(200, new Gson().toJson("success"));
	}
	
	private Entry getFormEntry() throws ValidationException, ServiceException{
		Entry entry = new Entry();
		
		try {
			entry.setDate(DateConvert.stringToLocalDate(getRequest().getParameter("date"), "dd/MM/yyyy"));
		} catch (ConvertException e) {
			formValidationList.add(
					new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE));
		}
		
		String invoiceNumber = getRequest().getParameter("invoiceNumber");
		if (StringUtils.isAllBlank(invoiceNumber) || !StringValidator.isValidLen(25, invoiceNumber)) {
			formValidationList.add(
					new FormValidateJSON("invoiceNumber", ValidationMessageUtil.INVOICE_MAX_LEN));
		}
		
		entry.setInvoice(new Invoice(invoiceNumber));	
		
		Provider ṕrovider = getServiceFactory()
				.getService(ProviderService.class)
				.validateAndFindById(getRequest().getParameter("provider"));
		entry.setProvider(ṕrovider);
		
		return entry;
	}
	
	private List<EntryItem> getFormEntryItems() throws ValidationException, ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		String[] itemsForm = getRequest().getParameterValues("item");
		String[] quantitiesForm = getRequest().getParameterValues("amount");
		String[] valuesForm = getRequest().getParameterValues("value");
		
		List<EntryItem> entryItems = new ArrayList<>();
		EntryItem entryItem = null;
		
		for (int i = 0; i < itemsForm.length; i++) {
			entryItem = new EntryItem();
			
			try {
				entryItem.setAmount(NumberConvert.stringToInteger(Locale.FRANCE, quantitiesForm[i]));
			} catch (ConvertException | NumberFormatException e) {
				formValidationList.add(
						new FormValidateJSON("amount", ValidationMessageUtil.INVALID_INTEGER_CONVERSION));
				break;
			}
			
			try {
				Double value = NumberConvert.stringToDouble(Locale.FRANCE, valuesForm[i]);
				if (value > Math.pow(10, 6) || valuesForm[i].length() > 15)
					throw new IllegalStateException();
				
				entryItem.setValue(new BigDecimal(value));
			} catch (Exception e) {
				formValidationList.add(
						new FormValidateJSON("value", ValidationMessageUtil.INVALID_DOUBLE_CONVERSION));
				break;
			}
			
			entryItem.setItem(itemSvc.validateAndFindById(itemsForm[i]));
			entryItems.add(entryItem);
		}
		
		return entryItems;
	}
}
