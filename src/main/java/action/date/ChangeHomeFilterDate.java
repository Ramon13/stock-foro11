package action.date;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.AdminHomeDateType;
import action.ApplicationAction;

public class ChangeHomeFilterDate extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		LocalDate newDate;
		AdminHomeDateType dateType;
		String paramDate = null;
		
		if (!StringUtils.isAllBlank((paramDate = getRequest().getParameter("primaryDate"))) ) {
			dateType = AdminHomeDateType.PRIMARY_DATE;
		}
		else {
			paramDate = getRequest().getParameter("secondDate");
			dateType = AdminHomeDateType.SECOND_DATE;
		}
		
		newDate = validateDate(paramDate);
		ActionUtil.putAdminHomeDateOnSession(dateType, newDate, getRequest().getSession());
	}
}
