package nucleo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;

import archivos.Archivos;
import archivos.Estilos;
import canciones.Acorde;
import canciones.Cancion;
import canciones.CancionAprendida;

import net.java.ao.EntityManager;
import estructura.ListaValores;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import estructura.Valores;
import excepciones.ArchivosException;
import excepciones.CancionException;
import excepciones.EstilosException;
import excepciones.ValoresException;
import utiles.*;

public class Controlador {
	
	private EntityManager manager;
	private ListaValores miListaDeTonicas;
	private ListaValores miListaDeTempos;
	private ListaValores miListaDeDuraciones;
	private ListaValores miListaDeEstilosPrincipales;
	private MatrizEstilos miMatrizEstilos;
	private Map<String, MatrizAcordes> MatrizEvolutiva;
	private static final boolean DEBUG = true;
	
	//#########################################################################################
	/**
	 * constructor
	 */
	//#########################################################################################
	public Controlador(){
		
		this.setMatrizEvolutiva(new HashMap<String, MatrizAcordes>());
		this.setMiMatrizEstilos(new MatrizEstilos());
		this.setMiListaDeTonicas(new ListaValores());
		this.setMiListaDeTempos(new ListaValores());
		this.setMiListaDeDuraciones(new ListaValores());
		this.setMiListaDeEstilosPrincipales(new ListaValores());
		
	}
	
