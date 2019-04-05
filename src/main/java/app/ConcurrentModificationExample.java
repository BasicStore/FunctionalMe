package app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConcurrentModificationExample {

	public static void myTest() {
		List<String> myList = Arrays.asList("1", "2", "3", "4", "5");

		// Java Collection classes are fail-fast which means that if the Collection is 
		// changed while a single thread is traversing over it using an iterator, or another thread modifies it, 
		// then the iterator.next() will throw a ConcurrentModificationException
//		Iterator<String> it = myList.iterator();
//		while (it.hasNext()) {
//			String value = it.next();
//			System.out.println("List Value:" + value);
//			if (value.equals("3")) {
//				myList.remove(value);
//			}
//		}


		// RESOLUTION 1: if a list, convert to an array and then iterate
		
		String[] myArr = myList.toArray(new String[myList.size()]);;
		for (String s : myArr) {
			if (s.equals("3")) {
				//myList.remove(s);
			}
		}
		
		
		
		// On a map, when creating or removing items whilst iterating through a map
//		Map<String, String> myMap = new HashMap<String, String>();
//		myMap.put("1", "1");
//		myMap.put("2", "2");
//		myMap.put("3", "3");
//
//		Iterator<String> it1 = myMap.keySet().iterator();
//		while (it1.hasNext()) {
//			String key = it1.next();
//			System.out.println("Map Value:" + myMap.get(key));
//			if (key.equals("2")) {
//				// modification of an existing item is allowed (although it is hard to justify)
//				myMap.put("1", "4");
//				
//				// adding or removing elements from the map will throw a ConcurrentModificationException
//				// myMap.remove("1");
//				// myMap.put("4", "4");
//			}
//		}

	}
}


