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

package uniandes.isis2304.aforocc.negocio;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import jdk.internal.org.jline.utils.Log;
import uniandes.isis2304.aforocc.persistencia.PersistenciaAforoCC;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class AforoCC 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AforoCC.class.getName());
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAforoCC pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public AforoCC ()
	{
		pp = PersistenciaAforoCC.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public AforoCC (JsonObject tableConfig)
	{
		pp = PersistenciaAforoCC.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	
	public Visitante registrarVisistante(String nombre, String correo, String telefono, String nombreEmergencia, 
			String telefonoEmergencia, String tipoVisitante, long centroComercial)
	{
		log.info("Registrando al visitante [" + nombre + "]");
		Visitante resp = pp.registrarVisitante(nombre, correo, telefono, nombreEmergencia, telefonoEmergencia, tipoVisitante, centroComercial);
		log.info("Registrando al visitante: " + resp != null ? resp : "NO EXISTE");
		return resp;
	}
	
	public long eliminarVisitantePorId(long idVisitante)
	{
		log.info("Eliminando Visitante por Id: " + idVisitante);
		long resp = pp.eliminarVisitantePorId(idVisitante);
		log.info("Eliminando Visitante por id " + resp + " tuplas eliminadas");
		return resp;
	}
	
	
	public Visita registrarEntradaVisitante ( String codigoVisitante, String nomEspacio ) throws Exception
	{	
		Date horaInicial = pp.darFechaActual();
		
		Visitante visitante = darVisitantePorCodigo(codigoVisitante);
	
		log.info("Registrando entrada  [" + horaInicial + ", " + visitante.getId() + "]");
		
		long idLector = -1;
		
		if (nomEspacio.equals("Centro comercial"))
		{
			VOCentroComercial cc = darCentroComercial();
			idLector = cc.getLector_Entrada_CC();
		}
		else
		{
			Espacio espacio = darEspacioPorNombre(nomEspacio);
			LocalComercial local = darLocalComercialPorIdEspacio(espacio.getId());
			if (local != null && local.getEstado().equals(LocalComercial.CERRADO) && !visitante.getTipo_Visitante().equals(TipoVisitante.EMPLEADO_CC))
				throw new Exception("No se puede registrar la entrada ya que el local está cerrado y el visitante no tiene permiso de entrar");
			idLector = espacio.getLector();
			
		}
		Visita resp = pp.registrarEntradaVisitante(horaInicial, null, visitante.getId(), idLector);
		System.out.println(resp);
		log.info("Registrando entrada: " + resp != null ? resp : "NO EXISTE");
		return resp;
	}
	
	public long registrarSalidaVisitante(String codigoVisitante, String nomEspacio)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String horaSalida = dtf.format(now);
		Visitante visitante = darVisitantePorCodigo(codigoVisitante);

		log.info("Registrando salida [" + horaSalida + ", " + visitante.getId() + "]");
		long idLector = -1;
		if (nomEspacio.equals("Centro comercial"))
		{
			VOCentroComercial cc = darCentroComercial();
			idLector = cc.getLector_Entrada_CC();
		}
		else
		{
			VOEspacio espacio = darEspacioPorNombre(nomEspacio);
			idLector = espacio.getLector();
		}
		
		long resp = pp.registrarSalidaVisitante(visitante.getId(), idLector, horaSalida);
		log.info("Registrando salida: " + resp + " tuplas editadas");
		return resp;
	}
	
	public long cerrarLocalComercial(long idLocal)
	{
		log.info("Cerrando local comercial " + idLocal);
		long resp = pp.cerraLocalComercial(idLocal);
		log.info("Cerrando local comercial: " + resp + " tuplas editadas");
		return resp;
	}
	
	public Visitante darVisitantePorCodigo(String codigo)
	{
		log.info("Obteniendo visitante por codigo [" + codigo + "]");
		Visitante resp = pp.darVisitantePorCodigo(codigo);
		log.info("Obteniendo visitante por codigo:" + resp != null ? resp : "NO EXISTE");
		return resp;
	}
	
	public List<Visitante> darVisitantesEspacio(String espacio, String horaIni, String horaFin)
	{
		log.info("Obteniendo visitantes espacio en rango de fechas [" + espacio + ", " + horaIni + "," + horaFin + "]");
		List<Visitante> resp = pp.darVisitantesEspacio(espacio, horaIni, horaFin);
		log.info("Obteniendo visitantes espacio en rango de fechas: " + resp.size() + " existentes");
		return resp;
	}
	
	public List<Visita> darVisitasEnCursoLocalComercial(long idLocal)
	{
		log.info("Obteniendo las visitas en curso del local: " + idLocal);
		List<Visita> resp = pp.darVisitasEnCurso(idLocal);
		log.info("Obteniendo las visitas en curso del local: " + resp.size() + " existentes");
		return resp;
	}
	public List<String> mostrar20EstablecimientosMasPopulares(String horaIni, String horaFin)
	{
		log.info("Obteniendo 20 espacios más populares en rango de fechas [" + horaIni + "," + horaFin + "]");
		List<String> resp = pp.mostrar20EstablecimientosMasPopulares(horaIni, horaFin);
		log.info("Obteniendo 20 espacios más populares en rango de fechas: " + resp.size() + " existentes");
		return resp;
	}
	
	public double darIndiceAforo(String opcion,String horaIni,String horaFin,String idEspacio,String tipoEstablecimiento)
	{
		if(opcion.equalsIgnoreCase("cc"))
		{
			int aforoReal = darAforoRealCC(horaIni, horaFin);  				
			
			int areaEstablecimientos = darAreaTotalLocalesComerciales();
			int numAscensores = darNumeroTotalAscensores();
			int numSanitarios = darNumeroTotalSanitarios();
			int aforoMaximo = areaEstablecimientos/15 + numAscensores*2 + numSanitarios/2;
			
			double indiceAforo = (double)aforoReal/(double)aforoMaximo;
			return indiceAforo;
		}
		else if(opcion.equalsIgnoreCase("establecimiento"))
		{	
			int aforoReal = mostrarAforoRealEstablecimiento(horaIni, horaFin, idEspacio);
			
			int areaEstablecimiento = mostrarAreaEstablecimiento(idEspacio);
			int aforoMaximo = areaEstablecimiento/15;
			
			double indiceAforo = (double)aforoReal/(double)aforoMaximo;
			return indiceAforo;
		}
		else if(opcion.equalsIgnoreCase("tipoestablecimiento"))
		{
			int aforoReal = mostrarAforoRealTipoEstablecimiento(horaIni, horaFin, tipoEstablecimiento);
			
			List<Integer> areasTipoEstablecimiento = mostrarAreasTipoEstablecimiento(horaIni, horaFin, tipoEstablecimiento);
			int totalAreasTipoEstablecimiento = 0;
			for(int area: areasTipoEstablecimiento)
			{
				totalAreasTipoEstablecimiento+=area;
			}
			int aforoMaximo = totalAreasTipoEstablecimiento/15;
			
			double indiceAforo = (double)aforoReal/(double)aforoMaximo;
			return indiceAforo;
			
		}
		return -1;
	}
	
	public Espacio darEspacioPorNombre(String nombre)
	{
		log.info("Obteniendo espacio por nombre [" + nombre + "]");
		Espacio resp = pp.darEspacioPorNombre(nombre);
		log.info("Obteniendo espacio por nombre: " + resp != null? resp : "NO EXISTE");
		return resp;
	}
	
	public CentroComercial darCentroComercial()
	{
		log.info("Obteniendo centro comercial");
		CentroComercial resp = pp.darCentroComercial();
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public LocalComercial darLocalComercialPorIdEspacio(long idEspacio)
	{
		log.info("Obteniendo local comercial " + idEspacio);
		LocalComercial resp = pp.darLocalComercialPorIdEspacio(idEspacio);
		log.info("Obteniendo local comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	
	public int mostrarAforoRealEstablecimiento(String horaIni, String horaFin, String idEspacio)
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.mostrarAforoRealEstablecimiento(horaIni, horaFin, idEspacio);
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int mostrarAforoRealTipoEstablecimiento(String horaIni, String horaFin, String tipoEstablecimiento)
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.mostrarAforoRealTipoEstablecimiento(horaIni, horaFin, tipoEstablecimiento);
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int mostrarAreaEstablecimiento(String idEspacio)
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.mostrarAreaEstablecimiento(idEspacio);
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public List<Integer> mostrarAreasTipoEstablecimiento(String horaIni, String horaFin, String tipoEstablecimiento)
	{
		log.info("Obteniendo centro comercial");
		List<Integer> resp = pp.mostrarAreasTipoEstablecimiento(horaIni, horaFin, tipoEstablecimiento);
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int darAforoRealCC(String horaIni, String horaFin)
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.darAforoRealCC(horaIni, horaFin);
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int darAreaTotalLocalesComerciales()
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.darAreaTotalLocalesComerciales();
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int darNumeroTotalAscensores()
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.darNumeroTotalAscensores();
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public int darNumeroTotalSanitarios()
	{
		log.info("Obteniendo centro comercial");
		int resp = pp.darNumeroTotalSanitarios();
		log.info("Obteniendo centro comercial: " + resp != null ? resp: "NO EXISTE");
		return resp;
	}
	
	public List<Visitante> encontrarVisitantesQueTuvieronContactoConOtroDeterminadoVisitante(String idVisitante, Date fecha)
	{
		log.info("Obteniendo ENCONTRAR LOS VISITANTES QUE ESTUVIERON CONTACTO CON OTRO DETERMINADO VISITANTE");
		List<Visitante> visitantes = new ArrayList();
		List<Visita> visitas = pp.darVisitasPorVisitanteDeterminado(idVisitante, fecha);

		for(Visita vis: visitas)
		{
			List<Visitante> visits = pp.darVisitantesVisita(vis);
			for(Visitante visitors: visits)
			{
				if(visitors.getId()!=Long.parseLong(idVisitante))
				{
					visitantes.add(visitors);
				}
			}
		}
		
		return visitantes;
		
	}
	
	public List<Visitante> darVisitantesFrecuentesEspacio(String espacio)
	{
		Log.info("Encontrando clientes frecuentes");
		List<Visitante> visitantes = new ArrayList();
		List<Visitante> imposibles = new ArrayList();
		int primerAnio = 2018;
		int primerMes = 1;
		for(int i = primerAnio; i <= pp.darFechaActual().getMonth(); i++)
		{
			for(int j = primerMes; j <= pp.darFechaActual().getMonth(); j++)
			{
				List<Visitante> candidatos = pp.darVisitantesMasDeTresVisitasMesEspacioDeterminado(j+"", i+"", espacio);
				if(i == primerAnio && j == primerMes)
				{
					visitantes = candidatos;
				}
				
				for(Visitante vis: visitantes)
				{
					if(!candidatos.contains(vis))
					{
						visitantes.remove(vis);
					}
				}
			}
			
		}
		
		
		return visitantes;
	}

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de AforoCC
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAforoCC ()
	{
        log.info ("Limpiando la BD de AforoCC");
        long [] borrrados = pp.limpiarAforoCC();	
        log.info ("Limpiando la BD de AforoCC: Listo!");
        return borrrados;
	}
}
