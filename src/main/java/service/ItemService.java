package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import action.AdminHomeDateType;
import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.DateValidator;
import dao.ItemDAO;
import domain.DateUtil;
import domain.ItemLocale;
import domain.ItemLocaleFilter;
import domain.ItemLocales;
import domain.OrderStatus;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.EntryItem;
import entity.Image;
import entity.Item;
import entity.Locale;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;
import entity.SubCategory;

public class ItemService extends ApplicationService<Item, ItemDAO>{

	public ItemService() {
		super(ItemDAO.class);
	}

	public Item findById(Long id) throws ServiceException{
		try {
			 Item item = getDaoFactory()
				.getDAO(ItemDAO.class)
				.load(id);
			 
			 if (item == null)
				 throw new ServiceException(ValidationMessageUtil.INVALID_ITEM_ID);
			 
			 return item;
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public void edit(Item item, Path imagePath) throws ServiceException {
		try {
			Path itemImagePath = imagePath.resolve(item.getId().toString());
			
			getServiceFactory().getService(ImageService.class)
				.saveImages(item, Files.createDirectories(itemImagePath));
			
			getDaoFactory().getDAO(ItemDAO.class).merge(item);
		} catch (DAOException | IOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void save(Item item, Path imagePath) throws ServiceException, ValidationException{
		try {
		
			getDaoFactory().getDAO(ItemDAO.class).save(item);
			
			Path itemImagePath = imagePath.resolve(item.getId().toString());
			Long mainImageId = getServiceFactory().getService(ImageService.class)
					.saveImages(item, Files.createDirectories(itemImagePath));
			
			item.setMainImage(mainImageId);
		} catch (DAOException | IOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}

	public Item findByName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(ItemDAO.class).findItemByName(name);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public boolean isValidNewItemName(String name) throws ServiceException{
		return findByName(name) == null;
	}
	
	public boolean isValidNewItemName(String name, Item item) throws ServiceException{
		Item foundItem = findByName(name);
		
		if (foundItem != null) {
			if (item.getId() == null)
				return false;
			
			else if (foundItem.getId() != item.getId())
				return false;
		}
		
		return true;
	}
	
	public List<Item> listBySubCategory(PaginationFilter filter, SubCategory...subCategories) throws DAOException{
		return getDAO().listBySubCategory(filter, subCategories);
	}
	
	public List<Item> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(ItemDAO.class).list();
		} catch (DAOException e) {
			throw new ServiceException("An exception ocourred in an attempt to recover data", e);
		}
	}
	
	public void sumLocales(List<Item> items, ItemLocales ... itemLocalesList) throws ServiceException {
		Locale lastLocale = getServiceFactory().getService(LocaleService.class).getLastLocaleById();
		int lastLocaleId = lastLocale.getId().intValue(); 
		
		int idTemp = 0;
		OrderService orderService = getServiceFactory().getService(OrderService.class);
		for (int i = 0; i < items.size(); i++) {
			
			for (ItemLocales itemLocales : itemLocalesList) {
				ItemLocale il = new ItemLocale();
				for (int j = 0; j <= lastLocaleId; j++) {
					il.getSumLocales().add(0);
				}
				il.setItem(items.get(i));
				itemLocales.getItemLocales().add(il);
			}
			
			
			for(OrderItem oi : items.get(i).getOrderItems()) {
				
				for (ItemLocales itemLocales : itemLocalesList) {
					
					if (orderService.isValidToCheckOut(oi.getOrder()) &&
							isBetweenDateRange(itemLocales.getFilter(), oi.getOrder().getFinalDate()))
					{
						idTemp = oi.getOrder().getCustomer().getLocale().getId().intValue();
						int sum = itemLocales
								.getItemLocales()
								.get(i)
								.getSumLocales()
								.get(idTemp);
						
						sum += oi.getAmount();
						itemLocales.getItemLocales().get(i).getSumLocales().set(idTemp, sum);
						
					}
					
					if (orderService.isValidToCheckOut(oi.getOrder()) && 
							itemLocales.getFilter().isCalcItemAmountByDate()) {
						
						addOrderItemAmount(itemLocales.getItemLocales().get(i), oi,
								AdminHomeDateType.PRIMARY_DATE, itemLocales.getFilter().getStartDate());
						
						addOrderItemAmount(itemLocales.getItemLocales().get(i), oi,
								AdminHomeDateType.SECOND_DATE, itemLocales.getFilter().getEndDate());
					}
				}
			}
			
			
			for (EntryItem ei : items.get(i).getEntryItems()) {
				for (ItemLocales itemLocales : itemLocalesList) {
					if (itemLocales.getFilter().isCalcItemAmountByDate()) {
						addEntryAmount(itemLocales.getItemLocales().get(i),
								ei, AdminHomeDateType.PRIMARY_DATE, itemLocales.getFilter().getStartDate());
						
						addEntryAmount(itemLocales.getItemLocales().get(i),
								ei, AdminHomeDateType.SECOND_DATE, itemLocales.getFilter().getEndDate());
					}
				}
			}
		}
	}
	
	private void addEntryAmount(ItemLocale itemLocale, EntryItem entryItem,
			AdminHomeDateType adminHomeDateType, LocalDate date) {
		if (entryItem.getEntry().getDate().isBefore(date)) {
			if (adminHomeDateType == AdminHomeDateType.PRIMARY_DATE) {
				int amount = itemLocale.getStartDateAmount();
				itemLocale.setStartDateAmount(amount + entryItem.getAmount());
			
			}else {
				int amount = itemLocale.getEndDateAmount();
				itemLocale.setEndDateAmount(amount + entryItem.getAmount());
			}
			
		}
	}
	
	private void addOrderItemAmount(ItemLocale itemLocale, OrderItem orderItem,
			AdminHomeDateType adminHomeDateType, LocalDate date) {
		
		if (orderItem.getOrder().getFinalDate().isBefore(date)) {
			
			if (adminHomeDateType == AdminHomeDateType.PRIMARY_DATE) {
				int amount = itemLocale.getStartDateAmount();
				itemLocale.setStartDateAmount(amount - orderItem.getAmount());
			
			}else {
				int amount = itemLocale.getEndDateAmount();
				itemLocale.setEndDateAmount(amount - orderItem.getAmount());
			}
			
		}
	}
	
	private boolean isBetweenDateRange(ItemLocaleFilter filter, LocalDate orderDate) {
		return DateValidator.isWithinRange(filter.getStartDate(), filter.getEndDate(), orderDate);
	}
	
	public void changeMainImage(Long itemId, Long imageId) throws ServiceException, ValidationException{
		Image image = getServiceFactory().getService(ImageService.class).findById(imageId);
		if (image == null)
			throw new ValidationException(ValidationMessageUtil.INVALID_IMAGE_ID);
		
		Item item = findById(itemId);
		item.setMainImage(image.getId());
	}
	
	public List<Item> getItemsByOrder(Order order) throws ServiceException{
		List<Item> items = new ArrayList<Item>();
		for (OrderItem orderItem : order.getOrderItems())
			items.add(orderItem.getItem());
		
		return items;
	}
	
	
	public void setItemAmountByLocale(Collection<OrderItem> orderItems, Locale locale) throws ServiceException{
		Item item;
		
		for (OrderItem oi : orderItems) {
			item = oi.getItem();
			
			item.setCurrentYearAmount(
					getItemAmountByLocaleAndYear(locale, LocalDate.now().getYear(), item));
			
			item.setSumByMonth(getPreviousMonthsAmount(item, 12, locale));
			
			item.setAmount(getItemCurrentAmount(item));
		}
	}
	
	public long getItemAmountByLocaleAndYear(Locale locale, int year, Item item) throws ServiceException{
		long orderSum = 0;
		Order order = null;
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		for (OrderItem orderItem : item.getOrderItems()) {
			order = orderItem.getOrder();
			
			if (orderSvc.isValidOrder(order) &&
					OrderStatus.isFinalizedOrder(order) &&
					order.getCustomer().getLocale().getId() == locale.getId() &&
					order.getFinalDate().isAfter(DateUtil.firstDayOfYear(year))) {
				
				orderSum += orderItem.getAmount();	
			}
		}
				
		return orderSum;
	}
	
	public List<Integer> getPreviousMonthsAmount(Item item, int numOfMonths, Locale locale) throws ServiceException {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		Order order;
		
		int[] sumByMonth = new int[numOfMonths + 1];
				
		LocalDate oneYearBefore = LocalDate.now().withDayOfMonth(1).minusMonths(numOfMonths);
		
		for (OrderItem orderItem : item.getOrderItems()) {
			order = orderItem.getOrder();
			
			if (orderSvc.isValidOrder(order) &&
					OrderStatus.isFinalizedOrder(order) &&
					order.getCustomer().getLocale().getId() == locale.getId() &&
					order.getFinalDate().isAfter(oneYearBefore)) {
				
					int monthValue = order.getFinalDate().getMonthValue();
					sumByMonth[monthValue] += orderItem.getAmount();
				
			}
		}
		
		List<Integer> sum = new ArrayList<Integer>();
		LocalDate today = LocalDate.now();
		for (int i = 1; i <= numOfMonths; i++) {
			sum.add(sumByMonth[today.minusMonths(i).getMonthValue()]);
		}
		
		return sum;
	}
	
	public BigDecimal getItemCurrentAmount(Item item) throws ServiceException {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		int entrySum = 0;
		int orderSum = 0;
		Order order;
	
		for (EntryItem entryItem : item.getEntryItems()) {
			entrySum += entryItem.getAmount();
		}
		
		for (OrderItem orderItem : item.getOrderItems()) {
			order = orderItem.getOrder();
			
			if (orderSvc.isValidOrder(order) && OrderStatus.isFinalizedOrReleased(order)) {
				orderSum += orderItem.getAmount();
			}
				
		}
		
		return new BigDecimal(entrySum - orderSum);
	}
	
	
	/**
	 *  
	 * @param sumByMonth Array with the sum of every month.
	 * Each index represents the sum of the respective month
	 * 
	 * @return A sorted list on decreasing order starting on the current month.
	 * Example: current month = nov -> sorted: oct, sept, jul...
	 */
	private List<Integer> sortSums(int[] sumByMonth){
		List<Integer> sorted = new ArrayList<Integer>();
		LocalDate date = LocalDate.now().withDayOfMonth(1);
		
		for (int i = 1; i < sumByMonth.length; i++) {
			date = date.minusMonths(1);
			sorted.add(sumByMonth[date.getMonthValue()]);
		}
		
		return sorted;
	}
	
	public List<Item> searchOnItems(PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(ItemDAO.class).search(filter);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
}



