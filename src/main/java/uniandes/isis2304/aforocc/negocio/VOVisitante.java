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
	
	public String getNombre_Emergencia();
	
	public String getTelefono_Emergencia();
	
	public String getTipo_Visitante();
	
	public String getCodigo_QR();
	
	public long getCentro_Comercial();

	@Override
	public String toString();

}
