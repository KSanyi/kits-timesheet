package hu.kits.timesheet.infrastructure.server;

import com.vaadin.server.VaadinServlet;

public class TimesheetServlet extends VaadinServlet {

	private static final long serialVersionUID = 1L;

	public final ApplicationService applicationService;
	
	public TimesheetServlet(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
