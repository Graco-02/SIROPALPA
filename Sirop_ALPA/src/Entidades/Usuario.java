package Entidades;

public class Usuario {

	private static int id,llave,estado;
	private static String user,clave,fecha;
	private static int correo_ini,telefono_ini;
	private static int id_habitante;
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Usuario.id = id;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Usuario.user = user;
	}

	public static String getClave() {
		return clave;
	}

	public static void setClave(String clave) {
		Usuario.clave = clave;
	}

	public static String getFecha() {
		return fecha;
	}

	public static void setFecha(String fecha) {
		Usuario.fecha = fecha;
	}

	public static int getLlave() {
		return llave;
	}

	public static void setLlave(int llave) {
		Usuario.llave = llave;
	}

	public static int getEstado() {
		return estado;
	}

	public static void setEstado(int estado) {
		Usuario.estado = estado;
	}

	public static int getTelefono_ini() {
		return telefono_ini;
	}

	public static void setTelefono_ini(int telefono_ini) {
		Usuario.telefono_ini = telefono_ini;
	}

	public static int getCorreo_ini() {
		return correo_ini;
	}

	public static void setCorreo_ini(int correo_ini) {
		Usuario.correo_ini = correo_ini;
	}

	public static int getId_habitrante() {
		return id_habitante;
	}

	public static void setId_habitante(int id_habitante) {
		Usuario.id_habitante = id_habitante;
	}

}
