package grafica;

import java.sql.SQLException;

import net.java.ao.*;
import compositor.Aprendiz;
import orm.*;

public class Inicio {

	public static void main(String args[]){

		EntityManager manager = Conexion.getConexionPsql();
		
		Tonicas t ;
		try {
			
			//pruebas de uso del orm
			
			/*
			miEstilo = manager.create(Estilos.class);
			miEstilo.setNombre("Rock");
			miEstilo.setCompasesDeDosTiempos(2);
			miEstilo.save();
			*/
			
			Estilos e1 = EstilosDTO.buscar(manager,"Rock");
			Acordes ac1 = AcordesDTO.buscar(manager, "Gm");
			Acordes ac2 = AcordesDTO.buscar(manager, "Fm7");
			
			/*
			 // guardo una tonica
			t = manager.create(Tonicas.class);
			t.setAcorde(ac1);
			t.setEstilos(e1);
			t.setCantidad(4);
			t.save();
			System.out.print(" tonica :"+t.getAcorde());
			*/
			
			if (TonicasDTO.existe(manager,ac1,e1)){
				System.out.println("existe la tonica ");
				Tonicas t1 = TonicasDTO.buscar(manager,ac1,e1);
			}
			//System.out.println("tonica : "+ t1.getCantidad());			
			/*
			OcurrenciasAcordes oc1 = OcurrenciasAcordesDTO.buscar(manager,ac1,ac2);
			System.out.println("cantidad :"+oc1.getCantidad());
			oc1.setEstilos(miEstilo)	;
			oc1.save();
				*/
			/*
			Tempos te1 = manager.create(Tempos.class);
			te1.setCantidad(20);
			te1.setTempo(80);
			te1.setEstilos(e1);
			te1.save();
			
			System.out.println("tempo : "+te1.getCantidad()+ te1.toString());
			*/
			
			if(TemposDTO.existe(manager, 80, e1)){
				System.out.println("existe el tempo!");
				Tempos te1 = TemposDTO.buscar(manager,80,e1);
			}
			
			/*
			
			Duracion d1 = manager.create(Duracion.class);
			
			d1.setEstilos(e1);
			d1.setDuracion(100);
			d1.setCantidad(22);
			d1.save();
			*/
			
			
			if(DuracionDTO.existe(manager,100,e1)){
				System.out.println("existe la duracion!");
				
			}
			/*
			OcurrenciasEstilos oe1 = manager.create(OcurrenciasEstilos.class);
			oe1.setEstiloPrincipal(e1);
			oe1.setEstiloSecundario(e1);
			oe1.setCantidad(22);
			oe1.save();
			*/
			
			if(OcurrenciasEstilosDTO.existe(manager,e1,e1)){
				
				System.out.println("existe la ocurrencias estilo!!");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	
		//Aprendiz aprendiz = new Aprendiz();
		//aprendiz.levantarBase(manager);
		
		//	new Pantalla(aprendiz);
		//while(true);
		
	}
	
}
