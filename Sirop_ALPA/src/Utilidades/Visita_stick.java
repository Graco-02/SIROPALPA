package Utilidades;
import java.text.SimpleDateFormat;
import java.util.Date;

import Entidades.visita;
import utilidades.*;

public class Visita_stick {

	private Ticket ticket = new Ticket();
	
	public Visita_stick(visita visita){
		
		Date date=new Date();
		SimpleDateFormat fecha=new SimpleDateFormat("dd/MM/yyyy");
		
		
		ticket.AddCabecera("SIROP ALPA 1.0");
		ticket.AddCabecera(ticket.DarEspacio());
		ticket.AddCabecera("ALCO PARADISSE III");
		ticket.AddCabecera(ticket.DarEspacio());
		ticket.AddCabecera(ticket.DibujarLinea(20));
		ticket.AddCabecera(ticket.DarEspacio());
		
		ticket.AddSubCabecera("VISITANTE NO. "+visita.getId());
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		ticket.AddSubCabecera("EN FECHA.:"+fecha.format(date));
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		ticket.AddSubCabecera("VISITANTE "
		   +(visita.getPersona().getNombres()+","+visita.getPersona().getApellidos()));
		
		ticket.AddSubCabecera(ticket.DarEspacio());
		
		
		ticket.AddSubCabecera(ticket.DibujarLinea(29));
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea("!RECOJER SU IDENTIFICACION");
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea("AL SALIR. GRACIAS!");
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.AddPieLinea(ticket.DarEspacio());
		ticket.ImprimirDocumento();
		ticket.imprime_prueba();
	}
   
}
