package action.restrict.order;

import action.ActionUtil;
import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.LoggedUser;
import domain.util.ValidationMessageUtil;
import entity.Order;
import service.OrderService;

public class FinishOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		Order order = validateFields(orderSvc);
		
		if (orderSvc.isValidForFinish(order, loggedUser)) {
			orderSvc.finishOrder(order);
		}
	}

	private Order validateFields(OrderService orderSvc) throws ValidationException, ServiceException{
		String orderIdParam = getRequest().getParameter("order");
		String receivedPerson = getRequest().getParameter("receivedPerson");
		
		if (StringValidator.isEmpty(receivedPerson))
			formValidationList.add(
					new FormValidateJSON("receivedPerson", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		else if (!StringValidator.isValidLen(255, receivedPerson))
			formValidationList.add(
					new FormValidateJSON("receivedPerson", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		
		
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}
		
		Order order = orderSvc.validateAndFindById(orderIdParam);
		order.setReceivedPersonName(receivedPerson);
		
		return order;
	}
}
