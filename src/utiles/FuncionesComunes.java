package utiles;

import java.io.File;

public class FuncionesComunes {
	
	public static String getPathOS(){
		
	      String ruta=null; 
	      // Recuperamos el sistema operativo 
	      String osName = System.getProperty ( "os.name" ); 
	              
	      // Cargamos la cadena del interprete de comandos según el sistema operativo y el comando a ejecutar 
	      if ( osName.equals ("Windows XP") ) 
	          ruta = "c:\\"; 
	      else 
	          if ( osName.equals ("Windows 95") || osName.equals ("Windows 98") ) 
	                ruta = "c:\\";  
	          else { 
	                ruta = "/home";
	        } 
	      
	      return ruta;
		
	}

}
