package uniandes.isis2304.aforocc.persistencia;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import java.util.Date;
import java.util.List;

import uniandes.isis2304.aforocc.negocio.Espacio;
import uniandes.isis2304.aforocc.negocio.VOEspacio;
import uniandes.isis2304.aforocc.negocio.Visita;
import uniandes.isis2304.aforocc.negocio.Visitante;

public class SQLEspacio 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAforoCC.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAforoCC pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLEspacio (PersistenciaAforoCC pp)
	{
		this.pp = pp;
	}
	
	
	public Espacio darEspacioPorNombre(PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEspacio() + " WHERE nombre = ?");
		q.setResultClass(Espacio.class);
		q.setParameters(nombre);
		return (Espacio) q.executeUnique();
	}
	
	public Espacio darEspacioPorLector(PersistenceManager pm, long idLector)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEspacio() + " WHERE lector = ?");
		q.setResultClass(Espacio.class);
		q.setParameters(idLector);
		return (Espacio) q.executeUnique();
	}
	
	public Espacio darEspacioPorId(PersistenceManager pm, long idEspacio)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEspacio() + " WHERE ID = ?");
		q.setResultClass(Espacio.class);
		q.setParameters(idEspacio);
		return (Espacio) q.executeUnique();
	}
	
	public List<Espacio> darEspacios(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEspacio());
		q.setResultClass(Espacio.class);
		return (List<Espacio>) q.executeList();
	}
	
/**
 * Retorna visitantes de un espacio en un rango de tiempo dado
 * @param pm Manejador de persistencia
 * @param espacio Espacio que se quiere consultar
 * @param horaIni Hora Inicial del rango de horas que se quiere consultar
 * @param horaFin Hora final del rango de horas que se quiere consultar
 * @return
 */
	public List<Visitante> darVisitantesEspacio(PersistenceManager pm, String espacio, String horaIni, String horaFin)
	{
		String q1 = "SELECT VISITANTE.* ";
		q1 += "FROM " + pp.darTablaVisitante();
		q1+=" INNER JOIN " + pp.darTablaVisita();
		q1+=" ON VISITANTE.id  = VISITA.visitante";
		q1+=" INNER JOIN " + pp.darTablaEspacio();
		q1+=" ON VISITA.lector = ESPACIO.lector";
		q1+=" WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin + "' , 'hh24:mi:ss') AND ESPACIO.nombre = '" + espacio + "'";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visitante.class);
		q.setParameters(espacio);
		
		return (List<Visitante>) q.executeList();
	}
	
	public List<String> mostrar20EstablecimientosMasPopulares(PersistenceManager pm, String horaIni, String horaFin)
	{
		String q1 = "SELECT ESPACIO.nombre"
				+ " FROM " + pp.darTablaVisitante()
				+ " INNER JOIN VISITA "  + pp.darTablaVisita()
				+ " ON VISITANTE.id = VISITA.visitante"
				+ " INNER JOIN " + pp.darTablaEspacio()
				+ " ON VISITA.lector = ESPACIO.lector"
				+ " INNER JOIN " + pp.darTablaLocalComercial()
				+ " ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id"
				+ " WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin + "', 'hh24:mi:ss')"
				+ " GROUP BY ESPACIO.nombre, ESPACIO.id"
				+ " ORDER BY COUNT(ESPACIO.id) DESC";

		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(String.class);
		
		return (List<String>) q.executeList();
	}
	
	public int mostrarAforoRealEstablecimiento(PersistenceManager pm, String horaIni, String horaFin, String idEspacio)
	{
		String q1 = "SELECT COUNT(VISITANTE.id)\r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector"
				+ " WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin + "', 'hh24:mi:ss') AND ESPACIO.id = " + idEspacio;
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Integer.class);
		
		return (Integer) q.executeUnique();
	}
	
	public int mostrarAforoRealTipoEstablecimiento(PersistenceManager pm, String horaIni, String horaFin, String tipoEstablecimiento)
	{
		String q1 = "SELECT COUNT(VISITANTE.id)\r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector\r\n"
				+ "INNER JOIN LOCAL_COMERCIAL\r\n"
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id"
				+ " WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin + "', 'hh24:mi:ss') AND LOCAL_COMERCIAL.tipo_establecimiento = '" + tipoEstablecimiento + "'";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Integer.class);
		
		return (Integer) q.executeUnique();
	}
	
	public int mostrarAreaEstablecimiento(PersistenceManager pm, String idEspacio	)
	{
		String q1 = "SELECT LOCAL_COMERCIAL.area\r\n"
				+ "FROM ESPACIO\r\n"
				+ "INNER JOIN LOCAL_COMERCIAL\r\n"
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id\r\n"
				+ "WHERE ESPACIO.id = '" + idEspacio + "'";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Integer.class);
		
		return (Integer) q.executeUnique();
	}
	
	public List<Integer> mostrarAreasTipoEstablecimiento(PersistenceManager pm, String horaIni, String horaFin, String tipoEstablecimiento)
	{
		String q1 = "SELECT LOCAL_COMERCIAL.area\r\n"
				+ "FROM ESPACIO\r\n"
				+ "INNER JOIN LOCAL_COMERCIAL\r\n"
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id\r\n"
				+ "WHERE LOCAL_COMERCIAL.tipo_establecimiento = '" + tipoEstablecimiento + "'";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Integer.class);
		
		return (List<Integer>) q.executeList();
	}
	
	public long actualizarEstado(PersistenceManager pm, long idEspacio, long idNuevoEstado)
	{
		String q1 = "UPDATE " + pp.darTablaEspacio() + " SET estado = ? WHERE ID = ?";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(idNuevoEstado, idEspacio);
		return (long) q.executeUnique(); 	
	}
	
	public List<Espacio> darEspacioVisitadosPorVisitanteDeterminado(PersistenceManager pm, String idVisitante, Date fechaD)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
		String fecha= sdf.format(fechaD);
		String q1 = "SELECT ESPACIO.*"
				+ " FROM VISITANTE"
				+ " INNER JOIN VISITA"
				+ " ON VISITANTE.id = VISITA.visitante"
				+ " INNER JOIN ESPACIO"
				+ " ON VISITA.lector = ESPACIO.lector"
				+ " WHERE VISITANTE.id = " + idVisitante + " AND hora_inicial BETWEEN TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Espacio.class);
		
		return (List<Espacio>) q.executeList();
	}
	
	public List<Visita> darVisitasEnCurso(PersistenceManager pm, long idEspacio)
	{
		String q1 = "SELECT VISITA.* ";
		q1 += "FROM " + pp.darTablaVisita() + ", "  + pp.darTablaLector() + ", " + pp.darTablaEspacio(); 
		q1 += " WHERE visita.lector = LECTOR.ID AND ";
		q1 += "espacio.lector = LECTOR.ID AND ";
		q1 += "visita.hora_final IS NULL AND ";
		q1 += "espacio.id = ?";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visita.class);
		q.setParameters(idEspacio);

		return (List<Visita>) q.executeList();
	}
	
}
