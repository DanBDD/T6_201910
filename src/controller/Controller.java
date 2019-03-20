package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import jdk.nashorn.internal.parser.JSONParser;
import model.data_structures.ArregloDinamico;
import model.data_structures.Comparaciones;
import model.data_structures.LinearProbing;
import model.data_structures.SeparateChaining;
import model.util.Sort;
import model.vo.VOMovingViolations;
import view.MovingViolationsManagerView;

public class Controller {

	private MovingViolationsManagerView view;


	/**
	 * Ruta de archivo CSV Enero.
	 */
	public static final String rutaEnero = "./data/Moving_Violations_Issued_in_January_2018.json";

	/**
	 * Ruta de archivo CSV Febrero.
	 */
	public static final String rutaFebrero = "./data/Moving_Violations_Issued_in_February_2018.json";

	/**
	 * Ruta de archivo CSV Marzo.
	 */
	public static final String rutaMarzo = "./data/Moving_Violations_Issued_in_March_2018.json";

	/**
	 * Ruta de archivo CSV Abril.
	 */
	public static final String rutaAbril = "./data/Moving_Violations_Issued_in_April_2018.json";

	/**
	 * Ruta de archivo CSV Mayo.
	 */
	public static final String rutaMayo = "./data/Moving_Violations_Issued_in_May_2018.json";

	/**
	 * Ruta de archivo CSV Junio.
	 */
	public static final String rutaJunio = "./data/Moving_Violations_Issued_in_June_2018.json";

