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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Visita;
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
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVisitante () + "(id, nombre, correo, telefono, nombre_Emergencia, telefono_Emergencia, tipo_Visitante, codigo_QR, centro_Comercial) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
	
	public Visitante darVisitantePorCodigo(PersistenceManager pm, String codigo)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVisitante() + " WHERE codigo_qr = ?");
		q.setResultClass(Visitante.class);
		q.setParameters(codigo);
		return (Visitante) q.executeUnique();
	}
	
	public List<Visitante> darVisitantesVisita(PersistenceManager pm, Visita visita)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"); 
		String fechaFinal= sdf.format(visita.getHora_Final());
		String fechaInicial= sdf.format(visita.getHora_Inicial());
		
		String q1 = "SELECT VISITANTE.*\r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector\r\n"
				+ "WHERE VISITA.lector = " + visita.getLector() + " AND hora_inicial <=  TO_DATE('" + fechaFinal + "', 'YYYY-MM-DD-HH24:MI:SS') AND hora_final >= TO_DATE('" + fechaInicial + "', 'YYYY-MM-DD-HH24:MI:SS')";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visitante.class);
		
		return (List<Visitante>) q.executeList();
	}
	
	public Visitante darVisitantesMasDeTresVisitasMesEspacioDeterminado(PersistenceManager pm, String mes, String anio, String espacio)
	{		
		String q1 = "SELECT Visitante.*\r\n"
				+ "FROM VISITANTE\r\n"
				+ "INNER JOIN VISITA\r\n"
				+ "ON VISITANTE.id = VISITA.visitante\r\n"
				+ "INNER JOIN ESPACIO\r\n"
				+ "ON VISITA.lector = ESPACIO.lector\r\n"
				+ "INNER JOIN LOCAL_COMERCIAL\r\n"
				+ "ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id\r\n"
				+ "WHERE ESPACIO.id =" + espacio + " AND  VISITANTE.tipo_visitante = 'CONSUMIDOR' AND hora_inicial BETWEEN TO_DATE('" + anio + "-" + mes + "10-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS') AND ADD_MONTHS(TO_DATE('" + anio + "-" + mes + "-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS'),1)\r\n"
				+ "GROUP BY Visitante.id\r\n"
				+ "HAVING COUNT(Visitante.id) >=3";
		
		Query q = pm.newQuery(SQL, q1);	
		q.setResultClass(Visitante.class);
		
		return (Visitante) q.executeList();
	}
	
	public List<Visitante> darVisitantes(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVisitante ());
		q.setResultClass(Visitante.class);
		return (List<Visitante>) q.executeList();
	}
	
	public long actualizarEstado(PersistenceManager pm, long idVisitante, long idNuevoEstado)
	{
		String q1 = "UPDATE " + pp.darTablaVisitante() + " SET estado = ? WHERE ID = ?";
		Query q = pm.newQuery(SQL, q1);
		q.setParameters(idNuevoEstado, idVisitante);
		return (long) q.executeUnique(); 	
	}


}
