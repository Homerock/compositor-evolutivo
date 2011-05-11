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
	  * componer
	  **/
	//################################################################################
	/*public void componer(MatrizAcordes miMatrizAcordes, MatrizEstilos miMatrizEstilos, String tonica, String estiloSeleccionado, int duracion, String tempo) {
				
		Map<String, ArrayList<String>> cancion = new HashMap<String, ArrayList<String>>();
		ArrayList<Valores> estructuraDeCancion = new ArrayList<Valores>();
		Map<String, EstilosFila> mapEstilo;
		ArrayList<String> listaAcordes;
		String estiloInicial;
		Archivos miArchivo = new Archivos();
		int numLinea = 1;
		
		// si la tonica no esta cargada en la matriz no puedo componer
		if(!miMatrizAcordes.ExisteAcordePpal(tonica)){
			System.out.println("No conozco ese acorde principal!");
			return;
		}
	
		// si el estilo principal no esta cargado en la matriz no puedo componer
		if (!miMatrizEstilos.ExisteEstilo(estiloSeleccionado)){
			System.out.println("No conozco ese estilo principal!");
			return;
		}
		
		estiloInicial = estiloSeleccionado;
		mapEstilo = miMatrizEstilos.getMisEstilos();
		// si no contiene la cadena intro la busco dentro del arreglo que le corresponde al estilo principal
		if (estiloSeleccionado.indexOf("Intro") == -1) {
			if (mapEstilo.containsKey(estiloSeleccionado+Utiles.INTRO_ESTILO)) {
				estiloInicial = estiloSeleccionado+Utiles.INTRO_ESTILO;		// obtengo el estilo inicial para comenzar a armar la estructura de estilos
				System.out.println("Estilo Inicial: " + estiloInicial);
			} else {
				System.out.println("No existe Intro para este estilo, tomo como inicial al estilo: " + estiloInicial);
			}
		}
		
		// Armo una lista de estilos que forman la estructura de la cancion
		estructuraDeCancion = armarEstructuraEstilos(miMatrizEstilos, estiloInicial, duracion, estiloSeleccionado);
		
		// ahora que tengo la lista de estilos (estructura de la cancion)
		// y tengo la cantidad de compases que tiene cada estilo,
		// genero los acordes, utilizando la matriz de acordes y escribo el .mma
		
		cancion = cargarAcordesEnEstructura(estructuraDeCancion, miMatrizAcordes, tonica, estiloSeleccionado, tempo);
		
		miArchivo.escribirArchivo(Utiles.NOMBRE_CANCION, "Tempo " + tempo, false);
		miArchivo.escribirArchivo(Utiles.NOMBRE_CANCION, "", true);
		
		// recorro la estructura de la cancion obteniendo los estilos por orden
		for (Valores va : estructuraDeCancion) {
			miArchivo.escribirArchivo(Utiles.NOMBRE_CANCION, "Groove " + va.getEstilo(), true);
			
			listaAcordes = cancion.get(va.getEstilo());
			for (String acorde : listaAcordes) {
				numLinea++;
				miArchivo.escribirArchivo(Utiles.NOMBRE_CANCION, numLinea + " " + acorde, true);
			}
		}
		
		// creamos el archivo midi utilizando el programa mma
		crearMMA("mma " + Utiles.NOMBRE_CANCION, false);
			
		return;
	}*/

	//reemplaza a componer
	public void componerCancion(
			MatrizAcordes miMatrizAcordes, 
			MatrizEstilos miMatrizEstilos, 
			String tonica, 
			String estiloSeleccionado, 
			int duracion, 
			String tempo) throws CancionException{
		
		if(!miMatrizAcordes.ExisteAcordePpal(tonica)){
			throw new CancionException("No se encuentra el acorde '"+tonica+"' en la base de conocimientos.");
		}
	
		// si el estilo principal no esta cargado en la matriz no puedo componer
		if (!miMatrizEstilos.ExisteEstilo(estiloSeleccionado)){
			throw new CancionException("No se encuentra el estilo '"+estiloSeleccionado+"' en la base de conocimientos.");
			
		}
		String nombreCancion= estiloSeleccionado+"_"+tonica;
		Cancion nuevaCancion = new Cancion(nombreCancion,tempo,duracion,tonica,estiloSeleccionado);
		
		this.armarEstructuraEstilos(miMatrizEstilos, nuevaCancion);
		
		// FALTA LLENAR LA CANCION CON ACORDES
		
		return;
	}
	/**
	 * 
	 * @param miMatrizEstilos
	 * @param nuevaCancion
	 */
	//reemplaza el otro armarEstructuraEstilos
	private void armarEstructuraEstilos(
			MatrizEstilos miMatrizEstilos,
			Cancion nuevaCancion) {
		
		int numEstrofa = 1;
		String estiloInicial = nuevaCancion.getEstiloPrincipal();
		Map<String, EstilosFila> mapEstilo = miMatrizEstilos.getMisEstilos();
		
		if (estiloInicial.indexOf("Intro") == -1) {
			if (mapEstilo.containsKey(estiloInicial+Utiles.INTRO_ESTILO)) {
				estiloInicial = estiloInicial+Utiles.INTRO_ESTILO;		// obtengo el estilo inicial para comenzar a armar la estructura de estilos
			} 
		}
		
		
		EstilosFila miEstiloFila;
		String proxEstilo;
		int cantCompases;
		Estrofa miEstrofa;
		int n = 1;
		int semilla;
		Random rnd = new Random();
		String estilo = estiloInicial;
		int cantAcordes;
		
		while(n < nuevaCancion.getDuracion()) {
			
			//if (nuevaCancion.existeEstrofaEstilo(estilo)) {
				// CONTROLAR SI ESTE ESTILO YA ESTA EN LA CANCION
				// OSEA SI LA ESTROFA ES GEMELA
			//} else {
				miEstiloFila = miMatrizEstilos.getMisEstilos().get(estilo);
				semilla = miEstiloFila.getContador();
				cantCompases = this.calcularCantidadCompases(miEstiloFila);
				miEstrofa = new Estrofa(numEstrofa,estilo,cantCompases);
				for (int i = 0; i < cantCompases; i++) {
					cantAcordes = this.calcularCantidadAcordesUnCompas(miEstiloFila);
					Compas miCompas = new Compas(cantAcordes);
					miEstrofa.agregarCompas(miCompas);
				}
				nuevaCancion.agregarEstrofa(miEstrofa);
			//}
			
			
			
			numEstrofa++;
			n = n + cantCompases;		
			estilo = miEstiloFila.buscarEstilo(rnd.nextInt(semilla+1));
			
			// si encontramos el End terminamos de armar la estructura
			if ((estilo.indexOf(Utiles.END_ESTILO) != -1) || (estilo.indexOf(Utiles.INTRO_ESTILO) != -1)) {
				break;
			}
		}
	}
	
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
	
	/**
	 * devuelve la cantidad de compases para un estilo, este valor lo obtiene haciendo un random entre los datos cargados.
	 * @return valor
	 */
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
	

	/*//################################################################################
	*//**
	 * cargarAcordesEnEstructura
	 **//*
	//################################################################################
	private Map<String, ArrayList<String>> cargarAcordesEnEstructura(ArrayList<Valores> estructuraDeCancion, MatrizAcordes miMatrizAcordes, String tonica, String estilo, String tempo) {
	
		Map<String, ArrayList<String>> cancion = new HashMap<String, ArrayList<String>>();
		ArrayList<String> listaAcordes;
		AcordesFila acordePpal;
		String proxAcorde;
		Random rnd = new Random();
		int max, cant, miRandom;
		int n = 1;
		int numLinea = 1;
		
		acordePpal = miMatrizAcordes.getMisAcordes().get(tonica);
		max=acordePpal.getValorAcumuladoFila();
		
		// recorro la estructura de la cancion obteniendo los estilos por orden
		for (Valores va : estructuraDeCancion) {
			listaAcordes = new ArrayList<String>();
			if (cancion.containsKey(va.getEstilo())) {
				continue;
			}
			
			cant = va.getCantidad();
			n = 1;
			if (numLinea == 1) {
				cant = cant-1;	//un compas menos por ya lo uso con la tonica
				listaAcordes.add(tonica);
			}
			// dentro de cada estilo genero los acordes que forman este bloque de la estructura
			while(n <= cant){
				miRandom=rnd.nextInt(max+1);
				proxAcorde=acordePpal.buscarAcorde(miRandom);
				acordePpal = miMatrizAcordes.getMisAcordes().get(proxAcorde);
				max=acordePpal.getValorAcumuladoFila();	
				n++;
				numLinea++;
				listaAcordes.add(proxAcorde);
			}
			cancion.put(va.getEstilo(), listaAcordes);
		}
		return cancion;
	}*/
	
	/*//################################################################################
	*//**
	 * armarEstructuraEstilos
	 * partiendo de un estilo principal, arma una lista con los estilos que tendra la cancion
	 * para buscar los nuevos estilos se utiliza la matrizEstilos
	 * esta lista es la estructura de la cancion
	 **//*
	//################################################################################
	private ArrayList<Valores> armarEstructuraEstilos(MatrizEstilos miMatrizEstilos, String estiloInicial, int duracionCancion, String estiloSeleccionado) {
		
		ArrayList<Valores> estructuraDeCancion = new ArrayList<Valores>();
		Random rnd= new Random();
		int max, duracionEstilo = 0;
		int n=1;
		int miRandom;
		EstilosFila estiloF;
		String proxEstilo = null;
		Map<String, EstilosFila> mapEstilos;
		
		mapEstilos = miMatrizEstilos.getMisEstilos();
		estiloF = miMatrizEstilos.getMisEstilos().get(estiloInicial);
		max = estiloF.getContador();
		
		// cargo primero el estilo inicial
		duracionEstilo = 4;
		estructuraDeCancion.add(new Valores(estiloInicial,duracionEstilo));
		n = n + duracionEstilo;
		
		// busco el siguiente estilo hasta llegar al End o hasta que se alcance el limite de duracion de cancion
		// el valor de n es la suma de compases que tiene cada estilo
		while(n<duracionCancion){
			
			duracionEstilo = estiloF.cantidadCompases();			
			miRandom = rnd.nextInt(max+1);
			proxEstilo = estiloF.buscarEstilo(miRandom);
			
			// si encontramos el End terminamos de armar la estructura
			if ((proxEstilo.indexOf(Utiles.END_ESTILO) != -1) || (proxEstilo.indexOf(Utiles.INTRO_ESTILO) != -1)) {
				break;
			}
			estructuraDeCancion.add(new Valores(proxEstilo, duracionEstilo));
			estiloF = miMatrizEstilos.getMisEstilos().get(proxEstilo);
			max = estiloF.getContador();
			n = n + duracionEstilo;
		}
		// Si existe un End para el estilo principal lo agrego y terminamos con un ultimo acorde que seria la tonica
		if (mapEstilos.containsKey(estiloSeleccionado+Utiles.END_ESTILO)) {
			duracionEstilo = estiloF.cantidadCompases();
			estructuraDeCancion.add(new Valores(proxEstilo, duracionEstilo));
		} 
		
		return estructuraDeCancion;
	}*/
	
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
