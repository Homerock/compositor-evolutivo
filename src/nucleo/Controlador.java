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
import canciones.Compas;
import canciones.Estrofa;

import net.java.ao.EntityManager;
import estructura.ListaValores;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import estructura.Valores;
import excepciones.AcordesException;
import excepciones.ArchivosException;
import excepciones.CancionException;
import excepciones.EstilosException;
import excepciones.ORMException;
import excepciones.PersistenciaException;
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
	private Map<String, Cancion> listaCanciones;
	
	private static final boolean DEBUG = true;
	private Persistencia manejadorPersistencia = null;
	
	private Cancion cancionNueva=null;
	
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
		this.setListaCanciones(new HashMap<String, Cancion>());
		
		
		try {
			manejadorPersistencia = new Persistencia();
			datosAMemoria();
			
		} catch (PersistenciaException e) {
			System.err.println("No se pudo conectar con la base de datos " + e.getMessage());
		} catch (ORMException e) {
			System.err.println("No se pudo conectar con la base de datos " + e.getMessage());
		}
		
	}
	
	//#########################################################################################
	/**
	 * 
	 * @param ruta
	 */
	//#########################################################################################
	public void aprenderArchivo(String ruta) {
		
		CancionAprendida cancionAprendida = null;
		
		try {		
			
			cancionAprendida = Aprendizaje.aprenderCancion(ruta);
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
		
		// calcular acumulados
		this.calcularAcumuladoDeMap(this.getMatrizEvolutiva());
		this.getMiMatrizEstilos().calcularAcumulados();
		if (DEBUG)
			this.mostrarDatos();
	}
	
	//#########################################################################################
	/**
	 * 
	 * @param ruta
	 */
	//#########################################################################################
	public void aprenderDirectorio(File[] listOfFiles, String path) {
		
		CancionAprendida cancionAprendida = null;
		String files;
		
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
		// calcular acumulados
		this.calcularAcumuladoDeMap(this.getMatrizEvolutiva());
		this.getMiMatrizEstilos().calcularAcumulados();
		if (DEBUG)
			this.mostrarDatos();
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
			
				System.out.println("Tipo nada:"+tipo);
			//si se carga un directorio
			if (tipo.compareTo(Constantes.DIRECTORIO_LINUX)==0 || tipo.compareTo(Constantes.DIRECTORIO_WINDOWS)==0){	
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
							System.err.println("El formato del archivo es incorrecto" + ee.getMessage());
						}catch(ValoresException ve){
							System.err.println("El formato del archivo es incorrecto" + ve.getMessage());
						} catch (ArchivosException e) {
							System.err.println("El formato del archivo es incorrecto" + e.getMessage());
						} catch (CancionException e) {
							System.err.println("El formato del archivo es incorrecto" + e.getMessage());
						} catch(IndexOutOfBoundsException ee){
							System.err.println("El formato del archivo es incorrecto" + ee.getMessage());
						}
					}
				}
			}
			//si es un archivo
			if(tipo.compareTo(Constantes.ARCHIVO_LINUX)==0 || tipo.compareTo(Constantes.ARCHIVO_WINDOWS)==0){
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
	
	/**
	 * @throws PersistenciaException 
	 * @throws ORMException 
	 * 
	 */
	private void datosAMemoria() throws PersistenciaException, ORMException {
	
		this.getManejadorPersistencia().estilosAMemoria(this.getMiMatrizEstilos());
		this.getManejadorPersistencia().ocurrenciasEstilosAMemoria(this.getMiMatrizEstilos());
		this.getManejadorPersistencia().estilosPrincipalesAMemoria(this.getMiListaDeEstilosPrincipales());
		this.getManejadorPersistencia().ocurrenciasAcordesAMemoria(this.getMatrizEvolutiva());
		this.getManejadorPersistencia().duracionAMemoria(getMiListaDeDuraciones());
		this.getManejadorPersistencia().temposAMemoria(getMiListaDeTempos());
		this.getManejadorPersistencia().tonicasAMemoria(getMiListaDeTonicas());
		this.getManejadorPersistencia().cancionesAMemoria(getListaCanciones());
	}
	
	/**
	 * @throws PersistenciaException 
	 * @throws ORMException 
	 * 
	 */
	public void memoriaABaseDeDatos() throws ORMException, PersistenciaException {
		
		if (this.getManejadorPersistencia() != null) {
			this.getManejadorPersistencia().matrizEstilosABaseDeDatos(getMiMatrizEstilos());
			this.getManejadorPersistencia().listaDeEstilosPrincipalesABaseDeDatos(getMiListaDeEstilosPrincipales());
			this.getManejadorPersistencia().matrizAcordesABaseDeDatos(getMatrizEvolutiva());
			this.getManejadorPersistencia().listaTemposABaseDeDatos(getMiListaDeTempos());
			this.getManejadorPersistencia().listaDeDuracionesABaseDeDatos(getMiListaDeDuraciones());
			this.getManejadorPersistencia().listaDeTonicasABaseDeDatos(getMiListaDeTonicas());
		} else {
			throw new PersistenciaException("Error en la conexion a base de datos. Los datos no se pudieron guardar"); 
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

		boolean modificadoNuevo = true;//para saber si modifico un valor leido de la base de datos
		boolean modificadoActualizar = false;
		
		// arreglar xq le mandamos los numeros en las lineas y los tomara como acordes.
		// probar utiles.calculaCantAcordesPorCompas("1 / Am")
		Estilos.guardarEstilosEnMatriz(cancion.getCancionSinRepeats(), miMatrizEstilos,modificadoNuevo);
		this.getMiListaDeEstilosPrincipales().agregarValor(cancion.getEstiloPrincipal(),modificadoNuevo,modificadoActualizar);
		
		this.getMiListaDeTonicas().agregarValor(cancion.getTonica().getNombre(), cancion.getEstiloPrincipal(),modificadoNuevo);	
		this.getMiListaDeTempos().agregarValor(cancion.getTempo(), cancion.getEstiloPrincipal(),modificadoNuevo);
		this.getMiListaDeDuraciones().agregarValor(String.valueOf(cancion.getDuracion()), cancion.getEstiloPrincipal(),modificadoNuevo);
		
		this.cargarAcordesDeCancion(cancion.getTonica().getNombre(),cancion.getListaAcordes(), cancion.getEstiloPrincipal());
		
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
	private void cargarAcordesDeCancion(String tonica , ArrayList<String> cancion, String estiloPrincipal) {
		boolean modificado = true;
		int pos = 0;
		String principal;
		String secundaria = null;

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
		
		// cerramos el circulo de la musica, para solucionar bags.
		principal = secundaria;
		if(principal !=null && !principal.equals(tonica)){
			if (!miMatrizAcordes.ExisteAcordePpal(principal)) {
				miMatrizAcordes.agregarAcordePrincipal(principal,modificado);
			} 
			miMatrizAcordes.agregaOcurrenciaAcordeSecundario(principal, tonica,modificado);
			
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
	
	public Cancion buscarCancionSeleccionada(String nombre) {
		
		Cancion cancion = listaCanciones.get(nombre);
		
		try {
			Archivos.generarArchivo(cancion);
		} catch (NullPointerException e) {
			return null;
		}
		
		return cancion;
		
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
			System.out.println("=== ESTILO : " + e.getKey() );
			miMatriz = (MatrizAcordes) e.getValue();
			System.out.println(miMatriz.toString());
			//miMatriz.listarAcordes();
		}
		System.out.println("-------------------listado de estilos-------------------");
		System.out.println(this.miMatrizEstilos.toString());
		System.out.println("-------------------listado de tonicas----------------------");
		System.out.println(this.miListaDeTonicas.toString());
		System.out.println("-------------------listado de tempos-----------------------");
		System.out.println(this.miListaDeTempos.toString());
		System.out.println("-------------------listado de duraciones-------------------");
		System.out.println(this.miListaDeDuraciones.toString());
		System.out.println("-------------------lista de estilos principales-------------------");
		System.out.println(this.getMiListaDeEstilosPrincipales().toString());
	}

	
	//#########################################################################################
	/**
	 * MostrarDatos
	 **/
	//#########################################################################################
	public void mostrarDatosConModificado() {


		
		System.out.println(this.miListaDeTonicas.toStringConModificado("TONICAS"));
		System.out.println(this.miListaDeTempos.toStringConModificado("TEMPOS"));
		System.out.println(this.miListaDeDuraciones.toStringConModificado("DURACIONES"));
		System.out.println(this.getMiListaDeEstilosPrincipales().toStringConModificado("ESTILOS PRINCIPALES"));
		
		System.out.println("-------------------ESTILOS Y OCURRENCIAS-------------------");
		System.out.println(this.miMatrizEstilos.toStringConModificado());
		
		
		System.out.println("-------------------ACORDES Y OCURRENCIAS----------------------");
		Map<String, MatrizAcordes> mapMatrizEvolutiva = this.getMatrizEvolutiva();
		MatrizAcordes miMatriz;
		Iterator it = mapMatrizEvolutiva.entrySet().iterator();
		//tengo que iterar para listar todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			System.out.println("=== ESTILO : " + e.getKey() );
			miMatriz = (MatrizAcordes) e.getValue();
			System.out.println(miMatriz.toStringConModificado());
			//miMatriz.listarAcordes();
		}
		
		
	}
	//#########################################################################################
	/**
	 * NO SE USAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa
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
		boolean error=false;

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
		Cancion nuevaCancion = null;
		try {
			nuevaCancion = miCompositor.componerCancion(miMatrizAcordes, this.getMiMatrizEstilos(), tonica, estilo, Integer.parseInt(duracion), tempo);
			// genero el archivo .mma que contiene a la nueva cancion 
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
			
		}  catch (AcordesException e) {
			error = true;
			System.err.println(e.getMessage());
			return;	
		}  catch (CancionException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (EstilosException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (ValoresException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (ArchivosException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
			
		}finally{
			//seteo la cancion
			if (!error){// si no hay error
				this.setCancionNueva(nuevaCancion);
			}else{// si hay error
				this.setCancionNueva(null);
			}
			
			
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
		boolean error = false;
		Cancion nuevaCancion = null;
		
		if (tempo.trim().equals("")) {
			try {
				tempo = this.getMiListaDeTempos().obtenerMayorValorPorEstilo(estilo);
			} catch (ValoresException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		if (duracion.trim().equals("")) {
			try {
				duracion = this.getMiListaDeDuraciones().obtenerMayorValorPorEstilo(estilo);
			} catch (ValoresException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		
		try {
			nuevaCancion = miCompositor.componerCancion(miMatrizAcordes, this.getMiMatrizEstilos(), tonica, estilo, Integer.parseInt(duracion), tempo);
			// genero el archivo .mma que contiene a la nueva cancion 
			Archivos.generarArchivo(nuevaCancion);
			
			
			// cargo en la matriz la nueva cancion que compuse
			//Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+ Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			//this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			//this.getMiMatrizEstilos().calcularAcumulados();
			
			
		}  catch (AcordesException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		}  catch (CancionException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} 
		finally {
			//seteo la cancion
			if (!error){// si no hay error
				this.setCancionNueva(nuevaCancion);
			}else{// si hay error
				this.setCancionNueva(null);
			}
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
		Cancion nuevaCancion = null;
		boolean error = false;
		
		int duracion;
		int cantCompasesIntro; 
		int cantCompasesEstrofaA; 
		int cantCompasesEstrofaB; 
		int cantCompasesEnd;
		
		if (tempo.trim().equals("")) {
			try {
				tempo = this.getMiListaDeTempos().obtenerMayorValorPorEstilo(estilo);
			} catch (ValoresException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		
		try {
			nuevaCancion = new Cancion(estilo+"_"+tonica,tempo,acorde,estilo);
				
			if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_A)) {
				cantCompasesIntro = 2;
				cantCompasesEstrofaA = 4;
				cantCompasesEstrofaB = 8;
				cantCompasesEnd = 1;
				duracion = cantCompasesIntro+cantCompasesEstrofaA+cantCompasesEstrofaB+cantCompasesEnd;
				nuevaCancion.setDuracion(duracion);
				miCompositor.armarEstructuraA(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
			} else if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_B)) {
				cantCompasesIntro = 2;
				cantCompasesEstrofaA = 4;
				cantCompasesEstrofaB = 8;
				cantCompasesEnd = 1;
				duracion = cantCompasesIntro+cantCompasesEstrofaA+cantCompasesEstrofaB+cantCompasesEnd;
				nuevaCancion.setDuracion(duracion);
				miCompositor.armarEstructuraB(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
			} else if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_C)){
				cantCompasesIntro = 2;
				cantCompasesEstrofaA = 4;
				cantCompasesEstrofaB = 8;
				cantCompasesEnd = 1;
				duracion = cantCompasesIntro+cantCompasesEstrofaA+cantCompasesEstrofaB+cantCompasesEnd;
				nuevaCancion.setDuracion(duracion);
				miCompositor.armarEstructuraC(miMatrizEstilos, nuevaCancion, cantCompasesIntro, cantCompasesEstrofaA, cantCompasesEstrofaB, cantCompasesEnd);
			}

	
			miCompositor.cargarAcordesEnEstructura(miMatrizAcordes, nuevaCancion);
			
			
			// tenemos que modificar una estrofa ya que es
			// A - B - B - A - B - B' -A 
			if (estructura.equalsIgnoreCase(Constantes.ESTRUCTURA_C)){
				ArrayList<Integer> numerosEstrofasAlteradas = nuevaCancion.getNumerosEstrofasAlteradas();
				for (Integer i : numerosEstrofasAlteradas){
					miCompositor.modificarEstrofaDeCancion(miMatrizAcordes, nuevaCancion,i.intValue());
				}				
			}
			
			
			Archivos.generarArchivo(nuevaCancion);
			// cargo en la matriz la nueva cancion que compuse
			//Aprendizaje.aprenderCancion(nuevaCancion.getNombre()+ Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			//this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			//this.getMiMatrizEstilos().calcularAcumulados();
			
		}catch (AcordesException e) {		
			error = true;
			System.err.println(e.getMessage());
			return;
		}  catch (CancionException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			error = true;
			System.err.println(e.getMessage());
			return;
		} 
		finally {
			//seteo la cancion
			if (!error) {// si no hay error
				this.setCancionNueva(nuevaCancion);
			}else {// si hay error
				this.setCancionNueva(null);
			}
		}
	}
	
	/**
	 * 
	 * @param cancion
	 */
	public void modificarCancion(Cancion cancion) {
		
		Acorde AcordeUltimo = cancion.getTonica();
		
		for (Estrofa e1 : cancion.getEstrofas()){
			for(Compas c1 :e1.getListaDeCompases()){
				if(c1.isModificarCompas()){
					try {
						Composicion.modificarAcordesDeCompas(this.getMatrizEvolutiva().get(cancion.getEstiloPrincipal()), AcordeUltimo, c1);
						
						c1.setModificarCompas(false);
						if (DEBUG)
							System.out.println("cambio acordes a compas : "+c1.toString());
						
					} catch (CancionException e) {
						System.err.println(e.getMessage());
					} catch (AcordesException e) {
						System.err.println(e.getMessage());
					}					
				}
				//actualizamos el ultimo acorde
				AcordeUltimo = c1.getUltimoAcorde();
			}
		}
		return ;
	}
	
	/**
	 * 
	 * @param cancionNueva
	 * @param nombreFantasia
	 * @param comentario
	 */
	public void guardarCancion(canciones.Cancion cancionNueva, String nombreFantasia, String comentario) {
		
		cancionNueva.setComentario(comentario);
		cancionNueva.setNombreFantasia(nombreFantasia);
		manejadorPersistencia.cancionNuevaABaseDeDatos(cancionNueva);
		try {
			this.getManejadorPersistencia().cancionesAMemoria(getListaCanciones());
		} catch (PersistenciaException e) {
			System.err.println(e.getMessage()+Controlador.class);
		}
		
		// cargo en la matriz la nueva cancion que compuse
		try {
			Aprendizaje.aprenderCancion(cancionNueva.getNombre()+ Constantes.EXTENSION_ARCHIVO);
			// vuelvo a calcular los acumulados para seguir componiendo
			String estilo = cancionNueva.getEstiloPrincipal();
			this.getMatrizEvolutiva().get(estilo).calcularAcumulados();
			this.getMiMatrizEstilos().calcularAcumulados();
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
		} catch (EstilosException e) {
			System.err.println(e.getMessage());
		} catch (ValoresException e) {
			System.err.println(e.getMessage());
		} catch (ArchivosException e) {
			System.err.println(e.getMessage());
		} catch (CancionException e) {
			System.err.println(e.getMessage());
		}
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
	public Persistencia getManejadorPersistencia() {
		return manejadorPersistencia;
	}
	public void setManejadorPersistencia(Persistencia manejadorPersistencia) {
		this.manejadorPersistencia = manejadorPersistencia;
	}

	public Cancion getCancionNueva() {
		return cancionNueva;
	}

	public void setCancionNueva(Cancion cancionNueva) {
		this.cancionNueva = cancionNueva;
	}

	public Map<String, Cancion> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(Map<String, Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}

}
