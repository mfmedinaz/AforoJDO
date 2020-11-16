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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.bcel.internal.generic.IDIV;

import uniandes.isis2304.aforocc.negocio.*;

/**
 * Clase para el manejador de persistencia del proyecto AforoCC
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaAforoCC 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaAforoCC.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaAforoCC instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaAforoCC
	 */
	private SQLUtil sqlUtil;
	
	 /** 
	  * Atributo para el acceso a la tabla VISITANTE de la base de datos
	 */
	private SQLVisitante sqlVisitante;
	
	/** 
	  * Atributo para el acceso a la tabla VISITA de la base de datos
	 */
	private SQLVisita sqlVisita;
	
	/** 
	  * Atributo para el acceso a la tabla ESPACIO de la base de datos
	 */
	private SQLEspacio sqlEspacio;
	
	/** 
	  * Atributo para el acceso a la tabla CENTRO_COMERCIAL de la base de datos
	 */
	private SQLCentroComercial sqlCentroComercial;
	
	/** 
	  * Atributo para el acceso a la tabla TIPO_VISITANTE de la base de datos
	 */
	private SQLTipoVisitante sqlTipoVisitante;
	
	
	private SQLLocalComercial sqlLocalComercial;
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaAforoCC ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("AforoCC");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("AforoCC_sequence");
		tablas.add ("ESPACIO");
		tablas.add ("LOCAL_COMERCIAL");
		tablas.add ("PARQUEADERO");
		tablas.add ("BANIO");
		tablas.add ("CENTRO_COMERCIAL");
		tablas.add ("VISITA");
		tablas.add ("VISITANTE");
		tablas.add ("TIPO_VISITANTE");
		tablas.add ("LECTOR");
		tablas.add ("ADMINISTRADOR");
		tablas.add ("ADMIN_CC");
		tablas.add ("ADMIN_LOCAL");
		tablas.add ("TIPO_ESTABLECMIENTO");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaAforoCC (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaAforoCC existente - Patrón SINGLETON
	 */
	public static PersistenciaAforoCC getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaAforoCC ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaAforoCC existente - Patrón SINGLETON
	 */
	public static PersistenciaAforoCC getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaAforoCC (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlVisitante = new SQLVisitante(this);
		sqlVisita = new SQLVisita(this);
		sqlUtil = new SQLUtil(this);
		sqlEspacio = new SQLEspacio(this);
		sqlCentroComercial = new SQLCentroComercial(this);
		sqlTipoVisitante = new SQLTipoVisitante(this);
		sqlLocalComercial = new SQLLocalComercial(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de aforoCC
	 */
	public String darSeqAforoCC ()
	{
		return tablas.get(0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de aforoCC
	 */
	public String darTablaEspacio ()
	{
		return tablas.get (1);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de aforoCC
	 */
	public String darTablaLocalComercial()
	{
		return tablas.get(2);
	}
	public String darTablaCentroComercial()
	{
		return tablas.get(5);
	}
	
	public String darTablaVisita()
	{
		return tablas.get(6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de aforoCC
	 */
	public String darTablaVisitante ()
	{
		return tablas.get (7);
	}
	
	public String darTablaTipoVisitante ()
	{
		return tablas.get (8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de aforoCC
	 */
	public String darTablaLector ()
	{
		return tablas.get (9);
	}

	

	

	
	/**
	 * Transacción para el generador de secuencia de AforoCC
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de AforoCC
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	
	
	public Visitante registrarVisitante(String nombre, String correo, String telefono, String nombreEmergencia, String telefonoEmergencia, String tipoVisitante, long centroComercial)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long idVisitante = nextval();
			String codigoQR = "CODIGO-QR-" + idVisitante;
			long tuplas = sqlVisitante.registrarVisitante(pm, idVisitante, nombre, correo, telefono, nombreEmergencia, telefonoEmergencia, tipoVisitante, codigoQR, centroComercial);
			tx.commit();
			
			log.trace("Se registro un nuevo visitante [" + nombre + "]");
			return new Visitante(idVisitante, nombre, correo, telefono, nombreEmergencia, telefonoEmergencia, tipoVisitante, codigoQR, centroComercial);
		}
		catch(Exception e)
		{
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			System.out.println("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}
	
	
	public long eliminarVisitantePorId(long idVisitante)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlVisitante.eliminarVisitantePorId(pm, idVisitante);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception: " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public Visita registrarEntradaVisitante(String horaInicial, String horaFinal, long idVisitante, long idLector) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long idVisita = nextval();
			long tuplasInsertadas = sqlVisita.registrarEntradaVisitanteEspacio(pm, idVisita, horaInicial, horaFinal, idVisitante, idLector);
			tx.commit();
			
			log.trace("Insercion a visita de nueva entrada: [" + idVisita +"]."
					+ " " + tuplasInsertadas + " tuplas insertadas");
			return new Visita(idVisita, horaInicial, horaFinal, idVisitante, idLector);
		}
		catch (Exception e)
		{
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
				tx.rollback();
			pm.close();
		}
	}
	
	
	public long registrarSalidaVisitante(long idVisitante, long idLector, String horaFinal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	tx.begin();
        	long resp = sqlVisita.registrarSalidaVisitanteEspacio(pm, idVisitante, idLector, horaFinal);
        	tx.commit();
        	return resp;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
        	if (tx.isActive())
        		tx.rollback();
        	pm.close();
        }
	}
	
	public long cerraLocalComercial(long idLocal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlLocalComercial.cerrarLocalComercial(pm, idLocal);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
		}
		finally
        {
        	if (tx.isActive())
        		tx.rollback();
        	pm.close();
        }
	}
	
	
	public Visitante darVisitantePorCodigo(String codigo)
	{
		return (Visitante) sqlVisitante.darVisitantePorCodigo(pmf.getPersistenceManager(), codigo);
	}
	
	public Espacio darEspacioPorNombre(String nombre)
	{
		return (Espacio) sqlEspacio.darEspacioPorNombre(pmf.getPersistenceManager(), nombre);
	}
	

	public List<Visitante> darVisitantesEspacio(String espacio, String horaIni, String horaFin)
	{
		return (List<Visitante>) sqlEspacio.darVisitantesEspacio (pmf.getPersistenceManager(), espacio, horaIni, horaFin);
	}
	
	public List<Visita> darVisitasEnCurso(long idLocal)
	{
		return (List<Visita>) sqlLocalComercial.darVisitasEnCurso(pmf.getPersistenceManager(), idLocal);
	}
	public List<String> mostrar20EstablecimientosMasPopulares(String horaIni, String horaFin)
	{
		return (List<String>) sqlEspacio.mostrar20EstablecimientosMasPopulares(pmf.getPersistenceManager(), horaIni, horaFin);
	}
	
	
	public CentroComercial darCentroComercial()
	{
		return (CentroComercial) sqlCentroComercial.darCentroComercial(pmf.getPersistenceManager());
	}
	
	public TipoVisitante darTipoCentroComercialPorNombre(String nombre)
	{
		return (TipoVisitante) sqlTipoVisitante.darTipoVisitantePorNombre(pmf.getPersistenceManager(), nombre);
	}
	
	public LocalComercial darLocalComercialPorIdEspacio(long idEspacio)
	{
		return (LocalComercial) sqlLocalComercial.darLocalComercialPorIdEspacio(pmf.getPersistenceManager(), idEspacio);
	}
	
	public int mostrarAforoRealEstablecimiento(String horaIni, String horaFin, String idEspacio)
	{
		return (int) sqlEspacio.mostrarAforoRealEstablecimiento(pmf.getPersistenceManager(), horaIni, horaFin, idEspacio);
	}
	
	public int mostrarAforoRealTipoEstablecimiento(String horaIni, String horaFin, String tipoEstablecimiento)
	{
		return (int) sqlEspacio.mostrarAforoRealTipoEstablecimiento(pmf.getPersistenceManager(), horaIni, horaFin, tipoEstablecimiento);
	}
	
	public int mostrarAreaEstablecimiento(String idEspacio)
	{
		return (int) sqlEspacio.mostrarAreaEstablecimiento(pmf.getPersistenceManager(), idEspacio);
	}
	
	public List<Integer> mostrarAreasTipoEstablecimiento(String horaIni, String horaFin, String tipoEstablecimiento)
	{
		return (List<Integer>) sqlEspacio.mostrarAreasTipoEstablecimiento(pmf.getPersistenceManager(), horaIni, horaFin, tipoEstablecimiento);
	}
	
	public int darAforoRealCC(String horaIni, String horaFin)
	{
		return (int) sqlCentroComercial.darAforoRealCC(pmf.getPersistenceManager(), horaIni, horaFin);
	}
	
	public int darAreaTotalLocalesComerciales()
	{
		return (int) sqlCentroComercial.darAreaTotalLocalesComerciales(pmf.getPersistenceManager());
	}
	
	public int darNumeroTotalAscensores()
	{
		return (int) sqlCentroComercial.darNumeroTotalAscensores(pmf.getPersistenceManager());
	}
	
	public int darNumeroTotalSanitarios()
	{
		return (int) sqlCentroComercial.darNumeroTotalSanitarios(pmf.getPersistenceManager());
	}
	
	public List<Visita> darVisitasPorVisitanteDeterminado(String idVisitante, String fecha)
	{
		return (List<Visita>) sqlVisita.darVisitasPorVisitanteDeterminado(pmf.getPersistenceManager(), idVisitante, fecha);
	}
	
	public List<Visitante> darVisitantesVisita(Visita visita)
	{
		return (List<Visitante>) sqlVisitante.darVisitantesVisita(pmf.getPersistenceManager(), visita);
	}
	

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de AforoCC
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAforoCC ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarAforoCC (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	

 }
