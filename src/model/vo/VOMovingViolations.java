package model.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Representation of a Trip object
 */
public class VOMovingViolations implements Comparable<VOMovingViolations>{

	/**
	 * Atributo que da el FINEAMT de la infraccion.
	 */
	private int fineAMT;

	/**
	 * Atributo que da el violationcode de la infraccion.
	 */
	private String violationCode;

	
	/**
	 * Atributo que da el identificador de la ubicacion de la infraccion.
	 */
	private int addressID;
	
	/**
	 * Atributo que da el identificador de la infraccion.
	 */
	private int ObjectID;

	/**
	 * Atributo que da la fecha de la infracci�n
	 */
	private String ticketIssueDate;

	/**
	 * Atributo que da la localizacion de la infraccion
	 */
	private String location;
	/**
	 * Constructor VOMovingViolations
	 * @param pLocation localizacion de la infraccion
	 * @param pIssueDate fecha de la infracci�n
	 * @param pAddress direccion de la infracci�n

	 */
	public VOMovingViolations(String pIssueDate, int pAddress, String pLocation){

		location = pLocation;
		addressID = pAddress;
		ticketIssueDate = pIssueDate;

	}

	public String darLocation(){
		return location;
	}

	public int darAddressID(){
		return addressID;
	}
	public String darFecha(){
		return ticketIssueDate;
	}
	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	{
		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'"));
	}

	public LocalDateTime darFechaLocalDateTime(){
		return convertirFecha_Hora_LDT(ticketIssueDate);
	}


	@Override
	public int compareTo(VOMovingViolations o) {
		// TODO Auto-generated method stub
		return 0;
	}



}
