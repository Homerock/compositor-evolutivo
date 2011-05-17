package compositor;

import estructura.AcordesFila;
import estructura.EstilosFila;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import estructura.Valores;
import excepciones.ArchivosException;
import excepciones.CancionException;
import excepciones.EstilosException;
import grafica.Pantalla;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import canciones.*;

import archivos.Archivos;
import archivos.Utiles;

//################################################################################
/**
  * @author Sebastian Pazos , Yamil Gomez
  *
  **/
//################################################################################
public class Compositor {
	
	Pantalla pantalla;
	
	//################################################################################
	/**
	  * Constructor
	  **/
	//################################################################################
	public Compositor(){
		
	}
		
	//################################################################################
	/**
	 * reemplaza a componer
	 * @param miMatrizAcordes
	 * @param miMatrizEstilos
	 * @param tonica
	 * @param estiloSeleccionado
	 * @param duracion
	 * @param tempo
	 * 
	 */
	//################################################################################
	public void componerCancion(
			MatrizAcordes miMatrizAcordes, 
			MatrizEstilos miMatrizEstilos, 
			String tonica, 
			String estiloSeleccionado, 
			int duracion, 
			String tempo) throws CancionException{
		
		boolean DEBUG = true;
		
		if(!miMatrizAcordes.ExisteAcordePpal(tonica)){
			throw new CancionException("No se encuentra el acorde '"+tonica+"' en la base de conocimientos.");
		}
	
		// si el estilo principal no esta cargado en la matriz no puedo componer
		if (!miMatrizEstilos.ExisteEstilo(estiloSeleccionado)){
			throw new CancionException("No se encuentra el estilo '"+estiloSeleccionado+"' en la base de conocimientos.");
			
		}
		String nombreCancion= estiloSeleccionado+"_"+tonica;
		Cancion nuevaCancion = new Cancion(nombreCancion,tempo,duracion,new Acorde(tonica),estiloSeleccionado);
		
		this.armarEstructuraEstilos(miMatrizEstilos, nuevaCancion);
		this.cargarAcordesEnEstructura(miMatrizAcordes, nuevaCancion);
		//this.armarArchivo(nuevaCancion);
		
		//PARA VER LA ESTRUCTURA DE LA CANCION
		//System.out.println(nuevaCancion.toString());
		// FALTA ARMAR UN ARCHIVO DE TEXTO Y CREAR EL MIDI
		// CONTROLAR QUE LA PRIMER NOTA DE LA CANCION SEA LA TONICA
		// CONTROLAR QUE EL ACORDE ANTERIOR SEA EL CORRECTO
		// CONTROLAR QUE LOS ACORDES DE ESTROFAS GEMELAS SEAN LAS MISMAS, ANDA Y NO SE PORQUE!!?!?!?!?!?!?!?
		if (DEBUG) {
			ArrayList<Estrofa> todasLasEstrofas = nuevaCancion.getEstrofas();
			System.out.println("CANCION: " + nuevaCancion.getNombre());
			for (Estrofa est : todasLasEstrofas) {
				System.out.println("----------------Num de estrofa: " + est.getNumeroEstrofa() + " --- " + "Estilo: " + est.getEstilo() + "--------------------");
				ArrayList<Compas> todosLosCompases = est.getListaDeCompases();
				if (est.isEsEstrofaGemela()) {
					System.out.println("Es gemela: " + est.getNroEstrofaGemela());
				}
				System.out.println("cantidad de compases: " + est.getCantidadCompases());
				for (Compas com : todosLosCompases) {
					System.out.print("Cant acordes del compas: " + com.getCantidadAcordes());
					ArrayList<Acorde> todosLosAcordes = com.getAcordes();
					System.out.print(" [ ");
					for (Acorde ac : todosLosAcordes) {
						System.out.print(ac.getNombre() + " ");
					}
					System.out.print(" ] \n");
				}	
				System.out.println("----------------------------Fin de estrofa------------------------------\n\n");
			}
		}
	}
	
