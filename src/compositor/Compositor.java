package compositor;

import estructura.*;
import grafica.Pantalla;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.sound.midi.Sequencer;
import archivos.Archivos;
import archivos.Midi;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/
public class Compositor {
	
	Pantalla pantalla;
	
	/**---------------------------------------------------------------------------
	  * Constructor
	  *---------------------------------------------------------------------------*/
	public Compositor(){
		
	}
	
	/**---------------------------------------------------------------------------
	  * @param miMatrizAcordes
	  * @param tonica
	  *---------------------------------------------------------------------------*/
	public void componer2(MatrizAcordes miMatrizAcordes, String tonica){
		
		AcordesFila mapAcordePpal;
		String proxAcorde;
		Random rnd= new Random();
		int max;
		int n=1;
		int miRandom;
		Archivos miArchivo = new Archivos();
		
		if(!miMatrizAcordes.ExisteAcordePpal(tonica)){
			System.out.println("No conozco esa Acorde principal!");
			return;
		}
		mapAcordePpal = miMatrizAcordes.getMisAcordes().get(tonica);
		//obetenmos el acumulador total
		max=mapAcordePpal.getContador();
		System.out.println("maximo :"+max);
		//rnd.nextInt(max);
		miArchivo.escribirArchivo("temp.mma", "groove 60sRock", false);
		miArchivo.escribirArchivo("temp.mma", 1 + " " + tonica, true);
		while(n<20){
			miRandom=rnd.nextInt(max);
			proxAcorde=mapAcordePpal.buscarAcorde(miRandom);
			System.out.println("valor :"+miRandom+" Acorde :"+proxAcorde);
			miArchivo.escribirArchivo("temp.mma", n+1 + " " + proxAcorde, true);
			mapAcordePpal = miMatrizAcordes.getMisAcordes().get(proxAcorde);
			max=mapAcordePpal.getContador();
			n++;
		}
		// creamos el archivo midi utilizando el programa mma
		crearMMA("mma temp.mma", false);
		// creamos un objeto de nuestra clase midi
		Midi mid = new Midi();
		// abrimos el archivo midi para obtener el Sequencer y ahi poder reproducirlo y detenerlo
		Sequencer seq = mid.abrir("temp.mid");
		//mid.reproducir(seq);
	}
	
