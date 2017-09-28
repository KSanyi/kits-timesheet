package hu.kits.timesheet.domain.roster.generator.geneticalgorithm;

import java.util.List;
import java.util.Random;

import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;

public class RosterGene {

	private final Random random = new Random();
	
	public void mutate() {
		
	}
	
	public RosterGene doCrossOver(RosterGene mate) {
		
		return null;
		
	}

	public int fitness() {
		return 0;
	}
	
	public static RosterGene createRandom(OpeningHoursCalendar calendar, List<Employee> employees) {
		return null;
	}
	
	public Roster toToRoster() {
		return null;
	}
	
}
