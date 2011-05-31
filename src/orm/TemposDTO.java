package orm;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.java.ao.EntityManager;
import net.java.ao.Query;

import org.postgresql.util.PSQLException;

import excepciones.ORMException;


//################################################################################################################
/**
 * Clase que tiene los metodos para el manejo de la persistencia en la base de datos.
 * 
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 *///################################################################################################################

public class TemposDTO {
	
	//################################################################################################################
	/**
	 * Inserta un tempo en la base de datos, para un estilo determinado. No se permite repetir los tempos.
	 * 
	 * @param manager
	 * @param tempo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void insertar(EntityManager manager, String tempo, Estilos estilo,int cant) throws SQLException, ORMException {
		
		if(tempo==null || tempo.trim().length()==0){
			throw new ORMException("El tempo a Insertar esta vacio .");
		}
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("tempo", tempo);
			parametros.put("cantidad", cant);
			parametros.put("estilosid", estilo.getID());
			manager.create(Tempos.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("El Tempo a Insertar '"+tempo+"' con el estilo "+estilo.getNombre()+" , ya existe.");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 * elimina un tempo asociado a un estilo de la base de datos.
	 * 
	 * @param manager
	 * @param tempo
	 * @param estilo
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static void eliminar (EntityManager manager, String tempo, Estilos estilo)throws SQLException {
		Tempos[] tempos = manager.find(Tempos.class, Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
		manager.delete(tempos);
		
	}
	
	//################################################################################################################
	/**
	 * actualiza la cantidad de apariciones de un tempo con un estilo determinado.
	 * 
	 * @param manager
	 * @param tempo
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void actualizar(EntityManager manager, String tempo, Estilos estilo,int cant) throws SQLException, ORMException{
		try{
			
			Tempos[] tempos = manager.find(Tempos.class, Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
			tempos[0].setCantidad(cant);
			tempos[0].save();
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Tempo '"+tempo+"' para el estilo "+estilo.getNombre()+" .");
		}
	}
	
	//################################################################################################################
	/**
	 * verifica si existe un tempo de un estilo determinado.
	 * 
	 * @param manager
	 * @param tempo
	 * @param estilo
	 * @return verdadero o falso.
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static boolean existe(EntityManager manager,String tempo,Estilos estilo) throws SQLException
	{	
		boolean existe = false;
		Tempos[] tempos = manager.find(Tempos.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
		
		if(tempos.length > 0){
			existe =true;
		}

		return existe;
	}
	
	//################################################################################################################
	/**
	 * selecciona un determinado tempo para un estilo. 
	 * 		select * FROM ACORDES WHERE NOMBRE LIKE 'nombre' and estiloID = estilo
	 * 
	 * @param manager
	 * @param tempo
	 * @param estilo
	 * @return Tempos
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static Tempos buscar(EntityManager manager,String tempo,Estilos estilo) throws SQLException, ORMException
	{
		try{
			Tempos[] tempos = manager.find(Tempos.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
			return tempos[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Tempo '"+tempo+"' para el estilo "+estilo.getNombre()+" .");
		}
		
	}
	
	//################################################################################################################
	/**
	 * Selecciona todos los tempos de la base de datos.
	 * 
	 * @param manager
	 * @return lista de tempos
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static Tempos[] seleccionarTodos(EntityManager manager) throws SQLException{
		Tempos[] tempos= null;		
		tempos = manager.find(Tempos.class);
		return tempos;
	}
	
	
	
}
