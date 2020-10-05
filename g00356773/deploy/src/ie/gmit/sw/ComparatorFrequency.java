// http://www.fredosaurus.com/notes-java/data/collections/maps/ex-wordfreq.html

package ie.gmit.sw;

import java.util.*;

/** Order words from least to most frequent. */
public class ComparatorFrequency implements Comparator<Map.Entry<String, Int>> {

	public int compare(Map.Entry<String, Int> obj1, Map.Entry<String, Int> obj2) {
		int result;
		int count1 = obj1.getValue().value;
		int count2 = obj2.getValue().value;
		
		if (count1 < count2) 
			result = -1;		
		else if (count1 > count2) 
			result = 1;		
		else 
			result = 0;
		
		return result;
	}
}