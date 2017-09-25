package hu.kits.timesheet.domain.roster;

import java.time.LocalDate;
import java.util.Collections;

import hu.kits.timesheet.domain.common.DateInterval;

public class TestMain {

	public static void main(String[] args) {
		
		OpeningHoursCalendar openingHoursCalendar = OpeningHoursCalendar.create(DateInterval.of(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 20)), Collections.emptyList());

		System.out.println(openingHoursCalendar);
		
	}

}
