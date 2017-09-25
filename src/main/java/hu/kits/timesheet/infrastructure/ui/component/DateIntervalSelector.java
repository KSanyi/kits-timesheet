package hu.kits.timesheet.infrastructure.ui.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.DateInterval;

@SuppressWarnings("serial")
public class DateIntervalSelector extends CustomField<DateInterval>{

	private final DateField fromField = new DateField("");
	private final DateField toField = new DateField("");
	
	public DateIntervalSelector(DateInterval dateInterval) {
		
		fromField.setValue(dateInterval.from);
		toField.setValue(dateInterval.to);
		
		fromField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		toField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		
		fromField.setWidth("120px");
		toField.setWidth("120px");
	}
	
	@Override
	public DateInterval getValue() {
		return DateInterval.of(fromField.getValue(), toField.getValue());
	}

	@Override
	protected Component initContent() {
		HorizontalLayout horizontalLayout = new HorizontalLayout(fromField, toField);
		return horizontalLayout;
	}

	@Override
	protected void doSetValue(DateInterval value) {
	}

}
