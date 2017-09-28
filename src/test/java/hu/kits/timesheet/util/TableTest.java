package hu.kits.timesheet.util;

import org.junit.Assert;
import org.junit.Test;

public class TableTest {

	@Test
	public void test() {
		
		Table<Integer, Integer, String> table = new Table<>();
		
		table.set(1, 1, "one-one");
		table.set(1, 2, "one-two");
		table.set(2, 1, "two-one");
		table.set(2, 2, "two-two");
		table.set(3, 1, "three-one");
		table.set(3, 2, "three-two");
		
		Assert.assertEquals("{1=one-one, 2=two-one, 3=three-one}", table.getColumn(1).toString());
		
		Assert.assertEquals("{1=one-one, 2=one-two}", table.getRow(1).toString());
		
		Assert.assertEquals("one-two", table.get(1, 2));
		Assert.assertEquals("one-two", table.getOrDefault(1, 2, "xxx"));
		Assert.assertEquals("xxx", table.getOrDefault(5, 5, "xxx"));
	}
	
}
