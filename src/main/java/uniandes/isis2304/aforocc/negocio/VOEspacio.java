package uniandes.isis2304.aforocc.negocio;

public interface VOEspacio 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public String getNombre();
	
	public long getCentroComercial();
	
	public long getLector();
	
	public String getTipo();

	@Override
	public String toString();

}
