package hu.kits.timesheet.infrastructure.ui.component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.DateInterval;

@SuppressWarnings("serial")
public class WeekSelector extends CustomField<DateInterval> implements HasValue.ValueChangeListener<LocalDate> {

	private final DateField dateField = new DateField("Dátum");
	private final WeekSteper weekNumberSteper;
	
	private Registration registration;
	
	private DateInterval currentWeek;
	
	public WeekSelector(DateInterval week) {
		currentWeek = week;
		weekNumberSteper = new WeekSteper(week);
		
		dateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		dateField.setWidth("120px");
		registration = dateField.addValueChangeListener(this);
		
		weekNumberSteper.addValueChangeListener(v -> weekChanged(v.getValue()));
		dateField.setShowISOWeekNumbers(true);
		
		dateField.setValue(week.from);
		//currentWeek = KitsDate.week(2017, 2);
	}
	
	private void weekChanged(DateInterval week) {
		
		currentWeek = week;
		LocalDate mondayOfWeek = currentWeek.from;
		registration.remove();
		dateField.setValue(mondayOfWeek);
		registration = dateField.addValueChangeListener(this);
		fireEvent(new ValueChangeEvent<DateInterval>(this, null, true));
	}
	
	@Override
	public DateInterval getValue() {
		return currentWeek;
	}

	@Override
	protected Component initContent() {
		HorizontalLayout layout = new HorizontalLayout(dateField, weekNumberSteper);
		layout.setComponentAlignment(weekNumberSteper, Alignment.BOTTOM_CENTER);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void doSetValue(DateInterval value) {
	}
	
	@Override
	public void valueChange(ValueChangeEvent<LocalDate> event) {
		LocalDate date = event.getValue();
		LocalDate mondayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		weekNumberSteper.setValue(DateInterval.of(mondayOfWeek, mondayOfWeek.plusDays(6)));
	}

}
