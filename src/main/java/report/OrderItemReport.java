package report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.OrderStatus;
import entity.Order;

public class OrderItemReport {
	
	public void sendSimpleOrderReporttoPdf(Order order, OutputStream out, Path reportsPath) throws ReportException {
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "order_order_item_report");
			Map<String, Object> parameters = new HashMap<>();
			
			parameters.put("reportTitle", "Pedido Nº: " + order.getId().toString());
			parameters.put("status", getPrettyPrintingOrderStatus(order));
			parameters.put("orderId", order.getId().toString());
			parameters.put("requestDate", getPrettyPrintingOrderDate(order));
			
			report.exportReportToPdf(parameters, order.getOrderItems(), out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
	
	private String getPrettyPrintingOrderStatus(Order order) {
		if (OrderStatus.isFinalizedOrder(order)) {
			return new String("Pedido Finalizado");
		
		}else if (OrderStatus.isReleasedOrder(order)) {
			return new String("Pedido Liberado");
		}
		
		return new String("Pedido Pendente"); 
	}
	
	private String getPrettyPrintingOrderDate(Order order) throws ConvertException {
		LocalDate date;
		if (OrderStatus.isFinalizedOrder(order)) {
			date = order.getFinalDate();
		
		}else if (OrderStatus.isReleasedOrder(order)) {
			date = order.getReleaseDate();
		
		}else {
			date = order.getRequestDate();
		}
		
		return DateConvert.parseLocalDateToString(date, "dd/MM/yyyy");
	}
}
