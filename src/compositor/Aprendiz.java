package compositor;

import estructura.AcordesFila;
import estructura.ListaValores;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import estructura.ValorAcordes;
import estructura.Valores;
import excepciones.ArchivosException;
import excepciones.CancionException;
import excepciones.EstilosException;
import excepciones.ValoresException;
import grafica.Pantalla;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import net.java.ao.EntityManager;
import orm.Acordes;
import orm.AcordesDTO;
import orm.EstilosDTO;
import orm.OcurrenciasAcordes;
import orm.OcurrenciasAcordesDTO;
import orm.Tonicas;
import orm.TonicasDTO;
import archivos.Archivos;
import archivos.Estilos;
import canciones.Cancion;

//#########################################################################################
/**
 * @author Sebastian Pazos , Yamil Gomez
 *
 **/
//#########################################################################################

public class Aprendiz {

	private final static String DIRECTORIO = "Directorio";
	private final static String ARCHIVO = "Archivo genérico";
	private Pantalla pantalla;
	private EntityManager manager;
	private ListaValores miListaDeTonicas;
	private ListaValores miListaDeTempos;
	private ListaValores miListaDeDuraciones;
	private ListaValores miListaDeEstilosPrincipales;
	private MatrizEstilos miMatrizEstilos;
	private Map<String, MatrizAcordes> MatrizEvolutiva;

	//#########################################################################################
	/**
	 * Constructor
	 **/
	//#########################################################################################
	public Aprendiz(){	
		this.setMatrizEvolutiva(new HashMap<String, MatrizAcordes>());
		this.setMiMatrizEstilos(new MatrizEstilos());
		this.setMiListaDeTonicas(new ListaValores());
		this.setMiListaDeTempos(new ListaValores());
		this.setMiListaDeDuraciones(new ListaValores());
		this.setMiListaDeEstilosPrincipales(new ListaValores());
	}

