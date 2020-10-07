package action;

import java.time.LocalDate;
import domain.DateUtil;

public enum AdminHomeDateType{

	PRIMARY_DATE (DateUtil.firstDayOfCurrentYear(), "primaryDate"), 
	SECOND_DATE (DateUtil.today(), "secondDate");
	
	private LocalDate defaultDate;
	private String paramName;
	
	AdminHomeDateType(LocalDate defaultDate, String paramName){
		this.defaultDate = defaultDate;
		this.paramName = paramName;
	}
	
	public LocalDate getDefaultDate() {
		return defaultDate;
	}
	
	public String getParamName() {
		return paramName;
	}
}
