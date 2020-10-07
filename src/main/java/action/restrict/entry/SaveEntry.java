package action.restrict.entry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.convert.StringConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Invoice;
import entity.Item;
import entity.Provider;
import service.EntryService;

public class SaveEntry extends ApplicationAction{

	List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
	
	//TODO solve URL rewriting problems
	@Override
	public void processAction() throws Exception {					
		Entry entry = validateFields();
		getServiceFactory().getService(EntryService.class).saveEntry(entry);
		responseToClient(200, new Gson().toJson("success"));
	}
		
	private Entry validateFields() throws ValidationException{
		Entry entry = new Entry();
		
		String strDate = getRequest().getParameter("date");
		String invoiceNumber = getRequest().getParameter("invoiceNumber");
		String providerId = getRequest().getParameter("provider");
		String[] items = getRequest().getParameterValues("item");
		
		//TODO: Test scale and precision hibernate validation
		String[] amountArr = getRequest().getParameterValues("amount");
		String[] valueArr = getRequest().getParameterValues("value");
		EntryItem[] entryItemArr = new EntryItem[amountArr.length];
		populateEntryItemArr(entryItemArr);
		
		LocalDate date = null;
		try {
			date = DateConvert.stringToLocalDate(strDate, "dd/MM/yyyy");
		} catch (ConvertException e) {
			formValidationList.add(
					new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE));
		}
		
		if (StringUtils.isAllBlank(invoiceNumber))
			formValidationList.add(
					new FormValidateJSON("invoiceNumber", ValidationMessageUtil.EMPTY_INVOICE));
		
		else if (!StringValidator.isValidLen(25, invoiceNumber))
			formValidationList.add(
					new FormValidateJSON("invoiceNumber", ValidationMessageUtil.INVOICE_MAX_LEN));
		
		if (!StringValidator.isValidLongParse(providerId))
			formValidationList.add(
					new FormValidateJSON("provider", ValidationMessageUtil.INVALID_PROVIDER_ID));
		
		Item item;
		for (int i = 0; i < entryItemArr.length; i++) {
			try {
				item = new Item();
				item.setId(StringConvert.stringToLong(items[i]));
			
				if (!StringValidator.isValidLongParse(items[i])) {
					throw new IllegalStateException();
				}
				
				entryItemArr[i].setItem(item);
			}catch(Exception e) {
				e.printStackTrace();
				formValidationList.add(
						new FormValidateJSON("item", ValidationMessageUtil.INVALID_ITEM_ID));
			}
		}
		
		
		for (int i = 0; i < entryItemArr.length; i++) {
			try {
				int amount = NumberConvert.stringToInteger(Locale.FRANCE, amountArr[i]);
				if (amount > Math.pow(10, 6))
					throw new IllegalStateException();
				
				entryItemArr[i].setAmount(amount);
			} catch (ConvertException | IllegalStateException e) {
				formValidationList.add(
						new FormValidateJSON("amount", ValidationMessageUtil.INVALID_INTEGER_CONVERSION));
				break;
			}
		}
		
		for (int i = 0; i < entryItemArr.length; i++) {
			try {
				Double value = NumberConvert.stringToDouble(Locale.FRANCE, valueArr[i]);
				if (value > Math.pow(10, 6) || valueArr[i].length() > 15)
					throw new IllegalStateException();
				
				entryItemArr[i].setValue(new BigDecimal(value));
			} catch (Exception e) {
				formValidationList.add(
						new FormValidateJSON("value", ValidationMessageUtil.INVALID_DOUBLE_CONVERSION));
				break;
			}
		}
		
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}

		entry.setDate(date);
		entry.setEntryItems(new HashSet<EntryItem>(Arrays.asList(entryItemArr)));
		
		Provider provider = new Provider();
		provider.setId(Long.parseLong(providerId));
		
		Invoice invoice = new Invoice();
		invoice.setInvoiceIdNumber(invoiceNumber);
		
		entry.setInvoice(invoice);
		entry.setProvider(provider);
		
		return entry;
	}

	private void populateEntryItemArr(EntryItem arr[]) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new EntryItem();
		}
	}
}
