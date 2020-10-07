package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import dao.Search;

public class Entry extends Entity{

	@Search
	private LocalDate date;
	
	@Search (getMarckedFields = true)
	private Provider provider;
	
	@Search (getMarckedFields = true)
	private Invoice invoice;
	
	private Set<EntryItem> entryItems = new HashSet<EntryItem>();
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Set<EntryItem> getEntryItems() {
		return entryItems;
	}

	public void setEntryItems(Set<EntryItem> entryItems) {
		this.entryItems = entryItems;
	}
}
