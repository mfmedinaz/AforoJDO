package uniandes.isis2304.aforocc.negocio;

public class Visitante implements VOVisitante
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String nombre;
	
	private String correo;
	
	private String telefono;
	
	private String nombreEmergencia;
	
	private String telefonoEmergencia;
	
	private String tipoVisitante;
	
	private String codigoQR;
	
	private long centroComercial;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Visitante(long id, String nombre, String correo, String telefono, String nombreEmergencia,
			String telefonoEmergencia, String tipoVisitante, String codigoQR, long centroComercial) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.nombreEmergencia = nombreEmergencia;
		this.telefonoEmergencia = telefonoEmergencia;
		this.tipoVisitante = tipoVisitante;
		this.codigoQR = codigoQR;
		this.centroComercial = centroComercial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreEmergencia() {
		return nombreEmergencia;
	}

	public void setNombreEmergencia(String nombreEmergencia) {
		this.nombreEmergencia = nombreEmergencia;
	}

	public String getTelefonoEmergencia() {
		return telefonoEmergencia;
	}

	public void setTelefonoEmergencia(String telefonoEmergencia) {
		this.telefonoEmergencia = telefonoEmergencia;
	}

	public String getTipoVisitante() {
		return tipoVisitante;
	}

	public void setTipoVisitante(String tipoVisitante) {
		this.tipoVisitante = tipoVisitante;
	}

	public String getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}

	public long getCentroComercial() {
		return centroComercial;
	}

	public void setCentroComercial(long centroComercial) {
		this.centroComercial = centroComercial;
	}

	@Override
	public String toString() {
		return "Visitante [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono
				+ ", nombreEmergencia=" + nombreEmergencia + ", telefonoEmergencia=" + telefonoEmergencia
				+ ", tipoVisitante=" + tipoVisitante + ", codigoQR=" + codigoQR + ", centroComercial=" + centroComercial
				+ "]";
	}


}