	//################################################################################
	/**
	 * genera un archivo de texto con el formato de mma
	 * @param miCancion
	 */
	//################################################################################
	private void armarArchivo(Cancion miCancion) {
		
		String compas = " ";
		ArrayList<Estrofa> todasLasEstrofas = miCancion.getEstrofas();
		int linea = 1;
		
		Archivos miArchivo = new Archivos();
		miArchivo.escribirArchivo(miCancion.getNombre()+".mma", "Tempo " + miCancion.getTempo(), false);
		miArchivo.escribirArchivo(miCancion.getNombre()+".mma", "", true);
		
		for (Estrofa est : todasLasEstrofas) {
			
			miArchivo.escribirArchivo(miCancion.getNombre()+".mma", "Groove " + est.getEstilo(), true);
			
			ArrayList<Compas> todosLosCompases = est.getListaDeCompases();
		
			for (Compas com : todosLosCompases) {
				ArrayList<Acorde> todosLosAcordes = com.getAcordes();
				compas = " ";
				for (Acorde ac : todosLosAcordes) {
					compas = compas + " " + ac.getNombre();
				}
				miArchivo.escribirArchivo(miCancion.getNombre()+".mma", linea + compas, true);
				linea++;
			}	
		}
		System.out.println("Nuevo archivo generado: " + miCancion.getNombre()+".mma");
	}
	
	//################################################################################
	/**
	 * reemplaza el otro armarEstructuraEstilos
	 * @param miMatrizEstilos
	 * @param nuevaCancion
	 */
	//################################################################################
	private void armarEstructuraEstilos(MatrizEstilos miMatrizEstilos, Cancion nuevaCancion) {
		
		int numEstrofa = 1;
		String estiloInicial = nuevaCancion.getEstiloPrincipal();
		Map<String, EstilosFila> mapEstilo = miMatrizEstilos.getMisEstilos();
		
		if (estiloInicial.indexOf("Intro") == -1) {
			if (mapEstilo.containsKey(estiloInicial+Utiles.INTRO_ESTILO)) {
				estiloInicial = estiloInicial+Utiles.INTRO_ESTILO;		// obtengo el estilo inicial para comenzar a armar la estructura de estilos
			} 
		}
		
		EstilosFila miEstiloFila;
		int cantCompases;
		Estrofa miEstrofa, estrofaGemela;
		int n = 1;
		int semilla;
		Random rnd = new Random();
		String estilo = estiloInicial;
		int cantAcordes;
		
		while(n < nuevaCancion.getDuracion()) {
			
			miEstiloFila = miMatrizEstilos.getMisEstilos().get(estilo);
			// busco si ya existe la estrofa con este estilo
			if (nuevaCancion.existeEstrofaEstilo(estilo)) {
				miEstrofa = nuevaCancion.buscarEstrofaEstilo(estilo);
				cantCompases = miEstrofa.getCantidadCompases();
				estrofaGemela = new Estrofa(numEstrofa,estilo,cantCompases);
				estrofaGemela.setEsEstrofaGemela(true);
				estrofaGemela.setNroEstrofaGemela(miEstrofa.getNumeroEstrofa());
				estrofaGemela.setListaDeCompases(miEstrofa.getListaDeCompases());
				nuevaCancion.agregarEstrofa(estrofaGemela);
			} else {	
				// si no existe la creo
				cantCompases = this.calcularCantidadCompases(miEstiloFila);
				miEstrofa = new Estrofa(numEstrofa,estilo,cantCompases);
				for (int i = 0; i < cantCompases; i++) {
					cantAcordes = this.calcularCantidadAcordesUnCompas(miEstiloFila);
					Compas miCompas = new Compas(cantAcordes);
					miEstrofa.agregarCompas(miCompas);
				}
				nuevaCancion.agregarEstrofa(miEstrofa);
			}

			numEstrofa++;
			n = n + cantCompases;		
			semilla = miEstiloFila.getContador();
			estilo = miEstiloFila.buscarEstilo(rnd.nextInt(semilla+1));
			
			// si encontramos el End terminamos de armar la estructura
			if ((estilo.indexOf(Utiles.END_ESTILO) != -1) || (estilo.indexOf(Utiles.INTRO_ESTILO) != -1)) {
				System.out.println("CORTE POR ENCONTRAR: " + estilo);
				break;
			}
		}
	}
	
