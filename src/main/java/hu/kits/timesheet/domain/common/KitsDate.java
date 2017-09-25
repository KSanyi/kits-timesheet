package hu.kits.timesheet.domain.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class KitsDate {

	public static DateInterval week(int year, int n) {
		
		LocalDate firstOfJan = LocalDate.of(year, 1, 1);
		LocalDate firstMonday = firstOfJan.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		return week(firstMonday.plusWeeks(n-1));
	}
	
	public static DateInterval week(LocalDate date) {
		LocalDate monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		
		return DateInterval.of(monday, monday.plusDays(6)); 
	}
	
}
