package com.neil.web.handler;

import com.neil.beans.BeanFactory;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingHandler {

	private String uri;
	private Method method;
	private Class<?> controller;
	private String[] args;

	public MappingHandler(String uri, Method method, Class<?> cls, String[] args) {
		this.uri = uri;
		this.method = method;
		this.controller = cls;
		this.args = args;
	}

	/**
	 * Handler Servlet Request
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public boolean handle(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String requestUri = httpServletRequest.getRequestURI();
		if (!StringUtils.equalsIgnoreCase(requestUri, this.uri)) {
			return false;
		}

		int argsLength = args.length;
		Object[] params = new Object[argsLength];
		for (int i = 0; i < argsLength; i++) {
			params[i] = httpServletRequest.getParameter(args[i]);
		}

		//Object controllerInstance = this.controller.newInstance();
		Object controllerInstance = BeanFactory.getBean(this.controller);
		Object response = method.invoke(controllerInstance, params);
		servletResponse.getWriter().println(response.toString());
		return true;
	}
}
