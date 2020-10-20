package uniandes.isis2304.aforocc.persistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import java.util.List;

import uniandes.isis2304.aforocc.negocio.Espacio;
import uniandes.isis2304.aforocc.negocio.VOEspacio;
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
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Visitante> darVisitantesEspacio(PersistenceManager pm, VOEspacio espacio, String horaIni, String horaFin)
	{
		String q1 = "SELECT VISITANTE.* FROM " + pp.darTablaVisitante();
		q1+=" INNER JOIN " + pp.darTablaVisita();
		q1+=" ON VISITANTE.id  = VISITA.visitante";
		q1+=" INNER JOIN " + pp.darTablaEspacio();
		q1+=" ON VISITA.lector = ESPACIO.lector";
		q1+=" WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin +"' , 'hh24:mi:ss') AND ESPACIO.nombre = ?";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visitante.class);
		q.setParameters(horaIni, horaFin, espacio.getNombre());
		
		return (List<Visitante>) q.executeList();
	}
}
