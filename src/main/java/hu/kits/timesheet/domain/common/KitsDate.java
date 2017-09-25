package hu.kits.timesheet.domain.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class KitsDate {

	public static DateInterval week(int year, int n) {
		
		LocalDate firstOfJan = LocalDate.of(year, 1, 1);
		
		LocalDate firstMonday = firstOfJan.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		
		LocalDate from = firstMonday.plusWeeks(n-1);
		LocalDate to = from.plusDays(6);
		
		return DateInterval.of(from, to);
	}
	
}
