package uniandes.isis2304.aforocc.persistencia;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.EstadoEspacio;
import uniandes.isis2304.aforocc.negocio.EstadoVisitante;

public class SQLEstadoEspacio 
{
	private final static String SQL = PersistenciaAforoCC.SQL;
	
	private PersistenciaAforoCC pa;
	
	public SQLEstadoEspacio (PersistenciaAforoCC pa)
	{
		this.pa = pa;
	}
	
	public long registrarNuevoEstadoEspacio(PersistenceManager pm, long id, String nombre, Date fechaAsignacion)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
		String fecha= sdf.format(fechaAsignacion);
		
		String q1 = "INSERT INTO " + pa.darTablaEstadoEspacio() + " (ID, NOMBRE, FECHA_ASIGNACION)"; 
		q1 += " VALUES (?, ? , TO_DATE('" + fecha + "', 'YYYY-MM-DD-HH24:MI:SS'))";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(id, nombre);
		return (long) q.executeUnique(); 
	}
	
	public EstadoEspacio darEstadoEspacioPorId(PersistenceManager pm, long id)
	{
		String q1 = "SELECT * FROM " + pa.darTablaEstadoEspacio() + " WHERE ID = ?";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(id);
		q.setResultClass(EstadoEspacio.class);
		
		return (EstadoEspacio) q.executeUnique();
	}
}
