package uniandes.isis2304.aforocc.negocio;

public class Espacio implements VOEspacio
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	public final static String LOCAL_COMERCIAL = "LOCAL_COMERCIAL";
	public final static String BANIO = "BAÑO";
	public final static String PARQUEADERO = "PARQUEADERO";
	public final static String ASCENSOR = "ASCENSOR";
	
	private long id;
	
	private String nombre;

	private long centro_Comercial;
	
	private long lector;
	
	private String tipo;
	
	private long estado;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Espacio(long id, String nombre, long centroComercial, long lector, String tipo, long estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.centro_Comercial = centroComercial;
		this.lector = lector;
		this.tipo = tipo;
		this.estado = estado;
	}
	
	

	public Espacio() {
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

	public long getCentro_Comercial() {
		return centro_Comercial;
	}

	public void setCentro_Comercial(Long centroComercial) {
		this.centro_Comercial = centroComercial;
	}

	public long getLector() {
		return lector;
	}

	public void setLector(Long lector) {
		this.lector = lector;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getEstado() {
		return estado;
	}

	public void setEstado(long estado) {
		this.estado = estado;
	}



	@Override
	public String toString() {
		return "Espacio [id=" + id + ", nombre=" + nombre + ", centroComercial=" + centro_Comercial + ", lector="
				+ lector + ", tipo=" + tipo + "]";
	}	

}
