package model.data_structures;

import java.util.Iterator;

public interface IHash<K,V> {

	void put(K k, V v);
	V get(K k);
	void delete(K k);
	Iterator<K> keys();

}
