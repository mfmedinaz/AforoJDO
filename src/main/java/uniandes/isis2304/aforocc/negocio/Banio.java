package uniandes.isis2304.aforocc.negocio;

public class Banio implements VOBanio
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idEspacio;
	
	private int numSanitarios;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Banio(long idEspacio, int numSanitarios) {
		super();
		this.idEspacio = idEspacio;	
		this.numSanitarios = numSanitarios;
	}
	
	public long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(long idEspacio) {
		this.idEspacio = idEspacio;
	}

	public int getNumSanitarios() {
		return numSanitarios;
	}

	public void setnumSanitarios(int numSanitarios) {
		this.numSanitarios = numSanitarios;
	}

	@Override
	public String toString() {
		return "Parqueadero [idEspacio=" + idEspacio + ", numSanitarios=" + numSanitarios + "]";
	}

	

}
