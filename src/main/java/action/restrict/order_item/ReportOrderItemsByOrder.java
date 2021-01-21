package action.restrict.order_item;

import java.nio.file.Paths;

import action.ActionUtil;
import action.ApplicationAction;
import entity.Order;
import report.OrderItemReport;
import service.OrderService;

public class ReportOrderItemsByOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		Order order = getServiceFactory().getService(OrderService.class).validateAndFindById(getRequest().getParameter("order"));
		
		getResponse().setContentType("application/pdf");

        getResponse().setHeader("Content-disposition", String.format("inline; filename=relatorio-pedido-%s.pdf", order.getId().toString()));
		
		new OrderItemReport().sendSimpleOrderReporttoPdf(order, getResponse().getOutputStream(), Paths.get(ActionUtil.getReportsPath(getRequest())));
	}

}
