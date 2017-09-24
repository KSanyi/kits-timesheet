package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Roster {

	private final SortedMap<LocalDate, DailyRoster> map;

	public Roster(SortedMap<LocalDate, DailyRoster> map) {
		this.map = map;
	}
	
	@Override
	public String toString() {
		return map.values().stream()
				.map(DailyRoster::toString)
				.collect(Collectors.joining("\n"));
	}
	
}