	//#########################################################################################
	/**
	 * 
	 */
	//#########################################################################################
	public void aprender() {
		
		CancionAprendida cancionAprendida = null;
		
		try {
			JFileChooser chooser= new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int resultado = chooser.showOpenDialog(null);

			String tipo=chooser.getTypeDescription(chooser.getSelectedFile());
			if (resultado == JFileChooser.CANCEL_OPTION)
				return;

			//si se carga un directorio
			if (tipo.compareTo(Constantes.DIRECTORIO_LINUX)==0){	
				String path = chooser.getSelectedFile()+"/";
				String files;
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				System.out.println("Directorio a cargar :" + path + " Cant: " + listOfFiles.length);
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						files = listOfFiles[i].getName();
						System.out.println(i + " - Antes de cargar: " + path+files);
						try{
							
							cancionAprendida = Aprendizaje.aprenderCancion(path+files);
							guardarCancionEnMemoria(cancionAprendida);
							
						}catch(EstilosException ee){
							System.err.println(ee.getMessage());
						}catch(ValoresException ve){
							System.err.println(ve.getMessage());
						} catch (ArchivosException e) {
							System.err.println(e.getMessage());
						} catch (CancionException e) {
							System.err.println(e.getMessage());
						}
					}
				}
			}
			//si es un archivo
			if(tipo.compareTo(Constantes.ARCHIVO_LINUX)==0){
					try {		
						
						cancionAprendida = Aprendizaje.aprenderCancion(chooser.getSelectedFile().toString());
						guardarCancionEnMemoria(cancionAprendida);

					}catch(EstilosException ee){
						System.err.println(ee.getMessage());
					}catch(ValoresException ve){
						System.err.println(ve.getMessage());
					} catch (ArchivosException e) {
						System.err.println(e.getMessage());
					} catch (CancionException e) {
						System.err.println(e.getMessage());
					}
			}
			// calcular acumulados
			this.calcularAcumuladoDeMap(this.getMatrizEvolutiva());
			this.getMiMatrizEstilos().calcularAcumulados();
			if (DEBUG)
				this.mostrarDatos();
			 	
		}catch(NullPointerException e1){
			e1.printStackTrace();
		}	
	}
	//#########################################################################################
	/**
	 * Guarda todos los datos de la cancion en las estructuras en memoria: 
	 * 				ListaDeTonicas , 
	 * 				ListaDeTempos, 
	 * 				ListaDeDuraciones, 
	 * 				ListaDeEstilosPrincipales, 
	 * 				MatrizEstilos,
	 * 				MatrizAcorde.
	 * 
	 * @param cancion
	 * @throws EstilosException 
	 * @throws ValoresException 
	 * 
	 *///#######################################################################################
	private void guardarCancionEnMemoria(CancionAprendida cancion) throws EstilosException, ValoresException {

		boolean modificado = true;//para saber si modifico un valor leido de la base de datos
		
		// arreglar xq le mandamos los numeros en las lineas y los tomara como acordes.
		// probar utiles.calculaCantAcordesPorCompas("1 / Am")
		Estilos.guardarEstilosEnMatriz(cancion.getCancionSinRepeats(), miMatrizEstilos,modificado);
		this.getMiListaDeEstilosPrincipales().agregarValor(cancion.getEstiloPrincipal(),modificado);
		
		this.getMiListaDeTonicas().agregarValor(cancion.getTonica().getNombre(), cancion.getEstiloPrincipal(),modificado);	
		this.getMiListaDeTempos().agregarValor(cancion.getTempo(), cancion.getEstiloPrincipal(),modificado);
		this.getMiListaDeDuraciones().agregarValor(String.valueOf(cancion.getDuracion()), cancion.getEstiloPrincipal(),modificado);
		
		this.cargarAcordesDeCancion(cancion.getListaAcordes(), cancion.getEstiloPrincipal());
		
		return;
	}
	
	//#########################################################################################
	/**
	 * Recibe por parametros una lista llamada cancion que contiene todas los Acordes
	 * que componen una cancion, recorre esta lista cargando en la matriz los Acordes
	 * principales y luego las ocurrencias de estas con las secundarias
	 * 
	 * @param cancion
	 * @param miMatrizAcordes
	 **/
	//#########################################################################################
	private void cargarAcordesDeCancion(ArrayList<String> cancion, String estiloPrincipal) {
		boolean modificado = true;
		int pos = 0;
		String principal;
		String secundaria;

		if (cancion.size() == 0)
			return;

		MatrizAcordes miMatrizAcordes = this.obtenerMatrizEnMap(estiloPrincipal);
		
		while(pos < cancion.size()-1){

			principal = cancion.get(pos);
			secundaria = cancion.get(pos+1);
			// cargo la matriz en memoria
			if (!miMatrizAcordes.ExisteAcordePpal(principal)) {
				miMatrizAcordes.agregarAcordePrincipal(principal,modificado);
			} 
			pos++;			
			// CARGAR EN LA MATRIZ DE ESE ESTILO PRINCIPAL
			miMatrizAcordes.agregaOcurrenciaAcordeSecundario(principal, secundaria,modificado);	
		}
	}
	//#########################################################################################
	/**
	 * buscarMatrizEnMap
	 * Busca la matriz correspondiente a un estilo principal dentro del map de matrices evolutivas , si no existe la crea!.
	 **/
	//#########################################################################################
	private MatrizAcordes obtenerMatrizEnMap(String estilo) {

		MatrizAcordes miMatrizAcordes = this.getMatrizEvolutiva().get(estilo);

		if (miMatrizAcordes == null) {
			miMatrizAcordes = new MatrizAcordes();
			this.getMatrizEvolutiva().put(estilo, miMatrizAcordes);
		}
		return miMatrizAcordes;
	}
	
	//#########################################################################################
	/**
	 * 
	 * calcula Acumulado De cada matriz de acordes 
	 * 
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
	 * MostrarDatos
	 **/
	//#########################################################################################
	public void mostrarDatos() {

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
	 * 
	 * @param tonica
	 * @param estilo
	 * @throws ArchivosException 
	 * @throws ValoresException 
	 * @throws EstilosException 
	 */
	//#########################################################################################
	public void componer(String tonica, String estilo) {
		
		Composicion miCompositor = new Composicion();
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

		//Obtengo la matriz de acordes correspondiente a el estilo principal
		MatrizAcordes miMatrizAcordes = this.obtenerMatrizEnMap(estilo);

		try {
			Cancion nuevaCancion = miCompositor.componerCancion(miMatrizAcordes, this.getMiMatrizEstilos(), tonica, estilo, Integer.parseInt(duracion), tempo);
			// genero el archivo .mma que contiene a la nueva cancion 
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
			
		}  catch (CancionException e) {
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			return;
		} catch (EstilosException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ArchivosException e) {
			System.err.println(e.getMessage());
			return;
		}		
	}
	
	//#########################################################################################
	/**
	 * 
	 * @param tonica
	 * @param estilo
	 * @param tempo
	 * @param duracion
	 * @throws ArchivosException 
	 * @throws ValoresException 
	 * @throws EstilosException 
	 */
	//#########################################################################################
	public void componer(String tonica, String estilo, String tempo, String duracion) {
		
		Composicion miCompositor = new Composicion();
		//Obtengo la matriz de acordes correspondiente a el estilo principal
		MatrizAcordes miMatrizAcordes = this.obtenerMatrizEnMap(estilo);

		try {
			Cancion nuevaCancion = miCompositor.componerCancion(miMatrizAcordes, this.getMiMatrizEstilos(), tonica, estilo, Integer.parseInt(duracion), tempo);
			// genero el archivo .mma que contiene a la nueva cancion 
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+ Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
			
		}  catch (CancionException e) {
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			return;
		} catch (EstilosException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ArchivosException e) {
			System.err.println(e.getMessage());
			return;
		}
	}
	
	//#########################################################################################
	/**
	 * 
	 * @param tonica
	 * @param estilo
	 * @param tempo
	 * @param duracion
	 * @param estructura
	 */
	//#########################################################################################
	public void componerConEstructruras(String tonica, String estilo, String tempo, String estructura) {
		
		Composicion miCompositor = new Composicion();
		MatrizAcordes miMatrizAcordes = this.obtenerMatrizEnMap(estilo);
		Acorde acorde = new Acorde(tonica);
		Cancion nuevaCancion = new Cancion(estilo+"_"+tonica,tempo,acorde,estilo);
		
		int cantCompasesIntro; 
		int cantCompasesEstrofaA; 
		int cantCompasesEstrofaB; 
		int cantCompasesEnd;
		
		if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_A)) {
			cantCompasesIntro = 2;
			cantCompasesEstrofaA = 4;
			cantCompasesEstrofaB = 8;
			cantCompasesEnd = 1;
			miCompositor.armarEstructuraA(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
		} else if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_B)) {
			cantCompasesIntro = 2;
			cantCompasesEstrofaA = 4;
			cantCompasesEstrofaB = 8;
			cantCompasesEnd = 1;
			miCompositor.armarEstructuraB(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
		} else if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_C)){
			cantCompasesIntro = 2;
			cantCompasesEstrofaA = 4;
			cantCompasesEstrofaB = 8;
			cantCompasesEnd = 1;
			miCompositor.armarEstructuraC(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
		}

		try {
			miCompositor.cargarAcordesEnEstructura(miMatrizAcordes, nuevaCancion);
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+ Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
			
		}  catch (CancionException e) {
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			return;
		} catch (EstilosException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ArchivosException e) {
			System.err.println(e.getMessage());
			return;
		}
		//System.out.println(nuevaCancion.toString());
		
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
	
	private EntityManager getManager() {
		return manager;
	}
	private void setManager(EntityManager manager) {
		this.manager = manager;
	}
	public ListaValores getMiListaDeTonicas() {
		return miListaDeTonicas;
	}
	public void setMiListaDeTonicas(ListaValores miListaDeTonicas) {
		this.miListaDeTonicas = miListaDeTonicas;
	}
	public ListaValores getMiListaDeTempos() {
		return miListaDeTempos;
	}
	private void setMiListaDeTempos(ListaValores miListaDeTempos) {
		this.miListaDeTempos = miListaDeTempos;
	}
	public ListaValores getMiListaDeDuraciones() {
		return miListaDeDuraciones;
	}
	public void setMiListaDeDuraciones(ListaValores miListaDeDuraciones) {
		this.miListaDeDuraciones = miListaDeDuraciones;
	}
	public ListaValores getMiListaDeEstilosPrincipales() {
		return miListaDeEstilosPrincipales;
	}
	public void setMiListaDeEstilosPrincipales(
			ListaValores miListaDeEstilosPrincipales) {
		this.miListaDeEstilosPrincipales = miListaDeEstilosPrincipales;
	}
	public MatrizEstilos getMiMatrizEstilos() {
		return miMatrizEstilos;
	}
	private void setMiMatrizEstilos(MatrizEstilos miMatrizEstilos) {
		this.miMatrizEstilos = miMatrizEstilos;
	}
	public Map<String, MatrizAcordes> getMatrizEvolutiva() {
		return MatrizEvolutiva;
	}
	private void setMatrizEvolutiva(Map<String, MatrizAcordes> matrizEvolutiva) {
		MatrizEvolutiva = matrizEvolutiva;
	}
}
