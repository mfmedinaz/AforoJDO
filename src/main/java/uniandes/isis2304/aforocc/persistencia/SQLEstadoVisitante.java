package uniandes.isis2304.aforocc.persistencia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.EstadoVisitante;
import uniandes.isis2304.aforocc.negocio.Visita;

public class SQLEstadoVisitante 
{
	private final static String SQL = PersistenciaAforoCC.SQL;
	
	private PersistenciaAforoCC pa;
	
	public SQLEstadoVisitante (PersistenciaAforoCC pa)
	{
		this.pa = pa;
	}
	
	public long registrarNuevoEstadoVisitante(PersistenceManager pm, long id, String nombre, Date fechaAsignacion)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
		String fecha= sdf.format(fechaAsignacion);
		
		String q1 = "INSERT INTO " + pa.darTablaEstadoVisitante() + " (ID, NOMBRE, FECHA_ASIGNACION)"; 
		q1 += " VALUES (?, ? , TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS'))";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(id, nombre);
		return (long) q.executeUnique(); 
	}
	
	public EstadoVisitante darEstadoVisitantePorId(PersistenceManager pm, long id)
	{
		String q1 = "SELECT * FROM " + pa.darTablaEstadoVisitante() + " WHERE ID = ?";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(id);
		q.setResultClass(EstadoVisitante.class);
		
		return (EstadoVisitante) q.executeUnique();
	}
}
