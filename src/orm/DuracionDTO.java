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

public class DuracionDTO {

	//################################################################################################################
	/**
	 * inserta una duracion asociada a un estilo determinado, no se permite una repetir una duracion con un estilo .
	 * 
	 * @param manager
	 * @param duracion
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void insertar(EntityManager manager, int duracion, Estilos estilo,int cant) throws SQLException, ORMException {
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("duracion", duracion);
			parametros.put("cantidad", cant);
			parametros.put("estilosid", estilo.getID());
			manager.create(Duracion.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("La duracion a Insertar '"+duracion+"' con el estilo "+estilo.getNombre()+" , ya existe.");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 * elimina una duracion asociada un estilo determinado.
	 * 
	 * @param manager
	 * @param duracion
	 * @param estilo
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void eliminar(EntityManager manager, int duracion,Estilos estilo) throws SQLException, ORMException{
		try{
			Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
			
			manager.delete(d);
			return;
			
		}catch(Exception e){
			throw new ORMException("Error en los parametros .");
		}
		
	}
	//################################################################################################################
	/**
	 * actualiza la cantidad de una duracion asociada a un estilo determinado.
	 * 
	 * @param manager
	 * @param duracion
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void actualizar(EntityManager manager, int duracion,Estilos estilo,int cant) throws SQLException, ORMException{
		try{
			Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
			d[0].setCantidad(cant);
			d[0].save();
			return;
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe la duracion '"+duracion+"' para el estilo "+estilo.getNombre()+" .");
		}
		
	}

	
	//################################################################################################################
	/**
	 * Verifica si existe una duracion para un estilo determinado.
	 * 
	 * @param manager
	 * @param duracion
	 * @param estilo
	 * @return verdadero o falso.
	 * @throws ORMException 
	 * @throws SQLException.
	 * 
	 *///################################################################################################################
	public static boolean existe(EntityManager manager, int duracion,Estilos estilo) throws SQLException, ORMException{
		try{
			boolean existe = false;
			Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
			
			if(d.length >0){
				existe = true;
			}
			return existe;
			
		}catch (NullPointerException e){
			throw new ORMException("Error en los parametros.");
		}
	}
	//################################################################################################################
	/**
	 * Selecciona una duracion para un estilo determinado.
	 * 		select * FROM duracion WHERE duracion= duracion and estiloID = estilo.
	 * 
	 * @param manager
	 * @param duracion
	 * @param estilo
	 * @return
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static Duracion buscar(EntityManager manager, int duracion,Estilos estilo) throws SQLException, ORMException{
		try{
			Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
			return d[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe la duracion '"+duracion+"' para el estilo "+estilo.getNombre()+" .");
		}
		
	}

	
	//################################################################################################################
	/**
	 * selecciona todos los duracion de la base datos.
	 * 	
	 * @param manager
	 * @return lista de todos las duraciones de la base datos.
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static Duracion[] seleccionarTodos(EntityManager manager) throws SQLException{
		
		Duracion[] duracion = manager.find(Duracion.class);
		return duracion;
		
	}
	
	
}
