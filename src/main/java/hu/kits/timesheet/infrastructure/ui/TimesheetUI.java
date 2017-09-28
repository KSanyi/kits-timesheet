package hu.kits.timesheet.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import hu.kits.timesheet.infrastructure.server.ApplicationService;
import hu.kits.timesheet.infrastructure.server.TimesheetServlet;
import hu.kits.timesheet.infrastructure.ui.roster.OpeningHoursScreen;
import hu.kits.timesheet.infrastructure.ui.roster.RosterScreen;
import hu.kits.timesheet.usermanagement.UserInfo;

@Theme("timesheet")
public class TimesheetUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetUI.class);
	
	private ApplicationService applicationService = ((TimesheetServlet)VaadinServlet.getCurrent()).applicationService;
	
	private UserInfo userInfo;
	
	private HorizontalLayout mainScreen = new HorizontalLayout();

	@Override
	protected void init(VaadinRequest request) {

        logger.debug("Timesgeet-UI initialized");
        
        if(userInfo == null) {
            showLogin();
        }
        
        addShortcutListener(VaadinUtil.createErrorSubmissionShortcutListener());
	}
	
	private void showLogin() {
	    LoginWindow loginWindow = new LoginWindow(applicationService.authenticator, this::buildUI);
	    addWindow(loginWindow);
	}
	
	private void buildUI(UserInfo userInfo) {
	    logger.info(userInfo.loginName + " logged in");
	    
	    mainScreen.setSpacing(false);
	    
	    VerticalLayout pageLayout = new VerticalLayout(new Header(userInfo), new Menu(), mainScreen);
	    pageLayout.setMargin(false);
	    pageLayout.setSpacing(false);
	    pageLayout.setSizeUndefined();
	    
		setContent(pageLayout);
	}
	
	public static TimesheetUI getCurrent() {
		return (TimesheetUI)UI.getCurrent();
	}

	public void showRosterScreen() {
		addWindow(new RosterScreen(applicationService.rosterRepository.loadRoster()));
	}

	public void showOpeningHoursScreen() {
		mainScreen.removeAllComponents();
		mainScreen.addComponent(new OpeningHoursScreen(applicationService.rosterRepository.loadRoster().openingHoursCalendar));
	}
	
}