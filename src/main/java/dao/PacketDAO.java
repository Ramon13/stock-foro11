package dao;

import java.util.List;

import org.hibernate.query.Query;

import br.com.javamon.exception.DAOException;
import entity.Packet;

public class PacketDAO extends ApplicationDAO<Packet>{

	public PacketDAO() {
		super(Packet.class);
	}
	
	public List<Packet> list() throws DAOException{
		String hql = "from Packet"; 
		return list(hql);
	}
	
	public Packet findByName(String name) throws DAOException{
		String hql = "from Packet p where p.name like :name";
		Query<Packet> query = createQuery(hql, Packet.class);
		query.setParameter("name", name);
		
		return query.uniqueResult();
	}
}
