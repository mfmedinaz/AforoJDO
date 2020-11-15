package uniandes.isis2304.aforocc.negocio;

public class LocalComercial implements VOLocalComercial
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	public final static String CERRADO = "CERRADO";
	
	public final static String ABIERTO = "ABIERTO";
	
	private long id_Espacio;
	
	private int area;

	private String tipo_Establecimiento;
	
	private String estado;
	
	private int clientes_atendidos;

	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public LocalComercial(long id_Espacio, int area, String tipo_Establecimiento, String estado, int clientes_atendidos) {
		super();
		this.id_Espacio = id_Espacio;
		this.area = area;
		this.tipo_Establecimiento = tipo_Establecimiento;
		this.estado = estado;
		this.clientes_atendidos = clientes_atendidos;
	}
	
	public LocalComercial()
	{
		
	}

	public long getId_Espacio() {
		return id_Espacio;
	}

	public void setId_Espacio(long id_Espacio) {
		this.id_Espacio = id_Espacio;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getTipo_Establecimiento() {
		return tipo_Establecimiento;
	}

	public void setTipo_Establecimiento(String tipo_Establecimiento) {
		this.tipo_Establecimiento = tipo_Establecimiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getClientes_atendidos() {
		return clientes_atendidos;
	}

	public void setClientes_atendidos(int clientes_atendidos) {
		this.clientes_atendidos = clientes_atendidos;
	}
	
	
	
	

}
