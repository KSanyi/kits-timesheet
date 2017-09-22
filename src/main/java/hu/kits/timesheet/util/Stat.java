package hu.kits.timesheet.util;

import java.util.List;

public class Stat {

	public static double stdev(List<Integer> list){
		int n = list.size();
        double sum = list.stream().mapToDouble(v -> v).sum();
        double mean = sum / n;
        
        double sumSquare = list.stream().mapToDouble(v -> Math.pow((double)v - mean, 2)).sum();

        return Math.sqrt(sumSquare / n);
    }
	
}
