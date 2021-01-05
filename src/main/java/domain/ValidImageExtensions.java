package domain;

public enum ValidImageExtensions {

	JPG (".jpg"),
	JPEG (".jpeg"),
	WEBP (".webp"),
	PNG (".png");
	
	private String value;
	
	ValidImageExtensions(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
