package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class TipoEstablecimiento implements VOTipoEstablecimiento
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	
	private String nombre;
	
	private Date hora_apertura;
	
	private Date hora_cierre;

	public TipoEstablecimiento(String nombre, Date horaApertura, Date horaCierre) {
		super();
		this.nombre = nombre;
		hora_apertura = horaApertura;
		hora_cierre = horaCierre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHora_Apertura() {
		return hora_apertura;
	}

	public void setHora_Apertura(Date horaApertura) {
		hora_apertura = horaApertura;
	}

	public Date getHora_Cierre() {
		return hora_cierre;
	}

	public void setHora_Cierre(Date horaCierre) {
		hora_cierre = horaCierre;
	}

	@Override
	public String toString() {
		return "TipoEstablecimiento [nombre=" + nombre + ", HoraApertura=" + hora_apertura + ", HoraCierre=" + hora_cierre
				+ "]";
	}

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	



}
