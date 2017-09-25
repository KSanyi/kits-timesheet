package hu.kits.timesheet.infrastructure.ui.roster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.ui.Grid;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.DailyRoster;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.infrastructure.ui.VaadinUtil;

@SuppressWarnings("serial")
public class DailyRosterTable extends Grid<DailyRosterRow> {

	private final Interval openingHours;
	
	private final DailyRoster dailyRoster;
	
	public DailyRosterTable(Interval openingHours, DailyRoster dailyRoster) {
		
		setCaption(dailyRoster.date + " - " + dailyRoster.date.getDayOfWeek());
		
		this.openingHours = openingHours;
		this.dailyRoster = dailyRoster;
		
		addColumn(r -> r.employeeName).setCaption("Eladó");
		openingHours.stream().forEach(hour -> {
			addColumn(r -> r.workMap.getOrDefault(hour, false) ? "X" : "")
			.setCaption(hour + " - " + (hour+1))
			.setStyleGenerator(item -> "v-align-center");
		});
		
		List<DailyRosterRow> rows = dailyRoster.employees().stream().map(this::createDailyRosterRow).collect(Collectors.toList());
		
		setItems(rows);
		
		setHeightByRows(Math.max(3, rows.size()));
		setWidth("700px");
        addStyleName(VaadinUtil.GRID_SMALL);
	}
	
	private DailyRosterRow createDailyRosterRow(Employee employee) {
	
		Map<Integer, Boolean> workMap = new HashMap<>();
		openingHours.stream().forEach(hour -> workMap.put(hour, dailyRoster.workAt(employee, hour)));
		
		return new DailyRosterRow(employee.name, workMap);
	}
	
}

class DailyRosterRow {
	
	final String employeeName;
	
	final Map<Integer, Boolean> workMap;

	public DailyRosterRow(String employeeName, Map<Integer, Boolean> workMap) {
		this.employeeName = employeeName;
		this.workMap = workMap;
	}
	
}
