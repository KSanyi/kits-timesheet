package hu.kits.timesheet.domain.roster;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar.OpeningHoursRule;

public class OpeningHoursCalendarTest {

	private final DateInterval wholeYear = DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 12, 31));
	
	@Test
	public void test() {
		
		OpeningHoursRule tenToSixRule = new OpeningHoursRule(date -> true, Interval.of(10, 18));
		OpeningHoursRule saturdayRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY, Interval.of(10, 14));
		OpeningHoursRule sundayClosedRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY, Interval.empty);
		OpeningHoursRule summerSaturdayClosedRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY && Arrays.asList(6, 7, 8).contains(date.getMonthValue()), Interval.empty);
		OpeningHoursRule summerClosedRule =  new OpeningHoursRule(date -> DateInterval.of(LocalDate.of(2017,7,10), LocalDate.of(2017,7,15)).contains(date), Interval.empty);
		
		List<OpeningHoursRule> openingHoursRules = Arrays.asList(tenToSixRule, saturdayRule, sundayClosedRule, summerSaturdayClosedRule, summerClosedRule);
		
		OpeningHoursCalendar openingHoursCalendar = OpeningHoursCalendar.create(wholeYear, openingHoursRules);
		
		System.out.println(openingHoursCalendar);
	}
	
}
