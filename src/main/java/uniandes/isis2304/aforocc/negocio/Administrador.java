package uniandes.isis2304.aforocc.negocio;

public class Administrador implements VOAdministrador
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String nombre;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Administrador(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", nombre=" + nombre + "]";
	}		

}
