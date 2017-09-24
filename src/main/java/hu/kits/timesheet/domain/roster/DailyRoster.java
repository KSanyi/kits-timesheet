package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import hu.kits.timesheet.domain.common.Interval;

public class DailyRoster {

	public final LocalDate date;
	
	private final Map<Employee, Interval> employeeWorkingHours;

	public DailyRoster(LocalDate date, Map<Employee, Interval> employeeWorkingHours) {
		this.date = date;
		this.employeeWorkingHours = Collections.unmodifiableMap(new TreeMap<>(employeeWorkingHours));
	}
	
	public int coverage(int hour) {
		return (int)employeeWorkingHours.values().stream()
				.filter(interval -> interval.contains(hour))
				.count();
	}
	
	private boolean workAt(Employee employee, int hour) {
		return employeeWorkingHours.getOrDefault(employee, Interval.empty).contains(hour);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(date + " - " + date.getDayOfWeek()).append("\n");
		sb.append("   10 11 12 13 14 15 16 17 18\n");
		for(Employee employee : employeeWorkingHours.keySet()) {
			sb.append(employee + ":  ");
			int counter = 0;
			for(int hour=10;hour<=18;hour++) {
				if(workAt(employee, hour)) {
					counter++;
					sb.append("X  ");
				} else {
					sb.append("   ");
				}
			}
			sb.append(counter + "\n");
		}
		return sb.toString();
	}
	
}
