package hu.kits.timesheet.domain.roster.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.common.Rand;
import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;

public class RandomRosterGenerator implements RosterGenerator {

	private Random random = new Random();
	private Rand rand = new Rand();
	
	@Override
	public Roster generateRoster(OpeningHoursCalendar calendar, List<Employee> employees) {
		
		MutableRoster roster = new MutableRoster();
		
		for(Employee employee : employees) {
			for(LocalDate date : calendar.interval()) {
				roster.set(employee, date, calendar.openingHoursAt(date));
			}
			
			for(DateInterval week : calendar.weeks()) {
				
				int workDays = (int)week.stream().filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY).count();
				int workHours = workDays * 8;
				
				int plusWorkHours = calendar.sumOpeningHours(week) - workHours;
				
				while(plusWorkHours > 0) {
					LocalDate day = randomDay(week);
					Interval workHoursForDay = roster.get(employee, day);
					if(workHoursForDay.length() > 0) {
						int decrease = rand.generateRandomNumber(0, Math.min(plusWorkHours, workHoursForDay.length()));
						plusWorkHours -= decrease;
						int newWorkHours = workHoursForDay.length() - decrease;
						roster.set(employee, day, rand.generateRandomSubIntervalWithLength(calendar.openingHoursAt(day), newWorkHours));
					}
				}
			}
			
		}
		
		return roster.toRoster(calendar);
	}
	
	private LocalDate randomDay(DateInterval week) {
		
		return week.getDay(random.nextInt(week.length()));
	}
	
}
