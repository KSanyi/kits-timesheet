package hu.kits.timesheet.domain.roster.generator;

import java.util.List;

import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;

public interface RosterGenerator {

	Roster generateRoster(OpeningHoursCalendar calendar, List<Employee> employees);
	
}
