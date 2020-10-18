package uniandes.isis2304.aforocc.negocio;

public class Lector implements VOLector
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	public Lector(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Lector [id=" + id + "]";
	}		
	

	

}
