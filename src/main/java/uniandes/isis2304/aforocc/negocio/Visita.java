package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class Visita implements VOVisita
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String horaInicial;
	
	private String horaFinal;
	
	private long visitante;
	
	private long lector;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Visita(long id, String horaInicial, String horaFinal, long visitante, long lector) {
		super();
		this.id = id;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.visitante = visitante;
		this.lector = lector;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public long getVisitante() {
		return visitante;
	}

	public void setVisitante(long visitante) {
		this.visitante = visitante;
	}

	public long getLector() {
		return lector;
	}

	public void setLector(long lector) {
		this.lector = lector;
	}

	@Override
	public String toString() {
		return "Visita [id=" + id + ", horaInicial=" + horaInicial + ", horaFinal=" + horaFinal + ", visitante="
				+ visitante + ", lector=" + lector + "]";
	}
	
	
		

}
