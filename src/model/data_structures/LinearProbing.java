package model.data_structures;

import java.util.Iterator;
/**
 * C�digo tomado del libro Algorithms de Wayne y Sedgweick.
 *
 * @param <K> Llave para identificar posici�n para guardar el objeto.
 * @param <V> Objeto a guardar
 */
public class LinearProbing<K extends Comparable <K>, V> implements IHash<K,V>{

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private K[] keys;      // the keys
    private V[] vals;    // the values

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbing(int capacity) {
        m = capacity;
        n = 0;
        keys = (K[]) new Comparable[m];
        vals = (V[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }
    public int m()
    {
    	return m;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbing<K, V> temp = new LinearProbing<K, V>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }

	@Override
	public void put(K k, V v) {
		 if (k == null) return;

	        if (v == null) {
	            delete(k);
	            return;
	        }

	        // double table size if 50% full
	        if (n >=(double) 0.75*m) resize(2*m);

	        int i;
	        for (i = hash(k); keys[i] != null; i = (i + 1) % m) {
	            if (keys[i].equals(k)) {
	                vals[i] = v;
	                return;
	            }
	        }
	        keys[i] = k;
	        vals[i] = v;
	        n++;
		
	}

	@Override
	public V get(K k) {
		   if (k == null) throw new IllegalArgumentException("argument to get() is null");
	        for (int i = hash(k); keys[i] != null; i = (i + 1) % m)
	            if (keys[i].equals(k))
	                return vals[i];
	        return null;
	}

	@Override
	public void delete(K k) {
		 if (k == null) throw new IllegalArgumentException("argument to delete() is null");

	        // find position i of key
	        int i = hash(k);
	        while (!k.equals(keys[i])) {
	            i = (i + 1) % m;
	        }

	        // delete key and associated value
	        keys[i] = null;
	        vals[i] = null;

	        // rehash all keys in same cluster
	        i = (i + 1) % m;
	        while (keys[i] != null) {
	            // delete keys[i] an vals[i] and reinsert
	            K   keyToRehash = keys[i];
	            V valToRehash = vals[i];
	            keys[i] = null;
	            vals[i] = null;
	            n--;
	            put(keyToRehash, valToRehash);
	            i = (i + 1) % m;
	        }

	        n--;

	        // halves size of array if it's 12.5% full or less
	        if (n > 0 && n <= m/8) resize(m/2);


		
	}

	@Override
	public Iterator<K> keys() {
		  Cola<K> queue = new Cola<K>();
	        for (int i = 0; i < m; i++)
	            if (keys[i] != null) queue.enqueue(keys[i]);
	        return queue.iterator();
	}
}
