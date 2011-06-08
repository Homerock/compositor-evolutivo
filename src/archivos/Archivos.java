package archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

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
				
				if (linea.startsWith(Utiles.NUEVO_ESTILO)){
					throw new ArchivosException("No contemplamos definiciones de nuevos estilos. "+ArchivosOLD.class);
				}
				if(Utiles.cadenaContienePatron(linea, Utiles.COMIENZO_DE_VARIABLE)){
					//ej :
					//Set Pass 1
					//Groove $Pass BossaNova BossaNovaSus BossaNova1Sus

					throw new ArchivosException("No contemplamos variables en definicion de estilos."+ArchivosOLD.class);
				}
				
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(Utiles.cadenaContienePatron(linea, Utiles.REPEAT)){
					cancion.add(linea);// carga si es un repeat
				}
				if(linea.startsWith(Utiles.ESTILO)){
					cancion.add(linea); // carga si es un estilo
				}
				
				if(linea.indexOf(Utiles.TEMPO)!=-1){
					cancion.add(linea);// si define tempo	
				}
			
				
			}
		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}

}
