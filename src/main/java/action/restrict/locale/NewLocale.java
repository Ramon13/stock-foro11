package action.restrict.locale;

import br.com.javamon.action.Action;

public class NewLocale extends Action {

	@Override
	public void process() throws Exception {
		foward("/restrict/new-locale-ajax.jsp");
	}

}
