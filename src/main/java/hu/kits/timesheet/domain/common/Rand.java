package hu.kits.timesheet.domain.common;

import java.util.Random;

public class Rand {

	private final Random random = new Random();
	
	public Interval generateRandomSubInterval(Interval interval, int minLength) {
		
		int length = generateRandomNumber(minLength, interval.length());
		int from = generateRandomNumber(interval.from, interval.to - length + 1);
		
		return new Interval(from, from + length - 1);
	}
	
	public Interval generateRandomSubIntervalWithLength(Interval interval, int length) {
		
		int from = generateRandomNumber(interval.from, interval.to - length + 1);
		return new Interval(from, from + length - 1);
	}
	
	public int generateRandomNumber(int min, int max) {
		if(min > max) throw new IllegalArgumentException(min + " > " + max);
		if(min == max) return min;
		return random.nextInt(max - min + 1) + min;
	}
	
}
