package model.data_structures;

import java.io.Serializable;
import model.vo.VOMovingViolations;

/**
 * 
 * 
 *
 */
public enum Comparaciones implements Serializable{
	ADDRESSID("AddressID", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;


		//TODO Cree y complete el m�todo compare, de acuerdo a la documentaci�n.
		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			int comparacion=o1.darAddressID() - o2.darAddressID();
			if(comparacion<0)
				return -1;
			else if(comparacion>0)
				return 1;
			else return 0;

		}


	}),
	DATE("Fecha", new SerializableComparator<VOMovingViolations>() {
		/**
		 * 
		 */

		/**
		 * 
		 */
		private static final long serialVersionUID = 1456L;

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			// TODO Auto-generated method stub

			return o2.darFechaLocalDateTime().compareTo(o1.darFechaLocalDateTime());
		}
	});

	/**
	 * Nombre para mostrarle al usuario del nombre del criterio de comparaci�n.
	 */
	public String nombre;

	/**
	 * Criterio de comparaci�n del elemento de la enumeraci�n.
	 */
	public SerializableComparator<VOMovingViolations> comparador;

	/**
	 * Constructor del enum. Asigna el nombre y el comparador.
	 * @param nombre Nombre para mostrarle al usuario.
	 * @param comparador Comparador del elemento del enum.
	 */
	private Comparaciones(String nombre, SerializableComparator<VOMovingViolations> comparador) 
	{
		this.nombre = nombre;
		this.comparador = comparador;
	}

	@Override
	public String toString() 
	{
		return nombre;
	}
}
