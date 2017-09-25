package hu.kits.timesheet.infrastructure.ui.component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.KitsDate;

@SuppressWarnings("serial")
public class WeekSelector extends CustomField<DateInterval> implements HasValue.ValueChangeListener<LocalDate> {

	private final DateField dateField = new DateField("Dátum");
	private final ComboBox<Integer> weekNumberCombo = new ComboBox<>("Hét", generateWeekNumbers());
	
	private Registration registration;
	
	public WeekSelector(DateInterval week) {
		
		dateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		dateField.setWidth("120px");
		dateField.setValue(LocalDate.of(2017,1,1));
		registration = dateField.addValueChangeListener(this);
		
		weekNumberCombo.addStyleName(ValoTheme.COMBOBOX_SMALL);
		weekNumberCombo.setEmptySelectionAllowed(false);
		weekNumberCombo.setWidth("80px");
		weekNumberCombo.setValue(1);
		weekNumberCombo.addValueChangeListener(v -> weekChanged(v.getValue()));
	}
	
	private void weekChanged(int weekNumber) {
		LocalDate mondayOfWeek = KitsDate.week(dateField.getValue().getYear(), weekNumberCombo.getValue()).from;
		registration.remove();
		dateField.setValue(mondayOfWeek);
		registration = dateField.addValueChangeListener(this);
		fireEvent(new ValueChangeEvent<DateInterval>(this, null, true));
	}
	
	@Override
	public DateInterval getValue() {
		return KitsDate.week(dateField.getValue().getYear(), weekNumberCombo.getValue());
	}

	@Override
	protected Component initContent() {
		HorizontalLayout horizontalLayout = new HorizontalLayout(dateField, weekNumberCombo);
		return horizontalLayout;
	}

	@Override
	protected void doSetValue(DateInterval value) {
	}
	
	private static List<Integer> generateWeekNumbers() {
		return IntStream.range(1, 52).mapToObj(i -> i).collect(Collectors.toList());
	}

	@Override
	public void valueChange(ValueChangeEvent<LocalDate> event) {
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int weekNumber = event.getValue().get(weekFields.weekOfWeekBasedYear());
		weekNumberCombo.setValue(weekNumber);
	}

}
