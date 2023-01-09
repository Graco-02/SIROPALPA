package Utilidades;
import java.text.SimpleDateFormat;
import java.util.Date;

import Controladores.Persona_control;
import Controladores.Reg_paquetes_control;
import Entidades.Paquete;
import Entidades.Persona;
import Entidades.visita;
import utilidades.*;

public class Paquete_stick {

	private Ticket ticket = new Ticket();
	
	public Paquete_stick(Paquete paquete){
		
		Date date=new Date();
		SimpleDateFormat fecha=new SimpleDateFormat("dd/MM/yyyy");
		Persona persona = new Persona_control().get_persona(paquete.getId_receptor());
		
		ticket.AddCabecera("SIROP ALPA 1.0");
		ticket.AddCabecera(ticket.DarEspacio());
		ticket.AddCabecera("ALCO PARADISSE III");
		ticket.AddCabecera(ticket.DarEspacio());
		ticket.AddCabecera(ticket.DibujarLinea(20));
		ticket.AddCabecera(ticket.DarEspacio());
		
		ticket.AddSubCabecera("PAQUETE NO. "+paquete.getId());
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		
		ticket.AddSubCabecera("RECIBIDO EN FECHA.:"+fecha.format(date));
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		ticket.AddSubCabecera("PARA..: " 
		+  persona.getNombres() + ","
		+ persona.getApellidos() );
		
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		
		ticket.AddSubCabecera(ticket.DibujarLinea(29));
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea("!PASE UN EXCELENTE RESTO DEL DIA");
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		
		ticket.ImprimirDocumento();
		ticket.imprime_prueba();
	}
   
}
