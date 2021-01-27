package report;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

interface Report{

	public void exportReportToPdf(Map<String, Object> parameters, Collection<?> datasource, OutputStream out);
	public void exportReportToXls(Map<String, Object> parameters, Collection<?> datasource, OutputStream out);

}
