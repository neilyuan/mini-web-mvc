package com.neil.web.handler;

import com.neil.web.mvc.Controller;
import com.neil.web.mvc.RequestMapping;
import com.neil.web.mvc.RequestParam;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {

	public static List<MappingHandler> mappingHandlers = new ArrayList<>();

	/**
	 * Retrieve all classes & Construct MappingHandlers container
	 * {@link HandlerManager#mappingHandlers}
	 * @param classList
	 */
	public static void resolveMappingHandler(List<Class<?>> classList) {
		for (Class<?> aClass : classList) {
			if (aClass.isAnnotationPresent(Controller.class)) {
				parseHandlerFromController(aClass);
			}
		}
	}

	/**
	 * Retrieve all methods & Construct {@link MappingHandler}
	 * @param cls
	 */
	private static void parseHandlerFromController(Class<?> cls) {
		Method[] methodList = cls.getDeclaredMethods();

		for (Method method : methodList) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				String uri = method.getAnnotation(RequestMapping.class).value();
				List<String> paramsList = new ArrayList<>();
				Parameter[] parameters = method.getParameters();
				for (Parameter parameter : parameters) {
					if (parameter.isAnnotationPresent(RequestParam.class)) {
						paramsList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
					}
				}
				String[] args = paramsList.toArray(new String[paramsList.size()]);
				MappingHandler mappingHandler = new MappingHandler(uri, method, cls, args);
				HandlerManager.mappingHandlers.add(mappingHandler);
			}
		}
	}

}
