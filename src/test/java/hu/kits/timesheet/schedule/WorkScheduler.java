package hu.kits.timesheet.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorkScheduler {

	private static Random random = new Random();
	
	public static void main(String args[]) {
		
		int n = 100_000_000;
		for(int i=0;i<n;i++) {
			WeeklySchedule schedule = createSchedule();
			//System.out.println(schedule);
			if(testSchedule(schedule)) {
				System.out.println(schedule);
				return;
			} else {
				if(i % 100_000 == 0) System.out.println((double)i / n);
			}
		}
		
		//System.out.println("NO");
		
	}
	
	private static WeeklySchedule createSchedule() {
		
		Worker worker1 = new Worker("1");
		Worker worker2 = new Worker("2");
		Worker worker3 = new Worker("3");
		
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for(int i=1;i<=5;i++) {
			WorkerSchedule worker1Schedule = randomWorkerSchedule(worker1, i);
			WorkerSchedule worker2Schedule = randomWorkerSchedule(worker2, i);
			WorkerSchedule worker3Schedule = randomWorkerSchedule(worker3, i);
			dailySchedules.add(new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)));
		}
		
		return new WeeklySchedule(dailySchedules);
	}
	
	private static WorkerSchedule randomWorkerSchedule(Worker worker, int day) {
		if(day == 5 && (40 - worker.hoursWorked) <= 9) {
			int hours = 40 - worker.hoursWorked;
			//int from = random.nextInt(4) + 10;
			return new WorkerSchedule(worker, 10, 10 + hours - 1);
		}
		int from = random.nextInt(4) + 10;
		int maxHours = 19 - from;
		int hours = generateRandomHours(4, maxHours);
		worker.work(hours);
		return new WorkerSchedule(worker, from, from + hours - 1);
	}
	
	private static int generateRandomHours(int min, int max) {
		if(min > max) return 0;
		if(min == max) return min;
		return random.nextInt(max - min) + min + 1;
	}
	
	private static boolean testSchedule(WeeklySchedule schedule) {
		
		if(Arrays.asList("1", "2", "3").stream().anyMatch(worker -> schedule.hoursWorked(worker) != 40)) {
			return false;
		}
		
		if(schedule.days.stream()
				.anyMatch(day -> Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18).stream()
						.anyMatch(hour -> day.numberOfWorkersAt(hour) < 2))){
			return false;
		}
		
		return true;
		
	}
	
}

