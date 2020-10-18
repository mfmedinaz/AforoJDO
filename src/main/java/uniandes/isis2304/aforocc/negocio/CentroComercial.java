package uniandes.isis2304.aforocc.negocio;

public class CentroComercial implements VOCentroComercial
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private long lectorEntradaCC;


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public CentroComercial(long id, long lectorEntradaCC) {
		super();
		this.id = id;
		this.lectorEntradaCC = lectorEntradaCC;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLectorEntradaCC() {
		return lectorEntradaCC;
	}

	public void setLectorEntradaCC(long lectorEntradaCC) {
		this.lectorEntradaCC = lectorEntradaCC;
	}

	@Override
	public String toString() {
		return "CentroComercial [id=" + id + ", lectorEntradaCC=" + lectorEntradaCC + "]";
	}

}
