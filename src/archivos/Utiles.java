package archivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Utiles {


/*################################################################################################################
 ###################						CONSTANTES						###################################### 
 ################################################################################################################# */

	protected static final String COMENTARIO = "//";
	protected static final String CADENAVACIA="";
	public static final String ESTILO = "Groove";//para definir un estilo debe estar definido asi "Groove miEstilo"
	protected static final String NUEVO_ESTILO ="DefGroove";
	protected static final String VAR_ULTIMO_ESTILO ="$_LastGroove";
	public static final String END_ESTILO ="End";
	public static final String INTRO_ESTILO ="Intro";
	public static final String NOMBRE_CANCION ="temp.mma";
	protected static final String SIN_ESTILO = "sinEstilo";
	protected static final String REPEAT = "Repeat";
	protected static final String REPEAT_ENDING = "RepeatEnding";
	protected static final String REPEAT_END = "RepeatEnd";
	protected static final String TEMPO = "Tempo";
	public static final String DEFINICION_VARIABLE= "Set";
	public static final String ACORDE_REPETIDO = "/";
	public static final int UN_ACORDE = 1;
	public static final int DOS_ACORDE = 2;
	public static final int TRES_ACORDE = 3;
	public static final int CUATRO_ACORDE = 4;


	private static String[] notasPpales= {"A","B","C","D","E","F","G"};
	private static ArrayList<String> notasPosibles=new ArrayList(Arrays.asList(notasPpales));


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
	 *  
	 **/
	//#########################################################################################
	public static ArrayList<String> quitarRepets(ArrayList<String> cancion, boolean soloAcordes){

		ArrayList<Integer> arrayRepeat=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnding=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnd=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeticiones=new ArrayList<Integer>();

		int pos = 0;
		int cantRepeticiones = 0;
		ArrayList<String> listaDeNotas=new ArrayList<String>();
		String valor="";

		String Traza="\n";
		Reconocedor.cargarTablasAcordes();
		try {
			while(pos< cancion.size()){
				valor =cancion.get(pos);
				if (!valor.startsWith(REPEAT)) {

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
				if (valor.indexOf(REPEAT_ENDING)!=-1){

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
					if(valor.indexOf(REPEAT_END)!=-1){

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
						if (valor.indexOf(REPEAT)!=-1){
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
			System.out.print(cancion.toString());
			System.out.println("error de indice de array \n" );
			System.out.println(Traza);
			e.printStackTrace();
			return null;
		}
		return listaDeNotas;
	}

	//#########################################################################################
	/**
	 * obtenerDuracion
	 * Obtiene la duracion de una cancion (cantidad de compases) analizando la lista cancionConEstilos
	 * @param cancion con estilos
	 * @return la duracion de la cancion
	 **/
	//#########################################################################################
	public static int obtenerDuracion(ArrayList<String> cancion){

		ArrayList<Integer> arrayRepeat=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnding=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnd=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeticiones=new ArrayList<Integer>();

		int cont = 0;
		int pos = 0;
		int cantRepeticiones = 0;
		ArrayList<String> listaDeNotas=new ArrayList<String>();
		String valor="";

		try {
			while(pos< cancion.size()){
				valor =cancion.get(pos);
				if (!valor.startsWith(REPEAT) && !valor.startsWith(ESTILO)) {
					cont = cont+1;
				}

				//si es repeat ending
				if (valor.indexOf(REPEAT_ENDING)!=-1){

					if (!arrayRepeatEnding.contains(pos)) {
						arrayRepeatEnding.add(pos);
						arrayRepeticiones.add(calcRepeticiones(valor));
					}

					// si llegamos a cero
					if (arrayRepeticiones.get(arrayRepeticiones.size()-1)==0){
						pos=arrayRepeatEnd.get(arrayRepeatEnd.size()-1);
						arrayRepeticiones.remove(arrayRepeticiones.size()-1);
						arrayRepeatEnd.remove(arrayRepeatEnd.size()-1);
						arrayRepeat.remove(arrayRepeat.size()-1);
						arrayRepeatEnding.remove(arrayRepeatEnding.size()-1);
					}

				}else {
					// si es repeatEnd
					if(valor.indexOf(REPEAT_END)!=-1){

						if (!arrayRepeatEnd.contains(pos)) {
							arrayRepeatEnd.add(pos);
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
						}
					}else {
						//si es Repeat
						if (valor.indexOf(REPEAT)!=-1){
							if (!arrayRepeat.contains(pos)) {
								arrayRepeat.add(pos);
							}
						}
					}
				}
				pos++;

			}//fin del while
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.print(cancion.toString());
			System.out.println("error de indice de array \n" );
			e.printStackTrace();
			return 0;
		}
		return cont;
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

		if(linea.startsWith(COMENTARIO)){
			return CADENAVACIA;
		}
		String[] campos = linea.split(COMENTARIO);	
		return campos[0];
	}

	//#########################################################################################
	/**
	 * dada una linea y un separador (token) devuelve el valor siguiente al separador
	 * 
	 * @param linea
	 * @param token
	 * @return
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
		
		if(linea.trim().startsWith(DEFINICION_VARIABLE) && linea.indexOf(ESTILO)!=-1){
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
		linea = linea.trim();
		
		String[] tokens = linea.split(" ");
		
		if(tokens.length>0){
			// si empieza con "/"
			if(tokens[0].equals(ACORDE_REPETIDO)){
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
}
