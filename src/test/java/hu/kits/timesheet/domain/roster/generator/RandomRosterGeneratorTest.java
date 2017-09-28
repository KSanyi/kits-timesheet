package hu.kits.timesheet.domain.roster.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar.OpeningHoursRule;
import hu.kits.timesheet.domain.roster.Roster;

public class RandomRosterGeneratorTest {

	private OpeningHoursCalendar calendar;
	
	public RandomRosterGeneratorTest() {
		OpeningHoursRule tenToSixRule = new OpeningHoursRule(date -> true, Interval.of(10, 18));
		OpeningHoursRule saturdayRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY, Interval.of(10, 14));
		OpeningHoursRule sundayClosedRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY, Interval.empty);
		
		List<OpeningHoursRule> openingHoursRules = Arrays.asList(tenToSixRule, saturdayRule, sundayClosedRule);
		
		calendar = OpeningHoursCalendar.create(DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)), openingHoursRules);
	}
	
	@Test
	public void test() {
		
		Roster roster = new RandomRosterGenerator().generateRoster(calendar, Arrays.asList(new Employee("Gabi")));
		System.out.println(roster);
		
	}
	
}
