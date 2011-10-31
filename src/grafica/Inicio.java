package grafica;

import nucleo.Controlador;


//########################################################################
/**
 * @author Sebastian Pazos , Yamil Gomez
 *
 **/
//########################################################################

public class Inicio {

	public static void main(String args[]){
			
			Controlador controlador = new Controlador();
			Interfaz grafica = new Interfaz(controlador);
			grafica.cargarCombo();
			while(true);
	}
	
}
