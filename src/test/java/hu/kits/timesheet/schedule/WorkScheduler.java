package hu.kits.timesheet.schedule;

import java.util.Arrays;

public class WorkScheduler {

	private static final int workers = 3;
	private static final int workingHours = 40;
	private static final int dayliWorkingHours = 9;
	private static final int minCoverage = 2;
	
	public static void main(String args[]) {
		
		WorkerSchedule worker1Schedule = new WorkerSchedule(new Worker("1"), 11, 18);
		WorkerSchedule worker2Schedule = new WorkerSchedule(new Worker("2"), 14, 19);
		WorkerSchedule worker3Schedule = new WorkerSchedule(new Worker("3"), 10, 15);
		
		WeeklySchedule weeklySchedule = new WeeklySchedule(Arrays.asList(
				new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)),
				new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)),
				new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)),
				new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)),
				new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule))));
		
		System.out.println(weeklySchedule);
	}
	
}
