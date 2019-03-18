package model.data_structures;

import java.util.Iterator;

public class SeparateChaining<K extends Comparable <K>, V> implements IHash<K,V> {

	private static final int INIT_CAPACITY = 4;

	private int n;                                // number of key-value pairs
	private int m;                                // hash table size
	private SequentialSearch<K, V>[] st;  // array of linked-list symbol tables


	/**
	 * Initializes an empty symbol table with {@code m} chains.
	 * @param m the initial number of chains
	 */
	public SeparateChaining(int m) {
		this.m = m;
		st = (SequentialSearch<K, V>[]) new SequentialSearch[m];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearch<K, V>();
	} 

	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize(int chains) {
		SeparateChaining<K, V> temp = new SeparateChaining<K, V>(chains);
		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m  = temp.m;
		this.n  = temp.n;
		this.st = temp.st;
	}

	// hash value between 0 and m-1
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	} 

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	} 


	@Override
	public void put(K k, V v) {
		if (k == null) throw new IllegalArgumentException();
		if (v == null) {
			delete(k);
			return;
		}

		// double table size if average length of list >= 10
		if (n >= 5*m) resize(2*m);

		int i = hash(k);
		if (!st[i].contains(k)) n++;
		st[i].put(k, v);
	}

	@Override
	public V get(K k) {
		if (k == null) throw new IllegalArgumentException();
		int i = hash(k);
		return st[i].get(k);
	}

	@Override
	public void delete(K k) {
		if (k == null) throw new IllegalArgumentException();

		int i = hash(k);
		if (st[i].contains(k)) n--;
		st[i].delete(k);

		// halve table size if average length of list <= 2
		if (m > INIT_CAPACITY && n <= 2*m) resize(m/2);	
	}

	@Override
	public Iterator<K> keys() {
		Cola<K> queue = new Cola<K>();
		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys())
				queue.enqueue(key);
		}
		return queue.iterator();
	}

	public int m() {
		// TODO Auto-generated method stub
		return m;
	}

}
