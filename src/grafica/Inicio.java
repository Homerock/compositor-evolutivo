package grafica;

import archivos.Reconocedor;
import orm.Conexion;
import net.java.ao.EntityManager;
import nucleo.Controlador;


//########################################################################
/**
 * @author Sebastian Pazos , Yamil Gomez
 *
 **/
//########################################################################

public class Inicio {

	public static void main(String args[]){
		
			EntityManager manager = Conexion.getConexionPsql();
			
			Controlador controlador = new Controlador();
			//Reconocedor.cargarTablasAcordes();
			//System.out.println("Objeto Aprendiz: "+aprendiz);
			//aprendiz.levantarBase(manager);
			//new Pantalla(aprendiz);
			Interfaz grafica = new Interfaz(controlador);
			while(true);
	}
	
}
