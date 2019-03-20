
package tests;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.SeparateChaining;

public class TestSeparateChaining extends TestCase{

	protected SeparateChaining<String, Integer> separate;
	@Before
	public void setUp()
	{
		separate=new SeparateChaining<>(101);
		System.out.println("TestSeparateChaining");
		int contador=0;
		while(separate.keys().hasNext())
		{
			contador++;
		}
		assertEquals(contador, 0);
		System.out.println("Tamaño del arreglo inicial: "+contador);
		int inic=separate.m();
		int cambios=0;
		for(int i=0;i<10000;i++)
		{
			separate.put(Integer.toString(i), i);
			if(separate.m()!=inic)
			{
				inic=separate.m();
				cambios++;
			}
		}
		System.out.println("Cantidad de rehashes: "+cambios);
	}
	@Test
	public void testSize()
	{
		System.out.println("Tama�o final del arreglo: "+separate.size());
		System.out.println("Numero de duplas: "+separate.size());
		System.out.println("Factor de carga final: "+(double)separate.size()/separate.m());
		long startTime = System.currentTimeMillis();
		for(int i=0;i<10000;i++)
		{
			int v=separate.get(Integer.toString(i));
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		double l=(double)duration/10000;
		System.out.println("Tiempo promedio: "+l+" milisegundos");
	}

}

