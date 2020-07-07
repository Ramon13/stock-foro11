package admin.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Packet;
import service.PacketService;

public class SavePacketAction extends AdminAction{

	@Override
	public void processAction() throws Exception {
		
		Packet packet = validateFields();
		try {
			packet = getServiceFactory().getService(PacketService.class).save(packet);		
			responseToClient(200, new Gson().toJson(packet));
		} catch (ValidationException e) {
			getRequest().setAttribute("formValidationList", Arrays.asList(new FormValidateJSON("pgeneral", e.getMessage())));
			throw new ValidationException();
		}
	}

	private Packet validateFields() throws ValidationException{
		Packet packet = new Packet();
		List<FormValidateJSON> formValidateJSONs = new ArrayList<FormValidateJSON>(0);
		
		packet.setName(getRequest().getParameter("pname"));
		if (StringValidator.isEmpty(packet.getName()))
			formValidateJSONs.add(new FormValidateJSON("pname", ValidationMessageUtil.EMPTY_PACKET_NAME));
		
		if (!StringValidator.isValidLen(255, packet.getName()))
			formValidateJSONs.add(new FormValidateJSON("pname", ValidationMessageUtil.PACKET_MAX_LEN));
		
		if (formValidateJSONs.size() != 0) {
			getRequest().setAttribute("formValidationList", formValidateJSONs);
			throw new ValidationException();
		}
		
		return packet;
	}
}
