package model.data_structures;

import java.util.Iterator;

public class SeparateChaining<K extends Comparable <K>, V> implements IHash<K,V> {

	private int tam;

	public SeparateChaining(int tam)
	{
		this.tam=tam;
	}
	@Override
	public void put(K k, V v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V get(K k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V delete(K k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

}
