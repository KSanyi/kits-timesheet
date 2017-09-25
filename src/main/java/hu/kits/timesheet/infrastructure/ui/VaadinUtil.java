package hu.kits.timesheet.infrastructure.ui;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.UI;

import hu.kits.timesheet.infrastructure.ui.component.ErrorSubmissionWindow;

public class VaadinUtil {

    public final static String GRID_SMALL = "gridSmall";
    
    public static TimesheetUI getUi() {
		return (TimesheetUI)UI.getCurrent();
	}
    
    @SuppressWarnings("serial")
    public static ShortcutListener createErrorSubmissionShortcutListener() {
        return new ShortcutListener("Alt+E", ShortcutAction.KeyCode.E, new int[] { ShortcutAction.ModifierKey.CTRL }) {
            
            @Override
            public void handleAction(Object sender, Object target) {
                UI.getCurrent().addWindow(new ErrorSubmissionWindow());
                
            }
        };
    };
    
}
