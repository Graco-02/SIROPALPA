/**
 * 
 */
package Entidades;

/**
 * @author Graco
 *
 */
public class Empresa {

	/**
	 * 
	 */
	private static String nombre,identificador,clave;
	private static Contacto contactos;
	private static Foto foto_logo;
	
	public Empresa() {
		
	}

	public static String getNombre() {
		return nombre;
	}

	public static void setNombre(String nombre) {
		Empresa.nombre = nombre;
	}

	public static String getIdentificador() {
		return identificador;
	}

	public static void setIdentificador(String identificador) {
		Empresa.identificador = identificador;
	}

	public static Contacto getContactos() {
		return contactos;
	}

	public static void setContactos(Contacto contactos) {
		Empresa.contactos = contactos;
	}


	public static Foto getFoto_logo() {
		return foto_logo;
	}

	public static void setFoto_logo(Foto foto_logo) {
		Empresa.foto_logo = foto_logo;
	}

	public static String getClave() {
		return clave;
	}

	public static void setClave(String clave) {
		Empresa.clave = clave;
	}

}
