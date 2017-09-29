package hu.kits.timesheet.infrastructure.ui.component.calendar;

import java.time.LocalDate;

public class CalendarEvent {

	public final LocalDate date;
	
	public final String title;
	
	public final String details;

	public CalendarEvent(LocalDate date, String title, String details) {
		this.date = date;
		this.title = title;
		this.details = details;
	}
	
	public CalendarEvent(LocalDate date, String title) {
		this(date, title, "");
	}
	
	@Override
	public String toString() {
		return date + ": " + title;
	}
	
}
