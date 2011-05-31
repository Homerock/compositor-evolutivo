package grafica;

import archivos.Reconocedor;
import orm.Conexion;
import net.java.ao.EntityManager;
import compositor.Aprendiz;


//########################################################################
/**
 * @author Sebastian Pazos , Yamil Gomez
 *
 **/
//########################################################################

public class Inicio {

	public static void main(String args[]){
		
			EntityManager manager = Conexion.getConexionPsql();
			
			Aprendiz aprendiz = new Aprendiz();
			Reconocedor.cargarTablasAcordes();
			//System.out.println("Objeto Aprendiz: "+aprendiz);
			//aprendiz.levantarBase(manager);
			//new Pantalla(aprendiz);
			Interfaz grafica = new Interfaz(aprendiz);
			while(true);
	}
	
}
