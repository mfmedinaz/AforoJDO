package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class TipoEstablecimiento implements VOTipoEstablecimiento
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombre;
	
	private Date HoraApertura;
	
	private Date HoraCierre;

	public TipoEstablecimiento(String nombre, Date horaApertura, Date horaCierre) {
		super();
		this.nombre = nombre;
		HoraApertura = horaApertura;
		HoraCierre = horaCierre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHoraApertura() {
		return HoraApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		HoraApertura = horaApertura;
	}

	public Date getHoraCierre() {
		return HoraCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		HoraCierre = horaCierre;
	}

	@Override
	public String toString() {
		return "TipoEstablecimiento [nombre=" + nombre + ", HoraApertura=" + HoraApertura + ", HoraCierre=" + HoraCierre
				+ "]";
	}

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	



}
