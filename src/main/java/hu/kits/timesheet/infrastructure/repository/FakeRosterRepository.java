package hu.kits.timesheet.infrastructure.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar.OpeningHoursRule;
import hu.kits.timesheet.domain.roster.Roster;
import hu.kits.timesheet.domain.roster.RosterRepository;
import hu.kits.timesheet.domain.roster.generator.RandomRosterGenerator;

public class FakeRosterRepository implements RosterRepository {

	private OpeningHoursCalendar openingHoursCalendar;
	
	private Roster roster;
	
	public FakeRosterRepository() {
		openingHoursCalendar = createOpeningHoursCalendar();
		
		List<Employee> employees = Arrays.asList(new Employee("Gabi"), new Employee("Ági"), new Employee("Brigi"));
		roster = new RandomRosterGenerator().generateRoster(openingHoursCalendar, employees);
	}
	
	@Override
	public Roster loadRoster() {
		
		//openingHoursCalendar.interval().stream()
		//		.forEach(date -> dailyRosters.put(date, new DailyRoster(date, generate(date, employees))));
		
		return roster;
	}
	
	private OpeningHoursCalendar createOpeningHoursCalendar() {
		
		List<LocalDate> nationalHolidays = Arrays.asList(
				LocalDate.of(2017,1,1),
				LocalDate.of(2017,3,15),
				LocalDate.of(2017,4,14),
				LocalDate.of(2017,4,17), 
				LocalDate.of(2017,5,1),
				LocalDate.of(2017,6,5),
				LocalDate.of(2017,8,20),
				LocalDate.of(2017,10,23),
				LocalDate.of(2017,11,1),
				LocalDate.of(2017,12,24),
				LocalDate.of(2017,12,25),
				LocalDate.of(2017,12,26));
		
		OpeningHoursRule tenToSixRule = new OpeningHoursRule(date -> true, Interval.of(10, 17));
		OpeningHoursRule tenToSevenRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.THURSDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY, Interval.of(10, 18));
		OpeningHoursRule saturdayRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY, Interval.of(10, 14));
		OpeningHoursRule sundayClosedRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY, Interval.empty);
		OpeningHoursRule summerSaturdayClosedRule = new OpeningHoursRule(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY && Arrays.asList(6, 7, 8).contains(date.getMonthValue()), Interval.empty);
		OpeningHoursRule summerClosedRule =  new OpeningHoursRule(date -> DateInterval.of(LocalDate.of(2017,7,10), LocalDate.of(2017,7,15)).contains(date), Interval.empty);
		OpeningHoursRule nationalHolidayRule =  new OpeningHoursRule(date -> nationalHolidays.contains(date), Interval.empty);
		
		
		List<OpeningHoursRule> openingHoursRules = Arrays.asList(tenToSixRule, tenToSevenRule, saturdayRule, sundayClosedRule, summerSaturdayClosedRule, summerClosedRule, nationalHolidayRule);
		
		return OpeningHoursCalendar.create(DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 12, 31)), openingHoursRules);
	}

}
