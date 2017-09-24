package hu.kits.timesheet.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import hu.kits.timesheet.domain.common.Interval;
import hu.kits.timesheet.domain.common.Rand;
import hu.kits.timesheet.util.Stat;

public class WorkScheduler {

	private static Rand rand = new Rand();
	
	public static void main(String args[]) {
		
		WeeklySchedule scheduleX = createWeeklySchedule();
		System.out.println(scheduleX);
		System.out.println(scoreSchedule(scheduleX));
		
		int n = 1_000_000_000;
		double minScore = Double.MAX_VALUE;
		List<WeeklySchedule> bestSchedules = new ArrayList<>();
		boolean changed = false;
		for(int i=1;i<=n;i++) {
			try {
				WeeklySchedule schedule = createRandomSchedule();
				double score = scoreSchedule(schedule);
				if(score < minScore) {
					minScore = score;
					bestSchedules = new ArrayList<>();
					bestSchedules.add(schedule); 
					changed = true;
				} else if(score == minScore) {
					bestSchedules.add(schedule);
					changed = true;
				} 
				if(i % 500_000 == 0) {
					if(changed) {
						System.out.println(bestSchedules.size() + " best schedules so far after " + i + " tries:\n" + bestSchedules);
						System.out.println("Score: " + minScore);
						changed = false;
					} else {
						System.out.println("No change. " + i + " tries");
					}
				}
				
			} catch(IllegalStateException ex) {
				ex.printStackTrace();
			}
			
		}
		System.out.println("DONE");
	}
	
	private static WeeklySchedule createRandomSchedule() {
		
		Worker worker1 = new Worker("1");
		Worker worker2 = new Worker("2");
		Worker worker3 = new Worker("3");
		
		for(Day day : Day.values()) {
			randomWork(worker1, day);
			randomWork(worker2, day);
			randomWork(worker3, day);
		}
		
		return new WeeklySchedule(Arrays.asList(worker1, worker2, worker3));
	}
	
	private static void randomWork(Worker worker, Day day) {
		
		switch(day) {
			case MON:
			case TUE:
			case WED:
			case THU: {
				Interval workHours = rand.generateRandomSubInterval(day.workHours, 4);
				worker.work(day, workHours);
				break;
			} 
			case FRI: {
				int hoursLeftToWork = Math.min(Worker.WEEKLY_HOURS - worker.hoursWorked(), day.workHours.length());
				//if(hoursLeftToWork > day.workHours.length()) {
				//	throw new IllegalStateException();
				//}
				Interval workHours = rand.generateRandomSubIntervalWithLength(day.workHours, hoursLeftToWork);
				worker.work(day, workHours);
				break;
			}
			default: throw new IllegalArgumentException();
		}
	}
		
	private static double scoreSchedule(WeeklySchedule schedule) {
		double penalty = 0.0;
		penalty += Math.pow(schedule.workers().stream().mapToDouble(worker -> 2 * Math.abs(worker.hoursWorked() - Worker.WEEKLY_HOURS)).sum(), 3);
		
		for(Day day : Day.values()) {
			DailySchedule dailySchedule = schedule.dailySchedule(day);
			for(int hour = day.workHours.from;hour <= day.workHours.to;hour++) {
				int coverage = dailySchedule.numberOfWorkersAt(hour);
				if(coverage == 0) {
					penalty += 100;
				} else if(coverage == 1) {
					penalty += 10;
				} else if(coverage == 3) {
					penalty += 1;
				}
			}
			penalty += 3 * Stat.stdev(dailySchedule.workerWorkHours());
		}
		
		for(int hour=4;hour<=9;hour++) {
			final int h = hour;
			List<Integer> frequencies = schedule.workers().stream().map(worker -> worker.workHourFrequency().count(h)).collect(Collectors.toList());
			penalty += 5 * Stat.stdev(frequencies);
		}
		
		List<Integer> startsAt10Counts = schedule.workers().stream().map(Worker::startsAt10Counter).collect(Collectors.toList());
		penalty += 5 * Stat.stdev(startsAt10Counts);
		
		List<Integer> leavesAtLateCounts = schedule.workers().stream().map(Worker::leavesAtLateCounter).collect(Collectors.toList());
		penalty += 3 * Stat.stdev(leavesAtLateCounts);
		
		return penalty;
	}
	
	private static WeeklySchedule createWeeklySchedule() {
		
		Worker worker1 = new Worker("1");
		Worker worker2 = new Worker("2");
		Worker worker3 = new Worker("3");
		
		worker1.work(Day.MON, Interval.of(10, 16));
		worker2.work(Day.MON, Interval.of(10, 17));
		worker3.work(Day.MON, Interval.of(10, 17));
		
		worker1.work(Day.TUE, Interval.of(10, 17));
		worker2.work(Day.TUE, Interval.of(10, 16));
		worker3.work(Day.TUE, Interval.of(10, 17));
		
		worker1.work(Day.WED, Interval.of(10, 17));
		worker2.work(Day.WED, Interval.of(10, 17));
		worker3.work(Day.WED, Interval.of(10, 16));
		
		worker1.work(Day.THU, Interval.of(10, 18));
		worker2.work(Day.THU, Interval.of(10, 18));
		worker3.work(Day.THU, Interval.of(10, 17));
		
		worker1.work(Day.FRI, Interval.of(10, 17));
		worker2.work(Day.FRI, Interval.of(10, 17));
		worker3.work(Day.FRI, Interval.of(10, 18));
		
		
		return new WeeklySchedule(Arrays.asList(worker1, worker2, worker3));
	}
	
}

