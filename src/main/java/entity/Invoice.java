package entity;

import dao.Search;

public class Invoice extends Entity{

	@Search
	private String invoiceIdNumber;
	private Entry entry;

	public Invoice() {}
	
	public Invoice(String invoiceIdNumber) {
		this.invoiceIdNumber = invoiceIdNumber;
	}

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
