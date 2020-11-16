package uniandes.isis2304.aforocc.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.CentroComercial;
import uniandes.isis2304.aforocc.negocio.Visitante;

public class SQLCentroComercial 
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
	public SQLCentroComercial (PersistenciaAforoCC pp)
	{
		this.pp = pp;
	}


	public CentroComercial darCentroComercial(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCentroComercial() );
		q.setResultClass(CentroComercial.class);
		return (CentroComercial) q.executeUnique();
	}

	public int darAforoRealCC(PersistenceManager pm, String horaIni, String horaFin)
	{
		String q1 = "SELECT COUNT(VISITANTE.id)\r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector\r\n"
				+ " WHERE hora_inicial BETWEEN TO_DATE( '" + horaIni + "' , 'hh24:mi:ss') AND TO_DATE('" + horaFin + "', 'hh24:mi:ss')";
		Query q = pm.newQuery(SQL, q1);
		q.setResultClass(Integer.class);
		return (Integer) q.executeUnique();
	}
	
	public int darAreaTotalLocalesComerciales(PersistenceManager pm)
	{
		String q1 = "SELECT SUM(LOCAL_COMERCIAL.area)\r\n"
				+ "FROM ESPACIO\r\n"
				+ "INNER JOIN LOCAL_COMERCIAL\r\n"
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id";
		Query q = pm.newQuery(SQL, q1);
		q.setResultClass(Integer.class);
		return (Integer) q.executeUnique();
	}
	
	public int darNumeroTotalAscensores(PersistenceManager pm)
	{
		String q1 = "SELECT COUNT(ESPACIO.id)\r\n"
				+ "FROM ESPACIO\r\n"
				+ "WHERE ESPACIO.tipo = 'ASCENSOR'";
		Query q = pm.newQuery(SQL, q1);
		q.setResultClass(Integer.class);
		return (Integer) q.executeUnique();
	}
	
	public int darNumeroTotalSanitarios(PersistenceManager pm)
	{
		String q1 = "SELECT SUM(BANIO.numero_sanitarios)\r\n"
				+ "FROM ESPACIO\r\n"
				+ "INNER JOIN BANIO\r\n"
				+ "ON BANIO.id_espacio = ESPACIO.id";
		Query q = pm.newQuery(SQL, q1);
		q.setResultClass(Integer.class);
		return (Integer) q.executeUnique();
	}
}
