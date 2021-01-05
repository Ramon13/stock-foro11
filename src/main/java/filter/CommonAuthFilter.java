package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.LoggedUser;
import domain.PermissionRoles;

@WebFilter( urlPatterns = {"/common/*"})
public class CommonAuthFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) resp;

			LoggedUser loggedUser = (LoggedUser) request.getSession().getAttribute("loggedUser");
			if (loggedUser == null || 
					!PermissionRoles.isUser(loggedUser.getUser().getPermission())) {
				foward(request, response, "/public/login.jsp");
				return;
			}
				
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	private void foward(HttpServletRequest request, HttpServletResponse response, String path) throws Exception{
		request.getRequestDispatcher(path).forward(request, response);
	}

}