package service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import domain.ItemStockForecast;
import domain.OrderStatus;
import entity.Entry;
import entity.EntryItem;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;
import entity.PaginationFilter.orders;

public class ItemStockForecastService extends Service {

	public void setOrderAverages(List<ItemStockForecast> itemStockForecasts, LocalDate baseDate, LocalDate forecastDate) throws ServiceException{
		int arrLen = getServiceFactory().getService(ItemService.class).getLastItemId().intValue() + 1;
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		
		LocalDate twoYearsAgo = baseDate.minusMonths(24);
		LocalDate oneYearAgo = baseDate.minusMonths(12);
		
		int[] twoYearsAvg = new int[arrLen];
		int[] twelveMonthsAvg = new int[arrLen];
		int[] itemAmountSum = new int[arrLen];
		double[] lastEntryValueByItem = new double[arrLen];
		
		Order order;
		int itemId;
		for (OrderItem orderItem : orderItemSvc.list()) {
			order = orderItem.getOrder();
			itemId = orderItem.getItem().getId().intValue();
			if (orderSvc.isValidOrder(order) && (order.getStatus() == OrderStatus.RELEASED.getValue() ||
					order.getStatus() == OrderStatus.FINALIZED.getValue())) {
			
				if (order.getReleaseDate().isAfter(oneYearAgo)) 
					twelveMonthsAvg[itemId] += orderItem.getAmount();
				
				if (order.getReleaseDate().isAfter(twoYearsAgo)) 
					twoYearsAvg[itemId] += orderItem.getAmount();
				
				if (order.getReleaseDate().isBefore(baseDate))
					itemAmountSum[itemId] -= orderItem.getAmount();
			}
		}
		
		Entry entry;
		PaginationFilter filter = new PaginationFilter();
		filter.setSortProperty("entry.date");
		filter.setOrder(orders.ASC);
		filter.setFirstResultPage(0);
		filter.setMaxResults(null);
		for (EntryItem entryItem : getServiceFactory().getService(EntryItemService.class).list(filter)) {
			entry = entryItem.getEntry();
			itemId = entryItem.getItem().getId().intValue();
			
			if (entry.getDate().isBefore(baseDate)) 
				itemAmountSum[itemId] += entryItem.getAmount();
			
			lastEntryValueByItem[itemId] = entryItem.getValue().doubleValue();
		}
		
		
		long diffMonths = ChronoUnit.MONTHS.between(baseDate, forecastDate);
		
		double forecastStock;
		for (ItemStockForecast isf : itemStockForecasts) {
			itemId = isf.getItem().getId().intValue();
			isf.setTwelveMonthsAverage(twelveMonthsAvg[itemId] / 12.0);
			isf.setTwoYearsAverage(twoYearsAvg[itemId] / 2.0);
			isf.setAmount(itemAmountSum[itemId]);
			isf.setMinRecommendedStock(isf.getTwelveMonthsAverage() * 6);
			forecastStock = (isf.getAmount() - isf.getTwelveMonthsAverage()) - ((isf.getMaxAvg() / 12) * diffMonths);
			isf.setForecastedStock(forecastStock);
			
			if (forecastStock < 0) {
				isf.setBidAmount(Math.abs(forecastStock));
			}
			
			isf.setLastEntryValue(lastEntryValueByItem[itemId]);
			isf.setTotal(isf.getBidAmount() * isf.getLastEntryValue());
		}
	}
	
	public void sortItemStockForecasts(PaginationFilter filter, List<ItemStockForecast> itemStockForecasts) throws ServiceException{
		try {
			Field field = ItemStockForecast.class.getDeclaredField(filter.getAppSortProperty());
			field.setAccessible(true);
			
			Collections.sort(itemStockForecasts, new Comparator<ItemStockForecast>() {

				@Override
				public int compare(ItemStockForecast o1, ItemStockForecast o2) {
					try {
						Double o1Value = (Double) field.get(o1);
						Double o2Value = (Double) field.get(o2);
						
						if (filter.getOrder() == orders.ASC)
							return o1Value.compareTo(o2Value);
						
						return o2Value.compareTo(o1Value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					
					return -1;	
				}
			});
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
