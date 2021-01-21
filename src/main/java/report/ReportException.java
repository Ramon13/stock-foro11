package report;

public class ReportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReportException(Throwable throwable) {
		super(throwable);
	}
	
	public ReportException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public ReportException(String msg) {
		super(msg);
	}
}