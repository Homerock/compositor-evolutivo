package grafica;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.java.ao.*;
import compositor.Aprendiz;
import orm.*;

public class Inicio {

	public static void main(String args[]){
		
			EntityManager manager = Conexion.getConexionMysql();
			Aprendiz aprendiz = new Aprendiz();
			aprendiz.levantarBase(manager);
			
			new Pantalla(aprendiz);
			while(true);
			
		
		
	}
	
}
