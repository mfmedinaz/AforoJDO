package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOVisita 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public Date getHoraInicial();
	
	public Date getHoraFinal();
	
	public long getVisitante();
	
	public long getLector();

	@Override
	public String toString();

}
