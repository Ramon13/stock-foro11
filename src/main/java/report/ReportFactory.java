package report;

import java.nio.file.Path;

import br.com.javamon.exception.ServiceException;

public class ReportFactory {

	private static ReportFactory instance;
	
	private ReportFactory() {}
	
	public static ReportFactory getInstance(){
		if(instance == null)
			instance = new ReportFactory();
		return instance;
	}
	
	public Report getReport(Path reportsPath, String action) throws ServiceException{
		return new JasperReport(reportsPath, action);
	}
}
