package hu.kits.timesheet.schedule;

import java.util.List;

public class WeeklySchedule {

	public final List<DailySchedule> days;

	public WeeklySchedule(List<DailySchedule> days) {
		this.days = days;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		for(int i=1;i<=5;i++) {
			sb.append("day " + i + "\n");
			sb.append(days.get(i-1));
			sb.append("\n");
		}
		return sb.toString();
	}
	
}

class DailySchedule {
	
	List<WorkerSchedule> schedules;

	public DailySchedule(List<WorkerSchedule> schedules) {
		this.schedules = schedules;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("   10 11 12 13 14 15 16 17 18 19\n");
		for(WorkerSchedule schedule : schedules) {
			sb.append(schedule.worker.name + ":  ");
			for(int hour=10;hour<=19;hour++) {
				if(schedule.workAt(hour)) {
					sb.append("X  ");
				} else {
					sb.append("   ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}

class WorkerSchedule {
	
	final Worker worker;
	
	final int from;
	
	final int to;

	public WorkerSchedule(Worker worker, int from, int to) {
		this.worker = worker;
		this.from = from;
		this.to = to;
	}

	public boolean workAt(int time) {
		return from <= time && time <= to;
	}
	
}

class Worker {
	
	final String name;
	
	int hoursWorked = 0;
	
	public Worker(String name) {
		this.name = name;
	}

	void work(int hours) {
		hoursWorked += hours;
	}
	
}
