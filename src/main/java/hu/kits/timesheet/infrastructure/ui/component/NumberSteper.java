package hu.kits.timesheet.infrastructure.ui.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.Interval;

@SuppressWarnings("serial")
public class NumberSteper extends CustomField<Integer> {

	private final TextField numberField;
	
	private final Button pervButton = new Button("", click -> incrementOrDecrement(-1));
	private final Button nextButton = new Button("", click -> incrementOrDecrement(+1));
	
	private int currentValue;
	
	private final Interval interval;
	
	public NumberSteper(String caption, int currentValue, Interval interval) {
		
		this.interval = interval;
		
		numberField = new TextField(caption);
		
		doSetValue(currentValue);
		
		pervButton.setIcon(VaadinIcons.ARROW_LEFT);
		nextButton.setIcon(VaadinIcons.ARROW_RIGHT);
		
		numberField.setWidth("60px");
		numberField.setStyleName(ValoTheme.TEXTAREA_SMALL);
		pervButton.setStyleName(ValoTheme.BUTTON_SMALL);
		nextButton.setStyleName(ValoTheme.BUTTON_SMALL);

		numberField.addValueChangeListener(v -> numberChanged(v));
		numberField.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
	}
	
	public NumberSteper(String caption, int currentValue) {
		this(caption, currentValue, Interval.of(0, 100));
	}
	
	private void numberChanged(ValueChangeEvent<String> valueChangeEvent) {
		try {
			int newValue = Integer.parseInt(valueChangeEvent.getValue());
			doSetValue(newValue);
		} catch(NumberFormatException ex) {
			numberField.setValue(valueChangeEvent.getOldValue());
		}
	}
	
	private void incrementOrDecrement(int change) {
		numberField.setValue(String.valueOf(currentValue + change));
	}
	
	@Override
	public Integer getValue() {
		return currentValue;
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
	protected void doSetValue(Integer value) {
		int newValue = interval.cap(value);
		if(newValue != currentValue) {
			int oldValue = currentValue;
			currentValue = newValue;
			fireEvent(new ValueChangeEvent<Integer>(this, oldValue, true));
		}
		numberField.setValue(String.valueOf(currentValue));
	}

}
