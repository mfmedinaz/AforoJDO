package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class TipoVisitante implements VOTipoVisitante
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombre;
	
	private Date HoraInicialPermitida;
	
	private Date HoraFinalPermitida;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public TipoVisitante(String nombre, Date horaInicialPermitida, Date horaFinalPermitida) {
		super();
		this.nombre = nombre;
		HoraInicialPermitida = horaInicialPermitida;
		HoraFinalPermitida = horaFinalPermitida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHoraInicialPermitida() {
		return HoraInicialPermitida;
	}

	public void setHoraInicialPermitida(Date horaInicialPermitida) {
		HoraInicialPermitida = horaInicialPermitida;
	}

	public Date getHoraFinalPermitida() {
		return HoraFinalPermitida;
	}

	public void setHoraFinalPermitida(Date horaFinalPermitida) {
		HoraFinalPermitida = horaFinalPermitida;
	}

	@Override
	public String toString() {
		return "TipoVisitante [nombre=" + nombre + ", HoraInicialPermitida=" + HoraInicialPermitida
				+ ", HoraFinalPermitida=" + HoraFinalPermitida + "]";
	}



}