	/**---------------------------------------------------------------------------
	  * componer
	  *---------------------------------------------------------------------------*/
	public void componer(MatrizAcordes miMatrizAcordes, MatrizEstilos miMatrizEstilos, String tonica, String estilo, int duracion, String tempo) {
				
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
		if (!miMatrizEstilos.ExisteEstiloPpal(estilo)){
			System.out.println("No conozco ese estilo principal!");
			return;
		}
		
		estiloInicial = estilo;
		mapEstilo = miMatrizEstilos.getMisEstilos();
		// si no contiene la cadena intro la busco dentro del arreglo que le corresponde al estilo principal
		if (estilo.indexOf("Intro") == -1) {
			if (mapEstilo.containsKey(estilo+"Intro")) {
				estiloInicial = estilo+"Intro";		// obtengo el estilo inicial para comenzar a armar la estructura de estilos
				System.out.println("Estilo Inicial: " + estiloInicial);
			} else {
				System.out.println("No existe Intro para este estilo, tomo como inicial al estilo: " + estiloInicial);
			}
		}
		
		// Armo una lista de estilos que forman la estructura de la cancion
		estructuraDeCancion = armarEstructuraEstilos(miMatrizEstilos, estiloInicial, duracion);
		
		// ahora que tengo la lista de estilos (estructura de la cancion)
		// y tengo la cantidad de compases que tiene cada estilo,
		// genero los acordes, utilizando la matriz de acordes y escribo el .mma
		
		cancion = cargarAcordesEnEstructura(estructuraDeCancion, miMatrizAcordes, tonica, estilo, tempo);
		
		miArchivo.escribirArchivo("temp.mma", "Tempo " + tempo, false);
		miArchivo.escribirArchivo("temp.mma", "", true);
		
		// recorro la estructura de la cancion obteniendo los estilos por orden
		for (Valores va : estructuraDeCancion) {
			miArchivo.escribirArchivo("temp.mma", "Groove " + va.getEstilo(), true);
			
			listaAcordes = cancion.get(va.getEstilo());
			for (String acorde : listaAcordes) {
				numLinea++;
				miArchivo.escribirArchivo("temp.mma", numLinea + " " + acorde, true);
			}
		}
		
		// Si existe un End para el estilo principal lo agrego y terminamos con un ultimo acorde que seria la tonica
		if (mapEstilo.containsKey(estilo+"End")) {
			miArchivo.escribirArchivo("temp.mma", "Groove " + estilo + "End", true);
			miArchivo.escribirArchivo("temp.mma", numLinea+1 + " " + tonica, true);
		} 
		
		// creamos el archivo midi utilizando el programa mma
		crearMMA("mma temp.mma", false);
		
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * cargarAcordesEnEstructura
	 *---------------------------------------------------------------------------*/
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
		max=acordePpal.getContador();
		
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
				miRandom=rnd.nextInt(max);
				proxAcorde=acordePpal.buscarAcorde(miRandom);
				acordePpal = miMatrizAcordes.getMisAcordes().get(proxAcorde);
				max=acordePpal.getContador();	
				n++;
				numLinea++;
				listaAcordes.add(proxAcorde);
			}
			cancion.put(va.getEstilo(), listaAcordes);
		}
		return cancion;
	}
	
	/**---------------------------------------------------------------------------
	 * armarEstructuraEstilos
	 * partiendo de un estilo principal, arma una lista con los estilos que tendra la cancion
	 * para buscar los nuevos estilos se utiliza la matrizEstilos
	 * esta lista es la estructura de la cancion
	 *---------------------------------------------------------------------------*/
	private ArrayList<Valores> armarEstructuraEstilos(MatrizEstilos miMatrizEstilos, String estiloInicial, int duracionCancion) {
		
		ArrayList<Valores> estructuraDeCancion = new ArrayList<Valores>();
		Random rnd= new Random();
		int max, duracionEstilo = 0;
		int n=1;
		int miRandom;
		EstilosFila mapEstilo;
		String proxEstilo;
		
		mapEstilo = miMatrizEstilos.getMisEstilos().get(estiloInicial);
		max = mapEstilo.getContador();
		
		// cargo primero el estilo inicial
		duracionEstilo = 4;
		estructuraDeCancion.add(new Valores(estiloInicial,duracionEstilo));
		n = n + duracionEstilo;
		
		// busco el siguiente estilo hasta llegar al End o hasta que se alcance el limite de duracion de cancion
		// el valor de n es la suma de compases que tiene cada estilo
		while(n<duracionCancion){
			duracionEstilo = 4;			// FALTA VER COMO SE CALCULA ESTE VALOR (aleatorio o usando los valores cargados en mapEstilo)
			miRandom = rnd.nextInt(max);
			proxEstilo = mapEstilo.buscarEstilo(miRandom);
			// si encontramos el End terminamos de armar la estructura
			if ((proxEstilo.indexOf("End") != -1) || (proxEstilo.indexOf("Intro") != -1)) {
				break;
			}
			estructuraDeCancion.add(new Valores(proxEstilo, duracionEstilo));
			mapEstilo = miMatrizEstilos.getMisEstilos().get(proxEstilo);
			max = mapEstilo.getContador();
			n = n + duracionEstilo;
		}
		return estructuraDeCancion;
	}
	
	/**---------------------------------------------------------------------------
	 * escribir
	 * Muestra un mensaje en el log de la pantalla principal
	 *---------------------------------------------------------------------------*/
	private void escribir(String mensaje) {
	
		pantalla.actualizarLog(mensaje);
		
	}
	
	/**---------------------------------------------------------------------------
	 * crearMMA
	 *---------------------------------------------------------------------------*/
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
