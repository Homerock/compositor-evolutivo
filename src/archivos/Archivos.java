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
	
	private String[] notasPpales= {"A","B","C","D","E","F","G"};
	
	private ArrayList<String> notasPosibles=new ArrayList(Arrays.asList(notasPpales));
	private ArrayList<String> cancionAnalizada=new ArrayList<String>();
	private ArrayList<String> cancionAnalizadaConEstilos=new ArrayList<String>();
	
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
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			while(linea != null){
				//quito los comentarios de la linea
				linea=Utiles.quitarComentarios(linea);
				
				if (!linea.isEmpty()){
					this.analizarLinea(linea);
				}
				linea = reader.readLine();
			}

			//quitamos las repeticinones de la lista de acordes 
			ArrayList<String> cancionSinRepeats =Utiles.quitarRepets(this.getCancionAnalizada());
			this.setCancionAnalizada(cancionSinRepeats);
			
			
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
					this.getCancionAnalizadaConEstilos().add(tok);//utilizado para los estilos
				}
			}else {
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(linea.indexOf(Utiles.REPEAT)!=-1){
					this.getCancionAnalizada().add(linea);	
					this.getCancionAnalizadaConEstilos().add(linea);//utilizado para los estilos
				}
				if(linea.indexOf(Utiles.ESTILO)!=-1){
					this.getCancionAnalizadaConEstilos().add(linea);//utilizado para los estilos
				}
				
				
			}

		}catch (NoSuchElementException e){
			//util para las lineas vacias y con espacios en blanco
		}
	}
	
	/**---------------------------------------------------------------------------
	  * @param nota
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean esNotaVailda(String nota){
		//dice si una nota esta contenida
		String notaSimple  = nota.substring(0, 1);//obtengo solo la primera letra
				
		if( this.getNotasPosibles().contains(notaSimple)){
			//System.out.println("nota : "+nota+"- notaSimple : "+notaSimple);
			return true;
		}
		return false;
		//falta ver q verifiq todo el resto de la nota	
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void vaciarCancionAnalizada(){
		this.getCancionAnalizada().clear();
	}
	
	public void vaciaCancionAnalizadaConEstilos(){
		this.getCancionAnalizadaConEstilos().clear();
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
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public ArrayList<String> getNotasPosibles() {
		return notasPosibles;
	}
	
	/**---------------------------------------------------------------------------
	  * @param notasPosibles
	  *---------------------------------------------------------------------------*/
	public void setNotasPosibles(ArrayList<String> notasPosibles) {
		this.notasPosibles = notasPosibles;
	}
	

	public ArrayList<String> getCancionAnalizadaConEstilos() {
		return this.cancionAnalizadaConEstilos;
	}

	public void setCancionAnalizadaConEstilos(ArrayList<String> cancionAnalizadaConEstilos) {
		this.cancionAnalizadaConEstilos = cancionAnalizadaConEstilos;
	}


}
