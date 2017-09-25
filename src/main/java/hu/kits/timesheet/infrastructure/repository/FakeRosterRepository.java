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
	
	private Roster roster;
	
	public FakeRosterRepository() {
		openingHoursCalendar = OpeningHoursCalendar.create(DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 12, 31)), Collections.emptyList());
		
		SortedMap<LocalDate, DailyRoster> dailyRosters = new TreeMap<>();
		
		List<Employee> employees = Arrays.asList(new Employee("Gabi"), new Employee("Ági"), new Employee("Brigi"));
		for(LocalDate date=openingHoursCalendar.interval().from;!date.isAfter(openingHoursCalendar.interval().to);date = date.plusDays(1)) {
			dailyRosters.put(date, new DailyRoster(date, generate(date, employees)));
		}
		
		roster = new Roster(openingHoursCalendar, dailyRosters);
	}
	
	@Override
	public Roster loadRoster() {
		
		//openingHoursCalendar.interval().stream()
		//		.forEach(date -> dailyRosters.put(date, new DailyRoster(date, generate(date, employees))));
		
		return roster;
	}
	
	private Map<Employee, Interval> generate(LocalDate date, List<Employee> employees) {
		
		Interval workingHours = openingHoursCalendar.openingHoursAt(date);
		
		return employees.stream().collect(Collectors.toMap(e -> e, e -> new Rand().generateRandomSubInterval(workingHours, 5)));
	}

}
