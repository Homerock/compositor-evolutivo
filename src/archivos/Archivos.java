package archivos;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import estructura.EstilosFila;
import estructura.ValorAcordes;

/**---------------------------------------------------------------------------
  * @author Yamil Gomez - Sebastian Pazos
  *
  *---------------------------------------------------------------------------*/
public class Archivos {
	
	private ArrayList<String> cancionAnalizada=new ArrayList<String>();
	private ArrayList<String> cancionAnalizadaConEstilo=new ArrayList<String>();
	
	private String estiloPpal;
	private String tonica;
	private String tempo;
		
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public Archivos(){
		
	}
	
	/**---------------------------------------------------------------------------
	  * lee un archivo 
	  * @param miMatrizNotas
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean leerArchivo(String NombreArch){
		
		File fichero = new File(NombreArch);
		this.vaciarCancionAnalizada();
		this.vaciaCancionAnalizadaConEstilos();
		Filtro fil = new Filtro(".mma");
		String lineaEstilos;
		
		try {
			if (!fil.accept(fichero, fichero.getName())) {
				System.out.println("El formato del fichero "+ fichero.getName()+ " es incorrecto");
				return false;
			}
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			
			while(linea != null){
				//quito los comentarios de la linea
				linea=Utiles.quitarComentarios(linea);
				
				if (!linea.isEmpty()){
					lineaEstilos = linea;
					this.analizarLinea(linea);
					this.analizarLineaEstilos(lineaEstilos);
				}
				linea = reader.readLine();
			}

			//quitamos las repeticinones de la lista de acordes 
			
			ArrayList<String> cancionSinRepeats =Utiles.quitarRepets(this.getCancionAnalizada(), true);
			this.setCancionAnalizada(cancionSinRepeats);
			
			System.out.println("CANCION CON REPEATS " + cancionAnalizadaConEstilo.toString());
			ArrayList<String> cancionConEstilosSinRepeats =Utiles.quitarRepets(this.getCancionAnalizadaConEstilo(), false);
			this.setCancionAnalizadaConEstilo(cancionConEstilosSinRepeats);
			System.out.println("CANCION SIN REPEATS " + cancionAnalizadaConEstilo.toString());
			
			//this.limpiarCancionEstilos();
			//return this.getCancionAnalizada();
			
			reader.close();
			this.tonica = this.getCancionAnalizada().get(0);
			
			return true;//todo ok loco
			
			
		} catch (Exception e) {
			System.out.println("Error en lectura del archivo " + NombreArch);
			e.printStackTrace();
			return false;//todo mal
		}
	
	}
	
	/**
	 * @param nombre
	 * @param contenido
	 * @param agregar
	 */
	public void escribirArchivo(String nombre, String contenido, boolean agregar) {
		
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
	
	
	/**---------------------------------------------------------------------------
	  * analiza una linea y guarda :
	  *  - los acordes y "repeat" en el arrayList cancionAnalizada
	  *  - los acordes , "repeats" y estilos en el arraylist cancionAnalizadoConEstilos 
	  * 
	  * @param linea
	  *---------------------------------------------------------------------------*/
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
			}

		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}
	
	
	/**---------------------------------------------------------------------------
	  * analiza una linea y guarda :
	  *  - los acordes y "repeat" en el arrayList cancionAnalizada
	  *  - los acordes , "repeats" y estilos en el arraylist cancionAnalizadoConEstilos 
	  * 
	  * @param linea
	  *---------------------------------------------------------------------------*/
	public void analizarLineaEstilos(String linea){
		
		try{
			StringTokenizer tokens = new StringTokenizer(linea);
			String primerToken =tokens.nextToken();
			String acordesAux="";
			//si es un numero 
			if (Utiles.isNumeric(primerToken)){
				while (tokens.hasMoreTokens()){
					String tok = tokens.nextToken();
					acordesAux = acordesAux + " " + tok;
				}
			this.getCancionAnalizadaConEstilo().add(acordesAux);
			}else {
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(linea.indexOf(Utiles.REPEAT)!=-1){
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
	
	
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void vaciarCancionAnalizada(){
		this.getCancionAnalizada().clear();
	}
	
	public void vaciaCancionAnalizadaConEstilos(){
		this.getCancionAnalizadaConEstilo().clear();
	}
	
	public String getTonica() {
		return this.tonica;
	}
	
	
	public String getEstiloPpal() {
		return this.estiloPpal;
	}

	public void setEstiloPpal(String estiloPpal) {
		this.estiloPpal = estiloPpal;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public ArrayList<String> getCancionAnalizada() {
		return cancionAnalizada;
	}
	
	/**---------------------------------------------------------------------------
	  * @param cancionAnalizada
	  *---------------------------------------------------------------------------*/
	public void setCancionAnalizada(ArrayList<String> cancionAnalizada) {
		this.cancionAnalizada = cancionAnalizada;
	}
	

	public ArrayList<String> getCancionAnalizadaConEstilo() {
		return this.cancionAnalizadaConEstilo;
	}

	public void setCancionAnalizadaConEstilo(ArrayList<String> cancionAnalizadaConEstilos) {
		this.cancionAnalizadaConEstilo = cancionAnalizadaConEstilos;
	}


}
