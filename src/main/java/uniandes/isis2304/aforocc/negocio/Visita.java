package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class Visita implements VOVisita
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long id;
	
	private String hora_Inicial;
	
	private String hora_Final;
	
	private long visitante;
	
	private long lector;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Visita(long id, Date horaInicial, Date horaFinal, long visitante, long lector) {
		super();
		this.id = id;
		this.hora_Inicial = horaInicial +"";
		this.hora_Final = horaFinal+"";
		this.visitante = visitante;
		this.lector = lector;
	}
	
	public Visita(long id, String horaInicial, String horaFinal, long visitante, long lector) {
		super();
		this.id = id;
		this.hora_Inicial = horaInicial +"";
		this.hora_Final = horaFinal+"";
		this.visitante = visitante;
		this.lector = lector;
	}
	public Visita()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHora_Inicial() {
		return hora_Inicial;
	}

	public void setHora_Inicial(String horaInicial) {
		this.hora_Inicial = horaInicial;
	}

	public String getHora_Final() {
		return hora_Final;
	}

	public void setHora_Final(String horaFinal) {
		this.hora_Final = horaFinal;
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
		return "Visita [id=" + id + ", horaInicial=" + hora_Inicial + ", horaFinal=" + hora_Final + ", visitante="
				+ visitante + ", lector=" + lector + "]";
	}
	
	
		

}
