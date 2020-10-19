package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class AdminCC implements VOAdminCC
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idAdmin;
	
	private long idCentroComercial;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public AdminCC(long idAdmin, long idCentroComercial) {
		super();
		this.idAdmin = idAdmin;
		this.idCentroComercial = idCentroComercial;
	}

	public long getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(long idAdmin) {
		this.idAdmin = idAdmin;
	}

	public long getIdCentroComercial() {
		return idCentroComercial;
	}

	public void setIdCentroComercial(long idCentroComercial) {
		this.idCentroComercial = idCentroComercial;
	}

	@Override
	public String toString() {
		return "AdminLocal [idAdmin=" + idAdmin + ", idCentroComercial=" + idCentroComercial + "]";
	}	
	
	
		

}
