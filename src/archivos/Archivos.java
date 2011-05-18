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

import canciones.Acorde;
import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;

import excepciones.ArchivosException;

//#########################################################################################
/**
 * @author Yamil Gomez - Sebastian Pazos
 *
 **/
//#########################################################################################
public class Archivos {

	private ArrayList<String> cancionAnalizada;
	private ArrayList<String> cancionAnalizadaConEstilo;	
	private String nombre;
	private String estiloPpal;
	private String tonica;
	private String tempo;
	private int duracion;


	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public Archivos(){
		this.cancionAnalizada=new ArrayList<String>();
		this.cancionAnalizadaConEstilo=new ArrayList<String>();
		
		
		this.duracion=0;
	}

	//#########################################################################################
	/**
	 * lee un archivo 
	 * @param miMatrizNotas
	 * @return
	  *
	 * @throws ArchivosException : si el archivo no existe o define un nuevo estilo
	 * 
	 **/
	//#########################################################################################
	public boolean leerArchivo(String nombreArch) throws ArchivosException{

		File fichero = new File(nombreArch);
		this.vaciarCancionAnalizada();
		this.vaciaCancionAnalizadaConEstilos();
		Filtro fil = new Filtro(".mma");
		String lineaEstilos;

		try {
			if (!fil.accept(fichero, fichero.getName())) {
				System.out.println("El formato del fichero "+ fichero.getName()+ " es incorrecto");
				return false;
			}
			this.setNombre(nombreArch);
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();

			while(linea != null){
				//quito los comentarios de la linea
				linea=Utiles.quitarComentarios(linea);
				if (!linea.isEmpty()){
					
					if(Utiles.defineUnEstilo(linea)){
						throw new ArchivosException("El Archivo '"+nombreArch+"' contiene definicion de estilos. No contemplado en esta version aun.");
					}
					lineaEstilos = linea;
					//this.contarAcordesEnCompas(linea);
					this.analizarLinea(linea);
					this.analizarLineaEstilos(lineaEstilos);
				}
				linea = reader.readLine();
			}

			//quitamos las repeticinones de la lista de acordes 
			//System.out.println("ACORDES SIN REPEATS " + cancionAnalizada.toString());
			ArrayList<String> cancionSinRepeats =Utiles.quitarRepets(this.getCancionAnalizada(), true);
			this.setCancionAnalizada(cancionSinRepeats);

			this.setDuracion(Utiles.obtenerDuracion(getCancionAnalizadaConEstilo()));
			
			//System.out.println("CANCION SIN REPEATS " + cancionAnalizadaConEstilo.toString());
			ArrayList<String> cancionConEstilosSinRepeats =Utiles.quitarRepets(this.getCancionAnalizadaConEstilo(), false);
			this.setCancionAnalizadaConEstilo(cancionConEstilosSinRepeats);

			try {
				this.setTonica(this.getCancionAnalizada().get(0));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("El formato del archivo es incorrecto - " + nombreArch);
				reader.close();
				return false;
			}

			reader.close();

			return true;//todo ok loco


		} catch (FileNotFoundException e) {
			throw new ArchivosException("El Archivo '"+nombreArch+"' No existe.");
		} catch (IOException e1) {
			throw new ArchivosException("Error en la lectura de archivo :"+nombreArch);
			
		}

	}

	//#########################################################################################
	/**
	 * 
	 * @param nombre
	 */
	//#########################################################################################
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		
		escribirArchivo(miCancion.getNombre()+".mma", "Tempo " + miCancion.getTempo(), false);
		escribirArchivo(miCancion.getNombre()+".mma", "", true);
		
