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
				//			case 1:
				//				view.printMessage("Dar tamaNo de la muestra: ");
				//				nMuestra = sc.nextInt();
				//				muestra = this.generarMuestra( nMuestra );
				//				int tam = muestra.length;
				//				view.printMessage("Muestra generada, tamano: " + tam);
				//				break;
				//			case 2: 
				//				if ( nMuestra > 0 && muestra != null)
				//				{    
				//					view.printDatosMuestra( nMuestra, muestra);
				//				}
				//				else
				//				{
				//					view.printMessage("Muestra invalida");
				//				}
				//				break;

			case 3:	
				fin=true;
				sc.close();
				break;
			}
		}
	}
	public int loadMovingViolations() {
		int contador = 0;
		JsonReader reader;
		int obID = 0;
		String loc = null;
		int addID = 0;
		String address = null;
		int amt = 0;
		String date = null;
		String code = null;
		
		try{
			JsonParser parser = new JsonParser();
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

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));

				}
			}
			JsonParser parser1 = new JsonParser();
			JsonArray ja1 = (JsonArray) parser1.parse(new FileReader(rutaFebrero));
			for(int i = 0; ja1 != null && i<ja1.size(); i++){
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

				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));

				}
			}
//			JsonArray ja2 = (JsonArray) parser.parse(new FileReader(rutaMarzo));
//			for(int i = 0; ja2 != null && i<ja2.size(); i++){
//				JsonObject actual = (JsonObject) ja.get(i);
//				if(actual.get("OBJECTID") != null){
//					obID = actual.get("OBJECTID").getAsInt();			
//				}
//				if(actual.get("LOCATION") != null){
//					loc = actual.get("LOCATION").getAsString();
//				}
//				if(actual.get("ADDRESS_ID") != null){
//					if(actual.get("ADDRESS_ID").toString().equals("null")){
//						address = "0";
//						addID = Integer.parseInt(address);
//					}
//					else{
//						address = actual.get("ADDRESS_ID").getAsString();
//						addID = Integer.parseInt(address);
//					}
//				}
//				if(actual.get("FINEAMT") != null){
//					amt = actual.get("FINEAMT").getAsInt();
//				}
//				if(actual.get("TICKETISSUEDATE") != null){
//					date = actual.get("TICKETISSUEDATE").getAsString();
//				}
//				if(actual.get("VIOLATIONCODE") != null){
//					code = actual.get("VIOLATIONCODE").getAsString();
//				}
//				
//				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
//					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));
//				
//				}
//			}
//			JsonArray ja3 = (JsonArray) parser.parse(new FileReader(rutaAbril));
//			for(int i = 0; ja3 != null && i<ja3.size(); i++){
//				JsonObject actual = (JsonObject) ja.get(i);
//				if(actual.get("OBJECTID") != null){
//					obID = actual.get("OBJECTID").getAsInt();			
//				}
//				if(actual.get("LOCATION") != null){
//					loc = actual.get("LOCATION").getAsString();
//				}
//				if(actual.get("ADDRESS_ID") != null){
//					if(actual.get("ADDRESS_ID").toString().equals("null")){
//						address = "0";
//						addID = Integer.parseInt(address);
//					}
//					else{
//						address = actual.get("ADDRESS_ID").getAsString();
//						addID = Integer.parseInt(address);
//					}
//				}
//				if(actual.get("FINEAMT") != null){
//					amt = actual.get("FINEAMT").getAsInt();
//				}
//				if(actual.get("TICKETISSUEDATE") != null){
//					date = actual.get("TICKETISSUEDATE").getAsString();
//				}
//				if(actual.get("VIOLATIONCODE") != null){
//					code = actual.get("VIOLATIONCODE").getAsString();
//				}
//				
//				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
//					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));
//				
//				}
//			}
//			JsonArray ja4 = (JsonArray) parser.parse(new FileReader(rutaMayo));
//			for(int i = 0; ja4 != null && i<ja4.size(); i++){
//				JsonObject actual = (JsonObject) ja.get(i);
//				if(actual.get("OBJECTID") != null){
//					obID = actual.get("OBJECTID").getAsInt();			
//				}
//				if(actual.get("LOCATION") != null){
//					loc = actual.get("LOCATION").getAsString();
//				}
//				if(actual.get("ADDRESS_ID") != null){
//					if(actual.get("ADDRESS_ID").toString().equals("null")){
//						address = "0";
//						addID = Integer.parseInt(address);
//					}
//					else{
//						address = actual.get("ADDRESS_ID").getAsString();
//						addID = Integer.parseInt(address);
//					}
//				}
//				if(actual.get("FINEAMT") != null){
//					amt = actual.get("FINEAMT").getAsInt();
//				}
//				if(actual.get("TICKETISSUEDATE") != null){
//					date = actual.get("TICKETISSUEDATE").getAsString();
//				}
//				if(actual.get("VIOLATIONCODE") != null){
//					code = actual.get("VIOLATIONCODE").getAsString();
//				}
//				
//				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
//					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));
//				
//				}
//			}
//			JsonArray ja5 = (JsonArray) parser.parse(new FileReader(rutaJunio));
//			for(int i = 0; ja5 != null && i<ja5.size(); i++){
//				JsonObject actual = (JsonObject) ja.get(i);
//				if(actual.get("OBJECTID") != null){
//					obID = actual.get("OBJECTID").getAsInt();			
//				}
//				if(actual.get("LOCATION") != null){
//					loc = actual.get("LOCATION").getAsString();
//				}
//				if(actual.get("ADDRESS_ID") != null){
//					if(actual.get("ADDRESS_ID").toString().equals("null")){
//						address = "0";
//						addID = Integer.parseInt(address);
//					}
//					else{
//						address = actual.get("ADDRESS_ID").getAsString();
//						addID = Integer.parseInt(address);
//					}
//				}
//				if(actual.get("FINEAMT") != null){
//					amt = actual.get("FINEAMT").getAsInt();
//				}
//				if(actual.get("TICKETISSUEDATE") != null){
//					date = actual.get("TICKETISSUEDATE").getAsString();
//				}
//				if(actual.get("VIOLATIONCODE") != null){
//					code = actual.get("VIOLATIONCODE").getAsString();
//				}
//				
//				if(obID != 0 && loc != null && addID != -1 && amt != 0 && date !=null && code != null){
//					arreglo.agregar(new VOMovingViolations(obID, loc, addID, amt, date, code));
//				
//				}
//			}
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
