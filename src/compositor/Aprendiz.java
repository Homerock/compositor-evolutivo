package compositor;

import estructura.MatrizAcordes;
import estructura.AcordesFila;
import estructura.ValorAcordes;
import grafica.Pantalla;
import net.java.ao.EntityManager;
import orm.*;

import java.sql.SQLException;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import archivos.Archivos;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/

public class Aprendiz {
	
	final static String DIRECTORIO="Directorio";
	final static String ARCHIVO = "Archivo genérico";
	MatrizAcordes miMatrizAcordes= new MatrizAcordes();
	int contConsultas;
	private Pantalla pantalla;
	private EntityManager manager;
	
	/**---------------------------------------------------------------------------
	  * Constructor
	  *---------------------------------------------------------------------------*/
	
	public Aprendiz(){	
		
	}
	
	
	/**---------------------------------------------------------------------------
	  * Recibe por parametros una lista llamada cancion que contiene todas las Acordes
	  * que componen la cancion, recorre esta lista cargando en la matriz las Acordes
	  * principales y luego las ocurrencias de estas con las secundarias
	  * @param cancion
	  * @param miMatrizAcordes
	  *---------------------------------------------------------------------------*/
	public void cargarCancion(ArrayList<String> cancion, MatrizAcordes miMatrizAcordes) {
		
		int pos = 0;
		String principal;
		String secundaria;
		
		if (cancion.size() == 0)
			return;
		
		while(pos < cancion.size()-1){
			
			principal = cancion.get(pos);
			secundaria = cancion.get(pos+1);
			// cargo la matriz en memoria
			if (!miMatrizAcordes.ExisteAcordePpal(principal)) {
				miMatrizAcordes.agregarAcordePrincipal(principal);
			} 
			pos++;			
			// en memoria!
			miMatrizAcordes.agregaOcurrenciaAcordeSecundario(principal, secundaria);	
		}
	}
	
	
	/**---------------------------------------------------------------------------
	 * actualiza la base de datos 
	 * se actualiza con los datos en memoria, si no existen los agrega
	 * Acordes y ocurrencias
	  * @param miMatrizAcordes
	  *---------------------------------------------------------------------------*/
	public void actualizarBD( MatrizAcordes miMatrizAcordes) {
		
		String nombreAPpal, nombreASec;
		int apariciones;
		Map<String, AcordesFila> mapAcordesMatriz = miMatrizAcordes.getMisAcordes();//toda la matriz de Acordes
		Map<String, ValorAcordes> mapAcordes;//para cada Acorde
		AcordesFila mapAcordePpal;
		Iterator it = mapAcordesMatriz.entrySet().iterator();
		Iterator it2;
		long t1, t0 = System.currentTimeMillis();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapAcordePpal= (AcordesFila) e.getValue();
			nombreAPpal = (String) e.getKey();
			escribir(" ---- Acorde ppal :"+ nombreAPpal +" ---- Total :" + mapAcordePpal.getContador());
			
			try {
				if (AcordesDTO.existe(this.manager, nombreAPpal)) {
					AcordesDTO.Actualizar(this.manager, nombreAPpal, mapAcordePpal.getContador());
				} else {
					AcordesDTO.Insertar(this.manager, nombreAPpal, mapAcordePpal.getContador());
				}
			
				//ahora vamos a cargar las ocurrencias 
				mapAcordes = mapAcordePpal.getMapAcordes();
				it2= mapAcordes.entrySet().iterator();
		
				while (it2.hasNext()) {
					Map.Entry e2 = (Map.Entry)it2.next();
					nombreASec = (String) e2.getKey();
					escribir(nombreASec + " " + e2.getValue().toString());//metodo para el log de la interfaz
					
					if (!AcordesDTO.existe(this.manager, nombreASec)) {
						AcordesDTO.Insertar(this.manager, nombreASec, 0);
					}
					ValorAcordes vn = (ValorAcordes) e2.getValue();
					actualizarBDOcurrencias(nombreAPpal, nombreASec, vn.getValor());
				}
			} catch (SQLException e1) {
				System.out.println("Error en actualizar base");
				e1.printStackTrace();
			}
			t1=System.currentTimeMillis();
			escribir("tiempo : "+ (t1-t0)/1000 + " segundos ");
		}	
		
	}
	
	/**---------------------------------------------------------------------------
	  * @param AcordePpal
	  * @param AcordeSec
	  *---------------------------------------------------------------------------*/
	private void actualizarBDOcurrencias(String nombreAPpal, String nombreASec, int cantidad) {
		
		int cant = 0;
		int acum = 0;
		//si existe la ocurrencia
		
		Acordes acordePpal;
		try {
			acordePpal = AcordesDTO.buscar(this.manager, nombreAPpal);
			
			Acordes acordeSec = AcordesDTO.buscar(this.manager, nombreASec);
			
			if (OcurrenciasAcordesDTO.existe(this.manager, acordePpal, acordeSec)) {
				OcurrenciasAcordesDTO.Actualizar(this.manager, acordePpal, acordeSec, cantidad);
		
			} else {
				OcurrenciasAcordesDTO.Insertar(this.manager, acordePpal, acordeSec, cantidad);
			}	
		
		} catch (SQLException e) {
			System.out.println("Error en actualizar ocurrencias");
			e.printStackTrace();
		}
	}
	
	/**---------------------------------------------------------------------------
	 * levantarBase
	 * 
	 *
	 * @param manager 
	 * 
	 * ---------------------------------------------------------------------------
	 * */
	public void levantarBase(EntityManager m) {

		this.manager = m;
		Acordes[] listaAcordes;
		try {
			listaAcordes = AcordesDTO.seleccionarTodos(this.manager);
		
			for (Acordes a : listaAcordes){
				this.miMatrizAcordes.agregarAcordePrincipal(a.getNombre(),a.getCantApariciones());
			}
		
			
			OcurrenciasAcordes[] listaOcurrencias =  OcurrenciasAcordesDTO.seleccionarTodos(this.manager);
			
			for (OcurrenciasAcordes oa : listaOcurrencias){
				this.miMatrizAcordes.agregaOcurrenciaAcordeSecundario(oa.getAcordePrincipal().getNombre(), oa.getAcordeSecundario().getNombre(), oa.getCantidad());
			}
		} catch (SQLException e) {
			System.out.println("Error en levantar base");
			e.printStackTrace();
		}
		
		this.miMatrizAcordes.calcularAcumulados();
		this.miMatrizAcordes.listarAcordes();	
	}
	
	/**---------------------------------------------------------------------------
	  * Iniciar
	  *---------------------------------------------------------------------------*/
	public void iniciar() {
		
		ArrayList<String> cancion=new ArrayList<String>();
		ArrayList<String> cancionEstilos=new ArrayList<String>();
		
		Aprendiz Aprendiz=new Aprendiz();
		Archivos miArchivo = new Archivos();
			
		try {
			JFileChooser chooser= new JFileChooser();
		    chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
		    int resultado = chooser.showOpenDialog(null);
		    
		    String tipo=chooser.getTypeDescription(chooser.getSelectedFile());
		    if (resultado == JFileChooser.CANCEL_OPTION)
		    	return;
		    
		    //si se carga un directorio
		    if (tipo.compareTo(DIRECTORIO)==0){
		    	
		    	  try{	
		  		    String path = chooser.getSelectedFile()+"/";
		  			String files;
		  			File folder = new File(path);
		  			escribir("Directorio a cargar :"+path);
		  		    
		  			File[] listOfFiles = folder.listFiles();
		  			long t1 = System.currentTimeMillis();
		  			for (int i = 0; i < listOfFiles.length; i++) {
		  				if (listOfFiles[i].isFile()) {
		  					files = listOfFiles[i].getName();
		  					escribir(path+files);
		  					
		  					if (miArchivo.leerArchivo(path+files)){
		  						cancion = miArchivo.getCancionAnalizada();
		  						escribir("LISTA DE ACORDES: "+cancion.toString());
			  					Aprendiz.cargarCancion(cancion, this.miMatrizAcordes);
			  					cancion.clear();	
		  					}
		  				}
		  			}
		  			long t2 = System.currentTimeMillis();
		  			escribir("tiempo en mls " + (t2 - t1));
		  	    }catch(NullPointerException e){
		  	    	escribir("Error en lectura de directorio");
		  	    }
		    }
		    //si es un archivo
		    if(tipo.compareTo(ARCHIVO)==0){
		    	escribir("Archivo a leer :"+chooser.getSelectedFile());
		    	
		    	if (miArchivo.leerArchivo(chooser.getSelectedFile().toString())){
		    		cancion = miArchivo.getCancionAnalizada();
		    		cancionEstilos = miArchivo.getCancionEstilos();
		    		escribir("LISTA DE ACORDES: "+cancion.toString());
					long t1 = System.currentTimeMillis();
					Aprendiz.cargarCancion(cancion, this.miMatrizAcordes);
					cancion.clear();
					long t2 = System.currentTimeMillis();
					escribir("tiempo en mls " + (t2 - t1));
		    	}
		    }
		       
		    this.miMatrizAcordes.calcularAcumulados();
		   // this.miMatrizAcordes.listarAcordes();
		       
		}catch(NullPointerException e1){
			escribir("Error: Aprendiz.iniciar()");
		}	
	}
		
	/**---------------------------------------------------------------------------
	 * componer
	 *---------------------------------------------------------------------------*/
	public void componer(String tonica) {
		
		Compositor miCompositor = new Compositor();
		
		miCompositor.Componer(this.miMatrizAcordes, tonica);
	}
	
	/**---------------------------------------------------------------------------
	 * limpiar
	 *---------------------------------------------------------------------------*/
	public void limpiar() {
		
		this.miMatrizAcordes.vaciarMatriz();
		OcurrenciasAcordesDTO.EliminarTabla();
		AcordesDTO.EliminarTabla();
	}
	
	/**---------------------------------------------------------------------------
	 * guardar
	 *---------------------------------------------------------------------------*/
	public void guardar() {
		
		//this.guardarCancion(this.miMatrizAcordes);
		this.actualizarBD(this.miMatrizAcordes);
		
	}
	
	/**---------------------------------------------------------------------------
	 * salir
	 *---------------------------------------------------------------------------*/
	public void salir() {
		
		System.exit(0);
		
	}
	
	/**---------------------------------------------------------------------------
	 * escribir
	 *---------------------------------------------------------------------------*/
	public void escribir(String mensaje) {
		
		this.pantalla.actualizarLog(mensaje + "\n");
		
	}
	
	/**---------------------------------------------------------------------------
	 * setInterfaz
	 *---------------------------------------------------------------------------*/
	public void setInterfaz(Pantalla pantalla) {
		this.pantalla = pantalla;
	}
}
