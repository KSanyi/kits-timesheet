package hu.kits.timesheet.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.util.FrequencyMap;

public class WeeklySchedule {

	private final List<Worker> workers;

	public WeeklySchedule(List<Worker> workers) {
		this.workers = workers;
	}

	public DailySchedule dailySchedule(Day day) {
		return new DailySchedule(day, workers);
	}
	
	List<Worker> workers() {
		return workers;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		
		for(Day day : Day.values()) {
			DailySchedule dailySchedule = dailySchedule(day);
			sb.append(day).append("\n");
			sb.append(dailySchedule).append("\n");
		}
		sb.append(workers().stream().map(worker -> worker + " worked " + worker.hoursWorked() + " hours").collect(Collectors.joining("\n"))).append("\n");
		sb.append(workers().stream().map(worker -> worker + " workhour frequency: " + worker.workHourFrequency()).collect(Collectors.joining("\n"))).append("\n");
		sb.append(workers().stream().map(worker -> worker + " start at 10AM " + worker.startsAt10Counter() + " times").collect(Collectors.joining("\n"))).append("\n");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof WeeklySchedule)) return false;
		return ((WeeklySchedule)other).workers.equals(workers);
	}
	
	@Override
	public int hashCode() {
		return workers.hashCode();
	}
	
}

class DailySchedule {
	
	public final Day day;
	
	public final List<Worker> workers;

	public DailySchedule(Day day, List<Worker> workers) {
		this.day = day;
		this.workers = workers;
	}
	
	int numberOfWorkersAt(int hour) {
		return (int)workers.stream().filter(schedule -> schedule.workAt(day, hour)).count();
	}
	
	List<Integer> workerWorkHours() {
		return workers.stream().map(worker -> worker.hoursWorked(day)).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("    9 10 11 12 13 14 15 16 17 18\n");
		for(Worker worker : workers) {
			sb.append(worker.name + ":  ");
			int counter = 0;
			for(int hour=9;hour<=18;hour++) {
				if(worker.workAt(day, hour)) {
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
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof DailySchedule)) return false;
		DailySchedule otherSchedule = (DailySchedule)other;
		return otherSchedule.day == day && otherSchedule.workers.equals(workers);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(day, workers);
	}
	
}

class Worker {
	
	final static int WEEKLY_HOURS = 40;
	
	final String name;
	
	private Map<Day, Interval> schedule = new HashMap<>();
	
	public Worker(String name) {
		this.name = name;
	}

	void work(Day day, Interval workHours) {
		schedule.put(day, workHours);
		//if(workHours.length() > WEEKLY_HOURS) {
		//	throw new IllegalStateException("Too many work hours");
		//}
	}
	
	public boolean workAt(Day day, int hour) {
		return schedule.get(day).contains(hour);
	}
	
	public int hoursWorked() {
		return schedule.values().stream().mapToInt(Interval::length).sum();
	}
	
	public int hoursWorked(Day day) {
		return schedule.get(day).length();
	}
	
	public FrequencyMap<Integer> workHourFrequency() {
		FrequencyMap<Integer> frequencyMap = new FrequencyMap<>();
		schedule.values().stream().forEach(interval -> frequencyMap.put(interval.length()));
		return frequencyMap;
	}
	
	public int startsAt10Counter() {
		return (int)schedule.values().stream().filter(interval -> interval.from == 10).count();
	}
	
	public int leavesAtLateCounter() {
		return (int)schedule.values().stream().filter(interval -> interval.to == 18).count();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof Interval)) return false;
		return ((Worker)other).name.equals(name);
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

enum Day {
	MON(Interval.of(9, 18)),
	TUE(Interval.of(9, 18)),
	WED(Interval.of(9, 18)),
	THU(Interval.of(9, 18)),
	FRI(Interval.of(9, 18)),
	SAT(Interval.of(10, 14));
	
	final Interval workHours;

	private Day(Interval workHours) {
		this.workHours = workHours;
	}
}

