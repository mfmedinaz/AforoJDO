package uniandes.isis2304.aforocc.negocio;

public interface VOEspacio 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public String getNombre();
	
	public long getCentro_Comercial();
	
	public long getLector();
	
	public String getTipo();
	
	public long getEstado();

	@Override
	public String toString();

}
