package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import hu.kits.timesheet.domain.common.DateInterval;
import hu.kits.timesheet.domain.common.Interval;

public class Employee {

	public static final int WEEKLY_WORK_HOURS = 40;
	
	public final String name;
	
	private Map<LocalDate, Interval> works = new HashMap<>();
	
	public Employee(String name) {
		this.name = name;
	}
	
	public void work(LocalDate date, Interval interval) {
		works.put(date, interval);
	}
	
	public int hoursWorked(DateInterval dateInterval) {
		return dateInterval.stream().mapToInt(date -> hoursWorked(date)).sum();
	}
	
	public int hoursWorked(LocalDate date) {
		return works.getOrDefault(date, Interval.empty).length();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof Interval)) return false;
		return ((Employee)other).name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return "Worker " + name;
	}
	
}