	//################################################################################
	/**
	 * recorre la lista de estrofas de una cancion y llama al metodo correspondiente para agregarle acordes a cada estrofa
	 * @param miMatrizAcordes
	 * @param nuevaCancion
	 * @throws CancionException 
	 */
	//################################################################################
	private void cargarAcordesEnEstructura(MatrizAcordes miMatrizAcordes, Cancion nuevaCancion) throws CancionException {
		
		ArrayList<Estrofa> listaEstrofas = nuevaCancion.getEstrofas();
		Acorde acordeAnterior = nuevaCancion.getTonica();
		int ultimo;
		ArrayList<Acorde> nuevosAcordes;
		Estrofa miEstrofa;
		
		Estrofa est = listaEstrofas.get(0);
		this.cargarPrimerEstrofa(miMatrizAcordes, acordeAnterior, est);
		nuevaCancion.agregarEstrofa(est);
		//obtenemos el ultimo acorde de la 1er estrofa
		acordeAnterior = est.getUltimoCompas().getUltimoAcorde();
		
		
		for (int i = 1; i < nuevaCancion.getEstrofas().size(); i++) {
			miEstrofa = nuevaCancion.getEstrofas().get(i);
			for (Compas miCompas : miEstrofa.getListaDeCompases()) {
				nuevosAcordes = this.generarAcordesDeCompas(miMatrizAcordes, acordeAnterior, miCompas);
				miCompas.setAcordes(nuevosAcordes);
				
				acordeAnterior = miCompas.getUltimoAcorde();	
				
			}
		}
	}
	
	// cargo la primer estrofa con la tonica
	private void cargarPrimerEstrofa(MatrizAcordes miMatrizAcordes, Acorde tonica, Estrofa miEstrofa) {
		
		ArrayList<Compas> listaCompas = miEstrofa.getListaDeCompases();
		Compas primerCompas = listaCompas.get(0);
		Compas miCompas;
		
		Acorde miAcorde;
		primerCompas.getAcordes().add(tonica);
		Acorde acordeAnterior = tonica;
		
		while (primerCompas.getAcordes().size() < primerCompas.getCantidadAcordes()) {
			miAcorde = this.generarAcorde(miMatrizAcordes, acordeAnterior);
			primerCompas.getAcordes().add(miAcorde);
			
			acordeAnterior = miAcorde;
		}
		
		//cargar desde el 2 compas (si lo tuviera)
		for (int i = 1; i < listaCompas.size(); i++) {
			miCompas = listaCompas.get(i);
			while (miCompas.getAcordes().size() < miCompas.getCantidadAcordes()) {
				miAcorde = this.generarAcorde(miMatrizAcordes, acordeAnterior);
				miCompas.getAcordes().add(miAcorde);
				
				acordeAnterior = miAcorde;
			}
		
		}
	}
	
	//################################################################################
	/**
	 * genera y devuelve una lista de acordes para un compas (entre uno y cuatro acordes)
	 * @param miMatrizAcordes
	 * @param acordeAnterior
	 * @param miCompas
	 * @return
	 * @throws CancionException 
	 */
	//################################################################################
	private ArrayList<Acorde> generarAcordesDeCompas(MatrizAcordes miMatrizAcordes, Acorde acordeAnterior, Compas miCompas) throws CancionException {
		
		
		ArrayList<Acorde> listaAcordes = new ArrayList<Acorde>();
		Acorde miAcorde;
		int cantidad;
		
		if (acordeAnterior == null || acordeAnterior.getNombre().trim().length() == 0) {
			throw new CancionException("Error en la composición - Falta el acorde anterior - " + Compositor.class);
		}
		
		if (miCompas.getCantidadAcordes() < Utiles.MINIMO_ACORDES || miCompas.getCantidadAcordes() > Utiles.MAXIMO_ACORDES) {
			throw new CancionException("Error en la composición - No se pueden generar " + miCompas.getCantidadAcordes() + " acordes. - " + Compositor.class);
		}
		
		cantidad = miCompas.getCantidadAcordes();

		
		for (int i = 1; i <= cantidad;i++) {
			miAcorde = this.generarAcorde(miMatrizAcordes, acordeAnterior);			
			listaAcordes.add(miAcorde);
			
			acordeAnterior = miAcorde;
		}
		return listaAcordes;
	}
	
	
	//################################################################################
	/**
	 * genera un nuevo acorde obteniendolo de la matriz de acordes teniendo en cuenta un acorde anterior
	 * @param listaAcordes
	 */
	//################################################################################
	private Acorde generarAcorde(MatrizAcordes miMatrizAcordes, Acorde acordeAnterior) {
		
		AcordesFila acordePpal;
		Random rnd = new Random();
		Acorde proxAcorde = new Acorde();
		int max;
		
		acordePpal = miMatrizAcordes.getMisAcordes().get(acordeAnterior.getNombre());
		max = acordePpal.getValorAcumuladoFila();
		proxAcorde.setNombre(acordePpal.buscarAcorde(rnd.nextInt(max+1)));
		
		return proxAcorde;
	}
	
