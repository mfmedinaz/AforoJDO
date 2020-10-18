package uniandes.isis2304.aforocc.negocio;

public class LocalComercial implements VOLocalComercial
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idEspacio;
	
	private int area;

	private String tipoEstablecimiento;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public LocalComercial(long idEspacio, int area, String tipoEstablecimiento) {
		super();
		this.idEspacio = idEspacio;
		this.area = area;
		this.tipoEstablecimiento = tipoEstablecimiento;
	}	

	public long getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(long idEspacio) {
		this.idEspacio = idEspacio;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	@Override
	public String toString() {
		return "LocalComercial [idEspacio=" + idEspacio + ", area=" + area + ", tipoEstablecimiento="
				+ tipoEstablecimiento + "]";
	}
	
	

}
