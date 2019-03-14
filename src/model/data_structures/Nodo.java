package model.data_structures;

public class Nodo<T extends Comparable<T>>{

	private Nodo<T> siguiente;
	
	private T elemento;
	
	public Nodo(T pElemento){
		elemento = pElemento;
		siguiente = null;
	}
	
	public void cambiarSiguiente(Nodo<T> nuevo){
		siguiente = nuevo;
	}
	
	public T darElem(){
		return elemento;
	}
	
	public Nodo<T> darSiguiente(){
		return siguiente;
	}
}