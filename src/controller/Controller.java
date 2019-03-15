package controller;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.opencsv.CSVReader;
import model.data_structures.ArregloDinamico;
import model.data_structures.Comparaciones;
import model.data_structures.MaxColaPrioridad;
import model.data_structures.MaxHeapCP;
import model.util.Sort;
import model.vo.LocationVO;
import model.vo.VOMovingViolations;
import view.MovingViolationsManagerView;

public class Controller {

	private MovingViolationsManagerView view;
	

	/**
	 * Ruta de archivo CSV Enero.
	 */
	public static final String rutaEnero = "./data/Moving_Violations_Issued_in_January_2018-2.csv";

	/**
	 * Ruta de archivo CSV Febrero.
	 */
	public static final String rutaFebrero = "./data/Moving_Violations_Issued_in_February_2018.csv";

	/**
	 * Ruta de archivo CSV Marzo.
	 */
	public static final String rutaMarzo = "./data/Moving_Violations_Issued_in_March_2018.csv";

	/**
	 * Ruta de archivo CSV Abril.
	 */
	public static final String rutaAbril = "./data/Moving_Violations_Issued_in_April_2018.csv";

	private ArregloDinamico<VOMovingViolations> arreglo;

	public Controller() {
		view = new MovingViolationsManagerView();
		arreglo=new ArregloDinamico<VOMovingViolations>(160000);


	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin=false;
		int nMuestra = 0;
		long startTime = 0;
		long endTime = 0;
		long duration = 0;
		int nDatos = 0;
		while(!fin)
		{
			view.printMenu();

			int option = sc.nextInt();

			switch(option)
			{
			case 0:
				nDatos = this.loadMovingViolations();
				view.printMessage("Datos cargados, total de datos: " + nDatos);
				break;
			case 1:
				view.printMessage("Dar tamaNo de la muestra: ");
				nMuestra = sc.nextInt();
				muestra = this.generarMuestra( nMuestra );
				int tam = muestra.length;
				view.printMessage("Muestra generada, tamano: " + tam);
				break;
			case 2: 
				if ( nMuestra > 0 && muestra != null)
				{    
					view.printDatosMuestra( nMuestra, muestra);
				}
				else
				{
					view.printMessage("Muestra invalida");
				}
				break;
			case 3:
				if ( nMuestra > 0 && muestra != null)
				{
					copia = this.obtenerCopia(muestra);
					startTime = System.currentTimeMillis();
					this.agregarColaPrioridad(copia);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Agregar terminado con Cola de Prioridad.");
					view.printMessage("Tiempo en agregar con Cola de Prioridad: " + duration + " milisegundos");

				}
				else
				{
					view.printMessage("Muestra invalida");
				}
				break;

			case 4:
				if ( nMuestra > 0 && muestra != null  )
				{
					copia = this.obtenerCopia(muestra);
					startTime = System.currentTimeMillis();
					this.agregarMaxHeap(copia);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Agregar terminado con HeapMAX.");
					view.printMessage("Tiempo en agregar con HeapMAX: " + duration + " milisegundos");
				}
				else
				{
					view.printMessage("Muestra invalida");
				}
				break;
			case 5:
				if ( nMuestra > 0 && muestra != null && cola.darNumElementos() > 0 )
				{					
					copia = this.obtenerCopia(muestra);
					startTime = System.currentTimeMillis();
					this.borrarMaxCola(copia);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Eliminar m�ximo terminado con Cola de Prioridad.");
					view.printMessage("Tiempo en eliminar m�ximo con Cola de Prioridad: " + duration + " milisegundos");
				}
				else
				{
					view.printMessage("Muestra invalida");
				}
				break;

			case 6:
				if ( nMuestra > 0 && muestra != null && heap.darNumElementos() > 0)
				{
					copia = this.obtenerCopia(muestra);
					startTime = System.currentTimeMillis();
					this.borrarMaxHeap(copia);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Eliminar m�ximo terminado con HeapMAX.");
					view.printMessage("Tiempo en eliminar m�ximo con HeapMAX: " + duration + " milisegundos");
				}
				else
				{
					view.printMessage("Muestra invalida");
				}
				break;
			case 7:
				view.printMessage("Ingrese la fecha con hora inicial (Ej : 2018-01-02T20:02:22.000Z)");
				LocalDateTime fechaInicial = convertirFecha_Hora_LDT(sc.next());

				view.printMessage("Ingrese la fecha con hora final (Ej : 2018-03-02T20:02:22.000Z)");
				LocalDateTime fechaFinal = convertirFecha_Hora_LDT(sc.next());
				view.printMessage("Ingrese la cantidad de vias que quiere ver: ");
				int num=sc.nextInt();
				startTime = System.currentTimeMillis();
				view.printElementos( num, this.crearMaxColaP(fechaInicial, fechaFinal));
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo con Cola de Prioridad: " + duration + " milisegundos");
				
				break;
			case 8:
				view.printMessage("Ingrese la fecha con hora inicial (Ej : 2018-01-02T20:02:22.000Z");
				LocalDateTime fechaInicial2 = convertirFecha_Hora_LDT(sc.next());

				view.printMessage("Ingrese la fecha con hora final (Ej : 2018-03-02T20:02:22.000Z");
				LocalDateTime fechaFinal2 = convertirFecha_Hora_LDT(sc.next());
				view.printMessage("Ingrese la cantidad de vias que quiere ver: ");
				int num2=sc.nextInt();
				startTime = System.currentTimeMillis();
				view.printElementos2( num2, this.crearMaxHeapCP(fechaInicial2, fechaFinal2));
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo con Heap: " + duration + " milisegundos");
				break;
			case 9:	
				fin=true;
				sc.close();
				break;
			}
		}
	}
	public int loadMovingViolations() {
		int contador = 0;
		boolean hayNulo = false;
		try {

			CSVReader lectorEnero = new CSVReader(new FileReader(rutaEnero));
			String[] lineaEnero = lectorEnero.readNext();
			while ((lineaEnero = lectorEnero.readNext()) != null) {

				String address = lineaEnero[3];
				int addressID = 0;
				if(address.equals("")){
					addressID = 0;
				}
				else{
					addressID = Integer.parseInt(address);
				}

				String location = lineaEnero[2];
				String issueDate = lineaEnero[13];

				arreglo.agregar(new VOMovingViolations(issueDate, addressID, location));
				contador++;
			}
			lectorEnero.close();

			CSVReader lectorFebrero = new CSVReader(new FileReader(rutaFebrero));
			String[] lineaFebrero = lectorFebrero.readNext();
			while ((lineaFebrero = lectorFebrero.readNext()) != null) {
				String address = lineaFebrero[3];
				int addressID = 0;
				if(address.equals("")){
					addressID = 0;
				}
				else{
					addressID = Integer.parseInt(address);
				}
				String location = lineaFebrero[2];
				String issueDate = lineaFebrero[13];

				arreglo.agregar(new VOMovingViolations(issueDate, addressID, location));
				contador++;

			}
			lectorFebrero.close();

			CSVReader lectorMarzo = new CSVReader(new FileReader(rutaMarzo));
			String[] lineaMarzo = lectorMarzo.readNext();
			while ((lineaMarzo = lectorMarzo.readNext()) != null) {
				String address = lineaMarzo[3];
				int addressID = 0;
				if(address.equals("")){
					addressID = 0;
				}
				else{
					addressID = Integer.parseInt(address);
				}
				String location = lineaMarzo[2];
				String issueDate = lineaMarzo[13];

				arreglo.agregar(new VOMovingViolations(issueDate, addressID, location));
				contador++;

			}
			lectorMarzo.close();

			CSVReader lectorAbril = new CSVReader(new FileReader(rutaAbril));
			String[] lineaAbril = lectorAbril.readNext();
			while ((lineaAbril = lectorAbril.readNext()) != null) {
				String address = lineaAbril[3];
				int addressID = 0;
				if(address.equals("")){
					addressID = 0;
				}
				else{
					addressID = Integer.parseInt(address);
				}
				String location = lineaAbril[2];
				String issueDate = lineaAbril[13];

				arreglo.agregar(new VOMovingViolations(issueDate, addressID, location));				

				contador++;
			}
			lectorAbril.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return contador;
	}

	public Comparable<LocationVO>[] generarMuestra(int numElems){

		Comparable<VOMovingViolations>[] temp = new Comparable[numElems];	

		int pos=0;
		int aleatorio = 0;
		while(pos<numElems)
		{
			aleatorio =  ThreadLocalRandom.current().nextInt(0, arreglo.darTamano());
			temp[pos] = arreglo.darElem(aleatorio);
			pos++;
		}
		Sort.ordenarMergeSort(temp, Comparaciones.ADDRESSID.comparador , true);
		int numAddressID = 0;
		int pos1 = 0 ;
		int contador=0;
		for(int i=0;i<temp.length-1;i++){
			VOMovingViolations actual = (VOMovingViolations) temp[i];
			if((actual.darAddressID() - ((VOMovingViolations) temp[i+1]).darAddressID()) != 0)
			{
				contador++;
			}
		}
		muestra = new Comparable[contador];
		for(int j=0; j<temp.length-1;j++){
			VOMovingViolations actual = (VOMovingViolations) temp[j];
			if((actual.darAddressID() - ((VOMovingViolations) temp[j+1]).darAddressID()) == 0){
				numAddressID++;
			}
			else{

				muestra[pos1] = new LocationVO(actual.darAddressID(), actual.darLocation(), numAddressID);
				pos1++;
				numAddressID = 1;
			}
		}
		return muestra;
	}


	/**
	 * Convertir fecha a un objeto LocalDate
	 * @param fecha fecha en formato dd/mm/aaaa con dd para dia, mm para mes y aaaa para agno
	 * @return objeto LD con fecha
	 */
	private static LocalDate convertirFecha(String fecha)
	{
		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}


	/**
	 * Convertir fecha y hora a un objeto LocalDateTime
	 * @param fecha fecha en formato dd/mm/aaaaTHH:mm:ss con dd para dia, mm para mes y aaaa para agno, HH para hora, mm para minutos y ss para segundos
	 * @return objeto LDT con fecha y hora integrados
	 */
	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	{
		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'"));
	}



}
