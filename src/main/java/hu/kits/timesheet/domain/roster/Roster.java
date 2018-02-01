package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;

public class Roster {

	public final OpeningHoursCalendar openingHoursCalendar;
	
	private final SortedMap<LocalDate, DailyRoster> map;

	public Roster(OpeningHoursCalendar openingHoursCalendar, SortedMap<LocalDate, DailyRoster> map) {
		this.openingHoursCalendar = openingHoursCalendar;
		this.map = map;
	}
	
	public DailyRoster dailyRosterAt(LocalDate date) {
		DailyRoster xxx= map.getOrDefault(date, DailyRoster.cretaeEmpty(date));
		return xxx;
	}
	
	public Interval openingHoursAt(LocalDate date) {
		return openingHoursCalendar.openingHoursAt(date);
	}
	
	public DateInterval interval() {
		return openingHoursCalendar.interval();
	}
	
	public List<Employee> employees() {
		return map.values().stream().flatMap(d -> d.employees().stream()).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return map.values().stream()
				.map(DailyRoster::toString)
				.collect(Collectors.joining("\n"));
	}
	
}
