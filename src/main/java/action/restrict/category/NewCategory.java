package action.restrict.category;

import br.com.javamon.action.Action;

public class NewCategory extends Action {

	@Override
	public void process() throws Exception {
		foward("/restrict/new-category-ajax.jsp");
	}

}
