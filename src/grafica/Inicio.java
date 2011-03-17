package grafica;

import net.java.ao.*;
import compositor.Aprendiz;
import orm.*;

public class Inicio {

	public static void main(String args[]){

		EntityManager manager = Conexion.getConexionPsql();
		
		Aprendiz aprendiz = new Aprendiz();
		aprendiz.levantarBase(manager);
		
		new Pantalla(aprendiz);
		while(true);
		
	}
	
}
