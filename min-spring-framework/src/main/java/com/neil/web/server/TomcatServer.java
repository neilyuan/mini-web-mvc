package com.neil.web.server;

import com.neil.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

	private Tomcat tomcat;
	private String[] args;

	public TomcatServer(String[] args) {
		this.args = args;
	}

	public void startServer() throws LifecycleException {
		// Initial tomcat
		tomcat = new Tomcat();
		tomcat.setPort(6699);
		tomcat.start();
		// Initial tomcat context
		Context context = new StandardContext();
		context.setPath("");
		context.addLifecycleListener(new Tomcat.FixContextListener());
		// Add dispatcher servlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet).setAsyncSupported(true);
		// Add servlet mapping
		context.addServletMappingDecoded("/", "dispatcherServlet");
		// add context to Host
		tomcat.getHost().addChild(context);

		// start await thread
		Thread waitThread = new Thread("Tomcat_wait_thread") {
			@Override
			public void run () {
				TomcatServer.this.tomcat.getServer().await();
			}
		};

		waitThread.setDaemon(false);
		waitThread.start();
	}
}
