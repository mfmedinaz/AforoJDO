package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class AdminLocal implements VOAdminLocal
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idAdmin;
	
	private long idLocalComercial;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public AdminLocal(long idAdmin, long idLocalComercial) {
		super();
		this.idAdmin = idAdmin;
		this.idLocalComercial = idLocalComercial;
	}

	public long getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(long idAdmin) {
		this.idAdmin = idAdmin;
	}

	public long getIdLocalComercial() {
		return idLocalComercial;
	}

	public void setIdLocalComercial(long idLocalComercial) {
		this.idLocalComercial = idLocalComercial;
	}

	@Override
	public String toString() {
		return "AdminLocal [idAdmin=" + idAdmin + ", idLocalComercial=" + idLocalComercial + "]";
	}	
	
	
		

}
