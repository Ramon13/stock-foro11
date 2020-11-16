package action.restrict.stock_forecast;

import action.ApplicationAction;

public class ListItems extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		foward("/restrict/stock-forecast.jsp");
	}


}
