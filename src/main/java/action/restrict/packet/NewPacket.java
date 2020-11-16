package action.restrict.packet;

import br.com.javamon.action.Action;

public class NewPacket extends Action {

	@Override
	public void process() throws Exception {
		foward("/restrict/new-packet-ajax.jsp");
	}

}
