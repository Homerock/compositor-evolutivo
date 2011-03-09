package grafica;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.java.ao.*;
import compositor.Aprendiz;
import estructura.ValorAcordes;
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
