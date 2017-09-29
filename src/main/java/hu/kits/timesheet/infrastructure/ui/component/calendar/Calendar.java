package hu.kits.timesheet.infrastructure.ui.component.calendar;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.infrastructure.ui.component.NumberSteper;
import hu.kits.timesheet.util.Clock;

@SuppressWarnings("serial")
public class Calendar extends VerticalLayout {
	
	private final NumberSteper yearSelector;
	
	private final HorizontalLayout container = new HorizontalLayout();
	
	private int year;
	
	private final List<CalendarEvent> events;
	
	public Calendar(List<CalendarEvent> events) {
		
		this.events = events;
		
		yearSelector = new NumberSteper("", 2017, Interval.of(2000, 2100));
		yearSelector.addValueChangeListener(v -> refresh(v.getValue()));
		
		addComponents(yearSelector, container);
		createLayout();
		
		yearSelector.setValue(Clock.date().getYear());
		refresh(Clock.date().getYear());
	}
	
	public Calendar() {
		this(Collections.emptyList());
	}
	
	private void createLayout() {
		setMargin(false);
		setSpacing(false);
		
		setComponentAlignment(yearSelector, Alignment.TOP_CENTER);
	}
	
	private void refresh(int year) {
		
		if(this.year == year) return;
		
		GridLayout gridLayout = new GridLayout(3, 4);
		gridLayout.setSpacing(true);
		gridLayout.setMargin(false);
		
		IntStream.range(1, 13).forEach(month -> gridLayout.addComponent(new CalendarMonthView(year, month, events)));
		container.removeAllComponents();
		container.addComponent(gridLayout);
		this.year = year;
	}
}
