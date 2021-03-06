package hu.kits.timesheet.infrastructure.ui.component;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Button extends com.vaadin.ui.Button {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    public Button(String caption) {
        super(caption);
        addClickListener(click -> logger.debug("USERACTION: button '" + caption + "' clicked"));
    }
    
    public Button(String caption, ClickListener listener) {
        this(caption);
        addClickListener(listener);
    }
    
}
