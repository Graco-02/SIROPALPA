/**
 * 
 */
package Entidades;

/**
 * @author FFerr
 *
 */
public class bloqueo {

	/**
	 * 
	 */
	private Persona reportado;
	private Lugar apt;
	private String motivo;
	private int bloqueo;
	private int estado;
	private int num;
	
	public bloqueo() {
		// TODO Auto-generated constructor stub
	}

	public Persona getReportado() {
		return reportado;
	}

	public Lugar getApt() {
		return apt;
	}

	public void setApt(Lugar apt) {
		this.apt = apt;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(int bloqueo) {
		this.bloqueo = bloqueo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void setReportado(Persona reportado) {
		this.reportado = reportado;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
