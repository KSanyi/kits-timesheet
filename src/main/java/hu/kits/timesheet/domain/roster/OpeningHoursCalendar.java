package hu.kits.timesheet.domain.roster;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
	
	public int sumOpeningHours(DateInterval interval) {
		
		return interval.stream().mapToInt(day -> openingHoursAt(day).length()).sum();
	}
	
	public DateInterval interval() {
		return DateInterval.of(map.firstKey(), map.lastKey());
	}
	
	public List<DateInterval> weeks() {
		
		List<DateInterval> weeks = new ArrayList<>();
		List<LocalDate> weekDays = new ArrayList<>();
		for(LocalDate day : interval()) {
			weekDays.add(day);
			if(day.getDayOfWeek() == DayOfWeek.SUNDAY) {
				weeks.add(DateInterval.of(weekDays.get(0), weekDays.get(weekDays.size()-1)));
				weekDays.clear();
			} 
		}
		
		return weeks;
		
	}
	
	@Override
	public String toString() {
		return map.keySet().stream()
				.map(day -> day + " " + day.getDayOfWeek() + " " + map.get(day))
				.collect(Collectors.joining("\n"));
	}
	
	public static OpeningHoursCalendar create(DateInterval dateInterval, List<OpeningHoursRule> rules) {
		
		List<OpeningHoursRule> reversedRules = new ArrayList<>(rules);
		Collections.reverse(reversedRules);
		
		SortedMap<LocalDate, Interval> map = new TreeMap<>();
		for(LocalDate date : dateInterval) {
			map.put(date, reversedRules.stream().filter(rule -> rule.condition.test(date)).findFirst().map(rule -> rule.interval).orElse(Interval.empty));
		}
		
		return new OpeningHoursCalendar(map);
	}
	
	public static class OpeningHoursRule {
		
		public final Predicate<LocalDate> condition;
		
		public final Interval interval;

		public OpeningHoursRule(Predicate<LocalDate> condition, Interval interval) {
			this.condition = condition;
			this.interval = interval;
		}
	}
	
}

