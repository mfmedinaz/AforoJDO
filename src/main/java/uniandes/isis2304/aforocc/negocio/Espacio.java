package uniandes.isis2304.aforocc.negocio;

public class Espacio implements VOEspacio
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String nombre;

	private long centro_Comercial;
	
	private long lector;
	
	private String tipo;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Espacio(long id, String nombre, long centroComercial, long lector, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.centro_Comercial = centroComercial;
		this.lector = lector;
		this.tipo = tipo;
	}
	
	

	public Espacio() {
		System.out.println("xd x2");
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

	@Override
	public String toString() {
		return "Espacio [id=" + id + ", nombre=" + nombre + ", centroComercial=" + centro_Comercial + ", lector="
				+ lector + ", tipo=" + tipo + "]";
	}	

}
