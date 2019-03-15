package model.data_structures;

import java.util.Iterator;

import model.vo.VOMovingViolations;

/**
 * [CODIGO CITADO] En esta clase el c�digo de agregar se tomo del esqueleto del taller 1
 * @author dagar
 *
 * @param <T> Objeto a guardar en ese arreglo dinamico generico
 */
public class ArregloDinamico<T>{

	private int tamanoActual;
	
	private int tamanoArreglo;
	
	private T[] elems;
	
	public ArregloDinamico(int pTamano){
		
		tamanoArreglo = pTamano;
		
		elems = (T[]) new Object[pTamano];
		
		tamanoActual = 0;
		
	}
	
	public void agregar( T dato )
    {
           if ( tamanoActual == tamanoArreglo )
           {  // caso de arreglo lleno (aumentar tamaNo)
                tamanoArreglo = 2 * tamanoArreglo;
                T [ ] copia = elems;
                elems = (T[]) new Object[tamanoArreglo];
                for ( int i = 0; i < tamanoActual; i++)
                {
                 	 elems[i] = copia[i];
                } 
        	    
           }	
           elems[tamanoActual] = dato;
           tamanoActual++;
   }
	public int darTamano(){
		return tamanoActual;
	}
	public T darElem(int pos){
		if(pos >= tamanoActual || pos < 0){
			throw new IndexOutOfBoundsException();
		}
		
		return elems[pos];
	}
	public void cambiarElementoEnPos(T dato, int pos)
	{
		
	}

	

}
