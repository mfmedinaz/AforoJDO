/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.aforocc.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
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
	public SQLUtil (PersistenciaAforoCC pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqAforoCC () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAforoCC (PersistenceManager pm)
	{
        Query qEspacio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEspacio ());          
        Query qLocalComercial = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaLocalComercial ());
        Query qParqueadero = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaParqueadero ());
        Query qBanio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBanio ());
        Query qCentroComercial = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCentroComercial ());
        Query qVisita = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisita ());
        Query qVisitante = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitante ());
        Query qTipoVisitante = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipoVisitante ());
        Query qLector = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaLector ());
        Query qAdministrador = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdministrador ());
        Query qTipoEstablecimiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipoEstablecimiento ());

        long espacioEliminados = (long) qEspacio.executeUnique ();
        long localComercialEliminados = (long) qLocalComercial.executeUnique ();
        long parqueaderoEliminados = (long) qParqueadero.executeUnique ();
        long banioEliminados = (long) qBanio.executeUnique ();
        long centroComercialEliminados = (long) qCentroComercial.executeUnique ();
        long visitaEliminados = (long) qVisita.executeUnique ();
        long visitanteEliminados = (long) qVisitante.executeUnique ();
        long tipoVisitanteEliminados = (long) qTipoVisitante.executeUnique ();
        long lectorEliminados = (long) qLector.executeUnique ();
        long administradorEliminados = (long) qAdministrador.executeUnique ();
        long tipoEstablecimientoEliminados = (long) qTipoEstablecimiento.executeUnique ();
        return new long[] {espacioEliminados, localComercialEliminados, parqueaderoEliminados, banioEliminados, 
        		centroComercialEliminados, visitaEliminados, visitanteEliminados, tipoVisitanteEliminados, lectorEliminados, 
        		administradorEliminados, tipoEstablecimientoEliminados};
	}

}
