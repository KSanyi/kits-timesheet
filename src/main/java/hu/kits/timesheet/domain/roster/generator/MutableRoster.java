package hu.kits.timesheet.domain.roster.generator;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.DailyRoster;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;
import hu.kits.timesheet.util.Table;

public class MutableRoster {

	private Table<Employee, LocalDate, Interval> table = new Table<>();
	
	public void set(Employee employee, LocalDate date, Interval interval) {
		table.set(employee, date, interval);
	}
	
	public Interval get(Employee employee, LocalDate date) {
		return table.get(employee, date);
	}
	
	public Roster toRoster(OpeningHoursCalendar openingHoursCalendar) {
		
		SortedMap<LocalDate, DailyRoster> map = new TreeMap<>();
		for(LocalDate date : openingHoursCalendar.interval()) {
			DailyRoster dailyRoster = new DailyRoster(date, table.getColumn(date));
			map.put(date, dailyRoster);
		}
		
		return new Roster(openingHoursCalendar, map);
	}
	
}
