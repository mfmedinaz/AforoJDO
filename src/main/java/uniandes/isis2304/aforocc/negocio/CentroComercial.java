package uniandes.isis2304.aforocc.negocio;

public class CentroComercial implements VOCentroComercial
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private long lector_Entrada_CC;
	
	private long area_total;


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public CentroComercial()
	{
	}
	
	

	public CentroComercial(long id, long lector_Entrada_CC, long area_total) {
		super();
		this.id = id;
		this.lector_Entrada_CC = lector_Entrada_CC;
		this.area_total = area_total;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLector_Entrada_CC() {
		return lector_Entrada_CC;
	}

	public void setLector_Entrada_CC(long lector_Entrada_CC) {
		this.lector_Entrada_CC = lector_Entrada_CC;
	}

	public long getArea_total() {
		return area_total;
	}

	public void setArea_total(long area_total) {
		this.area_total = area_total;
	}

	@Override
	public String toString() {
		return "CentroComercial [id=" + id + ", lectorEntradaCC=" + lector_Entrada_CC + "]";
	}

}
