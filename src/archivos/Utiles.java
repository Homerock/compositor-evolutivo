package archivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

import excepciones.ArchivosException;
import utiles.*;

public class Utiles {


/*################################################################################################################
 ###################						CONSTANTES						###################################### 
 ################################################################################################################# */
	

	private static String[] notasPpales= {"A","B","C","D","E","F","G"};
	private static ArrayList<String> notasPosibles=new ArrayList(Arrays.asList(notasPpales));

	private static boolean DEBUG = false;

	/*################################################################################################################
  ###################						 FUNCIONES 						###################################### 
 ################################################################################################################# */

	//#########################################################################################
	/**
	 *
	 * Quita los repeats del arreglo de notas y agrega las notas necesarias
	 * 
	 * Formas utilizadas por los archivo mma :
	 * 
	 *		- 1ra forma:  Repeat - [bloqueA]-RepeatEnd (cantidad de veces) :
	 *    			se repite todo el bloqueA la cantidad de veces especificado            
	 *
	 *		- 2da forma:  Repeat - [BloqueA]- RepeatEnding (cantidad de veces) - [BloqueB] - RepeatEnd :
	 *			Se repite el bloque entre repeat y repeatEnd (BloqueA + BloqueB) la cantidad de veces menos la ultima, 
	 *			que va desde el repeat hasta el repeatEnding (BloqueA) 
	 * 
	 * 
	 * @param	ArrayList con la cancion y sus repeats
	 * @param soloAcordes: indica si solo guardaremos acordes (true), o todo 
	 * @return ArrayList con la cancion sin los repeats o null en caso de error
	 * @throws ArchivosException 
	 *  
	 **/
	//#########################################################################################
	public static ArrayList<String> quitarRepets(ArrayList<String> cancion, boolean soloAcordes) throws ArchivosException{

		ArrayList<Integer> arrayRepeat=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnding=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnd=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeticiones=new ArrayList<Integer>();

		int pos = 0;
		int cantRepeticiones = 0;
		ArrayList<String> listaDeNotas=new ArrayList<String>();
		String valor="";

		String Traza="\n";
	//	Reconocedor.cargarTablasAcordes();
		try {
			while(pos< cancion.size()){
				valor =cancion.get(pos);
				if (!valor.startsWith(Constantes.REPEAT)) {

					if (soloAcordes) {
						//if (esNotaValida(valor)) { //Metodo anterior a usar el reconocedor
						if (Reconocedor.esAcordeValido(valor)) {
							listaDeNotas.add(valor);
						}
					} else {
						listaDeNotas.add(valor);
					}
				}

				//si es repeat ending
				if (valor.indexOf(Constantes.REPEAT_ENDING)!=-1){

					if (!arrayRepeatEnding.contains(pos)) {
						arrayRepeatEnding.add(pos);
						arrayRepeticiones.add(calcRepeticiones(valor));
						Traza+= "array repeatending :"+arrayRepeatEnding.toString()+"\n";
						Traza+= "array repeticiones :"+arrayRepeticiones.toString()+"\n";
					}

					// si llegamos a cero
					if (arrayRepeticiones.get(arrayRepeticiones.size()-1)==0){
						pos=arrayRepeatEnd.get(arrayRepeatEnd.size()-1);
						arrayRepeticiones.remove(arrayRepeticiones.size()-1);
						arrayRepeatEnd.remove(arrayRepeatEnd.size()-1);
						arrayRepeat.remove(arrayRepeat.size()-1);
						arrayRepeatEnding.remove(arrayRepeatEnding.size()-1);

						Traza+= "array repeat (r):"+arrayRepeat.toString()+"\n";
						Traza+= "array repeatEnd (r):"+arrayRepeatEnd.toString()+"\n";
						Traza+= "array repeatending (r):"+arrayRepeatEnding.toString()+"\n";
						Traza+= "array repeticiones (r):"+arrayRepeticiones.toString()+"\n";

					}

				}else {
					// si es repeatEnd
					if(valor.indexOf(Constantes.REPEAT_END)!=-1){

						if (!arrayRepeatEnd.contains(pos)) {
							arrayRepeatEnd.add(pos);
							Traza+= "array repeatend :"+arrayRepeatEnd.toString()+"\n";

							int posRepeat=0;
							int posRepeatEnding=0;
							//si el ARRAY REPEATENDING NO ES VACIO
							if (!arrayRepeatEnding.isEmpty()){
								posRepeatEnding=arrayRepeatEnding.get(arrayRepeatEnding.size()-1);
							}

							posRepeat = arrayRepeat.get(arrayRepeat.size()-1); 
							//si no tengo un repeatEnding para el ultimo repeat
							// debe estar entre repeat y repeatEnd , sino es asi es de otro bucle
							if(posRepeatEnding<=posRepeat | posRepeatEnding>pos ){
								arrayRepeticiones.add(calcRepeticiones(valor));
								Traza+= "array repeticiones :"+arrayRepeticiones.toString()+"\n";
							}
						}
						if (!arrayRepeticiones.isEmpty() && arrayRepeticiones.get(arrayRepeticiones.size()-1)>0){
							cantRepeticiones = arrayRepeticiones.get(arrayRepeticiones.size()-1);
							pos=arrayRepeat.get(arrayRepeat.size()-1);
							cantRepeticiones = cantRepeticiones - 1;
							arrayRepeticiones.set(arrayRepeticiones.size()-1, cantRepeticiones);
						}	else {

							if (!arrayRepeticiones.isEmpty()){
								arrayRepeticiones.remove(arrayRepeticiones.size()-1);
							}
							arrayRepeatEnd.remove(arrayRepeatEnd.size()-1);
							arrayRepeat.remove(arrayRepeat.size()-1);
							Traza+= "array repeats (r):"+arrayRepeat.toString()+"\n"; 
							Traza+= "array repeatend (r):"+arrayRepeatEnd.toString()+"\n";
							Traza+= "array repeatending (r):"+arrayRepeatEnding.toString()+"\n";
						}
					}else {
						//si es Repeat
						if (valor.indexOf(Constantes.REPEAT)!=-1){
							if (!arrayRepeat.contains(pos)) {
								arrayRepeat.add(pos);
								Traza+= "array repeats :"+arrayRepeat.toString()+"\n";
							}
						}
					}
				}
				pos++;

			}//fin del while
		}catch(ArrayIndexOutOfBoundsException e) {
			if (DEBUG) {
				System.out.print(cancion.toString());
				System.out.println("error de indice de array \n" );
				System.out.println(Traza);
				e.printStackTrace();
			}
			throw new ArchivosException("Error en la definicion de repeats - Archivo mal formado");
			
		}
		return listaDeNotas;
	}

