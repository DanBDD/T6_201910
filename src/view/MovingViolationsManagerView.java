package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import controller.Controller;
import model.data_structures.ArregloDinamico;
import model.data_structures.IQueue;
import model.vo.VOMovingViolations;

public class MovingViolationsManagerView 
{
	/**
	 * Constante con el nÃºmero maximo de datos maximo que se deben imprimir en consola
	 */
	public static final int N = 20;

	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Taller 6----------------------");
		System.out.println("0. Cargar datos del cuatrimestre");
		System.out.println("1. Crear copia");
		System.out.println("2. Cargar datos en tabla Hash de Linear Probing");
		System.out.println("3. Cargar datos en tabla Hash de Separate Chaining");
		System.out.println("4. Buscar por AddressID en LinearProbing");
		System.out.println("5. Buscar por AddressID en SeparateChaining");
		System.out.println("6. Salir");
		System.out.println("Digite el nï¿½mero de opciï¿½n para ejecutar la tarea, luego presione enter: (Ej., 1):");

	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void printBusqueda(ArregloDinamico<VOMovingViolations> param) {
		System.out.println("Resultados de b�squeda: " + param.darTamano());
		for(int i = 0; i<param.darTamano(); i++) {
			VOMovingViolations actual = param.darElem(i);
			System.out.println("Datos: OBJECTID: " + actual.darObjectID() + " LOCATION: " + actual.darLocation() +
					" TICKETISSUEDATE: " + actual.darFecha() + " VIOLATIONCODE: " +actual.darViolationCode() + " FINEAMT: " + actual.darAMT());
		}
	}
}
