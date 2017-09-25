package hu.kits.timesheet.infrastructure.ui.roster;

import java.time.LocalDate;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.KitsDate;
import hu.kits.timesheet.domain.roster.Roster;
import hu.kits.timesheet.infrastructure.ui.component.WeekSelector;
import hu.kits.timesheet.util.Clock;

@SuppressWarnings("serial")
public class RosterScreen extends Window {

	//private final DateIntervalSelector dateIntervalSelector;
	
	private final WeekSelector weekSelector = new WeekSelector(KitsDate.week(Clock.date()));
	
	private final GridLayout rostersLayout = new GridLayout(2, 5);
	
	private final Roster roster;
	
	public RosterScreen(Roster roster) {
		
		setCaption("Beosztás");
		center();
		this.roster = roster;
		
		weekSelector.addValueChangeListener(v -> refresh(v.getValue()));
		
		rostersLayout.setMargin(false);
		rostersLayout.setSpacing(true);
		
		VerticalLayout layout = new VerticalLayout(weekSelector, rostersLayout);
		layout.setExpandRatio(rostersLayout, 1);
		layout.setComponentAlignment(weekSelector, Alignment.TOP_CENTER);
		layout.setComponentAlignment(rostersLayout, Alignment.MIDDLE_CENTER);
		setContent(layout);
		refresh(weekSelector.getValue());
		
	}
	
	private void refresh(DateInterval week) {
		
		rostersLayout.removeAllComponents();
		
		int index = 0;
		for(LocalDate date=week.from;!date.isAfter(week.to);date = date.plusDays(1)) {
			DailyRosterTable dailyRosterTable = new DailyRosterTable(roster.dailyRosterAt(date));
			int column = index / 5;
			int row = index % 5;
			rostersLayout.addComponent(dailyRosterTable, column, row);
			index++;
		}
		
		//dateInterval.stream()
		//	.forEach(date -> rosters.addComponent(new DailyRosterTable(roster.openingHoursAt(date), roster.dailyRosterAt(date))));
		
	}
	
}
