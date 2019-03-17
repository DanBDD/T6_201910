
package model.util;

import java.util.Comparator;

import model.vo.VOMovingViolations;
/**
 * Se copiaron los metodos de ordenamiento Shell, Merge y Quick del libro Algorithhms 4th Edition by Robert Sedgewick, Kevin Wayne.pdf
 */
public class Sort {

	private static	Comparable [] aux=null;

	/**
	 * Ordenar datos aplicando el algoritmo ShellSort
	 * @param datos - conjunto de datos a ordenar (inicio) y conjunto de datos ordenados (final)
	 */
	public static void ordenarShellSort( Comparable [ ] datos ,Comparator comparador,  boolean b) {

		// TODO implementar el algoritmo ShellSort
		// Sort a[] into increasing order.
		int N = datos.length;
		int h = 1;
		while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
		while (h >= 1)
		{  // h-sort the array.
			for (int i = h; i < N; i++)
			{  // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
				for (int j = i; j >= h && less(datos[j], datos[j-h], comparador,b); j -= h)
					exchange(datos, j, j-h);
			}
			h = h/3; }
	}
	/**
	 * Ordenar datos aplicando el algoritmo MergeSort
	 * @param <T>
	 * @param datos - conjunto de datos a ordenar (inicio) y conjunto de datos ordenados (final)
	 */
	public static void ordenarMergeSort( Comparable[ ] datos, Comparator comparador, boolean b) {

		// TODO implementar el algoritmo MergeSort
		aux = new Comparable[datos.length];    // Allocate space just once.
		sort(datos, 0, datos.length - 1, comparador,b);
	}
	private static void sort(Comparable[] a, int lo, int hi, Comparator comparador, boolean b)
	{  // Sort a[lo..hi].
		if (hi <= lo) return;
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid, comparador,b);       // Sort left half.
		sort(a, mid+1, hi, comparador,b);     // Sort right half.
		merge(a, lo, mid, hi, comparador,b);  // Merge results (code on page 271).
	}
	public static void merge(Comparable[] a, int lo, int mid, int hi, Comparator comparador, boolean b)
	{  // Merge a[lo..mid] with a[mid+1..hi].
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++)  // Copy a[lo..hi] to aux[lo..hi].
			aux[k] = a[k];
		for (int k = lo; k <= hi; k++)  // Merge back to a[lo..hi].
			if      (i > mid)              a[k] = aux[j++];
			else if (j > hi )              a[k] = aux[i++];
			else if (less(aux[j], aux[i], comparador,b)) a[k] = aux[j++];
			else                           a[k] = aux[i++];
	}
	/**
	 * Ordenar datos aplicando el algoritmo QuickSort
	 * @param datos - conjunto de datos a ordenar (inicio) y conjunto de datos ordenados (final)
	 */
	public static void ordenarQuickSort( Comparable[ ] datos, Comparator comparador , boolean b ) {

		int lo=0;
		int hi=datos.length-1;
		if (hi <= lo) return;
		int j = partition(datos, lo, hi, comparador,b);  // Partition (see page 291).
		sort(datos, lo, j-1, comparador,b);              // Sort left part a[lo .. j-1].
		sort(datos, j+1, hi, comparador,b);              // Sort right part a[j+1 .. hi].
	}
	private static int partition(Comparable[] a, int lo, int hi, Comparator comparador,  boolean b)
	{  // Partition into a[lo..i-1], a[i], a[i+1..hi].
		int i = lo, j = hi+1;            // left and right scan indices
		Comparable v = a[lo];            // partitioning item
		while (true)
		{  // Scan right, scan left, check for scan complete, and exchange.
			while (less(a[++i], v, comparador,b)) if (i == hi) break;
			while (less(v, a[--j], comparador,b)) if (j == lo) break;
			if (i >= j) break;
			exchange(a, i, j);
		}
		exchange(a, lo, j);
		return j;
	}
	// Put v = a[j] into position
	// with a[lo..j-1] <= a[j] <= a[j+1..hi].
	/**
	 * Comparar 2 objetos usando la comparacion "natural" de su clase
	 * @param v primer objeto de comparacion
	 * @param w segundo objeto de comparacion
	 * @param comparador 
	 * @param b 
	 * @return true si v es menor que w usando el metodo compareTo. false en caso contrario.
	 */
	private static boolean less(Comparable v, Comparable w, Comparator comparador, boolean b)
	{
		if(b)
			return comparador.compare(v, w) < 0;
		else
			return comparador.compare(v, w)>0;
	}

	/**
	 * Intercambiar los datos de las posicion i y j
	 * @param datos contenedor de datos
	 * @param i posicion del 1er elemento a intercambiar
	 * @param j posicion del 2o elemento a intercambiar
	 */
	private static void exchange( Comparable[] datos, int i, int j)
	{
		// TODO implementar
		Comparable t = datos[i]; 
		datos[i] = datos[j]; 
		datos[j] = t; 
	}

}
