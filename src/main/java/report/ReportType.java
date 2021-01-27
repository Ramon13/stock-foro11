package report;

public enum ReportType {

	PDF("pdf"),
	XLS("xls");
	
	private String value;
	
	ReportType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static ReportType of(String strType) {
		for (ReportType type : values()) {
			if (strType.equals(type.getValue()))
				return type;
		}
		
		return null;
	}
}
