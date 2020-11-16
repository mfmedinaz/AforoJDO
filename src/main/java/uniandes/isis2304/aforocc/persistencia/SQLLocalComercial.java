package uniandes.isis2304.aforocc.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.*;

public class SQLLocalComercial 
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
	public SQLLocalComercial (PersistenciaAforoCC pp)
	{
		this.pp = pp;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Visita> darVisitasEnCurso(PersistenceManager pm, long idLocal)
	{
		String q1 = "SELECT VISITA.* ";
		q1 += "FROM " + pp.darTablaVisita() + ", " + pp.darTablaLocalComercial() + ", " + pp.darTablaLector() + ", " + pp.darTablaEspacio(); 
		q1 += " WHERE visita.lector = LECTOR.ID AND ";
		q1 += "espacio.lector = LECTOR.ID AND ";
		q1 += "local_comercial.id_espacio = espacio.ID AND ";
		q1 += "visita.hora_final IS NULL AND ";
		q1 += "espacio.id = ?";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visita.class);
		q.setParameters(idLocal);

		return (List<Visita>) q.executeList();
	}
	
	public long cerrarLocalComercial(PersistenceManager pm, long idLocal)
	{
		String q1 = "UPDATE " + pp.darTablaLocalComercial() + " SET estado = 'CERRADO' WHERE ID_ESPACIO = ?";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(idLocal);
		return (long) q.executeUnique();
	}
	
	public LocalComercial darLocalComercialPorIdEspacio(PersistenceManager pm, long idEspacio)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaLocalComercial() + " WHERE id_espacio = ?");
		q.setResultClass(LocalComercial.class);
		q.setParameters(idEspacio);
		return (LocalComercial) q.executeUnique();
	}
}
