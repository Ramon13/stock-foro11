package service;

import java.util.ArrayList;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.Service;
import br.com.javamon.validation.DateValidator;
import dao.ItemDAO;
import dao.LocaleDAO;
import domain.ItemLocale;
import domain.ItemLocaleFilters;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.Item;
import entity.Locale;
import entity.Order;
import entity.OrderItem;
import entity.Packet;
import entity.SubCategory;

public class ItemService extends Service{

	public void save(Item item) throws ServiceException, ValidationException{
		if (!isValidName(item))
			throw new ValidationException(ValidationMessageUtil.ITEM_NAME_EXISTS);
		
		Packet packet = getServiceFactory()
				.getService(PacketService.class)
				.findById(item.getPacket().getId());
		
		Category category = getServiceFactory()
			.getService(CategoryService.class)
			.findById(item.getCategory().getId());
		
		SubCategory subCategory = getServiceFactory()
			.getService(SubCategoryService.class)
			.findById(item.getSubCategory().getId());
		
		if (packet == null || category == null || subCategory == null)
			throw new ValidationException(ValidationMessageUtil.ID_NOT_FOUND);
		
		item.setPacket(packet);
		item.setCategory(category);
		item.setSubCategory(subCategory);
		try {
			getDaoFactory().getDAO(ItemDAO.class).save(item);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	private boolean isValidName(Item item) throws ServiceException{
		try {
			return getDaoFactory().getDAO(ItemDAO.class).findItemByName(item.getName()) == null;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public List<Item> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(ItemDAO.class).listItem();
		} catch (DAOException e) {
			throw new ServiceException("An exception ocourred in an attempt to recover data", e);
		}
	}
	
	public List<ItemLocale> listAndSumLocales(ItemLocaleFilters...itemLocaleFilters) throws ServiceException{
		
		List<ItemLocale> itemLocales = new ArrayList<ItemLocale>();
		try {
			Locale lastLocale;
			if ((lastLocale = getDaoFactory().getDAO(LocaleDAO.class).getLastLocale()) == null)
				return itemLocales;
			
			itemLocales = sumLocales(lastLocale.getId().intValue(), itemLocaleFilters);
			
		}catch (DAOException ex) {
			ex.printStackTrace();
		}
		
		return itemLocales;
	}
	

	private List<ItemLocale> sumLocales(int lastLocaleId, ItemLocaleFilters...itemLocaleFilters) throws ServiceException {
		int arrsize = lastLocaleId * 2 + 1;
		int[] sum = new int[arrsize];
		
		int idTemp = 0;
		List<ItemLocale> itemLocales = new ArrayList<ItemLocale>();
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		ItemLocale itemLocale = null;
		
		for (Item item : list()) {
		
			for(OrderItem oi : item.getOrderItems()) {
				
				for (int i = 0, n = 0; i < itemLocaleFilters.length; i++, n += lastLocaleId) {
					
					if (isValidOrder(itemLocaleFilters[i], oi.getOrder()))
					{
						idTemp = oi.getOrder().getCustomer().getLocale().getId().intValue();
						sum[idTemp + n] += oi.getAmount();
					}
				}
			}
			
			itemLocale = new ItemLocale();
			itemLocale.setItem(item);
			
			for (int i = 0, n = 0; i < itemLocaleFilters.length; i++, n += lastLocaleId) {
				
				for (Locale locale : locales) {
				
					idTemp = locale.getId().intValue();
					itemLocale.getSumLocales().add(sum[idTemp + n]);
					sum[idTemp + n] = 0;
				}
			}
		
			itemLocales.add(itemLocale);
		}
		
		return itemLocales;
	}
	
	private boolean isValidOrder(ItemLocaleFilters itemLocaleFilters, Order order) {
		
		return (order != null 
			&& DateValidator.isWithinRange(itemLocaleFilters.getStartDate(), itemLocaleFilters.getEndDate(), order.getFinalDate()) 
			&& itemLocaleFilters.getStatus() == order.getStatus());
	}
}
