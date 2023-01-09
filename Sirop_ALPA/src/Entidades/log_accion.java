/**
 * 
 */
package Entidades;

import java.sql.Date;
import java.sql.Time;

/**
 * @author FFerr
 *
 */
public class log_accion {

	/**
	 * 
	 */
	
	private static int id;
	private static String log;
	private static Time hora;
	private static Date fecha;
	private static int usuario;
	
	public log_accion() {
		
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		log_accion.id = id;
	}

	public static String getLog() {
		return log;
	}

	public static void setLog(String log) {
		log_accion.log = log;
	}

	public static Time getHora() {
		return hora;
	}

	public static void setHora(Time hora) {
		log_accion.hora = hora;
	}

	public static Date getFecha() {
		return fecha;
	}

	public static void setFecha(Date fecha) {
		log_accion.fecha = fecha;
	}

	public static int getUsuario() {
		return usuario;
	}

	public static void setUsuario(int usuario) {
		log_accion.usuario = usuario;
	}
	
	

}
