
package tests;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.SeparateChaining;

public class TestSeparateChaining extends TestCase{

	protected SeparateChaining<String, Integer> linear;
	@Before
	public void setUp()
	{
		linear=new SeparateChaining<>(101);
		System.out.println("TestSeparateChaining");
		int contador=0;
		while(linear.keys().hasNext())
		{
			contador++;
		}
		assertEquals(contador, 0);
		System.out.println("Tamaño del arreglo inicial: "+contador);
		int inic=linear.m();
		int cambios=0;
		for(int i=0;i<10000;i++)
		{
			linear.put(Integer.toString(i), i);
			if(linear.m()!=inic)
			{
				inic=linear.m();
				cambios++;
			}
		}
		System.out.println("Cantidad de rehashes: "+cambios);
	}
	@Test
	public void testSize()
	{
		//Tamaño final arreglo
		//		int contador2=0;
		//		while(linear.keys().hasNext())
		//		{
		//			System.out.println("Inside");
		//			contador2++;
		//		}
		//		assertEquals(contador2, 9999);
		System.out.println("Numero de duplas: "+linear.size());
		System.out.println("Factor de carga final: "+(double)linear.size()/linear.m());
		long startTime = System.currentTimeMillis();
		for(int i=0;i<10000;i++)
		{
			int v=linear.get(Integer.toString(i));
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		double l=(double)duration/10000;
		System.out.println("Tiempo promedio: "+l+" segundos");
	}

}

