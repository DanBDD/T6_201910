package model.data_structures;

import java.util.Iterator;

public class LinearProbing<K extends Comparable <K>, V> implements IHash<K,V>{

	private int M;
	private int N;
	private K[] keys;
	private V[] vals;

	public LinearProbing(int tam){
		this.M=tam;
		keys = (K[])   new Object[tam];
		vals = (V[]) new Object[tam];

	}
	private int hash(K key)
	{
		return (key.hashCode() & 0x7fffffff) % M;
	}
	private void resize(int cap)
	{
		LinearProbing<K, V> t;
	      t = new LinearProbing<K, V>(cap);
	      for (int i = 0; i < M; i++)
	         if (keys[i] != null)
	             t.put(keys[i], vals[i]);
	      keys = t.keys;
	      vals = t.vals;
	      M    = t.M;
	}
	@Override
	public void put(K k, V v) {
		if (N >= M/2) resize(2*M);  // double M (see text)
		   int i;
		   for (i = hash(k); keys[i] != null; i = (i + 1) % M)
		      if (keys[i].equals(k)) { vals[i] = v; return; }
		   keys[i] = k;
		   vals[i] = v;
		N++;
	}

	@Override
	public V get(K k) {
		for (int i = hash(k); keys[i] != null; i = (i + 1) % M)
		      if (keys[i].equals(k))
		          return vals[i];
		   return null;
	}

	@Override
	public V delete(K k) {
	     if (get(k)==null) return null;
	     int i = hash(k);
	     while (!k.equals(keys[i]))
	        i = (i + 1) % M;
	     keys[i] = null;
	     vals[i] = null;
	     i = (i + 1) % M;
	     while (keys[i] != null)
	     {
	        K keyToRedo = keys[i];
	        V valToRedo = vals[i];
	        keys[i] = null;
	        vals[i] = null;
	        N--;
	        put(keyToRedo, valToRedo);
	        i = (i + 1) % M;
	     }
	     N--;
	     if (N > 0 && N == M/8) resize(M/2);
	}

	@Override
	public Iterator<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

}
