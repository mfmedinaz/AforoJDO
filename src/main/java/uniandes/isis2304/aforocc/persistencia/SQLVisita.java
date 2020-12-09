package uniandes.isis2304.aforocc.persistencia;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Espacio;
import uniandes.isis2304.aforocc.negocio.Visita;
import uniandes.isis2304.aforocc.negocio.Visitante;

public class SQLVisita 
{
	private final static String SQL = PersistenciaAforoCC.SQL;
	
	private PersistenciaAforoCC pa;
	
	public SQLVisita (PersistenciaAforoCC pa)
	{
		this.pa = pa;
	}
	
	public long registrarEntradaVisitanteEspacio (PersistenceManager pm, long idVisita, Date horaInicial, Date horaFinal, long idVisitante, long idLector ) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss"); 
		String fecha= sdf.format(horaInicial);
		
		String sql = "INSERT INTO " + pa.darTablaVisita();
		sql+=" (id, hora_inicial, hora_final, visitante, lector)";
		sql += " VALUES ";
		sql += "( ? , TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS') , null, ?, ?)";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idVisita,idVisitante, idLector);

		long resp = (long) q.executeUnique();
		return resp;
	}
	
	public long registrarSalidaVisitanteEspacio (PersistenceManager pm, long idVisitante, long idLector, Date horaFinal)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss"); 
		String fecha= sdf.format(horaFinal);
		Query q = pm.newQuery(SQL, "UPDATE " + pa.darTablaVisita () + " SET hora_final = "
				+ "TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS') WHERE visitante = ? AND lector = ?");
		q.setParameters(idVisitante, idLector);
		return (long) q.executeUnique(); 		
	}
	
	public List<Visita> darVisitasPorVisitanteDeterminado(PersistenceManager pm, String idVisitante, Date fechaD)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
		String fecha= sdf.format(fechaD);
		String q1 = "SELECT VISITA.*"
				+ " FROM VISITANTE"
				+ " INNER JOIN VISITA"
				+ " ON VISITANTE.id = VISITA.visitante"
				+ " INNER JOIN ESPACIO"
				+ " ON VISITA.lector = ESPACIO.lector"
				+ " WHERE VISITANTE.id = " + idVisitante + " AND hora_inicial BETWEEN TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visita.class);
		
		return (List<Visita>) q.executeList();
	}
	
	//RFC10
	public List<Visitante> consultarVisitasEnAforoCC(PersistenceManager pm, String fechaInicial, String fechaFinal, String criterioOrdenamiento)
	{
		String q1 = "SELECT VISITANTE.* "
				+ "FROM VISITANTE "
				+ "INNER JOIN VISITA "
				+ "ON VISITANTE.id = VISITA.visitante "
				+ "INNER JOIN ESPACIO "
				+ "ON VISITA.lector = ESPACIO.lector "
				+ "INNER JOIN LOCAL_COMERCIAL "
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id "
				+ "WHERE hora_inicial BETWEEN TO_DATE('" + fechaInicial + "', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('" + fechaFinal + "', 'YYYY-MM-DD-HH24:MI:SS') "
				+ "ORDER BY " + criterioOrdenamiento;
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visitante.class);
		
		return (List<Visitante>) q.executeList();
	}
}
