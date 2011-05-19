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
			Aprendiz aprendiz = new Aprendiz();
			//aprendiz.levantarBase(manager);
			
			new Pantalla(aprendiz);
			while(true);
		
	}
	
}
