package com.neil.beans;

import com.neil.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

	private static Map<Class<?>, Object> beanStore = new ConcurrentHashMap<>();

	public static Object getBean(Class<?> cls) {
		return beanStore.get(cls);
	}

	/**
	 *
	 * @param classList
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void initBean(List<Class<?>> classList) throws InstantiationException, IllegalAccessException {
		List<Class<?>> classListCopy = new ArrayList<>(classList);
		while (classListCopy.size() > 0) {
			int remainClassCount = classListCopy.size();
			for (int i = 0; i < classListCopy.size(); i++) {
				if (beanInitCompletion(classListCopy.get(i))) {
					classListCopy.remove(i);
				}
			}
			if (remainClassCount == classListCopy.size()) {
				throw new RuntimeException("Bean initialization failed");
			}
		}
	}

	/**
	 *
	 * @param cls
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean beanInitCompletion(Class<?> cls) throws IllegalAccessException, InstantiationException {

		if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
			return true;
		}

		if (beanStore.containsKey(cls)) {
			return true;
		}

		Object beanInstance = cls.newInstance();

		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Autowired.class)) {
				Class<?> fieldType = field.getType();
				Object injectBean = beanStore.get(fieldType);
				if (injectBean == null) {
					return false;
				}
				field.setAccessible(true);
				field.set(beanInstance, injectBean);
			}
		}

		beanStore.put(cls, beanInstance);
		return true;
	}

}
