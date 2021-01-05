package action.restrict.entry;

import br.com.javamon.action.Action;

public class LoadEntryPage extends Action{

	@Override
	public void process() throws Exception {
		foward("/restrict/entries-page.jsp");
	}


}
