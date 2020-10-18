package uniandes.isis2304.aforocc.negocio;

public interface VOLocalComercial 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getIdEspacio();
	
	public int getArea();
	
	public String getTipoEstablecimiento();

	@Override
	public String toString();

}
