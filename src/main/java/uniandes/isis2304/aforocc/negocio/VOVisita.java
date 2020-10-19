package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOVisita 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public String getHoraInicial();
	
	public String getHoraFinal();
	
	public long getVisitante();
	
	public long getLector();

	@Override
	public String toString();

}
