package app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapLooper {

	/*
	    --> Identify 6 ways to iterate through a map these days:
  		    https://www.codingame.com/playgrounds/6162/6-ways-to-iterate-or-loop-a-map-in-java
  		    
  		    BASICALLY use:
  		    ==> forEach on map or stream
  		    ==> old fashioned loop entries or keys, via keySet() or entrySet()
  		    ==> iterator over the key set or entry set
	 */
	
	private Map<Integer, String> customers;
	
	public MapLooper() {
		customers = new HashMap<>();
		customers.put(1, "Jhon");
		customers.put(2, "Smith");
		customers.put(3, "Sally");
		
		System.out.println("\nExploring different ways to loop through a map.......");
		useForEachOnMapItself();
		useForEachOnStream();
		oldFashionedLoopThroughEntrySet();	
		oldFashionedLoopThroughKeySet();
		oldFashionedIterator();
	}
		
	// CHOICE 1: preferred
	private void useForEachOnMapItself() {
		System.out.println("==> Using foreach directly on the map:");
		customers.forEach((id, name) -> {
			System.out.println("        Key : " + id + " value : " + name);
		});		
	}
	
	// CHOICE 2: slightly more cumbersome in that get methods are needed
	private void useForEachOnStream() {
		System.out.println("==> Using foreach on map istself:");
		customers.entrySet().stream().forEach(e ->
			System.out.println("        Key : " + e.getKey() + " value : " + e.getValue()));
	}
	

	private void oldFashionedLoopThroughEntrySet() {
		System.out.println("==> Using EntrySet to loop through each key/value pair:");
		for (Map.Entry<Integer, String> entry : customers.entrySet()) {
		    System.out.println("        Key : " + entry.getKey() + " value : " + entry.getValue());
		}		
	}
	
	
	private void oldFashionedLoopThroughKeySet() {
		System.out.println("==> Using KeySet and the map itself to get the values:");
		for (Integer key : customers.keySet()) {
		    System.out.println("        Key : " + key + " value : " + customers.get(key));
		}
	}
	
	
	private void oldFashionedIterator() {
		System.out.println("==> Using Iterator to loop through each key/value pair via entrySet():");
		Iterator<Map.Entry<Integer, String>> iterator = customers.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = iterator.next();
			System.out.println("        Key : " + entry.getKey() + " value : " + entry.getValue());
		}
		
		
		System.out.println("==> Using Iterator to loop through each key/value pair via keySet():");
		Iterator<Integer> iteratorInt = customers.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = iteratorInt.next();
			System.out.println("        Key : " + key + " value : " + customers.get(key));
		}		
	}
	
	
}
