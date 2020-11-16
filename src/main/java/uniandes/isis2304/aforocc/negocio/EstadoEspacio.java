package uniandes.isis2304.aforocc.negocio;

public class EstadoEspacio implements VOEstadoEspacio
{
	public final static String DESOCUPADO = "DESOCUPADO";
	public final static String DESHABILITADO = "DESHABILITADO";
	public final static String ROJO = "ROJO";
	public final static String NARANJA = "NARANJA";
	public final static String VERDE = "VERDE";
	
	private long id;
	private String nombre;
	private String fecha_asignacion;
	
	public EstadoEspacio(long id, String nombre, String fecha_asignacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fecha_asignacion = fecha_asignacion;
	}
	
	public EstadoEspacio()
	{
		
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

	public String getFecha_asignacion() {
		return fecha_asignacion;
	}

	public void setFecha_asignacion(String fecha_asignacion) {
		this.fecha_asignacion = fecha_asignacion;
	}
}
