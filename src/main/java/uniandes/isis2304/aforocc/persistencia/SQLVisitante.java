/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: AforoCC Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.aforocc.persistencia;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Visitante;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VISITANTE de AforoCC
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLVisitante 
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
	public SQLVisitante (PersistenciaAforoCC pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un VISITANTE a la base de datos de AforoCC
	 * @return EL número de tuplas insertadas
	 */
	public long registrarVisitante (PersistenceManager pm, long id, String nombre, String correo, String telefono, String nombreEmergencia, String telefonoEmergencia, String tipoVisitante, String codigoQR, long centroComercial) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVisitante () + "(id, nombre, correo, telefono, nombreEmergencia, telefonoEmergencia, tipVisitante, codigoQR, centroComercial) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, correo, telefono, nombreEmergencia, telefonoEmergencia, tipoVisitante, codigoQR, centroComercial);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN VISITANTE de la base de datos de AforoCC, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idVisitante - El identificador del visitante
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVisitantePorId (PersistenceManager pm, long idVisitante)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitante () + " WHERE id = ?");
        q.setParameters(idVisitante);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN VISITANTE de la 
	 * base de datos de AforoCC, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idVisitante - El identificador del visitante
	 * @return El objeto VISITANTE que tiene el identificador dado
	 */
	public Visitante darVisitantePorId (PersistenceManager pm, long idVisitante) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVisitante () + " WHERE id = ?");
		q.setResultClass(Visitante.class);
		q.setParameters(idVisitante);
		return (Visitante) q.executeUnique();
	}

	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar la ciudad de un visitante en la 
	 * base de datos de AforoCC
	 * @param pm - El manejador de persistencia
	 * @param idVisitante - El identificador del visitante
	 * @param ciudad - La nueva ciudad del visitante
	 * @return El número de tuplas modificadas
	 */
	public long registrarEntradaVisitanteEspacio (PersistenceManager pm, long idVisitante, long idEspacio, long idVisita) 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 

		String sql1 = "SELECT lector";
		sql1 += " FROM " + pp.darTablaEspacio ();
		sql1 += " WHERE id = ? AND ROWNUM = 1";

		String sql = "INSERT INTO " + pp.darTablaVisita();
		sql+=" (id, hora_inicial, hora_final, visitante, lector)";
		sql += " VALUES ";
		sql += "(" + idVisita + ", TO_DATE('" + dtf.format(now)  + "', 'hh24:mi:ss') , null, ?, " + sql1 +")";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idVisitante, idEspacio);
		return (long) q.executeUnique();     
	}
	
	public long registrarSalidaVisitanteEspacio (PersistenceManager pm, long idVisitante, long idEspacio)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 

		String sql1 = "SELECT lector";
		sql1 += " FROM " + pp.darTablaEspacio ();
		sql1 += " WHERE id = ? AND ROWNUM = 1";

		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaVisita () + " SET hora_final = " + "TO_DATE('" + dtf.format(now)  + "', 'hh24:mi:ss') WHERE visitante = ? AND lector = " + sql1);
		q.setParameters(idVisitante, idEspacio);
		return (long) q.executeUnique(); 		
	}
	
	public static void main(String[] args)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	
	}


}
