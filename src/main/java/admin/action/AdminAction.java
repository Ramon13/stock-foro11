package admin.action;

import com.google.gson.Gson;

import br.com.javamon.action.Action;
import br.com.javamon.exception.ValidationException;

public abstract class AdminAction extends Action{
	
	public abstract void processAction() throws Exception;
	 
	@Override
	public void process() throws Exception{
		try {
			processAction();
		}catch(ValidationException ex) {
			ex.printStackTrace();
			responseToClient(230, new Gson().toJson(getRequest().getAttribute("formValidationList")));
		}
	}
}
