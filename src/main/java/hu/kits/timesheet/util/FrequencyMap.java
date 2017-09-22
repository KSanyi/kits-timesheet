package hu.kits.timesheet.util;

import java.util.HashMap;

public class FrequencyMap<T> {
	
	private HashMap<T, Integer> map = new HashMap<>();
	
	public void add(T item, int count) {
		int c = map.getOrDefault(item, 0);
		map.put(item, c + count);
	}
	
	public void put(T item) {
		add(item, 1);
	}
	
	public int count(T item) {
		return map.getOrDefault(item, 0);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
}