package compositor;

import estructura.*;
import grafica.Pantalla;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
	public void Componer(MatrizAcordes miMatrizAcordes, String tonica){
		
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
