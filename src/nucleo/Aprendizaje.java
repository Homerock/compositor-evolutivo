package nucleo;

import java.util.ArrayList;
import java.util.StringTokenizer;

import utiles.Constantes;

import canciones.Acorde;
import canciones.CancionAprendida;
import archivos.Archivos;
import archivos.Estilos;
import archivos.Reconocedor;
import archivos.Utiles;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import excepciones.ArchivosException;
import excepciones.CancionException;
import excepciones.EstilosException;
import excepciones.ValoresException;

public class Aprendizaje {
	
	
	//#########################################################################################
	/**
	 * procesarArchivo
	 * Se encarga de analizar una cancion extraida de un archivo
	 * carga las matrices de acordes y de estilos, ademas obtiene tonica, tempo y duracion del tema
	 * y los carga en sus listas correspondientes.
	 * @param miArchivo
	 * @return 
	 * @throws EstilosException 
	 * @throws ValoresException 
	 * @throws CancionException 
	 **/
	//#########################################################################################
	public static CancionAprendida aprenderCancion(String nombreCancion) throws IndexOutOfBoundsException, EstilosException, ValoresException,ArchivosException, CancionException {

		// convertimos el archivo en una lista 
		ArrayList<String> cancion = Archivos.convierteArchivoEnLista(nombreCancion);
		ArrayList<String> cancionSinRepeats = Utiles.quitarRepets(cancion, false);// quitar el reconocedor de notos de ahi
		
		String tempo = obtenerTempo(cancionSinRepeats);
		int duracion = Utiles.obtenerDuracion(cancionSinRepeats);
		String estiloPrincipal = Estilos.deteminarEstiloPrincipal(cancionSinRepeats);
		ArrayList<String> listaAcordes = obtenerListaAcordes(cancionSinRepeats);
		String tonica = listaAcordes.get(0);		
		
		
		CancionAprendida cancionAprendida = new CancionAprendida(
													nombreCancion, 
													tempo, duracion, 
													new Acorde(tonica), 
													estiloPrincipal,
													cancionSinRepeats,
													listaAcordes);

		return cancionAprendida;
	}
	
	private static String obtenerTempo(ArrayList<String> cancion) throws CancionException{
		
		String tempo ="";
		
		for(String linea : cancion){
			if( Utiles.cadenaContienePatron(linea, Constantes.TEMPO)){
				tempo =Utiles.obtenerDatos(linea, " ");
				break;
			}
		}
		
		if(tempo.trim().length()==0){
			throw new CancionException("La cancion NO tiene tempo definido.");
		}
		
		return tempo;
	}
	
	private static ArrayList<String> obtenerListaAcordes (ArrayList<String> cancionSinRepeats) {
		
		ArrayList<String> listaAcordes = new ArrayList<String>();
		StringTokenizer tokens;
		
		for (String linea : cancionSinRepeats) {
			
			if (	!Utiles.cadenaContienePatron(linea,Constantes.ESTILO)  &&
					!Utiles.cadenaContienePatron(linea,Constantes.TEMPO)  ) {
				
				tokens = new StringTokenizer(linea);
				String primerToken =tokens.nextToken();
				if (Utiles.isNumeric(primerToken)){
					//agregamos los posibles  acordes y "/" en el arraylist
					while(tokens.hasMoreTokens()){
						String tok = tokens.nextToken();
						if (Reconocedor.esAcordeValido(tok)) {
							listaAcordes.add(tok);
						}
					}	
				}
			}
		}
		
		return listaAcordes;
	}

}
