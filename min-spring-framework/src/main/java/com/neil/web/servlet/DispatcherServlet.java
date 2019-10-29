package com.neil.web.servlet;

import com.neil.web.handler.HandlerManager;
import com.neil.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet implements Servlet {

	public void init(ServletConfig servletConfig) throws ServletException {
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	/**
	 * retrieve MappingHandlers {@link HandlerManager#mappingHandlers}
	 * handle request {@link MappingHandler#handle(ServletRequest, ServletResponse)}
	 * @param servletRequest
	 * @param servletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

		for (MappingHandler mappingHandler : HandlerManager.mappingHandlers) {
			try {
				if (mappingHandler.handle(servletRequest, servletResponse)) {
					return;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public String getServletInfo() {
		return null;
	}

	public void destroy() {

	}
}
