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
 *///#############################################################################################################
public class TonicasDTO {
	
	//################################################################################################################
	/**
	 * Inserta una tonica en la base de datos, para un estilo determinado.
	 * 	 	No se permite repetir los tonicas asociado a un estilo.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void insertar(EntityManager manager, Acordes acordeTonica, Estilos estilo , int cant) throws SQLException, ORMException{
		try{
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("acordeid", acordeTonica.getID());
			parametros.put("cantidad", cant);
			parametros.put("estilosid", estilo.getID());
			manager.create(Tonicas.class, parametros);
			 
		}catch(PSQLException e){
			throw new ORMException("El Tonica a Insertar '"+acordeTonica.getNombre()+"' con el estilo "+estilo.getNombre()+" , ya existe.");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 *Inserta una tonica en la base de datos, para un estilo determinado (con cantidad cero).
	 * 	 	No se permite repetir los tonicas asociado a un estilo.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void insertar (EntityManager manager, Acordes acordeTonica, Estilos estilo ) throws SQLException, ORMException{
		try{
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("acordeid", acordeTonica.getID());		
			parametros.put("estilosid", estilo.getID());
			manager.create(Tonicas.class, parametros);
			 
		}catch(PSQLException e){
			throw new ORMException("El Tonica a Insertar '"+acordeTonica.getNombre()+"' con el estilo "+estilo.getNombre()+" , ya existe.");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 * Elimina una tonica en la base de datos, para un estilo determinado.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @throws SQLException
	 * 
	 *///##############################################################################################################
	public static void eliminar(EntityManager manager, Acordes acordeTonica, Estilos estilo) throws SQLException{
		Tonicas[] t = manager.find(Tonicas.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and acordeid = '"+acordeTonica.getID()+"' "));
		manager.delete(t);
	}
	
	//################################################################################################################
	/**
	 * Actualiza la cantidad , de una tonica para un estilo determinado, en la base de datos.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void actualizar(EntityManager manager, Acordes acordeTonica, Estilos estilo , int cant) throws SQLException, ORMException {
		try{
			Tonicas[] t = manager.find(Tonicas.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and acordeid = '"+acordeTonica.getID()+"' "));
			t[0].setCantidad(cant);
			t[0].save();
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("NO existe la tonica  '"+acordeTonica.getNombre()+"' con el estilo "+estilo.getNombre()+" .");		
		}
		
	}
	
	//################################################################################################################
	/**
	 * Verifica si existe una tonica para un estilo determinado, en la base de datos.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @return verdadero o falso.
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static boolean existe(EntityManager manager, Acordes acordeTonica, Estilos estilo) throws SQLException{	
		boolean existe = false;
		
		Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where(" acordeid = '"+acordeTonica.getID()+"'  and estilosid = '"+estilo.getID()+"' "));
		if(t1.length > 0){
			existe =true;
		}
		return existe;
		
	}
	
	//################################################################################################################
	/**
	 * Busca una tonica para un estilo determinado, en la base de datos.
	 * 
	 * @param manager
	 * @param acordeTonica
	 * @param estilo
	 * @return
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static Tonicas buscar(EntityManager manager, Acordes acordeTonica, Estilos estilo) throws SQLException, ORMException{
		
		try{
			Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where(" acordeid = '"+acordeTonica.getID()+"'  and estilosid = '"+estilo.getID()+"' "));
			return t1[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("NO existe la tonica  '"+acordeTonica.getNombre()+"' con el estilo "+estilo.getNombre()+" .");
		}
		
	}
	
	//################################################################################################################
	/**
	 * Selecciona todos las tonicas de la base de datos.
	 * 
	 * @param manager
	 * @return lista de tonicas
	 * @throws SQLException
	 * 
	 *///##############################################################################################################
	public static Tonicas[] seleccionarTodos(EntityManager manager) throws SQLException{
		Tonicas[] listaTonicas = manager.find(Tonicas.class);
		return listaTonicas;
	}
	
	//################################################################################################################
	/**
	 * Selecciona todos las tonicas asociadas a un estilo de la base de datos.
	 * 
	 * @param manager
	 * @param estilo
	 * @return lista de tonicas
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static Tonicas[] seleccionarTodos(EntityManager manager,  Estilos estilo) throws SQLException, ORMException{
		
		Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where("  estilosid = '"+estilo.getID()+"' "));
		return t1;
			
	}
	

}
