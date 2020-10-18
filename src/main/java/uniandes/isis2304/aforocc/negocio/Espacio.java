package uniandes.isis2304.aforocc.negocio;

public class Espacio implements VOEspacio
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String nombre;

	private long centroComercial;
	
	private long lector;
	
	private String tipo;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Espacio(long id, String nombre, long centroComercial, long lector, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.centroComercial = centroComercial;
		this.lector = lector;
		this.tipo = tipo;
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

	public long getCentroComercial() {
		return centroComercial;
	}

	public void setCentroComercial(Long centroComercial) {
		this.centroComercial = centroComercial;
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

	@Override
	public String toString() {
		return "Espacio [id=" + id + ", nombre=" + nombre + ", centroComercial=" + centroComercial + ", lector="
				+ lector + ", tipo=" + tipo + "]";
	}	

}
