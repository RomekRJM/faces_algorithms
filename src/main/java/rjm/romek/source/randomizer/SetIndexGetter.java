package rjm.romek.source.randomizer;

import java.util.Iterator;
import java.util.Set;

public class SetIndexGetter<T> {
	public T get(Set<T> set, int index) {
		Iterator<T> iterator = set.iterator();
		for(int i=0; i<index-1; ++i) {
			iterator.next();
		}
		return iterator.next();
	}
}
