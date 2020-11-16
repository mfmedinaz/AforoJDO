package uniandes.isis2304.aforocc.persistencia;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Espacio;
import uniandes.isis2304.aforocc.negocio.Visita;

public class SQLVisita 
{
	private final static String SQL = PersistenciaAforoCC.SQL;
	
	private PersistenciaAforoCC pa;
	
	public SQLVisita (PersistenciaAforoCC pa)
	{
		this.pa = pa;
	}
	
	public long registrarEntradaVisitanteEspacio (PersistenceManager pm, long idVisita, String horaInicial, String horaFinal, long idVisitante, long idLector ) 
	{
		String sql = "INSERT INTO " + pa.darTablaVisita();
		sql+=" (id, hora_inicial, hora_final, visitante, lector)";
		sql += " VALUES ";
		sql += "( ? , TO_DATE('" + horaInicial + "', 'hh24:mi:ss') , null, ?, ?)";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idVisita,idVisitante, idLector);

		long resp = (long) q.executeUnique();
		return resp;
	}
	
	public long registrarSalidaVisitanteEspacio (PersistenceManager pm, long idVisitante, long idLector, String horaFinal)
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pa.darTablaVisita () + " SET hora_final = "
				+ "TO_DATE('" + horaFinal + "', 'hh24:mi:ss') WHERE visitante = ? AND lector = ?");
		q.setParameters(idVisitante, idLector);
		return (long) q.executeUnique(); 		
	}
	
	public List<Visita> darVisitasPorVisitanteDeterminado(PersistenceManager pm, String idVisitante, String fecha)
	{
		String q1 = "SELECT VISITA.*    \r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector\r\n"
				+ "WHERE VISITANTE.id = 1 AND hora_inicial BETWEEN TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS')";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visita.class);
		
		return (List<Visita>) q.executeList();
	}
}
