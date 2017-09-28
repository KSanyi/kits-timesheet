package hu.kits.timesheet.infrastructure.ui.roster;

import java.util.stream.IntStream;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.infrastructure.ui.component.CalendarMonthView;

@SuppressWarnings("serial")
public class OpeningHoursScreen extends VerticalLayout {
	
	public OpeningHoursScreen(OpeningHoursCalendar openingHoursCalendar) {
		
		GridLayout gridLayout = new GridLayout(3, 4);
		gridLayout.setSpacing(true);
		gridLayout.setMargin(true);
		
		IntStream.range(1, 13).forEach(month ->gridLayout.addComponent(new CalendarMonthView(2017, month)));
		addComponent(gridLayout);
		setMargin(false);
		setSpacing(false);
	}

}
