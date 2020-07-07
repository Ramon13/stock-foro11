package entity;

public class Invoice extends Entity{

	private String invoiceIdNumber;
	private Entry entry;

	public String getInvoiceIdNumber() {
		return invoiceIdNumber;
	}

	public void setInvoiceIdNumber(String invoiceIdNumber) {
		this.invoiceIdNumber = invoiceIdNumber;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
}
