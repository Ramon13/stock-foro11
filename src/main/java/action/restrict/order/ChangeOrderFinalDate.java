package action.restrict.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ValidationException;
import domain.DateUtil;
import domain.util.ValidationMessageUtil;
import entity.Order;
import service.OrderService;

public class ChangeOrderFinalDate extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		LocalDate newDate = validateField();
		Order order = orderSvc.validateAndFindById(getRequest().getParameter("order"));
		
		if (orderSvc.isValidForRelease(order)) {
			orderSvc.editFinalDate(order, newDate);
		}
	}
	
	private LocalDate validateField() throws ValidationException{
		
		List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
		String strDate = getRequest().getParameter("date");
		
		LocalDate date = null;
		try {
			date = DateConvert.stringToLocalDate(strDate, "dd/MM/yyyy");
			
			if (date.isAfter(DateUtil.today()))
				formValidationList.add(
						new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE_FUTURE));
		} catch (ConvertException e) {
			formValidationList.add(
					new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE));
		}
		
		
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}
		
		return date;
	}
}
