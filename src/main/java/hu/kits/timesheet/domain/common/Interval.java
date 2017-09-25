package hu.kits.timesheet.domain.common;

import java.util.Objects;
import java.util.stream.IntStream;

public class Interval {
	
	public static Interval of(int from, int to) {
		return new Interval(from, to);
	}
	
	public final int from;
	
	public final int to;

	public Interval(int from, int to) {
		if(from <= to || (from == 0 && to == -1)) {
			this.from = from;
			this.to = to;
		} else {
			throw new IllegalArgumentException("Interval: " + from + " - " + to);
		}
	}
	
	public boolean contains(int hour) {
		return from <= hour && hour <= to;
	}
	
	public int length() {
		return to - from + 1;
	}
	
	public IntStream stream() {
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

	public static final Interval empty = new Interval(0,-1);

	public int cap(int value) {
		return Math.min(Math.max(from, value), to);
	}
}
