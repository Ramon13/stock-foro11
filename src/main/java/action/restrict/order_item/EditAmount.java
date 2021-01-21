package action.restrict.order_item;

import java.util.ArrayList;
import java.util.List;

import action.ActionUtil;
import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.LoggedUser;
import domain.PermissionRoles;
import domain.util.ValidationMessageUtil;
import entity.OrderItem;
import service.OrderItemService;

public class EditAmount extends ApplicationAction{

	@Override
	public void processAction() throws Exception {	
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		
		int newAmount = validateField();
		OrderItem orderItem = orderItemSvc.validateAndFindById(getRequest().getParameter("orderItem"));
		
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		
		if (orderItemSvc.isValidForChangeAmount(orderItem, newAmount) &&
				PermissionRoles.isSuperAdmin(loggedUser.getUser().getPermission())) {
			
			orderItemSvc.editAmount(orderItem, newAmount);
		}
	}
	
	private int validateField() throws ValidationException{
		String strAmount = getRequest().getParameter("amount");
		Integer amount = null;
		
		if (!StringValidator.isValidIntegerParse(strAmount)) {
			formValidationList.add(
					new FormValidateJSON("amount", ValidationMessageUtil.INVALID_INTEGER_CONVERSION));
		
		}else {
			amount = NumberConvert.stringToInteger(strAmount);
			if (amount > Math.pow(10, 6))
				formValidationList.add(
						new FormValidateJSON("amount", ValidationMessageUtil.INVALID_INTEGER_CONVERSION));
		}
			
		if (!formValidationList.isEmpty()) {
			throw new ValidationException();
		}
		
		return amount;
	}

}
