package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.Service;
import dao.PacketDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Packet;

public class PacketService extends Service{

	public Packet save(Packet packet) throws ServiceException, ValidationException{
		if (!isValidPacketName(packet.getName()))
			throw new ValidationException(ValidationMessageUtil.PACKET_NAME_EXISTS);
		
		try {
			Long newId = (Long) getDaoFactory().getDAO(PacketDAO.class).save(packet);
			return getDaoFactory().getDAO(PacketDAO.class).load(newId);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e.getCause());
		}
	}
	
	public Packet findById(Long packetId) throws ServiceException{
		try {
			return getDaoFactory().getDAO(PacketDAO.class).load(packetId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public Packet findByName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(PacketDAO.class).findByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public List<Packet> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(PacketDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	private boolean isValidPacketName(String name) throws ServiceException{
		return findByName(name) == null;
	}
}
