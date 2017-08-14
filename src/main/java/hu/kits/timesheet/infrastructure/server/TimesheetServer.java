package hu.kits.timesheet.infrastructure.server;

import java.lang.invoke.MethodHandles;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.kits.timesheet.infrastructure.ui.TimesheetUI;

public class TimesheetServer extends Server {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public TimesheetServer(int httpPort, ApplicationService applicationService) {
		super(httpPort);
		setHandler(createServletContextHandler(applicationService));
		logger.info("Timesheet Server initialised on port " + httpPort);
	}
	
	public void startServer() throws Exception {
		super.start();
		logger.info("Timesheet Server started");
		super.join();
	}
	
	private ServletContextHandler createServletContextHandler(ApplicationService applicationService) {
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
		servletContextHandler.addServlet(createServletHolder(applicationService), "/*");
		servletContextHandler.getSessionHandler().getSessionManager().setMaxInactiveInterval(60 * 60); // no timeout 
		return servletContextHandler;
	}
	
	private ServletHolder createServletHolder(ApplicationService applicationService) {
		ServletHolder servletHolder = new ServletHolder(new TimesheetServlet(applicationService));
		servletHolder.setInitParameter("UI", TimesheetUI.class.getCanonicalName());
		return servletHolder;
	}

	
}
