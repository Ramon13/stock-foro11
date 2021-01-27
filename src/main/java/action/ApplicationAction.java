package action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.com.javamon.action.Action;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ValidationException;
import domain.DateUtil;
import domain.util.ValidationMessageUtil;
import entity.PaginationFilter;
import entity.PaginationFilter.orders;

public abstract class ApplicationAction extends Action{
	
	protected List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
	protected PaginationFilter paginationFilter;
	
	public abstract void processAction() throws Exception;
	 
	@Override
	public void process() throws Exception{
		try {
			setFilters();
			processAction();
		}catch(ValidationException ex) {
			ex.printStackTrace();
			
			if (formValidationList.isEmpty()) 
				formValidationList = Arrays.asList(new FormValidateJSON("general", ex.getMessage()));
			
			responseToClient(230, new Gson().toJson(formValidationList));
		}
	}
	
	protected boolean isSearchAction() {
		return StringUtils.isAllBlank(paginationFilter.getSearchWord()) == false;
	}
	

	protected LocalDate validateDate(String strDate) throws ValidationException{
		LocalDate date = null;
		try {
			date = DateConvert.stringToLocalDate(strDate, "yyyy-MM-dd");
			if (date.isAfter(DateUtil.today()))
				formValidationList.add(
						new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE_FUTURE));
		} catch (ConvertException e) {
			formValidationList.add(
					new FormValidateJSON("date", ValidationMessageUtil.INVALID_DATE));
		}
	
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}
		
		return date;
	}
	
	private void setFilters() {
		paginationFilter = new PaginationFilter(
				getRequest().getParameter("search"),
				getRequest().getParameter("sortBy"),
				getOrder(),
				getFirstResultPage(),
				getRequest().getParameter("appSortBy"));
	}
	
	private orders getOrder() {
		String orderParam = getRequest().getParameter("order");
		
		if (!StringUtils.isAllBlank(orderParam) && orderParam.equalsIgnoreCase(orders.DESC.toString()))
			return orders.DESC;
		
		return orders.ASC;
	}
	
	private Integer getFirstResultPage() {
		int firstResultPage = 0;
		if (!StringUtils.isAllBlank(getRequest().getParameter("firstResultPage")))
			 firstResultPage = Integer.parseInt(getRequest().getParameter("firstResultPage"));
		
		return firstResultPage;
	}
	
	protected boolean isListAll() {
		return StringUtils.isAllBlank(getRequest().getParameter("listAll")) == false;
	}
}
