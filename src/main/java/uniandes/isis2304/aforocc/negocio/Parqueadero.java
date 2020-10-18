package uniandes.isis2304.aforocc.negocio;

public class Parqueadero implements VOParqueadero
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idEspacio;
	
	private int capacidadNormal;	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Parqueadero(long idEspacio, int capacidadNormal) {
		super();
		this.idEspacio = idEspacio;
		this.capacidadNormal = capacidadNormal;
	}
	
	public long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(long idEspacio) {
		this.idEspacio = idEspacio;
	}

	public int getCapacidadNormal() {
		return capacidadNormal;
	}

	public void setCapacidadNormal(int capacidadNormal) {
		this.capacidadNormal = capacidadNormal;
	}

	@Override
	public String toString() {
		return "Parqueadero [idEspacio=" + idEspacio + ", capacidadNormal=" + capacidadNormal + "]";
	}

	

}