	//#########################################################################################
	/**
	 * Recibe por parametros una lista llamada cancion que contiene todas las Acordes
	 * que componen una cancion, recorre esta lista cargando en la matriz las Acordes
	 * principales y luego las ocurrencias de estas con las secundarias
	 * 
	 * @param cancion
	 * @param miMatrizAcordes
	 **/
	//#########################################################################################
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
			// CARGAR EN LA MATRIZ DE ESE ESTILO PRINCIPAL
			miMatrizAcordes.agregaOcurrenciaAcordeSecundario(principal, secundaria);	
		}
	}

	//#########################################################################################
	/**
	 * actualiza la base de datos 
	 * se actualiza con los datos en memoria, si no existen los agrega
	 * Acordes y ocurrencias
	 * @param miMatrizAcordes
	 **/
	//#########################################################################################
	public void actualizarBD( MatrizAcordes miMatrizAcordes, ListaValores miListaTonicas, String estiloPpal) {

		String nombreAPpal, nombreASec;


		Map<String, AcordesFila> mapAcordesMatriz = miMatrizAcordes.getMisAcordes();//toda la matriz de Acordes
		ArrayList<ValorAcordes> listaOc;

		AcordesFila acordePpal;
		Iterator it = mapAcordesMatriz.entrySet().iterator();
		long t1, t0 = System.currentTimeMillis();


		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			acordePpal = (AcordesFila) e.getValue();
			nombreAPpal = (String) e.getKey();
			escribir(" ---- Acorde ppal :"+ nombreAPpal +" ---- Total :" + acordePpal.getValorAcumuladoFila());

			try {
				if (AcordesDTO.existe(this.manager, nombreAPpal)) {
					AcordesDTO.actualizar(this.manager, nombreAPpal, acordePpal.getValorAcumuladoFila());
				} else {
					AcordesDTO.insertar(this.manager, nombreAPpal, acordePpal.getValorAcumuladoFila());
				}

				//ahora vamos a cargar las ocurrencias 
				listaOc = acordePpal.getListaOcurrencias();

				for(ValorAcordes va : listaOc) {

					nombreASec = va.getAcordeSecundario();
					escribir(nombreASec + " " + va.getValor());//metodo para el log de la interfaz

					if (!AcordesDTO.existe(this.manager, nombreASec)) {
						AcordesDTO.insertar(this.manager, nombreASec, 0);
					}
					actualizarBDOcurrencias(nombreAPpal, nombreASec, va.getValor(), estiloPpal);
				}
			} catch (SQLException e1) {
				System.out.println("Error en actualizar base");
				e1.printStackTrace();
			}
			t1=System.currentTimeMillis();
			escribir("tiempo : "+ (t1-t0)/1000 + " segundos ");
		}	

		/*Iterator itTonicas = mapTonicas.entrySet().iterator();
		while (itTonicas.hasNext()) {
			Map.Entry e = (Map.Entry)itTonicas.next();
			String nombreTonica = (String) e.getKey();

			Acordes acorde;
			try {
				acorde = AcordesDTO.buscar(this.manager, nombreTonica);


				// NO ANDA EL EXISTE!!!!!!!!!!--------------------------------------------------------

				if (TonicasDTO.existe(this.manager, acorde)) {
					TonicasDTO.Actualizar(this.manager, acorde, (Integer) e.getValue());
				} else {
					TonicasDTO.Insertar(this.manager, nombreTonica, (Integer) e.getValue());
				}

				System.out.println("tonica: " + e.getKey() + " cantidad de apariciones: " + e.getValue());

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}*/

	}

	//#########################################################################################
	/**
	 * @param AcordePpal
	 * @param AcordeSec
	 **/
	//#########################################################################################
	private void actualizarBDOcurrencias(String nombreAPpal, String nombreASec, int cantidad, String estiloPpal) {

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
				orm.Estilos est = EstilosDTO.buscar(this.manager, estiloPpal);
				OcurrenciasAcordesDTO.Insertar(this.manager, acordePpal, acordeSec, cantidad, est);
			}	

		} catch (SQLException e) {
			System.out.println("Error en actualizar ocurrencias");
			e.printStackTrace();
		}
	}

	//#########################################################################################
	/**
	 * levantarBase
	 * 
	 *
	 * @param manager 
	 * 
	 **/
	//#########################################################################################
	public void levantarBase(EntityManager m) {

		this.manager = m;

		try {

			Acordes[] listaAcordes = AcordesDTO.seleccionarTodos(this.manager);
			for (Acordes a : listaAcordes){
				//this.miMatrizAcordes.agregarAcordePrincipal(a.getNombre(),a.getCantApariciones());
			}

			OcurrenciasAcordes[] listaOcurrencias =  OcurrenciasAcordesDTO.seleccionarTodos(this.manager);
			for (OcurrenciasAcordes oa : listaOcurrencias){
				//this.miMatrizAcordes.agregaOcurrenciaAcordeSecundario(oa.getAcordePrincipal().getNombre(), oa.getAcordeSecundario().getNombre(), oa.getCantidad());
			}

			Tonicas[] listaTonicas = TonicasDTO.seleccionarTodos(manager);
			for (Tonicas ton : listaTonicas) {
				this.miListaDeTonicas.agregarValor(ton.getAcorde().getNombre(), ton.getCantidad(), ton.getEstilos().getNombre());
			}


		} catch (SQLException e) {
			System.err.println("Error en levantar base :"+Aprendiz.class);
			//e.printStackTrace();
		}

		//	this.miMatrizAcordes.calcularAcumulados();
		//	this.miMatrizAcordes.listarAcordes();	
	}

	//#########################################################################################
	/**
	 * Iniciar
	 **/
	//#########################################################################################
	public void iniciar() {

		try {
			JFileChooser chooser= new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int resultado = chooser.showOpenDialog(null);

			String tipo=chooser.getTypeDescription(chooser.getSelectedFile());
			if (resultado == JFileChooser.CANCEL_OPTION)
				return;

			//si se carga un directorio
			if (tipo.compareTo(DIRECTORIO)==0){	
				String path = chooser.getSelectedFile()+"/";
				String files;
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				escribir("Directorio a cargar :" + path + " Cant: " + listOfFiles.length);
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						files = listOfFiles[i].getName();
						System.out.println(i + " - Antes de cargar: " + path+files);
						this.cargarArchivoEnMatriz(path+files);
					}
				}
			}
			//si es un archivo
			if(tipo.compareTo(ARCHIVO)==0){
				this.cargarArchivoEnMatriz(chooser.getSelectedFile().toString());
			}

			this.calcularAcumuladoDeMap(this.getMatrizEvolutiva());
			this.getMiMatrizEstilos().calcularAcumulados();
			//this.mostrarDatos();

		}catch(NullPointerException e1){
			escribir("Error: Aprendiz.iniciar()");
		}	
	}

	//#########################################################################################
	/**
	 * 
	 * @param nombreCancion
	 */
	//#########################################################################################
	public void cargarArchivoEnMatriz(String nombreCancion) {

		escribir("Archivo a leer :"+ nombreCancion);
		Archivos miArchivo = new Archivos();

		try {
			if (miArchivo.leerArchivo(nombreCancion)){
				this.procesarArchivo(miArchivo);
			}
		} catch (EstilosException ee) {
			System.err.println(ee.getMessage());
		} catch (ArchivosException ae) {
			System.err.println(ae.getMessage());
		} catch (ValoresException ve) {
			System.err.println(ve.getMessage());
		} catch (NullPointerException npe) {
			System.out.println("Error en: " + miArchivo.getNombre());
		}
	}

	//#########################################################################################
	/**
	 * procesarArchivo
	 * Se encarga de analizar una cancion extraida de un archivo
	 * carga las matrices de acordes y de estilos, ademas obtiene tonica, tempo y duracion del tema
	 * y los carga en sus listas correspondientes.
	 * @param miArchivo
	 * @throws EstilosException 
	 * @throws ValoresException 
	 **/
	//#########################################################################################
	private void procesarArchivo(Archivos miArchivo) throws EstilosException, ValoresException {

		ArrayList<String> cancion=new ArrayList<String>();
		ArrayList<String> cancionConEstilos=new ArrayList<String>();

		cancion = miArchivo.getCancionAnalizada();
		cancionConEstilos = miArchivo.getCancionAnalizadaConEstilo();//repeats , acordes  y estilos
		Estilos.guardarEstilosEnMatriz(cancionConEstilos, this.getMiMatrizEstilos());
		miArchivo.calcularEstiloPrincipal(cancionConEstilos);

		// obtengo la matriz de acordes correspondiente al estilo principal
		MatrizAcordes miMatrizAcordes = this.buscarMatrizEnMap(miArchivo.getEstiloPpal());
		this.cargarCancion(cancion, miMatrizAcordes);

		System.out.println(miArchivo.getNombre() + " Tempo: " + miArchivo.getTempo() + " Estilo: " + miArchivo.getEstiloPpal());

		this.getMiListaDeEstilosPrincipales().agregarValor(miArchivo.getEstiloPpal());
		this.getMiListaDeTonicas().agregarValor(miArchivo.getTonica(), miArchivo.getEstiloPpal());
		this.getMiListaDeTempos().agregarValor(miArchivo.getTempo(), miArchivo.getEstiloPpal());
		this.getMiListaDeDuraciones().agregarValor(String.valueOf(miArchivo.getDuracion()), miArchivo.getEstiloPpal());
		escribir("LISTA DE ACORDES: "+cancion.toString());
	}

	//#########################################################################################
	/**
	 * calcularAcumuladoDeMap
	 * @param mapMatriz
	 **/
	//#########################################################################################
	private void calcularAcumuladoDeMap(Map<String, MatrizAcordes> mapMatriz) {

		Map<String, MatrizAcordes> mapMatrizEvolutiva = this.getMatrizEvolutiva();
		MatrizAcordes miMatriz;
		Iterator it = mapMatrizEvolutiva.entrySet().iterator();

		//tengo que iterar para calcular los acumulados de todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miMatriz = (MatrizAcordes) e.getValue();
			miMatriz.calcularAcumulados();
		}
	}

	//#########################################################################################
	/**
	 * buscarMatrizEnMap
	 * Busca la matriz correspondiente a un estilo principal dentro del map de matrices evolutivas
	 **/
	//#########################################################################################
	public MatrizAcordes buscarMatrizEnMap(String estilo) {

		MatrizAcordes miMatrizAcordes = this.getMatrizEvolutiva().get(estilo);

		if (miMatrizAcordes == null) {
			miMatrizAcordes = new MatrizAcordes();
			this.getMatrizEvolutiva().put(estilo, miMatrizAcordes);
		}
		return miMatrizAcordes;
	}

	//#########################################################################################
	/**
	 * MostrarDatos
	 **/
	//#########################################################################################
	private void mostrarDatos() {

		System.out.println("-------------------listado de acordes----------------------");
		Map<String, MatrizAcordes> mapMatrizEvolutiva = this.getMatrizEvolutiva();
		MatrizAcordes miMatriz;
		Iterator it = mapMatrizEvolutiva.entrySet().iterator();
		//tengo que iterar para listar todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			System.out.println("-----listado de acordes de estilo: " + e.getKey() +" ----------------------");
			miMatriz = (MatrizAcordes) e.getValue();
			miMatriz.listarAcordes();
		}
		System.out.println("-------------------listado de estilos-------------------");
		this.miMatrizEstilos.listarEstilos();
		System.out.println("-------------------listado de tonicas----------------------");
		this.miListaDeTonicas.listarValor();
		System.out.println("-------------------listado de tempos-----------------------");
		this.miListaDeTempos.listarValor();
		System.out.println("-------------------listado de duraciones-------------------");
		this.miListaDeDuraciones.listarValor();
	}


	//#########################################################################################
	/**
	 * componer
	 **/
	//#########################################################################################
	public void componer(String tonica, String estilo) {

		Compositor miCompositor = new Compositor();
		String tempo;
		String duracion;

		try {
			tempo = this.getMiListaDeTempos().obtenerMayorValorPorEstilo(estilo);
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
			return;
		}
		try {
			duracion = this.getMiListaDeDuraciones().obtenerMayorValorPorEstilo(estilo);
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
			return;
		}

		System.out.println("Datos para componer: ");
		System.out.println("ESTILO: " + estilo);
		System.out.println("TONICA: " + tonica);
		System.out.println("DURACION: " + duracion);
		System.out.println("TEMPO: " + tempo);

		//Obtengo la matriz de acordes correspondiente a el estilo principal
		MatrizAcordes miMatrizAcordes = this.buscarMatrizEnMap(estilo);

		try {
			Cancion nuevaCancion = miCompositor.componerCancion(miMatrizAcordes, this.getMiMatrizEstilos(), tonica, estilo, Integer.parseInt(duracion), tempo);
			// genero el archivo .mma que contiene a la nueva cancion 
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			this.cargarArchivoEnMatriz(nuevaCancion.getNombre());
			// vuelvo a calcular los acumulados para seguir componiendo
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
			
		}  catch (CancionException e) {
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	//#########################################################################################
	/**
	 * limpiar
	 **/
	//#########################################################################################
	public void limpiar() {

		Map<String, MatrizAcordes> mapMatrizEvolutiva = this.getMatrizEvolutiva();
		MatrizAcordes miMatriz;
		Iterator it = mapMatrizEvolutiva.entrySet().iterator();

		//tengo que iterar para vaciar todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miMatriz = (MatrizAcordes) e.getValue();
			miMatriz.vaciarMatriz();
		}

		this.getMiMatrizEstilos().vaciarMatriz();
		this.getMiListaDeDuraciones().getLista().clear();
		this.getMiListaDeEstilosPrincipales().getLista().clear();
		this.getMiListaDeTempos().getLista().clear();
		this.getMiListaDeTonicas().getLista().clear();

		//OcurrenciasAcordesDTO.EliminarTabla();
		//AcordesDTO.EliminarTabla();
	}

	//#########################################################################################
	/**
	 * guardar
	 **/
	//#########################################################################################
	public void guardar() {

		//this.actualizarBD(this.miMatrizAcordes, this.miListaDeTonicas, this.estiloPpal);
	}

	//#########################################################################################
	/**
	 * salir
	 **/
	//#########################################################################################
	public void salir() {

		System.exit(0);
	}

	//#########################################################################################
	/**
	 * escribir
	 **/
	//#########################################################################################
	public void escribir(String mensaje) {

		this.pantalla.actualizarLog(mensaje + "\n");
	}

	//#########################################################################################
	/**
	 * getComboEstilos
	 **/
	//#########################################################################################
	public ArrayList<String> getComboEstilos() {

		ArrayList<Valores> listaValores = this.getMiListaDeEstilosPrincipales().getLista();
		ArrayList<String> miLista = new ArrayList<String>();

		for (Valores val : listaValores) {
			miLista.add(val.getEstilo());
		}
		return miLista;
	}

	//#########################################################################################
	/**
	 * setInterfaz
	 **/
	//#########################################################################################
	public void setInterfaz(Pantalla pantalla) {
		this.pantalla = pantalla;
	}

	//#########################################################################################
	/**
	 * getMatrizEvolutiva
	 **/
	//#########################################################################################
	public Map<String, MatrizAcordes> getMatrizEvolutiva() {
		return MatrizEvolutiva;
	}

	//#########################################################################################
	/**
	 * setMatrizEvolutiva
	 **/
	//#########################################################################################
	public void setMatrizEvolutiva(Map<String, MatrizAcordes> matrizEvolutiva) {
		MatrizEvolutiva = matrizEvolutiva;
	}

	//#########################################################################################
	/**
	 * getMiListaDeTonicas
	 **/
	//#########################################################################################
	public ListaValores getMiListaDeTonicas() {
		return miListaDeTonicas;
	}

	//#########################################################################################
	/**
	 * setMiListaDeTonicas
	 **/
	//#########################################################################################
	public void setMiListaDeTonicas(ListaValores miListaDeTonicas) {
		this.miListaDeTonicas = miListaDeTonicas;
	}

	//#########################################################################################
	/**
	 * getManager
	 **/
	//#########################################################################################
	public EntityManager getManager() {
		return manager;
	}

	//#########################################################################################
	/**
	 * setManager
	 **/
	//#########################################################################################
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	//#########################################################################################
	/**
	 * setMiMatrizEstilos
	 **/
	//#########################################################################################
	public void setMiMatrizEstilos(MatrizEstilos miMatrizEstilos) {
		this.miMatrizEstilos = miMatrizEstilos;
	}

	//#########################################################################################
	/**
	 * getMiMatrizEstilos
	 **/
	//#########################################################################################
	public MatrizEstilos getMiMatrizEstilos() {
		return miMatrizEstilos;
	}

	//#########################################################################################
	/**
	 * getMiListaDeTempos
	 **/
	//#########################################################################################
	public ListaValores getMiListaDeTempos() {
		return miListaDeTempos;
	}

	//#########################################################################################
	/**
	 * setMiListaDeTempos
	 **/
	//#########################################################################################
	public void setMiListaDeTempos(ListaValores miListaDeTempos) {
		this.miListaDeTempos = miListaDeTempos;
	}

	//#########################################################################################
	/**
	 * getMiListaDeDuraciones
	 **/
	//#########################################################################################
	public ListaValores getMiListaDeDuraciones() {
		return miListaDeDuraciones;
	}

	//#########################################################################################
	/**
	 * setMiListaDeDuraciones
	 **/
	//#########################################################################################
	public void setMiListaDeDuraciones(ListaValores miListaDeDuraciones) {
		this.miListaDeDuraciones = miListaDeDuraciones;
	}

	//#########################################################################################
	/**
	 * getMiListaDeEstilosPrincipales
	 **/
	//#########################################################################################
	public ListaValores getMiListaDeEstilosPrincipales() {
		return miListaDeEstilosPrincipales;
	}

	//#########################################################################################
	/**
	 * setMiListaDeEstilosPrincipales
	 **/
	//#########################################################################################
	public void setMiListaDeEstilosPrincipales(ListaValores miListaDeEstilosPrincipales) {
		this.miListaDeEstilosPrincipales = miListaDeEstilosPrincipales;
	}
}
