package hu.kits.timesheet.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class Menu extends MenuBar {

	private static Logger logger = LoggerFactory.getLogger(Menu.class);
	
	Menu() {
		setStyleName(ValoTheme.MENUBAR_SMALL);
		setSizeFull();
		
		MenuItem openingHoursMenuItem = addItem("Nyitvatartás", null);
		openingHoursMenuItem.addItem("Éves nyitvatartás", new LoggerCommand(c -> VaadinUtil.getUi().showOpeningHoursScreen()));
		openingHoursMenuItem.addItem("Eladó beosztás", new LoggerCommand(c -> VaadinUtil.getUi().showRosterScreen()));
	}
	
	private static class LoggerCommand implements Command {
		private final Command action;
		
		public LoggerCommand(Command action){
			this.action = action;
		}
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			logger.debug("Menuitem '" + selectedItem.getText() + "' was clicked");
			action.menuSelected(selectedItem);
		}
	}
	
}

