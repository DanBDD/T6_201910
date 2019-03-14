
package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
public class Iterador <T extends Comparable<T>> implements Iterator<T>{


	private Nodo<T> actual;


	public Iterador(Nodo<T> primerNodo) 
	{
		actual = primerNodo;
	}

	/**
	 * Indica si a�n hay elementos por recorrer
	 * @return true en caso de que  a�n haya elemetos o false en caso contrario
	 */
	public boolean hasNext() 
	{
		return actual != null;
	}

	/**
	 * Devuelve el siguiente elemento a recorrer
	 * <b>post:</b> se actualizado actual al siguiente del actual
	 * @return objeto en actual
	 * @throws NoSuchElementException Si se encuentra en el final de la lista y se pide el siguiente elemento.
	 */
	public T next() throws NoSuchElementException
	{
		if(actual == null)
			throw new NoSuchElementException("Se ha alcanzado el final de la lista");
		T valor = actual.darElem();
		actual = actual.darSiguiente();
		return valor;
	}


}


