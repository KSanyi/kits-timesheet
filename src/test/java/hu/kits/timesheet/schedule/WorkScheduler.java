package hu.bankmonitor.schedule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class WorkScheduler {

	private static Random random = new Random();
	
	public static void main(String args[]) {
		
		/*
		List<Integer> numbers = new ArrayList<>();
		for(int i=0;i<100;i++) {
			Interval interval = generateRandomSubInterval(new Interval(10, 17), 4);
			numbers.add(interval.length());
			System.out.println(generateRandomSubInterval(new Interval(10, 17), 4));
		}
		Collections.sort(numbers);
		System.out.println(numbers);
		*/
		
		int n = 1_000_000_000;
		double minScore = Double.MAX_VALUE;
		WeeklySchedule bestWeeklySchedule = null;
		Set<WeeklySchedule> schedules = new HashSet<>();
		for(int i=0;i<n;i++) {
			try {
				WeeklySchedule schedule = createSchedule();
				double score = scoreSchedule(schedule);
				if(score < minScore) {
					minScore = score;
					bestWeeklySchedule = schedule;
				}
				//System.out.println(schedule);
				/*
				if(testSchedule(schedule)) {
					schedules.add(schedule);
					System.out.println(schedules.size());
					//return;
				} else {
					if(i % 1_000_000 == 0) System.out.println(i * 100.0 / n + "%");
				}
				*/
				if(i % 1_000_000 == 0) {
					System.out.println(minScore);
					System.out.println(bestWeeklySchedule);
				}
				
			} catch(IllegalStateException ex) {
				ex.printStackTrace();
			}
			
		}
		
		//System.out.println("NO");
	}
	
	private static WeeklySchedule createSchedule() {
		
		Worker worker1 = new Worker("1");
		Worker worker2 = new Worker("2");
		Worker worker3 = new Worker("3");
		
		Map<Day, DailySchedule> dailySchedules = new LinkedHashMap<>();
		for(Day day : Day.values()) {
			WorkerSchedule worker1Schedule = randomWorkerSchedule(worker1, day);
			WorkerSchedule worker2Schedule = randomWorkerSchedule(worker2, day);
			WorkerSchedule worker3Schedule = randomWorkerSchedule(worker3, day);
			dailySchedules.put(day, new DailySchedule(Arrays.asList(worker1Schedule, worker2Schedule, worker3Schedule)));
		}
		
		return new WeeklySchedule(dailySchedules);
	}
	
	private static WorkerSchedule randomWorkerSchedule(Worker worker, Day day) {
		
		switch(day) {
			case MON:
			case TUE:
			case WEB:
			case THU: {
				Interval workHours = generateRandomSubInterval(day.workHours, 4);
				worker.work(workHours);
				return new WorkerSchedule(worker, workHours);
			} 
			case FRI: {
				int hoursLeftToWork = Math.min(Worker.WEEKLY_HOURS - worker.hoursWorked, day.workHours.length());
				//if(hoursLeftToWork > day.workHours.length()) {
				//	throw new IllegalStateException();
				//}
				Interval workHours = generateRandomSubIntervalWithLength(day.workHours, hoursLeftToWork);
				worker.work(workHours);
				return new WorkerSchedule(worker, workHours);
			}
			default: throw new IllegalArgumentException();
		}
	}
	
	private static Interval generateRandomSubInterval(Interval interval, int minLength) {
		
		int length = generateRandomHour(minLength, interval.length());
		int from = generateRandomHour(interval.from, interval.to - length + 1);
		
		//int from = generateRandomHour(interval.from, interval.to - minLength);
		//int to = generateRandomHour(from + 4 - 1, interval.to);
		return new Interval(from, from + length - 1);
	}
	
	private static Interval generateRandomSubIntervalWithLength(Interval interval, int length) {
		
		int from = generateRandomHour(interval.from, interval.to - length + 1);
		return new Interval(from, from + length - 1);
	}
	
	private static int generateRandomHour(int min, int max) {
		if(min > max) throw new IllegalArgumentException(min + " > " + max);
		if(min == max) return min;
		return random.nextInt(max - min + 1) + min;
	}
	
	private static boolean testSchedule(WeeklySchedule schedule) {
		
		if(Arrays.asList("1", "2", "3").stream().anyMatch(worker -> schedule.hoursWorked(worker) != 40)) {
			return false;
		}
		
		if(Stream.of(Day.values())
				.anyMatch(day -> day.workHours.stream()
						.anyMatch(hour -> schedule.dailySchedule(day).numberOfWorkersAt(hour) < 2))){
			return false;
		}
		
		return true;
	}
	
	private static double scoreSchedule(WeeklySchedule schedule) {
		double penalty = 0.0;
		penalty += Math.pow(Arrays.asList("1", "2", "3").stream().mapToDouble(worker -> 2 * Math.abs(schedule.hoursWorked(worker) - Worker.WEEKLY_HOURS)).sum(), 3);
		
		for(Day day : Day.values()) {
			DailySchedule dailySchedule = schedule.dailySchedule(day);
			for(int hour = day.workHours.from;hour <= day.workHours.to;hour++) {
				int coverage = dailySchedule.numberOfWorkersAt(hour);
				if(coverage == 0) {
					penalty += 100;
				} else if(coverage == 1) {
					penalty += 1;
				} else if(coverage == 3) {
					penalty += 1;
				}
			}
		}
		
		return penalty;
	}
	
}

