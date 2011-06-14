package archivos;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import utiles.Constantes;

import canciones.Acorde;
import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;

import excepciones.ArchivosException;

public class Archivos {
	
	
	

	/**
	 * 
	 * 	Abre un archivo y guarda en una lista : los datos basicos de una cancion (tempo,acordes,repeats,grooves).
	 * 
	 * @param nombreArchivo
	 * @return cancion
	 * @throws ArchivosException : si no existe el archivo o esta mal definido.
	 */
	public static ArrayList<String> convierteArchivoEnLista(String nombreArchivo) throws ArchivosException{

		File fichero = new File(nombreArchivo);
		Filtro fil = new Filtro(".mma");
		ArrayList<String> cancionAnalizada = new ArrayList<String>();
		
		try {
			if (!fil.accept(fichero, fichero.getName())) {
				throw new ArchivosException("El formato del fichero "+ fichero.getName()+ " es incorrecto"+Archivos.class);
				
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();

			while(linea != null){
				//quito los comentarios de la linea
				linea=Utiles.quitarComentarios(linea);
				if (!linea.isEmpty()){
					cargarLineaEnCancion(linea,cancionAnalizada);
				}
				linea = reader.readLine();
			}

			reader.close();
			
			return cancionAnalizada;//todo ok loco

		} catch (FileNotFoundException e) {
			throw new ArchivosException("El Archivo '"+nombreArchivo+"' No existe."+Archivos.class);
		} catch (IOException e1) {
			throw new ArchivosException("Error en la lectura de archivo :"+nombreArchivo + ". "+Archivos.class);
		}
	}
	
	//#########################################################################################
	/**
	 * 
	 * analiza una linea y guarda en la lista cancion:
	 *  	linea con los acordes .
	 *  	linea con "repeats". 
	 *  	linea con estilos .
	 *  	linea con tempo.
	 *  
	 *   Se interpretan como acordes, si la linea empiza con un numero.
	 *   
	 *   
	 * @param cancion : lista
	 * @param linea 
	 * @throws ArchivosException : 
	 * 					Si se define un estilo se lanza una ArchivosException. 
	 * 					Si se definen variables tambien. 
	 */
	//#########################################################################################
	public static void cargarLineaEnCancion(String linea,ArrayList<String> cancion) throws ArchivosException{

		try{
			StringTokenizer tokens = new StringTokenizer(linea);
			String primerToken =tokens.nextToken();
			String acordesAux="";
			//si es un numero 
			if (Utiles.isNumeric(primerToken)){
				cancion.add(linea); // carga el compas completo
			}else {
				
				if (linea.startsWith(Constantes.NUEVO_ESTILO)){
					throw new ArchivosException("No contemplamos definiciones de nuevos estilos. "+Archivos.class);
				}
				if(Utiles.cadenaContienePatron(linea, Constantes.COMIENZO_DE_VARIABLE)){
					//ej :
					//Set Pass 1
					//Groove $Pass BossaNova BossaNovaSus BossaNova1Sus

					throw new ArchivosException("No contemplamos variables en definicion de estilos."+Archivos.class);
				}
				
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(Utiles.cadenaContienePatron(linea, Constantes.REPEAT)){
					cancion.add(linea);// carga si es un repeat
				}
				if(linea.startsWith(Constantes.ESTILO)){
					cancion.add(linea); // carga si es un estilo
				}
				
				if(linea.indexOf(Constantes.TEMPO)!=-1){
					cancion.add(linea);// si define tempo	
				}
			
				
			}
		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}

	//################################################################################
	/**
	 * genera un archivo de texto con el formato de mma, de acuerdo a los datos de un objeto cancion
	 * pasado por parametro.
	 * @param miCancion
	 */
	//################################################################################
	public static void generarArchivo(Cancion miCancion) {
		
		String compas = " ";
		ArrayList<Estrofa> todasLasEstrofas = miCancion.getEstrofas();
		int linea = 1;
		
		// preguntar si el nombre de la cancion ya existe en el directorio, si existe agregarle un numero
		// ejemplo: rock_Am_1 volver a preguntar a ver si este tambien existe
		
		escribirArchivo(miCancion.getNombre()+Constantes.EXTENSION_ARCHIVO, "Tempo " + miCancion.getTempo(), false);
		escribirArchivo(miCancion.getNombre()+Constantes.EXTENSION_ARCHIVO, "", true);
		
		for (Estrofa est : todasLasEstrofas) {
			
			escribirArchivo(miCancion.getNombre()+Constantes.EXTENSION_ARCHIVO, "Groove " + est.getEstilo(), true);
			
			ArrayList<Compas> todosLosCompases = est.getListaDeCompases();
		
			for (Compas com : todosLosCompases) {
				ArrayList<Acorde> todosLosAcordes = com.getAcordes();
				compas = " ";
				for (Acorde ac : todosLosAcordes) {
					compas = compas + " " + ac.getNombre();
				}
				escribirArchivo(miCancion.getNombre()+Constantes.EXTENSION_ARCHIVO, linea + compas, true);
				linea++;
			}	
		}
		System.out.println("Nuevo archivo generado: " + miCancion.getNombre()+Constantes.EXTENSION_ARCHIVO);
		crearMMA("mma " + miCancion.getNombre(), false);
	}

	//#########################################################################################
	/**
	 * Escribe en un archivo con el nombre dado
	 * el contenido especificado
	 * la variable agregar indica si se debe agregar el contenido o crear uno nuevo 
	 * 
	 * 
	 * @param nombre
	 * @param contenido
	 * @param agregar : true agrega una nueva linea , false crea uno nuevo o sobreescribe el que esta
	 **/
	//#########################################################################################
	private static void escribirArchivo(String nombre, String contenido, boolean agregar) {

		FileOutputStream archivo = null;
		BufferedOutputStream buffer = null;
		byte entrada[] = new byte [100];
		ArrayList<String> lista = new ArrayList<String>(5);

		try {
			archivo = new FileOutputStream(nombre, agregar);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			buffer = new BufferedOutputStream(archivo);
			buffer.write(contenido.getBytes());
			buffer.write("\n".getBytes());
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//################################################################################
	/**
	 * crearMMA
	 * @param command: comando a ejecutar, incluyendo parametros
	 * @param flagbackground: valor boolean para determinar si el comando se ejecuta en background (solo en linux)
	 **/
	//################################################################################
	private static boolean crearMMA(String command, boolean flagbackground) {
		
		// Definimos la cadena del interprete de comandos del sistema 
		String commandShell=null; 
	
		// Recuperamos el sistema operativo 
		String osName = System.getProperty ( "os.name" ); 
	
		// Cargamos la cadena del interprete de comandos según el sistema operativo y el comando a ejecutar 
		if ( osName.equals ("Windows XP") ) 
			commandShell = "cmd.exe /C " + command; 
		else 
			if ( osName.equals ("Windows 95") || osName.equals ("Windows 98") ) 
				commandShell = "start " + command; 
			else { 
					// 	En UNIX y LUNUX podemos lanzar el proceso en background sufijandolo con & 
				if (flagbackground) 
					commandShell = "" + command +" &" ; 
				else 
					commandShell = "" + command ; 
				} 
	
			// Lanzamos el proceso	
		try { 
			Process proc = Runtime.getRuntime ().exec (commandShell); 
			BufferedReader brStdOut = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
			BufferedReader brStdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream())); 
			String str=null; 
			while ((str = brStdOut.readLine())!=null) { 
				System.out.println (str); 
			} 
			brStdOut.close(); 
			brStdErr.close(); 
			} catch (IOException eproc) { 
					//System.out.println ("Error to execute the command : "+eproc); 
					return false; 
		} 
		return true; 
	}

}
