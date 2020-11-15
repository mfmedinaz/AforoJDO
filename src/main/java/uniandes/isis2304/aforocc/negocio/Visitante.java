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
	
	private String nombre_Emergencia;
	
	private String telefono_Emergencia;
	
	private String tipo_Visitante;
	
	private String codigo_QR;
	
	private long centro_Comercial;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	
	
	public Visitante()
	{
	}
	
	public Visitante(long id, String nombre, String correo, String telefono, String nombre_Emergencia,
			String telefono_Emergencia, String tipo_Visitante, String codigo_QR, long centro_Comercial) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.nombre_Emergencia = nombre_Emergencia;
		this.telefono_Emergencia = telefono_Emergencia;
		this.tipo_Visitante = tipo_Visitante;
		this.codigo_QR = codigo_QR;
		this.centro_Comercial = centro_Comercial;
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

	public String getNombre_Emergencia() {
		return nombre_Emergencia;
	}

	public void setNombre_Emergencia(String nombre_Emergencia) {
		this.nombre_Emergencia = nombre_Emergencia;
	}

	public String getTelefono_Emergencia() {
		return telefono_Emergencia;
	}

	public void setTelefono_Emergencia(String telefono_Emergencia) {
		this.telefono_Emergencia = telefono_Emergencia;
	}

	public String getTipo_Visitante() {
		return tipo_Visitante;
	}

	public void setTipo_Visitante(String tipo_Visitante) {
		this.tipo_Visitante = tipo_Visitante;
	}

	public String getCodigo_QR() {
		return codigo_QR;
	}

	public void setCodigo_QR(String codigo_QR) {
		this.codigo_QR = codigo_QR;
	}

	public long getCentro_Comercial() {
		return centro_Comercial;
	}

	public void setCentro_Comercial(long centro_Comercial) {
		this.centro_Comercial = centro_Comercial;
	}

	@Override
	public String toString() {
		return "Visitante [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono
				+ ", nombreEmergencia=" + nombre_Emergencia + ", telefonoEmergencia=" + telefono_Emergencia
				+ ", tipoVisitante=" + tipo_Visitante + ", codigoQR=" + codigo_QR + ", centroComercial=" + centro_Comercial
				+ "]";
	}


}
