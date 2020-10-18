package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOTipoVisitante 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public String getNombre();
	
	public Date getHoraInicialPermitida();
	
	public Date getHoraFinalPermitida();
	

	@Override
	public String toString();

}
