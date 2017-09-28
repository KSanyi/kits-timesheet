package hu.kits.timesheet.domain.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class DateIntervalTest {

	private DateInterval interval = DateInterval.of(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31));
	
	@Test
	public void containment() {
		
		Assert.assertFalse(interval.contains(LocalDate.of(2016,12,13)));
		Assert.assertTrue(interval.contains(LocalDate.of(2017,1,2)));
		Assert.assertTrue(interval.contains(LocalDate.of(2017,1,31)));
		Assert.assertFalse(interval.contains(LocalDate.of(2017,2,1)));
	}
	
	@Test
	public void basic() {
		
		Assert.assertEquals(DateInterval.of(LocalDate.of(2017,1,1), LocalDate.of(2017,1,31)), interval);
		
		List<LocalDate> days = interval.stream().collect(Collectors.toList());
		Assert.assertEquals(31, days.size());
	}
	
	@Test
	public void iteration() {

		List<LocalDate> days = new ArrayList<>();
		for(LocalDate day : interval) {
			days.add(day);
		}
		
		Assert.assertEquals(31, days.size());
		Assert.assertEquals(LocalDate.of(2017,1,1), days.get(0));
		Assert.assertEquals(LocalDate.of(2017,1,31), days.get(30));
	}
	
}
