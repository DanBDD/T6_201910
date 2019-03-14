package model.data_structures;

import java.util.Iterator;

public interface IHash<K extends Comparable <K>,V> {

	void put(K k, V v);
	V get(K k);
	V delete(K k);
	Iterator<K> keys();
}
