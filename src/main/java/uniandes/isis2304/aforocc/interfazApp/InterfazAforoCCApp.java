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

package uniandes.isis2304.aforocc.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.aforocc.negocio.AforoCC;
import uniandes.isis2304.aforocc.negocio.*;

/**
 * Clase principal de la interfaz
 */
@SuppressWarnings("serial")

public class InterfazAforoCCApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazAforoCCApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private AforoCC aforoCC;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazAforoCCApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		aforoCC = new AforoCC (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "AforoCC App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "AforoCC APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/**
	 * Adiciona un tipo de bebida con la información dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
	 */

	public void registrarVisitante()
	{
		try
		{
			String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);
			String correo = JOptionPane.showInputDialog(this, "Ingrese el correo del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);
			String telefono = JOptionPane.showInputDialog(this, "Ingrese el telefono del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);
			String nombreEme = JOptionPane.showInputDialog(this, "Ingrese el nombre de un contacto de emergencia del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);
			String telEme = JOptionPane.showInputDialog(this, "Ingrese el telefono de un contacto de emergencia del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Ingrese el tipo del visitante", "Registrar visitante", JOptionPane.QUESTION_MESSAGE);

			if (nombre != null && correo != null && telefono != null && nombreEme != null && telEme != null && tipo != null)
			{
				VOCentroComercial cc = aforoCC.darCentroComercial(); 
				VOVisitante visitante = aforoCC.registrarVisistante(nombre, correo, telefono, nombreEme, telEme, tipo, cc.getId());
				if (visitante == null)
				{
					throw new Exception ("No se pudo resgistrar al visitante " + nombre);
				}
				String resultado = "En registrar visitante\n\n";
				resultado += "visitante creada exitosamente " + visitante;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void eliminarVisitante()
	{
		try
		{
			String idVisitanteStr = JOptionPane.showInputDialog(this, "Id del visitante", "Borrar visitante", JOptionPane.QUESTION_MESSAGE);
			if (idVisitanteStr != null)
			{
				long idVisitante = Long.valueOf(idVisitanteStr);
				long vEliminados = aforoCC.eliminarVisitantePorId(idVisitante);
				String resultado = "En eliminar visitante\n\n";
				resultado += vEliminados + " visitantes eliminados";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void registrarEntradaVisitante( )
	{
		try 
		{
			String codigoVisitante = JOptionPane.showInputDialog (this, "Ingrese el codigo del visitante", "Registrar entrada", JOptionPane.QUESTION_MESSAGE);
			String nomEspacio = JOptionPane.showInputDialog(this, "Ingrese el espacio al que ingresa", "Registrar entrada", JOptionPane.QUESTION_MESSAGE);


			if (codigoVisitante != null && nomEspacio != null)
			{
				VOVisita visita = aforoCC.registrarEntradaVisitante(codigoVisitante, nomEspacio);
				if (visita == null)
				{
					throw new Exception ("No se pudo resgistrar la entrada al espacio " + nomEspacio + " con el codigo " + codigoVisitante);
				}
				String resultado = "En registrarEntradaVisitante\n\n";
				resultado += "visita creada exitosamente " + visita;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void registrarSalidaVisitante()
	{
		try
		{
			String codigoVisitante = JOptionPane.showInputDialog (this, "Ingrese el codigo del visitante", "Registrar salida", JOptionPane.QUESTION_MESSAGE);
			String nomEspacio = JOptionPane.showInputDialog(this, "Ingrese el espacio del que sale", "Registrar salida", JOptionPane.QUESTION_MESSAGE);
			if (codigoVisitante != null && nomEspacio != null)
			{

				long vEditados = aforoCC.registrarSalidaVisitante(codigoVisitante, nomEspacio);
				String resultado = "En registrarEntradaVisitante\n\n";
				resultado += "visita creada exitosamente " + vEditados + " tuplas editadas";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}
		catch (Exception e)
		{

		}
	}

	public void cerrarEspacioComercial()
	{
		try
		{
			String idLocalStr = JOptionPane.showInputDialog(this, "Ingrese el id del local que desea cerrar", "Cerrar local comercial", JOptionPane.QUESTION_MESSAGE);
			if (idLocalStr != null)
			{
				long idLocal = Long.valueOf(idLocalStr);
				List<Visita> visitasActivas = aforoCC.darVisitasEnCursoLocalComercial(idLocal);
				if (!visitasActivas.isEmpty())
				{
					System.out.println(visitasActivas.get(0).getHora_Inicial());
					throw new Exception("No se puede cerrar el local ya que hay visitantes adentro");
				}
				long vEdit = aforoCC.cerrarLocalComercial(idLocal);
				String resultado = "En cerrarEspacioComercial\n\n";
				resultado += "local comercial editado exitosamente " + vEdit + " tuplas editadas";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);

			}
			else 
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		}	
		catch (Exception e)
		{
			//	e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void mostrarVisitantes( )
	{
		try 
		{
			String espacio = JOptionPane.showInputDialog(this, "Ingrese el espacio que se quiere consultar", "Mostrar visitantes", JOptionPane.QUESTION_MESSAGE);
			String horaInicial = JOptionPane.showInputDialog(this, "Ingrese la hora inicial del rango que se quiere consultar", "Mostrar visitantes", JOptionPane.QUESTION_MESSAGE);
			String horaFinal = JOptionPane.showInputDialog(this, "Ingrese la hora final del rango que se quiere consultar", "Mostrar visitantes", JOptionPane.QUESTION_MESSAGE);
			if (espacio != null && horaInicial != null && horaFinal !=null)
			{
				List<Visitante> visitantes = aforoCC.darVisitantesEspacio(espacio, horaInicial, horaFinal);

				if (visitantes == null)
				{
					throw new Exception ("No se pudo obtener visitantes del espacio " + espacio + " en el rango [ " + horaInicial + ", " + horaFinal + "]");
				}
				String resultado = "Visitantes obtenidos\n\n";
				for(Visitante vis: visitantes)
				{
					resultado+=vis.getNombre();
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void mostrar20EstablecimientosMasPopulares( )
	{
		try 
		{
			String horaInicial = JOptionPane.showInputDialog(this, "Ingrese la hora inicial del rango que se quiere consultar", "Mostrar visitantes", JOptionPane.QUESTION_MESSAGE);
			String horaFinal = JOptionPane.showInputDialog(this, "Ingrese la hora final del rango que se quiere consultar", "Mostrar visitantes", JOptionPane.QUESTION_MESSAGE);
			if (horaInicial != null && horaFinal !=null)
			{
				List<String> espacios = aforoCC.mostrar20EstablecimientosMasPopulares(horaInicial, horaFinal);

				if (espacios == null)
				{
					throw new Exception ("No se pudo obtener espacios en el rango [ " + horaInicial + ", " + horaFinal + "]");
				}
				String resultado = "Espacios obtenidos\n\n";

				for(int i = 0; i < 20 && i < espacios.size(); i++)
				{
					resultado+= "Puesto " + (i+1) + ": " + espacios.get(i) + "\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void mostrarIndiceAforoCC( )
	{
		try 
		{
			String opcion = JOptionPane.showInputDialog(this, "Ingrese la opción que va a buscar (\"CC\", \"ESTABLECIMIENTO\", \"TIPOESTABLECIMIENTO\")", "Mostrar Indice", JOptionPane.QUESTION_MESSAGE);

			if (opcion != null)
			{
				String horaIni = JOptionPane.showInputDialog(this, "Ingrese la hora inicial del rango", "Mostrar Indice", JOptionPane.QUESTION_MESSAGE);
				String horaFin = JOptionPane.showInputDialog(this, "Ingrese la hora final del rango", "Mostrar Indice", JOptionPane.QUESTION_MESSAGE);
				String idEspacio = "";
				String tipoEstablecimiento = "";

				if(opcion.equalsIgnoreCase("establecimiento"))
				{
					idEspacio = JOptionPane.showInputDialog(this, "Ingrese el id del espacio a consultar", "Mostrar Indice", JOptionPane.QUESTION_MESSAGE);
				}
				else if(opcion.equalsIgnoreCase("tipoestablecimiento"))
				{
					tipoEstablecimiento = JOptionPane.showInputDialog(this, "Ingrese el tipo de establecimiento a consultar", "Mostrar Indice", JOptionPane.QUESTION_MESSAGE);
				}

				double indiceAforo = aforoCC.darIndiceAforo(opcion, horaIni, horaFin, idEspacio, tipoEstablecimiento);

				String resultado = "";
				if (indiceAforo == -1)
					resultado += "Opción invalida";
				else
				{
					resultado += "Índice aforo del " + opcion + " es:\n\n";
					resultado += indiceAforo*100 + "%";
					resultado += "\n Operación terminada";
				}

				panelDatos.actualizarInterfaz(resultado);

			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
						e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void cambiarEstadoVisitante()
	{
		try 
		{
			String idVisStr = JOptionPane.showInputDialog(this, "Ingrese el id del visitante", "Cambiar estado visitante", JOptionPane.QUESTION_MESSAGE);
			String nuevoEstado = JOptionPane.showInputDialog(this, "Ingrese el nuevo estado", "Cambiar estado visitante", JOptionPane.QUESTION_MESSAGE);
			if (idVisStr != null && nuevoEstado != null)
			{
				long resp = aforoCC.cambiarEstadoVisitante(Long.valueOf(idVisStr), nuevoEstado);
				String resultado = "Se actualizaron " + resp + " registros";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
						e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void cambiarEstadoEspacio()
	{
		try 
		{
			String idEspStr = JOptionPane.showInputDialog(this, "Ingrese el id del espacio", "Cambiar estado espacio", JOptionPane.QUESTION_MESSAGE);
			String nuevoEstado = JOptionPane.showInputDialog(this, "Ingrese el nuevo estado", "Cambiar estado espacio", JOptionPane.QUESTION_MESSAGE);
			if (idEspStr != null && nuevoEstado != null)
			{
				long resp = aforoCC.cambiarEstadoEspacio(Long.valueOf(idEspStr), nuevoEstado);
				String resultado = "Se actualizaron " + resp + " registros";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
						e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void actualizarEstados()
	{
		try 
		{
			
				aforoCC.actualizarEstados();
				String resultado = "Se actualizaron los estados de los visitantes y de los espacios";
				panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
						e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void deshabilitarEspacio()
	{
		try 
		{
			String idEspStr = JOptionPane.showInputDialog(this, "Ingrese el id del espacio", "Deshabilitar un espacio", JOptionPane.QUESTION_MESSAGE);
			if (idEspStr != null)
			{
				long resp = aforoCC.desHabilitarUnEspacio(Long.valueOf(idEspStr));
				String resultado = "Se actualizaron " + resp + " registros";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
					e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void habilitarEspacio()
	{
		try 
		{
			String idEspStr = JOptionPane.showInputDialog(this, "Ingrese el id del espacio", "habilitar un espacio", JOptionPane.QUESTION_MESSAGE);
			if (idEspStr != null)
			{
				long resp = aforoCC.desHabilitarUnEspacio(Long.valueOf(idEspStr));
				String resultado = "Se actualizaron " + resp + " registros";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


        
    public void encontrarVisitantesQueTuvieronContactoConOtroDeterminadoVisitante()
    {
    	try 
    	{
    		String idVisitante = JOptionPane.showInputDialog (this, "Ingrese el codigo del visitante determinado", "Encontrar visitante", JOptionPane.QUESTION_MESSAGE);
    		String fecha = JOptionPane.showInputDialog(this, "Ingrese fecha de la consulta", "Encontrar visitante", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if (idVisitante != null && fecha != null)
    		{
    			//el formato debe ser ("yyyy/MM/dd HH:mm:ss")
    			Date date = new Date(fecha);
    			
    			List<Visitante> visitantes = aforoCC.encontrarVisitantesQueTuvieronContactoConOtroDeterminadoVisitante(idVisitante, date);
    			String resultado = "Visitantes que tuvieron contacto en los últimos 10 días con el visitante con id " + idVisitante + "\n\n";
    			
    			for(Visitante vis: visitantes)
    			{
    				resultado+=vis.getNombre() +"\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void darVisitantesFrecuentesEspacio()
    {
    	try 
    	{
    		String espacio = JOptionPane.showInputDialog (this, "Ingrese el id del espacio a consultar", "Encontrar clientes frecuentes", JOptionPane.QUESTION_MESSAGE);    		
    		
    		if (espacio != null)
    		{    
    			String resultado = "Consumidores frecuentes del espacio con id " + espacio + "\n\n";
    			
    			List<Visitante> frecuentes = aforoCC.darVisitantesFrecuentesEspacio(espacio);
    			
    			for(Visitante vis: frecuentes)
    			{
    				resultado+= vis.toString();
    				resultado += "\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //RFC10 
    public void consultarVisitasEnAforoCC()
    {
    	try 
    	{
    		String fechaInicial = JOptionPane.showInputDialog (this, "Ingrese la fecha inicial del rango a consultar", "Consultar visitas en aforo cc", JOptionPane.QUESTION_MESSAGE);
    		String fechaFinal = JOptionPane.showInputDialog (this, "Ingrese la fecha final del rango a consultar", "Consultar visitas en aforo cc", JOptionPane.QUESTION_MESSAGE);
    		String criterioOrdenamiento = JOptionPane.showInputDialog (this, "Ingrese el criterio de ordenamiento de la consulta", "Consultar visitas en aforo cc", JOptionPane.QUESTION_MESSAGE);
    		
    		if (fechaInicial != null && fechaFinal != null && criterioOrdenamiento != null)
    		{    
    			String resultado = "Visitantes encontrados: \n\n";
    			
    			List<Visitante> visitantesEncontrados = aforoCC.consultarVisitasEnAforoCC(fechaInicial, fechaFinal, criterioOrdenamiento);
    			
    			for(Visitante vis: visitantesEncontrados)
    			{
    				resultado+= vis.toString();
    				resultado += "\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    




	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogAforo ()
	{
		mostrarArchivo ("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogAforo ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecución de la demo y recolección de los resultados
			long eliminados [] = aforoCC.limpiarAforoCC();

			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}

	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}

	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}

	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
	/**


    /**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazAforoCCApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazAforoCCApp interfaz = new InterfazAforoCCApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
