package compositor;

import estructura.*;
import grafica.Pantalla;
import orm.*;
import java.sql.SQLException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import archivos.Archivos;
import archivos.Patrones;
import net.java.ao.EntityManager;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/

public class Aprendiz {
	
	private final static String DIRECTORIO="Directorio";
	private final static String ARCHIVO = "Archivo genérico";
	private int contConsultas;
	private Pantalla pantalla;
	private EntityManager manager;
	private MatrizAcordes miMatrizAcordes;
	private ListaTonicas miListaDeTonicas;
	private MatrizEstilos miMatrizEstilos;
	
	
	/**---------------------------------------------------------------------------
	  * Constructor
	  *---------------------------------------------------------------------------*/
	
	public Aprendiz(){	
		this.setMiMatrizAcordes(new MatrizAcordes());
		this.setMiListaDeTonicas(new ListaTonicas());
		this.setMiMatrizEstilos(new MatrizEstilos());
		
	}
	
	
	/**---------------------------------------------------------------------------
	  * Recibe por parametros una lista llamada cancion que contiene todas las Acordes
	  * que componen la cancion, recorre esta lista cargando en la matriz las Acordes
	  * principales y luego las ocurrencias de estas con las secundarias
	  * 
	  * @param cancion
	  * @param miMatrizAcordes
	  *---------------------------------------------------------------------------*/
	public void cargarCancion(ArrayList<String> cancion, MatrizAcordes miMatrizAcordes,String estiloPpal) {
		
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
			
			miMatrizAcordes.agregaOcurrenciaAcordeSecundario(principal, secundaria);	
		}
	}
	
	/**---------------------------------------------------------------------------
	 * actualiza la base de datos 
	 * se actualiza con los datos en memoria, si no existen los agrega
	 * Acordes y ocurrencias
	  * @param miMatrizAcordes
	  *---------------------------------------------------------------------------*/
	public void actualizarBD( MatrizAcordes miMatrizAcordes, ListaTonicas miListaTonicas) {
		
		String nombreAPpal, nombreASec;
		int apariciones;
		
		Map<String, Integer> mapTonicas = miListaTonicas.getMisTonicas();
		
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
		
		Iterator itTonicas = mapTonicas.entrySet().iterator();
		while (itTonicas.hasNext()) {
			Map.Entry e = (Map.Entry)itTonicas.next();
			String nombreTonica = (String) e.getKey();
			
			Acordes acorde;
			try {
				acorde = AcordesDTO.buscar(this.manager, nombreTonica);
			
				
				if (TonicasDTO.existe(this.manager, acorde)) {
				//	TonicasDTO.Actualizar(this.manager, acorde, (Integer) e.getValue());
				} else {
				//	TonicasDTO.Insertar(this.manager, nombreTonica, (Integer) e.getValue());
				}
				
				System.out.println("tonica: " + e.getKey() + " cantidad de apariciones: " + e.getValue());
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		 
		try {
			
			Acordes[] listaAcordes = AcordesDTO.seleccionarTodos(this.manager);
			for (Acordes a : listaAcordes){
				this.miMatrizAcordes.agregarAcordePrincipal(a.getNombre(),a.getCantApariciones());
			}
		
			OcurrenciasAcordes[] listaOcurrencias =  OcurrenciasAcordesDTO.seleccionarTodos(this.manager);
			for (OcurrenciasAcordes oa : listaOcurrencias){
				this.miMatrizAcordes.agregaOcurrenciaAcordeSecundario(oa.getAcordePrincipal().getNombre(), oa.getAcordeSecundario().getNombre(), oa.getCantidad());
			}
			
			Tonicas[] listaTonicas = TonicasDTO.seleccionarTodos(manager);
			for (Tonicas ton : listaTonicas) {
				this.miListaDeTonicas.agregarTonicas(ton.getAcorde().getNombre(), ton.getCantidad());
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
		ArrayList<String> cancionConEstilos=new ArrayList<String>();
		
		Aprendiz yoElAprendiz=new Aprendiz();
		Archivos miArchivo = new Archivos();
		String estiloPpal ="";
			
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
		  						
		  						cancion = miArchivo.getCancionAnalizada();//repeats y acordes
		  						cancionConEstilos = miArchivo.getCancionAnalizadaConEstilos();//repeats , acordes  y estilos
		  						escribir("LISTA DE ACORDES: "+cancion.toString());
		  						//guardo los acordes en la matriz (en memoria)
			  					
			  					Patrones.guardarEstilosEnMatriz(cancionConEstilos, this.getMiMatrizEstilos());
			  					estiloPpal = Patrones.deteminarEstiloPrincipal(cancionConEstilos);
			  					
			  					yoElAprendiz.cargarCancion(cancion, this.miMatrizAcordes,estiloPpal);
			  					this.miListaDeTonicas.agregarTonica(miArchivo.getTonica());
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
		    		
		    		escribir("LISTA DE ACORDES: "+cancion.toString());
		    		
					long t1 = System.currentTimeMillis();
					
					//estilos
					cancionConEstilos = miArchivo.getCancionAnalizadaConEstilos();//repeats , acordes  y estilos
					Patrones.guardarEstilosEnMatriz(cancionConEstilos, this.getMiMatrizEstilos());
  					estiloPpal = Patrones.deteminarEstiloPrincipal(cancionConEstilos);
					//guarda cada cancion en la matriz
					yoElAprendiz.cargarCancion(cancion, this.miMatrizAcordes,estiloPpal);
					
					this.miListaDeTonicas.agregarTonica(miArchivo.getTonica());
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
		this.actualizarBD(this.miMatrizAcordes, this.miListaDeTonicas);
		
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


	public MatrizAcordes getMiMatrizAcordes() {
		return miMatrizAcordes;
	}


	public void setMiMatrizAcordes(MatrizAcordes miMatrizAcordes) {
		this.miMatrizAcordes = miMatrizAcordes;
	}


	public ListaTonicas getMiListaDeTonicas() {
		return miListaDeTonicas;
	}


	public void setMiListaDeTonicas(ListaTonicas miListaDeTonicas) {
		this.miListaDeTonicas = miListaDeTonicas;
	}


	public int getContConsultas() {
		return contConsultas;
	}


	public void setContConsultas(int contConsultas) {
		this.contConsultas = contConsultas;
	}


	public EntityManager getManager() {
		return manager;
	}


	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


	public void setMiMatrizEstilos(MatrizEstilos miMatrizEstilos) {
		this.miMatrizEstilos = miMatrizEstilos;
	}


	public MatrizEstilos getMiMatrizEstilos() {
		return miMatrizEstilos;
	}
	
}
