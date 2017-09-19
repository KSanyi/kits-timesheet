package hu.bankmonitor.schedule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WeeklySchedule {

	private final Map<Day, DailySchedule> dailySchedule;

	public WeeklySchedule(Map<Day, DailySchedule> dailySchedule) {
		this.dailySchedule = dailySchedule;
	}

	public DailySchedule dailySchedule(Day day) {
		return dailySchedule.get(day);
	}
	
	int hoursWorked(String workerName) {
		return dailySchedule.values().stream().mapToInt(day -> day.hoursWorked(workerName)).sum();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		
		for(Day day : dailySchedule.keySet()) {
			sb.append(day).append("\n");
			sb.append(dailySchedule.get(day)).append("\n");
		}
		sb.append(Arrays.asList("1", "2", "3").stream().map(worker -> worker + ": " + hoursWorked(worker)).collect(Collectors.joining(", ")));
		return sb.append("\n").toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof WeeklySchedule)) return false;
		return ((WeeklySchedule)other).dailySchedule.equals(dailySchedule);
	}
	
	@Override
	public int hashCode() {
		return dailySchedule.hashCode();
	}
	
}

class DailySchedule {
	
	List<WorkerSchedule> schedules;

	public DailySchedule(List<WorkerSchedule> schedules) {
		this.schedules = schedules;
	}
	
	int hoursWorked(String workerName) {
		return schedules.stream().filter(schedule -> schedule.worker.name.equals(workerName)).mapToInt(WorkerSchedule::length).sum();
	}
	
	int numberOfWorkersAt(int hour) {
		return (int)schedules.stream().filter(schedule -> schedule.workAt(hour)).count();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("   10 11 12 13 14 15 16 17 18\n");
		for(WorkerSchedule schedule : schedules) {
			sb.append(schedule.worker.name + ":  ");
			int counter = 0;
			for(int hour=10;hour<=18;hour++) {
				if(schedule.workAt(hour)) {
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
		return ((DailySchedule)other).schedules.equals(schedules);
	}
	
	@Override
	public int hashCode() {
		return schedules.hashCode();
	}
	
}

class WorkerSchedule {
	
	final Worker worker;
	
	final Interval workHours;
	
	int length() {
		return workHours.length();
	}

	public WorkerSchedule(Worker worker, Interval workHours) {
		this.worker = worker;
		this.workHours = workHours;
	}

	public boolean workAt(int hour) {
		return workHours.contains(hour);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof WorkerSchedule)) return false;
		WorkerSchedule otherSchedule = (WorkerSchedule)other;
		return otherSchedule.worker.equals(worker) && otherSchedule.workHours.equals(workHours);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(worker, workHours);
	}
	
}

class Worker {
	
	final static int WEEKLY_HOURS = 40;
	
	final String name;
	
	int hoursWorked = 0;
	
	public Worker(String name) {
		this.name = name;
	}

	void work(Interval workHours) {
		hoursWorked += workHours.length();
		if(hoursWorked > WEEKLY_HOURS) {
			throw new IllegalStateException("Too many work hours");
		}
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
	
}

enum Day {
	MON(Interval.of(10, 17)),
	TUE(Interval.of(10, 17)),
	WEB(Interval.of(10, 17)),
	THU(Interval.of(10, 18)),
	FRI(Interval.of(10, 18));
	
	final Interval workHours;

	private Day(Interval workHours) {
		this.workHours = workHours;
	}
}

class Interval {
	
	static Interval of(int from, int to) {
		return new Interval(from, to);
	}
	
	final int from;
	
	final int to;

	public Interval(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	boolean contains(int hour) {
		return from <= hour && hour <= to;
	}
	
	int length() {
		return to - from + 1;
	}
	
	IntStream stream() {
		return IntStream.range(from, to+1);
	}
	
	@Override
	public String toString() {
		return "[" + from + "-" + to + "]";
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof Interval)) return false;
		Interval otherInterval = (Interval)other;
		return otherInterval.from == from && otherInterval.to == to;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
