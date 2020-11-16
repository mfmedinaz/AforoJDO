package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOTipoEstablecimiento 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public String getNombre();
	
	public Date getHora_Apertura();
	
	public Date getHora_Cierre();
	

	@Override
	public String toString();

}