	private ArregloDinamico<VOMovingViolations> arreglo;
	private LinearProbing<Integer, ArregloDinamico<VOMovingViolations>> linear;
	private SeparateChaining<Integer, ArregloDinamico<VOMovingViolations>> separate;
	private Comparable<VOMovingViolations>[ ] temp;
	public Controller() {
		view = new MovingViolationsManagerView();
		arreglo=new ArregloDinamico<VOMovingViolations>(5);
		linear= new LinearProbing<Integer, ArregloDinamico<VOMovingViolations>>(101);
		separate =new SeparateChaining<Integer, ArregloDinamico<VOMovingViolations>>(101);
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin=false;
		int nDatos = 0;
		int parametro = 0;
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
				this.crearCopia();
				System.out.println("Tama�o copia: " + temp.length);
				break;
			case 2:
				this.agregarLinearProbing(temp);
				System.out.println("Tama�o de LinearProbing con Infracciones con accidente: " + linear.size());
				break;
			case 3: 

				this.agregarSeparateChaining(temp);
				System.out.println("Tama�o de SeparateChaining con Infracciones con accidente: " + separate.size());
				break;
			case 4:
				view.printMessage("Ingrese el AddressID para buscar");
				parametro = sc.nextInt();
				view.printBusqueda(this.buscarLinear(parametro));

				break;
			case 5:
				view.printMessage("Ingrese el AddressID para buscar");
				parametro = sc.nextInt();
				view.printBusqueda(this.buscarSeparate(parametro));
				break;
			case 6:	
				fin=true;
				sc.close();
				break;
			}
		}
	}
	private void crearCopia () {

		int contador=0;
		for(int i=0;i<arreglo.darTamano();i++)
		{
			if(arreglo.darElem(i).darIndicator().equals("Yes"))
			{
				contador++;
			}
		}
		temp = new Comparable[contador];
		int pos=0;
		for(int j=0;j<arreglo.darTamano();j++)
		{
			if(arreglo.darElem(j).darIndicator().equals("Yes"))
			{
				temp[pos] = arreglo.darElem(j);
				pos++;
			}
		}
		Sort.ordenarMergeSort(temp, Comparaciones.ADDRESSID.comparador , true);
	}

	public LinearProbing<Integer, ArregloDinamico<VOMovingViolations>> agregarLinearProbing(Comparable<VOMovingViolations>[] temporal){

		ArregloDinamico<VOMovingViolations> r=new ArregloDinamico<VOMovingViolations>(6);
		for(int i=0;i<temporal.length-1;i++)
		{

			VOMovingViolations actual=(VOMovingViolations) temporal[i];
			if(actual.darAddressID() - ((VOMovingViolations) temporal[i+1]).darAddressID() == 0) {
				r.agregar(actual);
			}
			else if(actual.darAddressID() - ((VOMovingViolations) temporal[i+1]).darAddressID() != 0)
			{
				r.agregar(actual);
				linear.put(actual.darAddressID(), r);
				r=null;
				r= new ArregloDinamico<VOMovingViolations>(6);

			}
		}	
		return linear;
	}

	public SeparateChaining<Integer, ArregloDinamico<VOMovingViolations>> agregarSeparateChaining(Comparable<VOMovingViolations>[] temporal){

		ArregloDinamico<VOMovingViolations> r=new ArregloDinamico<VOMovingViolations>(6);
		for(int i=0;i<temporal.length-1;i++)
		{

			VOMovingViolations actual=(VOMovingViolations) temporal[i];
			if(actual.darAddressID() - ((VOMovingViolations) temporal[i+1]).darAddressID() == 0) {
				r.agregar(actual);
			}
			else if(actual.darAddressID() - ((VOMovingViolations) temporal[i+1]).darAddressID() != 0)
			{
				r.agregar(actual);
				separate.put(actual.darAddressID(), r);
				r=null;
				r= new ArregloDinamico<VOMovingViolations>(6);

			}
		}	
		return separate;
	}
	public Comparable<VOMovingViolations>[] buscarLinear(int pAddressID){

		ArregloDinamico<VOMovingViolations> datos=linear.get(pAddressID);
		Comparable<VOMovingViolations>[] c = new Comparable[datos.darTamano()];	
		int pos=0;
		while(pos<datos.darTamano())
		{
			c[pos] = arreglo.darElem(pos);
			pos++;
		}
		Sort.ordenarMergeSort(c, Comparaciones.DATE.comparador , false);
		return c;
	}
	public Comparable<VOMovingViolations>[] buscarSeparate(int pAddressID2){
		ArregloDinamico<VOMovingViolations> datos=separate.get(pAddressID2);
		Comparable<VOMovingViolations>[] c = new Comparable[datos.darTamano()];	
		int pos=0;
		while(pos<datos.darTamano())
		{
			c[pos] = arreglo.darElem(pos);
			pos++;
		}
		Sort.ordenarMergeSort(c, Comparaciones.DATE.comparador , false);
		return c;	
	}

	public int loadMovingViolations() {
		int obID = 0;
		String loc = null;
		int addID = 0;
		String address = null;
		int amt = 0;
		String date = null;
		String code = null;
		String accID=null;
		JsonParser parser = new JsonParser();
		try{
			JsonArray ja = (JsonArray) parser.parse(new FileReader(rutaEnero));
			for(int i = 0; ja != null && i<ja.size(); i++){
				JsonObject actual = (JsonObject) ja.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}
				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
			JsonArray ja1 = (JsonArray) parser.parse(new FileReader(rutaFebrero));
			for(int i = 0; ja1 != null && i<ja1.size(); i++){
				JsonObject actual = (JsonObject) ja1.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}

				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
			JsonArray ja2 = (JsonArray) parser.parse(new FileReader(rutaMarzo));
			for(int i = 0; ja2 != null && i<ja2.size(); i++){
				JsonObject actual = (JsonObject) ja2.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}

				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
			JsonArray ja3 = (JsonArray) parser.parse(new FileReader(rutaAbril));
			for(int i = 0; ja3 != null && i<ja3.size(); i++){
				JsonObject actual = (JsonObject) ja3.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}

				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
			JsonArray ja4 = (JsonArray) parser.parse(new FileReader(rutaMayo));
			for(int i = 0; ja4 != null && i<ja4.size(); i++){
				JsonObject actual = (JsonObject) ja4.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}

				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
			JsonArray ja5 = (JsonArray) parser.parse(new FileReader(rutaJunio));
			for(int i = 0; ja5 != null && i<ja5.size(); i++){
				JsonObject actual = (JsonObject) ja5.get(i);
				if(actual.get("OBJECTID") != null){
					obID = actual.get("OBJECTID").getAsInt();			
				}
				if(actual.get("LOCATION") != null){
					loc = actual.get("LOCATION").getAsString();
				}
				if(actual.get("ADDRESS_ID") != null){
					if(actual.get("ADDRESS_ID").toString().equals("null")){
						address = "0";
						addID = Integer.parseInt(address);
					}
					else{
						address = actual.get("ADDRESS_ID").getAsString();
						addID = Integer.parseInt(address);
					}
				}
				if(actual.get("FINEAMT") != null){
					amt = actual.get("FINEAMT").getAsInt();
				}
				if(actual.get("TICKETISSUEDATE") != null){
					date = actual.get("TICKETISSUEDATE").getAsString();
				}
				if(actual.get("VIOLATIONCODE") != null){
					code = actual.get("VIOLATIONCODE").getAsString();
				}

				if(actual.get("ACCIDENTINDICATOR") != null){
					accID= actual.get("ACCIDENTINDICATOR").getAsString();			
				}

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code,accID));

				}
			}
		}
		catch(IOException e){
			e.getMessage();
		}
		return arreglo.darTamano();
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
