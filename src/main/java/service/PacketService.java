package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import dao.PacketDAO;
import domain.util.ExceptionMessageUtil;
import entity.Packet;

public class PacketService extends ApplicationService<Packet, PacketDAO>{

	public PacketService() {
		super(PacketDAO.class);
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
	
	public boolean isValidPacketName(String name) throws ServiceException{
		return findByName(name) == null;
	}
}
