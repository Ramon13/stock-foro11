package admin.action;

public class FormValidateJSON {

	private String divName;
	
	private String message;
	
	public FormValidateJSON(String divName, String message) {
		this.divName = divName;
		this.message = message;
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public String getMessage() {
		return message;
	}

	public void setErrorMessage(String message) {
		this.message = message;
	}
}
