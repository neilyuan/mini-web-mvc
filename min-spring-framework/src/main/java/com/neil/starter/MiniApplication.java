package com.neil.starter;

import com.neil.beans.BeanFactory;
import com.neil.core.ClassScanner;
import com.neil.web.handler.HandlerManager;
import com.neil.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MiniApplication {

	private static Logger logger = Logger.getLogger(MiniApplication.class.getName());

	public static void run( Class<?> cls, String[] args) {
		TomcatServer tomcatServer = new TomcatServer(args);
		try {
			tomcatServer.startServer();
			logger.info("Tomcat server started");

			List<Class<?>> classList = ClassScanner.scan(cls.getPackage().getName());
			logger.info("Classes scan completed - total is " + classList.size());

			BeanFactory.initBean(classList);
			logger.info("Bean Init completed successfully ");

			HandlerManager.resolveMappingHandler(classList);
			logger.info("Mapping handler completed successfully");

		} catch (LifecycleException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}
