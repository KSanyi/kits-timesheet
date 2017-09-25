package hu.kits.timesheet.infrastructure.ui.roster;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.FooterRow;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.DailyRoster;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.infrastructure.ui.VaadinUtil;

@SuppressWarnings("serial")
public class DailyRosterTable extends Grid<DailyRosterRow> {

	private final Interval hours = Interval.of(10, 18);
	
	private final DailyRoster dailyRoster;
	
	public DailyRosterTable(DailyRoster dailyRoster) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM d EEEE");
		
		setCaption(formatter.format(dailyRoster.date));
		
		this.dailyRoster = dailyRoster;
		
		addColumn(r -> r.employeeName).setCaption("Eladó");
		hours.stream().forEach(hour -> {
			addColumn(r -> "")
			.setCaption(hour + " - " + (hour+1))
			.setId(String.valueOf(hour))
			.setStyleGenerator(r -> "v-align-center")
			.setStyleGenerator(r -> r.workMap.getOrDefault(hour, false) ? "BUSY" : "")
			.setWidth(80);
		});
		addColumn(r -> r.sum() + " óra").setCaption("");
		
		FooterRow footerRow = appendFooterRow();
		hours.stream().forEach(hour -> footerRow.getCell(String.valueOf(hour)).setText(dailyRoster.coverage(hour) + ""));
		
		List<DailyRosterRow> rows = dailyRoster.employees().stream().map(this::createDailyRosterRow).collect(Collectors.toList());
		
		setItems(rows);
		
		setHeightByRows(Math.max(3, rows.size()));
		setWidth("860px");
        addStyleName(VaadinUtil.GRID_SMALL);
	}
	
	private DailyRosterRow createDailyRosterRow(Employee employee) {
	
		Map<Integer, Boolean> workMap = new HashMap<>();
		hours.stream().forEach(hour -> workMap.put(hour, dailyRoster.workAt(employee, hour)));
		
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
	
	int sum() {
		return (int)workMap.values().stream().filter(v -> v).count();
	}
	
}
