package uniandes.isis2304.aforocc.persistencia;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		return (long) q.executeUnique();     
	}
	
	public long registrarSalidaVisitanteEspacio (PersistenceManager pm, long idVisitante, long idLector, String horaFinal)
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pa.darTablaVisita () + " SET hora_final = "
				+ "TO_DATE('" + horaFinal + "', 'hh24:mi:ss') WHERE visitante = ? AND lector = ?");
		q.setParameters(idVisitante, idLector);
		return (long) q.executeUnique(); 		
	}
}
