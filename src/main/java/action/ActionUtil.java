package action;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.SystemUtils;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.ServiceFactory;
import domain.LoggedUser;
import domain.PermissionRoles;
import entity.Item;
import entity.Order;
import entity.User;
import service.CategoryService;
import service.ItemService;
import service.PacketService;

public class ActionUtil{

	public static String getSearchParam(HttpServletRequest request) {
		return request.getParameter("search");
	}
	
	public static Item getRequestItem(HttpServletRequest request) throws ValidationException, ServiceException {
		return ServiceFactory
				.getInstance()
				.getService(ItemService.class)
				.validateAndFindById(request.getParameter("itemId"));
	}
	
	public static void setEditMode(HttpServletRequest request, boolean mode) {
		request.setAttribute("editMode", mode);
	}
	
	public static void setSuperAdminUser(HttpServletRequest request) {
		User user = ((LoggedUser) getSessionLoggedUser(request)).getUser();
		if (PermissionRoles.isSuperAdmin(user.getPermission())) {
			request.setAttribute("superAdmin", true);
		}
	}
	
	public static void addPacketsToRequest(HttpServletRequest request, PacketService packetService) 
			throws ServiceException {
		request.setAttribute("packets", packetService.list());
	}
	
	public static void addCategoryToRequest(HttpServletRequest request, CategoryService categoryService) 
			throws ServiceException {
		request.setAttribute("categories", categoryService.list());
	}
	
	public static void addOrderStatusOnRequest(HttpServletRequest request, Order order) {
		request.setAttribute("status", order.getStatus().toString());
	}

	public static LocalDate applicationStartDate(HttpServletRequest request) {
		LocalDate appStartDate = null;
		if ((appStartDate = (LocalDate) request.getServletContext()
				.getAttribute("appStartDate")) == null) {
			
			appStartDate = LocalDate.ofYearDay(2013, 1);
			request.getServletContext().setAttribute("appStartDate", appStartDate);
		}
		
		return appStartDate;
	}
	
	public static LoggedUser getSessionLoggedUser(HttpServletRequest request){
		LoggedUser lu = (LoggedUser) request.getSession().getAttribute("loggedUser");
		if (lu == null)
			throw new IllegalStateException();
		
		return lu;
	}
	
	public static LocalDate putAdminHomeDateOnSession(AdminHomeDateType dateType, 
			LocalDate date, HttpSession session) {
		
		String paramName = dateType.getParamName();
		if ( date != null) {
			session.setAttribute(paramName, date);
			return date;
		
		}else if ((LocalDate) session.getAttribute(paramName) == null) {
			date = dateType.getDefaultDate();
			session.setAttribute(paramName, date);
			return date;
		
		}else {
			return (LocalDate) session.getAttribute(paramName);
		}
	}
	
	public static Integer getFirstResultPagination(HttpSession session) {
		Integer firstResult = (Integer) session.getAttribute("paginationFirstResult");
		if (firstResult == null)
			return 0;
		
		return firstResult;
	}
	
	public static String getimagesPath(HttpServletRequest request) {
		String imagesPath = "UnixItemImagePath";
		if (SystemUtils.IS_OS_WINDOWS)
			imagesPath = "WindowsItemImagePath";
		
		return request.getServletContext().getInitParameter(imagesPath);
	}

}
