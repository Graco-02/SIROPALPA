/**
 * 
 */
package Utilidades;

import javax.mail.MessagingException;

import Controladores.Persona_control;
import Email.Email_Send;
import Email.Email_Validador;
import Entidades.Paquete;
import Entidades.Persona;

/**
 * @author FFerr
 * Esta clase se encarga de moldear, crear y enviar correos a los distintos condomines
 *  avisandoles de la recepcion de un paquete a su nombre
 */
public class Notificacion_paq {

	/**
	 * @throws MessagingException 
	 * 
	 */
	public Notificacion_paq(Paquete paq) throws MessagingException {
		// TODO Auto-generated constructor stub
		
		String asunto = "PAQUETE RECIBIDO";
		
		String quien_envia = "fferrer0287@gmail.com";
		String clave = "02Octubre";
		
	
		String contenido = " Saludos le estamos notificando que hemos recivido un paquete para usted \n"
				+ " con la siguiente descripcion..: "
				+ paq.getDescripcion() + "\n "
				+ "el cual fue depositado por.: "+paq.getDepositante().getNombres()+","+paq.getDepositante().getApellidos()
				;
		
		
		
		Persona receptor = new Persona_control().get_persona(paq.getId_receptor());
		
		Email_Send gestor_email = new Email_Send(asunto, contenido);
		
		
		
		for(int i=0;i<receptor.getContacto().get_lista_correos().size();i++){//el for recorre los elementos de correo asignados a este contacto en caso de tener mas de uno
			
			if(receptor.getContacto().get_Correo(i).get_correo().length() > 0){//valido que el correo agregaado este bien formado
				
			  switch (new Email_Validador().get_host(quien_envia)) {//valido el tipo de host
				
			  	case 'h'://de ser 'h' es Hotmail
					
					gestor_email.send_hotmail(quien_envia, clave, receptor.getContacto().get_Correo(i).get_correo());
			  		
					break;
					
				case 'g'://de ser 'g' es Gmail
					
					gestor_email.send_Gmail(quien_envia, clave, receptor.getContacto().get_Correo(i).get_correo());
					
					break;

				default:
					break;
			  }
				
			}//fin del bucle for
			
		}
		
	}

}
