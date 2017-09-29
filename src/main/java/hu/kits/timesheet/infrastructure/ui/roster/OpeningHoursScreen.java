package hu.kits.timesheet.infrastructure.ui.roster;

import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.infrastructure.ui.component.calendar.Calendar;
import hu.kits.timesheet.infrastructure.ui.component.calendar.CalendarEvent;

@SuppressWarnings("serial")
public class OpeningHoursScreen extends VerticalLayout {
	
	public OpeningHoursScreen(OpeningHoursCalendar openingHoursCalendar) {
		
		setMargin(new MarginInfo(false, true));
		
		List<CalendarEvent> events = openingHoursCalendar.interval().stream()
				.map(day -> new CalendarEvent(day, format(openingHoursCalendar.openingHoursAt(day))))
				.collect(Collectors.toList());
		
		addComponent(new Calendar(events));
	}
	
	private static String format(Interval openingHours) {
		if(openingHours.isEmpty()) {
			return "-";
		} else {
			return openingHours.from + ":00 - " + (openingHours.to+1) + ":00";
		}
	}

}
