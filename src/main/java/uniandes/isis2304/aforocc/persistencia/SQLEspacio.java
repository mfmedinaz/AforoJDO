package uniandes.isis2304.aforocc.persistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Espacio;
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
}
