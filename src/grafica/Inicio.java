package grafica;

import java.sql.SQLException;

import net.java.ao.*;
import compositor.Aprendiz;
import orm.*;

public class Inicio {

	public static void main(String args[]){

		EntityManager manager = Conexion.getConexion();
	
		Aprendiz aprendiz = new Aprendiz();
		aprendiz.levantarBase(manager);
		
		new Pantalla(aprendiz);
		while(true);
		
	}
	
}
