package hu.kits.timesheet.infrastructure.ui.component;

import java.time.temporal.WeekFields;
import java.util.Locale;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.DateInterval;

@SuppressWarnings("serial")
public class WeekSteper extends CustomField<DateInterval> {

	private final TextField numberField = new TextField("Hét");
	
	private final Button pervButton = new Button("", click -> incrementOrDecrement(-1));
	private final Button nextButton = new Button("", click -> incrementOrDecrement(+1));
	
	private DateInterval currentWeek;
	
	public WeekSteper(DateInterval week) {
		
		currentWeek = week;
		doSetValue(week);
		
		pervButton.setIcon(VaadinIcons.ARROW_LEFT);
		nextButton.setIcon(VaadinIcons.ARROW_RIGHT);
		
		numberField.setWidth("40px");
		numberField.setStyleName(ValoTheme.TEXTAREA_SMALL);
		pervButton.setStyleName(ValoTheme.BUTTON_SMALL);
		nextButton.setStyleName(ValoTheme.BUTTON_SMALL);

		numberField.addValueChangeListener(v -> numberChanged(v));
		numberField.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		
		numberField.setEnabled(false);
	}
	
	private void numberChanged(ValueChangeEvent<String> valueChangeEvent) {
		try {
			int newValue = Integer.parseInt(valueChangeEvent.getValue());
			
			int diff = newValue - currentWeekNumber();
			currentWeek = DateInterval.of(currentWeek.from.plusWeeks(diff), currentWeek.to.plusWeeks(diff));
			
			fireEvent(new ValueChangeEvent<DateInterval>(this, null, true));
		} catch(NumberFormatException ex) {
			numberField.setValue(valueChangeEvent.getOldValue());
		}
	}
	
	private void incrementOrDecrement(int change) {
		numberField.setValue(String.valueOf(currentWeekNumber() + change));
	}
	
	@Override
	public DateInterval getValue() {
		return currentWeek;
	}

	@Override
	protected Component initContent() {
		HorizontalLayout layout = new HorizontalLayout(pervButton, numberField, nextButton);
		layout.setComponentAlignment(pervButton, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(nextButton, Alignment.BOTTOM_RIGHT);
		layout.setSpacing(false);
		return layout;
	}

	@Override
	protected void doSetValue(DateInterval value) {
		WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
		int weekNumber = value.from.get(weekFields.weekOfWeekBasedYear());
		
		numberField.setValue(String.valueOf(weekNumber));
	}
	
	int currentWeekNumber() {
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return currentWeek.from.get(weekFields.weekOfWeekBasedYear());
	}

}
