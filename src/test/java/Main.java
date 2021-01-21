import java.math.BigDecimal;

import org.hibernate.Session;

import br.com.javamon.util.HibernateUtil;
import entity.Entry;
import entity.EntryItem;
import entity.Item;
import report.OrderItemReport;
import report.ReportException;

public class Main {

	
	public static void main(String[] args) throws ReportException{
		OrderItemReport orderItemReport = new OrderItemReport();
		orderItemReport.sendCompleteOrderReporttoPdf(null);
	}
	
	
}