		for (Estrofa est : todasLasEstrofas) {
			
			escribirArchivo(miCancion.getNombre()+".mma", "Groove " + est.getEstilo(), true);
			
			ArrayList<Compas> todosLosCompases = est.getListaDeCompases();
		
			for (Compas com : todosLosCompases) {
				ArrayList<Acorde> todosLosAcordes = com.getAcordes();
				compas = " ";
				for (Acorde ac : todosLosAcordes) {
					compas = compas + " " + ac.getNombre();
				}
				escribirArchivo(miCancion.getNombre()+".mma", linea + compas, true);
				linea++;
			}	
		}
		System.out.println("Nuevo archivo generado: " + miCancion.getNombre()+".mma");
		crearMMA("mma " + miCancion.getNombre(), false);
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

	
	//#########################################################################################
	/**
	 * analiza una linea y guarda :
	 * los acordes y "Repeat" en un arrayList (cancionAnalizada).
	 * Se interpretan como acordes, si la linea empiza con un numero.
	 * 
	 * @param linea
	 */
	//#########################################################################################
	public void analizarLinea(String linea){

		try{
			StringTokenizer tokens = new StringTokenizer(linea);
			String primerToken =tokens.nextToken();

			if (Utiles.isNumeric(primerToken)){

				//agregamos los posibles  acordes y "/" en el arraylist
				while(tokens.hasMoreTokens()){
					String tok = tokens.nextToken();

					if (tok.equals(Utiles.COMENTARIO)) {
						break;
					}
					this.getCancionAnalizada().add(tok);
				}	
			}else {
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(linea.indexOf(Utiles.REPEAT)!=-1){
					this.getCancionAnalizada().add(linea);		
				}
				if(linea.indexOf(Utiles.TEMPO)!=-1){
					this.setTempo(Utiles.obtenerDatos(linea, " "));	
				}
			}

		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}
	//#########################################################################################
	/**
	 * analiza una linea y guarda :
	 *  los acordes , "repeats" y estilos en un arraylist (cancionAnalizadoConEstilos).
	 *   Se interpretan como acordes, si la linea empiza con un numero.
	 *   Si se define un estilo se lanza una ArchivosException
	 * 
	 * @param linea
	 * @throws ArchivosException 
	 */
	//#########################################################################################
	public void analizarLineaEstilos(String linea) throws ArchivosException{

		try{
			StringTokenizer tokens = new StringTokenizer(linea);
			String primerToken =tokens.nextToken();
			String acordesAux="";
			//si es un numero 
			if (Utiles.isNumeric(primerToken)){
				while (tokens.hasMoreTokens()){//algo en el otro
					String tok = tokens.nextToken();
					acordesAux = acordesAux + " " + tok;
				}
				this.getCancionAnalizadaConEstilo().add(acordesAux);
			}else {
				
				if (linea.startsWith(Utiles.NUEVO_ESTILO)){
					throw new ArchivosException("ArchivosException : No contemplamos definiciones de nuevos estilos");
				}
				if(Utiles.cadenaContienePatron(linea, Utiles.COMIENZO_DE_VARIABLE)){
					//ej :
					//Set Pass 1
					//Groove $Pass BossaNova BossaNovaSus BossaNova1Sus

					throw new ArchivosException("ArchivosException : No contemplamos variables en definicion de estilos");
				}
				
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(Utiles.cadenaContienePatron(linea, Utiles.REPEAT)){
					this.getCancionAnalizadaConEstilo().add(linea);
				}
				if(linea.startsWith(Utiles.ESTILO)){
					this.getCancionAnalizadaConEstilo().add(linea);
				}
			
				
			}
		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}

	
	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void vaciarCancionAnalizada(){
		this.getCancionAnalizada().clear();
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void vaciaCancionAnalizadaConEstilos(){
		this.getCancionAnalizadaConEstilo().clear();
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void setTonica(String tonica) {
		this.tonica = tonica;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public String getTonica() {
		return this.tonica;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public String getEstiloPpal() {
		return this.estiloPpal;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void setEstiloPpal(String estiloPpal) {
		this.estiloPpal = estiloPpal;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public String getTempo() {
		return tempo;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public int getDuracion() {
		return duracion;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	//#########################################################################################
	/**
	 * @return
	 **/
	//#########################################################################################
	public ArrayList<String> getCancionAnalizada() {
		return cancionAnalizada;
	}

	//#########################################################################################
	/**
	 * @param cancionAnalizada
	 **/
	//#########################################################################################
	public void setCancionAnalizada(ArrayList<String> cancionAnalizada) {
		this.cancionAnalizada = cancionAnalizada;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public ArrayList<String> getCancionAnalizadaConEstilo() {
		return this.cancionAnalizadaConEstilo;
	}

	//#########################################################################################
	/**
	 * 
	 **/
	//#########################################################################################
	public void setCancionAnalizadaConEstilo(ArrayList<String> cancionAnalizadaConEstilos) {
		this.cancionAnalizadaConEstilo = cancionAnalizadaConEstilos;
	}


}
