package model.data_structures;

public class SequentialSearch <K extends Comparable <K>, V>{
	private int n;           // number of key-value pairs        
    private Node first;      // the linked list of key-value pairs

    // a helper linked list data type
    private class Node {
        private K key;
        private V val;
        private Node next;

        public Node(K key, V val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	}

	/**
	 * Is this symbol table empty?
	 * @return {@code true} if this symbol table is empty and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does this symbol table contain the given key?
	 * @param key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *     {@code false} otherwise
	 */
	public boolean contains(K key) {
		return get(key) != null;
	}

	/**
	 * Returns the value associated with the given key.
	 * @param key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and {@code null} if the key is not in the symbol table
	 */
	public V get(K key) {
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key))
				return x.val;
		}
		return null;
	}

	/**
	 * Inserts the key-value pair into the symbol table, overwriting the old value
	 * with the new value if the key is already in the symbol table.
	 * If the value is {@code null}, this effectively deletes the key from the symbol table.
	 * @param key the key
	 * @param val the value
	 */
	public void put(K key, V val) {
		if (val == null) {
			delete(key);
			return;
		}

		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {
				x.val = val;
				return;
			}
		}
		first = new Node(key, val, first);
		n++;
	}

	/**
	 * Removes the key and associated value from the symbol table
	 * (if the key is in the symbol table).
	 * @param key the key
	 */
	public void delete(K key) {
		first = delete(first, key);
	}

	// delete key in linked list beginning at Node x
	// warning: function call stack too large if table is large
	private Node delete(Node x, K key) {
		if (x == null) return null;
		if (key.equals(x.key)) {
			n--;
			return x.next;
		}
		x.next = delete(x.next, key);
		return x;
	}


	/**
	 * Returns all keys in the symbol table as an {@code Iterable}.
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 * @return all keys in the symbol table as an {@code Iterable}
	 */
	public Iterable<K> keys()  {
		Cola<K> queue = new Cola<K>();
		for (Node x = first; x != null; x = x.next)
			queue.enqueue(x.key);
		return queue;
	}
}