	/**
	 * 	
	 * @param cancionSinRepeats
	 * @return
	 */
	public static int obtenerDuracion(ArrayList<String> cancionSinRepeats){
		int contador=0;
		
		for(String linea: cancionSinRepeats ){
			
			if (	!Utiles.cadenaContienePatron(linea,Constantes.ESTILO)  &&
					!Utiles.cadenaContienePatron(linea,Constantes.TEMPO)  ) {
				contador ++;
			}
		}
		return contador;
	}
	
	public static boolean cadenaContieneNumero(String linea) {
		
		StringTokenizer tokens = new StringTokenizer(linea);
		String primerToken =tokens.nextToken();
		if (Utiles.isNumeric(primerToken)){
			return true;
		}
		return false;
		
	}

	//#########################################################################################
	/**
	 * calcula las repeticiones a realizar
	 * 
	 * Si tiene especificado la cantidad devolvera ese valor , en caso contrario 1
	 * 
	 * @param linea ej : "RepeatEnd" o "RepeatEnd 2"
	 * @return la cantidad de repeticiones
	 **/
	//#########################################################################################
	private static int calcRepeticiones(String linea){

		int cant = 1;
		try {
			StringTokenizer tokens = new StringTokenizer(linea);
			String tok = tokens.nextToken();
			tok =tokens.nextToken();			
			cant = Integer.parseInt(tok);
		}
		catch (NoSuchElementException e) {}
		catch (NumberFormatException e){}

		return cant;
	}

