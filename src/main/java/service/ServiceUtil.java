package service;

import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import br.com.javamon.service.ServiceFactory;
import entity.Packet;

public class ServiceUtil extends Service{
	
	private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
	
	public static Packet findPacketById(Long id) throws ServiceException{
		return serviceFactory.getService(PacketService.class).findById(id);
	}
}
