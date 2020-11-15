package uniandes.isis2304.aforocc.negocio;

import java.sql.Date;

public class TipoVisitante implements VOTipoVisitante
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	public final static String EMPLEADO_RESTAURANTE = "EMPLEADO_RESTAURANTE";
	public final static String EMPLEADO_SUPERMERCADO = "EMPLEADO_SUPERMERCADO";
	public final static String EMPLEADO_TIENDA = "EMPLEADO_TIENDA";
	public final static String EMPLEADO_CC = "EMPLEADO_CC";
	public final static String CONSUMIDOR = "CONSUMIDOR";
	
	private String nombre;
	
	private Date hora_Inicial_Permitida;
	
	private Date hora_Final_Permitida;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public TipoVisitante(String nombre, Date horaInicialPermitida, Date horaFinalPermitida) {
		super();
		this.nombre = nombre;
		hora_Inicial_Permitida = horaInicialPermitida;
		hora_Final_Permitida = horaFinalPermitida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getHoraInicialPermitida() {
		return hora_Inicial_Permitida;
	}

	public void setHoraInicialPermitida(Date horaInicialPermitida) {
		hora_Inicial_Permitida = horaInicialPermitida;
	}

	public Date getHoraFinalPermitida() {
		return hora_Final_Permitida;
	}

	public void setHoraFinalPermitida(Date horaFinalPermitida) {
		hora_Final_Permitida = horaFinalPermitida;
	}

	@Override
	public String toString() {
		return "TipoVisitante [nombre=" + nombre + ", HoraInicialPermitida=" + hora_Inicial_Permitida
				+ ", HoraFinalPermitida=" + hora_Final_Permitida + "]";
	}



}
