package action.restrict.packet;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Packet;
import service.PacketService;

public class SavePacket extends ApplicationAction{

	PacketService packetSvc;
	
	@Override
	public void processAction() throws Exception {
		packetSvc = getServiceFactory().getService(PacketService.class);
		Packet packet = validateFields();	
		Long packetId = packetSvc.save(packet);
		packet = packetSvc.findById(packetId);
		
		responseToClient(200, new Gson().toJson(packet));
	}

	private Packet validateFields() throws ValidationException, ServiceException{
		
		Packet packet = new Packet();
		
		packet.setName(getRequest().getParameter("pname"));
		
		if (StringValidator.isEmpty(packet.getName()))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.EMPTY_PACKET_NAME));
		
		if (!StringValidator.isValidLen(255, packet.getName()))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.PACKET_MAX_LEN));
		
		if (!packetSvc.isValidPacketName(packet.getName()))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.PACKET_NAME_EXISTS));
		
		if (!formValidationList.isEmpty()) {
			throw new ValidationException();
		}
		
		return packet;
	}
}
