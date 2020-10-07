package entity;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

import dao.Search;

public class Item extends Entity{
	
	@Search
	private String name;
	
	private BigDecimal amount;

	@Search(getMarckedFields = true)
	private Packet packet;
	
	@Search(getMarckedFields = true)
	private Category category;
	
	@Search(getMarckedFields = true)
	private SubCategory subCategory;
	
	private Long mainImage;
	
	private Blob description;
	
	private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);
	
	private Set<EntryItem> entryItems = new HashSet<EntryItem>(0);
	
	private Set<Image> images = new HashSet<Image>(0);
	
	private transient List<Integer> sumByMonth = new ArrayList<Integer>();
	
	private transient List<FileItem> multipartFiles;
	
	private transient int currentYearAmount;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Long getMainImage() {
		return mainImage;
	}

	public void setMainImage(Long mainImage) {
		this.mainImage = mainImage;
	}

	public Blob getDescription() {
		return description;
	}

	public void setDescription(Blob description) {
		this.description = description;
	}

	public List<FileItem> getMultipartFiles() {
		return multipartFiles;
	}

	public void setMultipartFiles(List<FileItem> multipartFiles) {
		this.multipartFiles = multipartFiles;
	}

	public Set<EntryItem> getEntryItems() {
		return entryItems;
	}

	public void setEntryItems(Set<EntryItem> entryItems) {
		this.entryItems = entryItems;
	}

	public List<Integer> getSumByMonth() {
		return sumByMonth;
	}

	public void setSumByMonth(List<Integer> sumByMonth) {
		this.sumByMonth = sumByMonth;
	}

	public int getCurrentYearAmount() {
		return currentYearAmount;
	}

	public void setCurrentYearAmount(int currentYearAmount) {
		this.currentYearAmount = currentYearAmount;
	}
}
