package grafica;

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
			try {
				Aprendiz aprendiz = new Aprendiz();
				//System.out.println("Objeto Aprendiz: "+aprendiz);
				//aprendiz.levantarBase(manager);
				//new Pantalla(aprendiz);
				Interfaz grafica = new Interfaz(aprendiz);
				while(true);
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
			
	}
	
}
