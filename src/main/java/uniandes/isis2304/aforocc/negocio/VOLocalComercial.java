package uniandes.isis2304.aforocc.negocio;

public interface VOLocalComercial 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId_Espacio();
	
	public int getArea();
	
	public String getTipo_Establecimiento();
	
	public String getEstado();
	
	public int getClientes_atendidos();

	@Override
	public String toString();

}
