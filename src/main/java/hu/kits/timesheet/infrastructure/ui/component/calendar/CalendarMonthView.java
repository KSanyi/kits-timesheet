package hu.kits.timesheet.infrastructure.ui.component.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.KitsDate;
import hu.kits.timesheet.util.StringUtil;

@SuppressWarnings("serial")
public class CalendarMonthView extends VerticalLayout {

	private static final int COL_WIDTH = 60;
	
	private final int year;
	private final int month;
	
	private final Grid<DateInterval> grid = new Grid<>();
	
	private final List<DateInterval> weeks;
	
	private final List<CalendarEvent> events;
	
	public CalendarMonthView(int year, int month, List<CalendarEvent> events) {
		this.year = year;
		this.month = month;
		this.events = events;
		weeks = createWeeks(year, month);
		grid.setItems(weeks);
		
		createLayout();
	}
	
	private void createLayout() {
		setMargin(false);
		setSizeUndefined();
		
		grid.addColumn(week -> String.valueOf(KitsDate.weekNumber(week.from)))
			.setId("WEEK")
			.setWidth(40)
			.setSortable(false)
			.setResizable(false)
			.setStyleGenerator(w -> "calendarWeekNumber");
		
		for(DayOfWeek day : DayOfWeek.values()) {
			grid.addColumn(week -> createDay(week.getDay(day.ordinal())), new HtmlRenderer())
				.setId(day.name())
				.setCaption(dayInitial(day))
				.setWidth(COL_WIDTH)
				.setSortable(false)
				.setResizable(false)
				.setDescriptionGenerator(c -> description(c, day))
				.setStyleGenerator(week -> week.getDay(day.ordinal()).getMonthValue() == month ? "calendarNormal" : "calendarFade");
		}
		
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setHeightByRows(weeks.size());
		grid.setWidth((COL_WIDTH * 7 + 40) + "px");
		grid.addItemClickListener(this::cellClicked);
		
		String monthName = StringUtil.capitalize(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM", new Locale("HU"))));
		
		Button monthButton = new Button(monthName);
		monthButton.addStyleName(ValoTheme.BUTTON_LINK);
		
		VerticalLayout holder = new VerticalLayout(monthButton, grid);
		holder.setMargin(false);
		holder.setSpacing(false);
		
		addComponent(holder);
	}
	
	private String createDay(LocalDate day) {
		List<CalendarEvent> eventsForDay = events.stream().filter(event -> event.date.equals(day)).collect(Collectors.toList());
		int dayNumber = day.getDayOfMonth();
		
		return dayNumber + "<br/>" + eventsForDay.stream().map(event -> event.title).collect(Collectors.joining("<br/>"));
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
	
	private static String dayInitial(DayOfWeek day) {
		return day.getDisplayName(TextStyle.NARROW, new Locale("HU"));
	}
	
	private void cellClicked(ItemClick<DateInterval> click) {
		DateInterval week = click.getItem();
		
		String columnId = click.getColumn().getId();
		if(columnId.equals("WEEK")) {
			Notification.show(week + "");			
		} else {
			DayOfWeek dayOfWeek = DayOfWeek.valueOf(click.getColumn().getId());
			LocalDate date = week.getDay(dayOfWeek.ordinal());
			Notification.show(date + "");
		}
	}
	
	private String description(DateInterval week, DayOfWeek dayOfWeek) {
		LocalDate date = week.getDay(dayOfWeek.ordinal());
		List<CalendarEvent> eventsForDay = events.stream().filter(event -> event.date.equals(date)).collect(Collectors.toList());
		return eventsForDay.stream().map(event -> event.title).collect(Collectors.joining("<br/>"));
	}
	
}
