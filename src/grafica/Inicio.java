package grafica;

import net.java.ao.EntityManager;
import orm.Conexion;

import compositor.Aprendiz;

public class Inicio {

	public static void main(String args[]){
		
			EntityManager manager = Conexion.getConexionMysql();
			Aprendiz aprendiz = new Aprendiz();
			aprendiz.levantarBase(manager);
			
			new Pantalla(aprendiz);
			while(true);
		
	}
	
}
