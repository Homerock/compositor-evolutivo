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
	private ArrayList<String> cancionEstilos=new ArrayList<String>();
	
	private String estiloPpal;
	
	

	private final String COMENTARIO = "//";
	private final String CADENAVACIA="";
	
	private final String ESTILO= "Groove";//para definir un estilo debe estar definido asi "Groove miEstilo" 
	

	
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
		this.getCancionAnalizada().clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			while(linea != null){
				//quito los comentarios de la linea
				linea=this.quitarComentarios(linea);
				
				if (!linea.isEmpty()){
					this.analizarLinea(linea);
				}
				linea = reader.readLine();
			}
			//System.out.println( this.getCancionAnalizada().toString());
			this.quitarRepets();
			this.limpiarCancionEstilos();
			//return this.getCancionAnalizada();
			
			reader.close();
			
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
	  * Limpia los estilos de una cancion
	  *---------------------------------------------------------------------------*/
	public void limpiarCancionEstilos() {
		int i=0;
		String e1=null,ePpal;
		Integer cant;
		Map <String,Integer> misEstilos = new HashMap<String, Integer> ();
		this.setEstiloPpal(null);
		
		if(this.getCancionEstilos().size()==0)
			return;
		
		// ACA HAY HAY UN ERRRRRRRORRRRRR
	//	this.setCancionEstilos(Utiles.tokenizarLista(this.getCancionEstilos(), " "));
		
		while(i<this.getCancionEstilos().size()){
			e1=this.getCancionEstilos().get(i);
			
			if (misEstilos.containsKey(e1)){
				cant = new Integer(misEstilos.get(e1));
				cant=cant+1;
			}else{
				cant = new Integer(1);
			}
			misEstilos.put(e1, cant);
			i++;
		}
		
		//buscamos el estilo con mas apariciones , ya que ese sera el principal
		Iterator it = misEstilos.entrySet().iterator();
		Integer cantApariciones;
		Map.Entry e = (Map.Entry)it.next();
		ePpal=(String) e.getKey();
		cantApariciones= (Integer) e.getValue();
		
		while (it.hasNext()) {
			if(cantApariciones< (Integer)e.getValue()){
				ePpal=(String)e.getKey();
				cantApariciones=(Integer)e.getValue();
			}
			e = (Map.Entry)it.next();
		}
		this.setEstiloPpal(ePpal);
		
		
	}
	

	/**---------------------------------------------------------------------------
	  * quita los repeats del arreglo de notas y agrega las notas necesarias
	  *---------------------------------------------------------------------------*/
	public void quitarRepets(){
		ArrayList<Integer> arrayRepeat=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnding=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeatEnd=new ArrayList<Integer>();
		ArrayList<Integer> arrayRepeticiones=new ArrayList<Integer>();
				
		int pos = 0;
		int cantRepeticiones = 0;
		ArrayList<String> listaDeNotas=new ArrayList<String>();
		String valor="";
		
		String Traza="\n";
		
		try {
		while(pos< this.getCancionAnalizada().size()){
			valor =this.getCancionAnalizada().get(pos);
			if (this.esNotaVailda(valor)) {
				listaDeNotas.add(valor);
			}
						
			//si es repeat ending
			if (valor.indexOf("RepeatEnding")!=-1){
				
				if (!arrayRepeatEnding.contains(pos)) {
					arrayRepeatEnding.add(pos);
					arrayRepeticiones.add(this.calcRepeticiones(valor));
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
				if(valor.indexOf("RepeatEnd")!=-1){
					
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
							arrayRepeticiones.add(this.calcRepeticiones(valor));
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
					if (valor.indexOf("Repeat")!=-1){
						
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
			System.out.print(this.getCancionAnalizada().toString());
			System.out.println("error de indice de array \n" );
			System.out.println(Traza);
			e.printStackTrace();
			return;
		}
		//System.out.print(this.getCancionAnalizada().toString());
		//System.out.println(Traza);
		// copio el array temporal en el array cancionAnalizada
		this.cancionAnalizada = listaDeNotas;
	}
	
	/**---------------------------------------------------------------------------
	  * calcula las repeticiones a realizar
	  * @param linea
	  * @return
	  *---------------------------------------------------------------------------*/
	public int calcRepeticiones(String linea){
		
		int cant;
		try {
			StringTokenizer tokens = new StringTokenizer(linea);
			String tok =tokens.nextToken();
			tok =tokens.nextToken();
			
			cant = Integer.parseInt(tok);
			//System.out.println("repeticiones :"+cant);
			return cant;
		}
		catch (NoSuchElementException e) {
			return 1;
		}
		catch (NumberFormatException e){
			return 1;
		}
	}
	
	/**---------------------------------------------------------------------------
	  * verifica si es un numero
	  * @param cadena
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean isNumeric(String cadena){
		
		try{
			Integer.parseInt(cadena);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	/**---------------------------------------------------------------------------
	  * quita los comentarios de una linea
	  * @param linea
	  * @return cadena sin comentarios
	  *---------------------------------------------------------------------------*/
	public String quitarComentarios(String linea){
		
		if(linea.startsWith(this.COMENTARIO)){
			return this.CADENAVACIA;
		}
		String[] campos = linea.split(this.COMENTARIO);	
		return campos[0];
	}
	
	/**---------------------------------------------------------------------------
	  * analiza una linea y guarda :
	  *  - los acordes y "repeat" en el arrayList cancionAnalizada
	  *  - los estilos en el arraylist cancionEstilo 
	  * 
	  * @param linea
	  *---------------------------------------------------------------------------*/
	public void analizarLinea(String linea){
		
		try{
			StringTokenizer tokens = new StringTokenizer(linea);
			String primerToken =tokens.nextToken();
			if (this.isNumeric(primerToken)){
			
				//agregamos los posibles  acordes y "/" en el arraylist
				while(tokens.hasMoreTokens()){
					String tok = tokens.nextToken();
					if (tok.equals(this.COMENTARIO)) {
						break;
					}
					this.getCancionAnalizada().add(tok);
				}
			}else {
				//me fijo si esta la palabra repeat en una cadena, ya que nos intersa, y la agrego en el arraylist
				if(linea.indexOf("Repeat")!=-1){
					this.getCancionAnalizada().add(linea);	
				}
				if(linea.indexOf(ESTILO)!=-1){
					this.getCancionEstilos().add(linea);
				}
				
			}

			/**
			 * 
			 * 
			 * aca carga los estilos en la matriz cancionEstilos
			 * ver si es mejor un switch
			 * 
			 * 
			 * 
			 */
			
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
	
	public String getEstiloPpal() {
		return estiloPpal;
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
	

	public ArrayList<String> getCancionEstilos() {
		return cancionEstilos;
	}

	public void setCancionEstilos(ArrayList<String> cancionEstilos) {
		this.cancionEstilos = cancionEstilos;
	}


}