	//################################################################################
	/**
	 * 
	 * @param miEstiloFila
	 * @return
	 */
	//################################################################################
	private int calcularCantidadAcordesUnCompas(EstilosFila miEstiloFila) {
		
		Random rnd= new Random();
		int miRandom;
		Map<Integer, Integer> mapCompases = new HashMap<Integer, Integer>();
		int acumulado = 0;
		
		if (miEstiloFila.getUnAcordeEnCompas() > 0) {
			acumulado += miEstiloFila.getUnAcordeEnCompas();
			mapCompases.put(1, acumulado);
		}
		if (miEstiloFila.getDosAcordesEnCompas() > 0) {
			acumulado += miEstiloFila.getDosAcordesEnCompas();
			mapCompases.put(2, acumulado);
		}
		if (miEstiloFila.getTresAcordesEnCompas() > 0) {
			acumulado += miEstiloFila.getTresAcordesEnCompas();
			mapCompases.put(3, acumulado);
		}
		if (miEstiloFila.getCuatroAcordesEnCompas() > 0) {
			acumulado += miEstiloFila.getCuatroAcordesEnCompas();
			mapCompases.put(4, acumulado);
		}
		miRandom = rnd.nextInt(acumulado);
		
		Iterator it = mapCompases.entrySet().iterator();
		int valor = 0;
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			if ((Integer)e.getValue() >= miRandom){
				valor = (Integer)e.getKey();
				break;
			}
		}
		return valor;
	}
	
	//################################################################################
	/**
	 * devuelve la cantidad de compases para un estilo, este valor lo obtiene haciendo un random entre los datos cargados.
	 * @return valor
	 */
	//################################################################################
	public int calcularCantidadCompases(EstilosFila miEstiloFila) {
		
		Random rnd= new Random();
		int miRandom;
		Map<Integer, Integer> mapCompases = new HashMap<Integer, Integer>();
		int acumulado = 0;
		
		if (miEstiloFila.getCantUnCompas() > 0) {
			acumulado += miEstiloFila.getCantUnCompas();
			mapCompases.put(1, acumulado);
		}
		if (miEstiloFila.getCantDosCompases() > 0) {
			acumulado += miEstiloFila.getCantDosCompases();
			mapCompases.put(2, acumulado);
		}
		if (miEstiloFila.getCantCuatroCompases() > 0) {
			acumulado += miEstiloFila.getCantCuatroCompases();
			mapCompases.put(4, acumulado);
		}
		if (miEstiloFila.getCantOchoCompases() > 0) {
			acumulado += miEstiloFila.getCantOchoCompases();
			mapCompases.put(8, acumulado);
		}
		miRandom = rnd.nextInt(acumulado);
		
		Iterator it = mapCompases.entrySet().iterator();
		int valor = 0;
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			if ((Integer)e.getValue() >= miRandom){
				valor = (Integer)e.getKey();
				break;
			}
		}
		return valor;
		
	}
	
	//################################################################################
	/**
	 * escribir
	 * Muestra un mensaje en el log de la pantalla principal
	 **/
	//################################################################################
	private void escribir(String mensaje) {
	
		pantalla.actualizarLog(mensaje);

	}
	
	//################################################################################
	/**
	 * crearMMA
	 **/
	//################################################################################
	private boolean crearMMA(String command, boolean flagbackground) {
		
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
	
}
