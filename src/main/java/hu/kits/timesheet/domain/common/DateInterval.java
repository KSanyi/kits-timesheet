package hu.kits.timesheet.domain.common;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DateInterval implements Iterable<LocalDate> {
	
	public static DateInterval of(LocalDate from, LocalDate to) {
		if(!to.isBefore(from)) {
			return new DateInterval(from, to);
		} else {
			throw new IllegalArgumentException("DateInterval: " + from + " - " + to);
		}
		
	}
	
	public final LocalDate from;
	
	public final LocalDate to;

	private DateInterval(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean contains(LocalDate date) {
		return !from.isAfter(date) && !date.isAfter(to);
	}
	
	public int length() {
		return (int)(to.toEpochDay() - from.toEpochDay() + 1);
	}
	
	@Override
	public String toString() {
		if(this == empty) {
			return "[]";
		} else {
			return "[" + from + " - " + to + "]";
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null || !(other instanceof DateInterval)) return false;
		DateInterval otherInterval = (DateInterval)other;
		return otherInterval.from.equals(from) && otherInterval.to.equals(to);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}

	public static final DateInterval empty = new DateInterval(LocalDate.of(1970,1,2), LocalDate.of(1970,1,1));

	@Override
	public Iterator<LocalDate> iterator() {
		return new Iterator<LocalDate>(){

			private LocalDate date = from;
			
			@Override
			public boolean hasNext() {
				return !date.isAfter(to);
			}

			@Override
			public LocalDate next() {
				LocalDate currentDate = date;
				date = date.plusDays(1);
				return currentDate;
			}
		};
	}
	
	public Stream<LocalDate> stream() {
		Iterable<LocalDate> iterable = () -> iterator();
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public LocalDate getDay(int index) {
		return from.plusDays(index);
	}
	
}
