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
				throw new ArchivosException("El formato del fichero "+ fichero.getName()+ " es incorrecto.");
				
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
		}catch (ArchivosException ae){
			
			throw new ArchivosException("No contemplamos definiciones de nuevos estilos, en :"+nombreArchivo);
		} catch (FileNotFoundException e) {
			throw new ArchivosException("El Archivo '"+nombreArchivo+"' No existe.");
		} catch (IOException e1) {
			throw new ArchivosException("Error en la lectura de archivo :"+nombreArchivo + ".");
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
					throw new ArchivosException("No contemplamos definiciones de nuevos estilos.");
				}
				
				if(Utiles.cadenaContienePatron(linea, Constantes.COMIENZO_DE_VARIABLE)){
					
					if(Utiles.cadenaContienePatron(linea, Constantes.VAR_ULTIMO_ESTILO)){
						cancion.add(linea);// carga si es $_LastGroove
					}else{
						//ej :
						//Set Pass 1
						//Groove $Pass BossaNova BossaNovaSus BossaNova1Sus

						throw new ArchivosException("No contemplamos variables en definicion de estilos.");
					}
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
	 * @throws ArchivosException 
	 */
	//################################################################################
	public static boolean generarArchivo(Cancion miCancion) throws ArchivosException {
		
		String compas = " ";
		ArrayList<Estrofa> todasLasEstrofas = miCancion.getEstrofas();
		int linea = 1;
		
		String nombre = Utiles.reemplazarEspaciosDeString(miCancion.getNombreArchivo());
		
		String sFichero = nombre+Constantes.EXTENSION_ARCHIVO;
		File fichero = new File(sFichero);
		
		while(fichero.exists()) {
			nombre = nombre+"_";
			sFichero = nombre+Constantes.EXTENSION_ARCHIVO;
			fichero = new File(sFichero);
			
		}
		
		miCancion.setNombreArchivo(nombre);

		
		escribirArchivo(miCancion.getNombreArchivo()+Constantes.EXTENSION_ARCHIVO, "Tempo " + miCancion.getTempo(), false);
		escribirArchivo(miCancion.getNombreArchivo()+Constantes.EXTENSION_ARCHIVO, "", true);
		
		for (Estrofa est : todasLasEstrofas) {
			
			escribirArchivo(miCancion.getNombreArchivo()+Constantes.EXTENSION_ARCHIVO, "Groove " + est.getEstilo(), true);
			
			ArrayList<Compas> todosLosCompases = est.getListaDeCompases();
		
			for (Compas com : todosLosCompases) {
				ArrayList<Acorde> todosLosAcordes = com.getAcordes();
				compas = " ";
				for (Acorde ac : todosLosAcordes) {
					compas = compas + " " + ac.getNombre();
				}
				escribirArchivo(miCancion.getNombreArchivo()+Constantes.EXTENSION_ARCHIVO, linea + compas, true);
				linea++;
			}	
		}
		//System.out.println("Nuevo archivo generado: " + miCancion.getNombreArchivo()+Constantes.EXTENSION_ARCHIVO);

		return crearMIDI(miCancion.getNombreArchivo(), false);
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
	private static boolean crearMIDI(String nombreArchivo, boolean flagbackground) throws ArchivosException{
		
		
		// Definimos la cadena del interprete de comandos del sistema 
		String commandShell=null; 
	
		// Recuperamos el sistema operativo 
		String osName = System.getProperty ( "os.name" ); 
	
		// Cargamos la cadena del interprete de comandos según el sistema operativo y el comando a ejecutar 
		if ( osName.startsWith("Windows") ) {
			String curDir =  System.getProperty("user.dir");
			commandShell = "cmd /c C:\\MMA\\python.exe C:\\MMA\\MMA.py  \""+curDir+'\\'+ nombreArchivo+".mma\"";
			System.out.println(commandShell);
		}else{ 
			// 	En UNIX y LUNIX podemos lanzar el proceso en background sufijandolo con & 
			if (flagbackground) 
				commandShell = "mma " + nombreArchivo +" &" ; 
			else 
				commandShell = "mma " + nombreArchivo ;
		}
			// Lanzamos el proceso	
		try { 
			Process proc = Runtime.getRuntime ().exec (commandShell); 
			BufferedReader brStdOut = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
			BufferedReader brStdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream())); 
			String str=null; 
			
			while ((str = brStdOut.readLine())!=null) { 
				System.out.println (str); 
				if (str.contains(Constantes.MMA_ERROR)) {
					throw new ArchivosException("mma no pudo generar el archivo MIDI -"+str);
				}
			} 
			
			brStdOut.close(); 
			brStdErr.close(); 
			} catch (ArchivosException ae) {
				throw new ArchivosException(ae.getMessage());
			}catch (Exception e) { 
				return false;  
			} 
		return true; 
	}

}
