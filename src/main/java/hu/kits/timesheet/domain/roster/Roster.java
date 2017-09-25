package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;

public class Roster {

	private final OpeningHoursCalendar openingHoursCalendar;
	
	private final SortedMap<LocalDate, DailyRoster> map;

	public Roster(OpeningHoursCalendar openingHoursCalendar, SortedMap<LocalDate, DailyRoster> map) {
		this.openingHoursCalendar = openingHoursCalendar;
		this.map = map;
	}
	
	public DailyRoster dailyRosterAt(LocalDate date) {
		return map.getOrDefault(date, DailyRoster.cretaeEmpty(date));
	}
	
	public Interval openingHoursAt(LocalDate date) {
		return openingHoursCalendar.openingHoursAt(date);
	}
	
	public DateInterval interval() {
		return openingHoursCalendar.interval();
	}
	
	@Override
	public String toString() {
		return map.values().stream()
				.map(DailyRoster::toString)
				.collect(Collectors.joining("\n"));
	}
	
}