	//#########################################################################################
	/**
	 * verifica si es un numero una cadena
	 * @param cadena
	 * @return
	 **/
	//#########################################################################################
	public static boolean isNumeric(String cadena){
		try{
			Integer.parseInt(cadena);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

	//#########################################################################################
	/**
	 * quita los comentarios de una linea
	 *  
	 * @param linea 
	 * 	ej : "RepeatEnding          // In the sheet music this is a "to coda" "
	 * @return cadena sin comentarios
	 * 	ej :  "RepeatEnding"      
	 **/
	//#########################################################################################
	public static String quitarComentarios(String linea){

		if(linea.startsWith(Constantes.COMENTARIO)){
			return Constantes.CADENAVACIA;
		}
		String[] campos = linea.split(Constantes.COMENTARIO);	
		return campos[0];
	}

	//#########################################################################################
	/**
	 * dada una linea y un separador (token) devuelve el siguiente token al separador.
	 * 
	 * @param linea :"1 acorde1 / "
	 * @param token : " "
	 * @return siguiente token : "acorde1"
	 */
	//#########################################################################################
	public static String obtenerDatos(String linea, String token){

		try {
			StringTokenizer tokens = new StringTokenizer(linea, token);
			tokens.nextToken();
			return tokens.nextToken();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
	
	/**
	 * 
	 * @param linea
	 * @param token
	 * @return
	 */
	public static String obtenerCadena(String linea, String token){

		try {
			StringTokenizer tokens = new StringTokenizer(linea, token);
			return tokens.nextToken();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	//#########################################################################################
	/**
	 *  dice si la linea contiene la definicion de un estilo 
	 *  
	 * @param linea
	 * @return  true si tiene la definicion de un estilo false sino
	 */
	//#########################################################################################
	public static boolean defineUnEstilo(String linea){
		
		//EJEMPLO : "Set MainPlus Groove Swing1Plus"
		
		if(linea.trim().startsWith(Constantes.DEFINICION_VARIABLE) && linea.indexOf(Constantes.ESTILO)!=-1){
			return true;
		}
		return false;
	}
	
	//#########################################################################################
	/**
	 * retorna la cantidad de acordes validos que tiene una linea
	 * si empieza con una barra(/) se cuenta como un acorde
	 * si la barra esta en el medio no se cuenta
	 * 
	 * @param linea
	 * @return cantidad de acordes validos
	 */
	//#########################################################################################
	public static int calculaCantAcordesPorCompas(String linea) {
		int cant=0;
		//linea = linea.trim();
		linea = quitarNumeroDeCadena(linea).trim();
		
		String[] tokens = linea.split(" ");
		
		if(tokens.length>0){
			// si empieza con "/"
			if(tokens[0].equals(Constantes.ACORDE_REPETIDO)){
				cant++;
			}
			
			for (String tok : tokens) {
				if (Reconocedor.esAcordeValido(tok)) {
					cant++;
				}
			}
		}
		return cant;
		
	}
	//#########################################################################################
	/**
	 * Quita el numero (al principio) de una cadena , si no tuviera devuelve la misma cadena.
	 * 	
	 * @param cadena :"1 Acorde1 acorde 2"
	 * @return cadena sin numero al principio "Acorde1 acorde 2"
	 * 
	 *///######################################################################################
	public static String quitarNumeroDeCadena(String cadena){
		String aux="";
		StringTokenizer tokens = new StringTokenizer(cadena);
		String primerToken =tokens.nextToken();
		
		if (isNumeric(primerToken)){
			while (tokens.hasMoreTokens()){//algo en el otro
				String tok = tokens.nextToken();
				aux = aux + " " + tok;
			}
			aux = aux.trim();	
		}else{
			aux = cadena;
		}
		
		return aux;
		
	}
	//#########################################################################################
	/**
	 * Verifica si la "cadena" contiene el "patron" dentro de ella
	 *  
	 * @param cadena
	 * @param patron
	 * @return true si la cadena contiene el patron
	 */
	//#########################################################################################
	public static boolean cadenaContienePatron(String cadena,String patron){
		
		if(cadena.indexOf(patron)!=-1){
			return true;
		}
		return false;
	}
	
	//#########################################################################################
	/**
	 * @return
	 **/
	//#########################################################################################
	public static ArrayList<String> getNotasPosibles() {
		return notasPosibles;
	}

	//#########################################################################################
	/**
	 * @param notasPosibles
	 **/
	//#########################################################################################
	public void setNotasPosibles(ArrayList<String> notasPosibles) {
		this.notasPosibles = notasPosibles;
	}
	
	
	
	//#########################################################################################
	/**
	 * GENERA UN NUMERO ALEATORIO ENTRE EL MINIMO Y EL MAXIMO 
	 * @param min
	 * @param max
	 * @return
	 */
	//#########################################################################################
	public static int generarNumeroAleatorio(int min,int max){
		Random rnd = new Random();
		int maxIteraciones = 50;
		int num = 0;
		for (int i =0 ; i< maxIteraciones;i++){
			num = rnd.nextInt(max+1);
			if(num >=min && num <=max){
				break;
			}		
		}
		return num;
	}
	
	public static String reemplazarEspaciosDeString(String texto) {
		
		String cadena = "";
		String espacio = " ";
		
		for (int i=0; i<texto.length();i++) {
			
			if (texto.charAt(i) == espacio.charAt(0)) {
				cadena = cadena + "_";
			} else {
				cadena = cadena + texto.charAt(i);
			}
			
		}
			
		
		return cadena;
	}
}
