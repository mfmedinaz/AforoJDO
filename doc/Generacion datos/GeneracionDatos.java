package sistrans;

import java.io.*;

public class GeneracionDatos 
{
	public static void main(String[] args)
	{
		try
		{
			generarLectores();
			generarEstadosEspacio();
			generarEspacios();
			generarBanios();
			generarParquederos();
			generarLocalesComerciales();
			generarEstadosVisitantes();
			generarVisitantes();
			generarVisitas();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void generarLectores() throws FileNotFoundException
	{
		File aLector = new File("./data/datosLectores.csv");
		PrintWriter pwLector = new PrintWriter(aLector);
		String datoLector = "ID";
		pwLector.println(datoLector);
		for (int i = 1; i <= 1001; i++) {
			pwLector.println(i);
		}
		pwLector.close();
	}
	
	public static void generarEstadosEspacio() throws FileNotFoundException
	{
		String[] estados = {"VERDE", "NARANJA", "ROJO", "DESHABILITADO", "DESOCUPADO"};
		File file = new File("./data/datosEstadoEspacio.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID,NOMBRE,FECHA_ASIGNACION";
		pw.println(dato);
		
		for (int i = 1; i <= 1000; i++)
		{
			dato = i + "," + estados[numeroAleatorio(0, estados.length-1)] + "," + fechaAleatoria();
			pw.println(dato);
		}
		pw.close();
	}
	
	public static void generarEspacios() throws FileNotFoundException
	{
		String[] tipos = {"LOCAL_COMERCIAL", "BAÑO", "PARQUEADERO", "ASCENSOR"};
		File file = new File("./data/datosEspacio.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID,NOMBRE,CENTRO_COMERCIAL,LECTOR,TIPO,ESTADO";
		pw.println(dato);
		
		for (int i = 1; i <= 1000; i++)
		{
			dato = i + "," + cadenaAleatoria(10) + "," + 1 + "," + (i+1) + "," + tipos[numeroAleatorio(0, tipos.length-1)] + "," + i;
			pw.println(dato);
		}
		pw.close();
	}
	
	public static void generarBanios() throws Exception
	{
		File file = new File("./data/datosBanio.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID_ESPACIO,NUMERO_SANITARIOS";
		pw.println(dato);
		
		BufferedReader br = new BufferedReader(new FileReader("./data/datosEspacio.csv"));
		String linea = br.readLine();
		linea = br.readLine();
		while(linea != null)
		{
			String[] valores = linea.split(",");
			if(valores[4].equals("BAÑO"))
			{
				dato = valores[0] + "," + numeroAleatorio(5, 20);
				pw.println(dato);
			}
			linea = br.readLine();
		}
		br.close();
		pw.close();
	}
	
	public static void generarParquederos() throws Exception
	{
		File file = new File("./data/datosParqueadero.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID_ESPACIO,CAPACIDAD_NORMAL";
		pw.println(dato);
		
		BufferedReader br = new BufferedReader(new FileReader("./data/datosEspacio.csv"));
		String linea = br.readLine();
		linea = br.readLine();
		while(linea != null)
		{
			String[] valores = linea.split(",");
			if(valores[4].equals("PARQUEADERO"))
			{
				dato = valores[0] + "," + numeroAleatorio(5, 10);
				pw.println(dato);
			}
			linea = br.readLine();
		}
		br.close();
		pw.close();
	}
	
	public static void generarLocalesComerciales() throws Exception
	{
		String[] tipos = {"RESTAURANTE", "SUPERMERCADO", "TIENDA"};
		File file = new File("./data/datosLocalComercial.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID_ESPACIO,AREA,TIPO_ESTABLECIMIENTO";
		pw.println(dato);
		
		BufferedReader br = new BufferedReader(new FileReader("./data/datosEspacio.csv"));
		String linea = br.readLine();
		linea = br.readLine();
		while(linea != null)
		{
			String[] valores = linea.split(",");
			if(valores[4].equals("LOCAL_COMERCIAL"))
			{
				dato = valores[0] + "," + numeroAleatorio(100, 500) + "," + tipos[numeroAleatorio(0, tipos.length-1)];
				pw.println(dato);
			}
			linea = br.readLine();
		}
		br.close();
		pw.close();
	}
	
	public static void generarEstadosVisitantes() throws FileNotFoundException
	{
		String[] estados = {"VERDE", "NARANJA", "ROJO", "POSITIVO"};
		File file = new File("./data/datosEstadoVisitante.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID,NOMBRE,FECHA_ASIGNACION";
		pw.println(dato);
		
		for (int i = 1; i <= 100000; i++)
		{
			dato = i + "," + estados[numeroAleatorio(0, estados.length-1)] + "," + fechaAleatoria();
			pw.println(dato);
		}
		pw.close();
	}
	
	public static void generarVisitantes() throws FileNotFoundException
	{
		String[] tipos = {"EMPLEADO_RESTAURANTE", "EMPLEADO_SUPERMERCADO", "EMPLEADO_TIENDA", "EMPLEADO_CC", "CONSUMIDOR"};
		File file = new File("./data/datosVisitante.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID,NOMBRE,CORREO,TELEFONO,NOMBRE_EMERGENCIA,TELEFONO_EMERGENCIA,TIPO_VISITANTE,CODIGO_QR,CENTRO_COMERCIAL,ESTADO";
		pw.println(dato);
		
		for (int i = 1; i <= 100000; i++)
		{
			String nombre = cadenaAleatoria(15);
			String nomEm = cadenaAleatoria(15);
			dato = i + "," + nombre + "," + nombre + "@correo.com," + telefonoAleatorio() + "," + nomEm + "," + telefonoAleatorio() + "," 
					+ tipos[numeroAleatorio(0, tipos.length-1)] + "," + "CODIGO-QR-" + i + "," + 1 + "," + i;
			pw.println(dato);
		}
		pw.close();
	}
	
	public static void generarVisitas() throws FileNotFoundException
	{
		File file = new File("./data/datosVisita.csv");
		PrintWriter pw = new PrintWriter(file);
		String dato = "ID,HORA_INICIAL,HORA_FINAL,VISITANTE,LECTOR";
		pw.println(dato);
		
		for (int i = 1; i <= 800000; i++)
		{
			dato = i + "," + horarioAleatorio() + "," + numeroAleatorio(1, 100000) + "," + numeroAleatorio(1, 1001);
			pw.println(dato);
		}
		pw.close();
	}
	
	public static String fechaAleatoria()
	{
		int anio = numeroAleatorio(2015, 2020);
		int mes = numeroAleatorio(1, 12);
		int dia = numeroAleatorio(1, 28);
		int hora = numeroAleatorio(0, 23);
		int min = numeroAleatorio(0, 59);
		int sec = numeroAleatorio(0, 59);

		return (anio + "-" + mes + "-" + dia + " " + hora + ":" + min + ":" + sec);

	}
	
	public static String horarioAleatorio()
	{
		int anio = numeroAleatorio(2015, 2020);
		int mes = numeroAleatorio(1, 12);
		int dia = numeroAleatorio(1, 28);
		int hora1 = numeroAleatorio(0, 23);
		int min1 = numeroAleatorio(0, 59);
		int sec1 = numeroAleatorio(0, 59);
		int hora2 = numeroAleatorio(0, 23);
		int min2 = numeroAleatorio(0, 59);
		int sec2 = numeroAleatorio(0, 59);

		String fechaIni = anio + "-" + mes + "-" + dia + " " + hora1 + ":" + min1 + ":" + sec1;
		String fechaFin = anio + "-" + mes + "-" + dia + " " + hora2 + ":" + min2 + ":" + sec2;

		boolean seguir = true;
		if(hora1 < hora2)
			seguir = false;
		else if(hora1 == hora2 && min1 < min2)
			seguir = false;
		else if(hora1 == hora2 && min1 == min2 && sec1 < sec2)
			seguir = false;
		
		while (seguir)
		{
			hora2 = numeroAleatorio(0, 23);
			min2 = numeroAleatorio(0, 59);
			sec2 = numeroAleatorio(0, 59);
			fechaFin = anio + "-" + mes + "-" + dia + " " + hora2 + ":" + min2 + ":" + sec2;
			if(hora1 < hora2)
				seguir = false;
			else if(hora1 == hora2 && min1 < min2)
				seguir = false;
			else if(hora1 == hora2 && min1 == min2 && sec1 < sec2)
				seguir = false;
		}
		return( fechaIni + "," + fechaFin);
	}
	
	public static String cadenaAleatoria(int longitud)
	{
		char[] abc = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ ".toCharArray();
		String cadena = "";
		for (int i = 0; i < longitud; i++) 
		{
			int pos = numeroAleatorio(0, abc.length-1);
			cadena += abc[pos];
		}
		return (cadena);
	}
	
	public static String telefonoAleatorio()
	{
		char[] numeros = "0123456789".toCharArray();
		String cadena = "";
		for (int i = 0; i < 10; i++) 
		{
			int pos = numeroAleatorio(0, numeros.length-1);
			cadena += numeros[pos];
		}
		return (cadena);
	}

	public static int numeroAleatorio(int inicio, int fin) 
	{
		return inicio + (int)Math.round(Math.random() * (fin - inicio));
	}
	
}
