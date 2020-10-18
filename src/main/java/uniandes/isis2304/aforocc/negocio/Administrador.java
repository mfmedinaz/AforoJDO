package uniandes.isis2304.aforocc.negocio;

public class Administrador implements VOAdministrador
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String nombre;
	
	private long centroComercial;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Administrador(long id, String nombre, long centroComercial) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	public long getCentroComercial() {
		return centroComercial;
	}

	public void setCentroComercial(long centroComercial) {
		this.centroComercial = centroComercial;
	}

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", nombre=" + nombre + ", centroComercial=" + centroComercial + "]";
	}		

}
