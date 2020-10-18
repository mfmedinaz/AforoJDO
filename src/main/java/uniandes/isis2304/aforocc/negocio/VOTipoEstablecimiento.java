package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOTipoEstablecimiento 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public String getNombre();
	
	public Date getHoraApertura();
	
	public Date getHoraCierre();
	

	@Override
	public String toString();

}
