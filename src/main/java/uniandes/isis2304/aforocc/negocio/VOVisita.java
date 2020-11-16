package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOVisita 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public String getHora_Inicial();
	
	public String getHora_Final();
	
	public long getVisitante();
	
	public long getLector();

	@Override
	public String toString();

}
