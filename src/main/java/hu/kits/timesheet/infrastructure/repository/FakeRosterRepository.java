package hu.kits.timesheet.infrastructure.repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.common.Rand;
import hu.kits.timesheet.domain.roster.DailyRoster;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;
import hu.kits.timesheet.domain.roster.RosterRepository;

public class FakeRosterRepository implements RosterRepository {

	private OpeningHoursCalendar openingHoursCalendar;
	
	public FakeRosterRepository() {
		openingHoursCalendar = OpeningHoursCalendar.create(DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 7)), Collections.emptyList());
	}
	
	@Override
	public Roster loadRoster() {
		
		List<Employee> employees = Arrays.asList(new Employee("Gabi"), new Employee("Ági"), new Employee("Brigi"));
		
		SortedMap<LocalDate, DailyRoster> dailyRosters = new TreeMap<>();
		
		for(LocalDate date=openingHoursCalendar.interval().from;!date.isAfter(openingHoursCalendar.interval().to);date = date.plusDays(1)) {
			dailyRosters.put(date, new DailyRoster(date, generate(date, employees)));
		}
		
		//openingHoursCalendar.interval().stream()
		//		.forEach(date -> dailyRosters.put(date, new DailyRoster(date, generate(date, employees))));
		
		return new Roster(openingHoursCalendar, dailyRosters);
	}
	
	private Map<Employee, Interval> generate(LocalDate date, List<Employee> employees) {
		
		Interval workingHours = openingHoursCalendar.openingHoursAt(date);
		
		return employees.stream().collect(Collectors.toMap(e -> e, e -> new Rand().generateRandomSubInterval(workingHours, 5)));
	}

}
