package hu.kits.timesheet.infrastructure.ui.component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.KitsDate;

@SuppressWarnings("serial")
public class CalendarMonthView extends VerticalLayout {

	private final int year;
	private final int month;
	
	private final Grid<DateInterval> grid = new Grid<>();
	
	private final List<DateInterval> weeks;
	
	public CalendarMonthView(int year, int month) {
		this.year = year;
		this.month = month;
		weeks = createWeeks(year, month);
		grid.setItems(weeks);
		
		createLayout();
	}
	
	private void createLayout() {
		setMargin(false);
		
		grid.setCaption(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM", new Locale("HU"))));
		for(DayOfWeek day : DayOfWeek.values()) {
			grid.addColumn(week -> week.getDay(day.ordinal()).getDayOfMonth())
				.setCaption(day.name().substring(0, 1))
				.setStyleGenerator(week -> week.getDay(day.ordinal()).getMonthValue() == month ? "calendarNormal" : "calendarFade");
		}
		
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setHeightByRows(weeks.size());
		addComponent(grid);		
	}
	
	private List<DateInterval> createWeeks(int year, int month) {
		DateInterval week = KitsDate.week(LocalDate.of(year, month, 1));
		List<DateInterval> weeks = new ArrayList<>();
		while(week.from.getMonthValue() == month || week.to.getMonthValue() == month) {
			weeks.add(week);
			week = KitsDate.week(week.to.plusDays(1));
		}	
		
		return weeks;
	}
	
}
