package hu.kits.timesheet.util;

import java.util.LinkedHashMap;

public class Table<K1, K2, V> {

	private final LinkedHashMap<K1, LinkedHashMap<K2, V>> map;

	public Table() {
		this.map = new LinkedHashMap<>();
	}
	
	public V get(K1 k1, K2 k2) {
		return map.getOrDefault(k1, new LinkedHashMap<>()).get(k2);
	}
	
	public V getOrDefault(K1 k1, K2 k2, V v) {
		return map.getOrDefault(k1, new LinkedHashMap<>()).getOrDefault(k2, v);
	}
	
	public void set(K1 k1, K2 k2, V v) {
		LinkedHashMap<K2, V> map2 = map.getOrDefault(k1, new LinkedHashMap<>());
		map2.put(k2, v);
		map.put(k1, map2);
	}
	
	public LinkedHashMap<K2, V> getRow(K1 k1) {
		return map.getOrDefault(k1, new LinkedHashMap<>());
	}
	
	public LinkedHashMap<K1, V> getColumn(K2 k2) {
		
		LinkedHashMap<K1, V> columns = new LinkedHashMap<>();
		
		map.forEach((k1, v) -> { 
			if(v.containsKey(k2)) columns.put(k1, v.get(k2));
		});
		
		return columns;
	}

}
