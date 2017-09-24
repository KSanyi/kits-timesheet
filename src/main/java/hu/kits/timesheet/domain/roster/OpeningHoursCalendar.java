package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;

public class OpeningHoursCalendar {

	public OpeningHoursCalendar(SortedMap<LocalDate, Interval> map) {
		this.map = map;
	}
	
	private final SortedMap<LocalDate, Interval> map;
	
	public Interval openingHoursAt(LocalDate date) {
		return map.getOrDefault(date, Interval.empty);
	}
	
	@Override
	public String toString() {
		return map.keySet().stream()
				.map(day -> day + " " + day.getDayOfWeek() + " " + map.get(day))
				.collect(Collectors.joining("\n"));
	}
	
}

class OpeningHoursCalendarFactory {
	
	static OpeningHoursCalendar create(DateInterval dateInterval, List<OpeningHoursRule> rules) {
		
		SortedMap<LocalDate, Interval> map = new TreeMap<>();
		for(LocalDate date=dateInterval.from;!date.isAfter(dateInterval.to);date=date.plusDays(1)) {
			map.put(date, Interval.of(10, 17));
		}
		
		return new OpeningHoursCalendar(map);
	}
	
}

interface OpeningHoursRule {
	
	Interval apply(Predicate<LocalDate> date);
}
