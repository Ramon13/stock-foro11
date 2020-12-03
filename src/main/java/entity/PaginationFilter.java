package entity;

import org.apache.commons.lang3.StringUtils;

public class PaginationFilter {

	public enum orders {ASC, DESC};
	
	private String searchWord;
	private String sortProperty;
	private orders order = orders.ASC;
	private Integer firstResultPage = 1;
	private Integer maxResults = 50;
	
	public PaginationFilter(String searchWord, String sortProperty, orders order, 
			Integer firsResultPage) {
		this.searchWord = searchWord;
		this.sortProperty = sortProperty;
		this.order = order;
		this.firstResultPage = firsResultPage;
	}
	
	public PaginationFilter() {
	
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSortProperty() {
		if (StringUtils.isAllBlank(this.sortProperty))
			return "id";
		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	public orders getOrder() {
		return order;
	}

	public void setOrder(orders order) {
		this.order = order;
	}
	
	public Integer getFirstResultPage() {
		return firstResultPage;
	}
	
	public void setFirstResultPage(Integer firstResultPage) {
		this.firstResultPage = firstResultPage;
	}
	
	public Integer getMaxResults() {
		return maxResults;
	}
	
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	@Override
	public String toString() {
		return "PaginationFilter [searchWord=" + searchWord + ", sortProperty=" + sortProperty + ", order=" + order
				+ ", firstResultPage=" + firstResultPage + "]";
	}
}
