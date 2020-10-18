package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public interface VOVisitante 
{
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getId();
	
	public String getNombre();
	
	public String getCorreo();
	
	public String getTelefono();
	
	public String getNombreEmergencia();
	
	public String getTelefonoEmergencia();
	
	public String getTipoVisitante();
	
	public String getCodigoQR();
	
	public long getCentroComercial();

	@Override
	public String toString();

}
