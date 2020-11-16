package uniandes.isis2304.aforocc.negocio;

import java.util.Date;

public interface VOVisita 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public Date getHora_Inicial();
	
	public Date getHora_Final();
	
	public long getVisitante();
	
	public long getLector();

	@Override
	public String toString();

}
