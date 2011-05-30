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

public class AcordesDTO {
	
	//################################################################################################################
	/**
	 * Inserta un acorde en la base de datos. No se permite repetir los acordes.
	 * 
	 * @param manager
	 * @param nombre del acorde
	 * @param cantidad de apariciones
	 * @throws SQLException
	 * @throws ORMException 
	 */
	//################################################################################################################
	public static void insertar(EntityManager manager, String acorde, int cant) throws SQLException, ORMException {
		
		if(acorde ==null || acorde.trim().length()==0){
			throw new ORMException("El Acorde a Insertar esta vacio .");
		}
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("nombre", acorde);
			parametros.put("cantapariciones", cant);
			manager.create(Acordes.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("El Acorde a Insertar '"+acorde+"' , ya existe .");
		}
		return;
	}
	
	
	//################################################################################################################
	/**
	 *  Inserta un acorde en la base de datos. No se permite repetir los acordes.
	 *  con cantidad de apariciones en cero.
	 * 
	 * @param manager
	 * @param acorde
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	
	public static void insertar(EntityManager manager, String acorde) throws SQLException, ORMException {
		
		if(acorde ==null || acorde.trim().length()==0){
			throw new ORMException("El Acorde a Insertar esta vacio .");
		}
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("nombre", acorde);
			manager.create(Acordes.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("El Acorde a Insertar '"+acorde+"' , ya existe .");
		}
		return;
	}
	//################################################################################################################
	/**
	 * elimina un acorde en la base de datos.
	 * 
	 * @param manager
	 * @param nombre del acorde
	 * @throws SQLException
	 */
	//################################################################################################################
	public static void eliminar (EntityManager manager, String nombre)throws SQLException {
		Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + nombre + "'"));
		manager.delete(ac);
		
	}
	
	//################################################################################################################
	/**
	 * actualiza el cantidad de apariciones de un acorde en la base de datos
	 * 
	 * @param manager
	 * @param nombre
	 * @param contador
	 * @throws SQLException
	 * @throws ORMException 
	 */
	//################################################################################################################
	public static void actualizar(EntityManager manager, String acorde, int contador) throws SQLException, ORMException{
		try{
			
			Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + acorde + "'"));
			ac[0].setCantApariciones(contador);
			ac[0].save();
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Acorde '"+acorde+"' a actualizar.");
		}
	}
	
	//################################################################################################################
	/**
	 * retorna todos los acordes de la base (select * from Acordes) 
	 * 
	 * @return Lista con todos los acordes
	 * @throws SQLException 
	 */
	//################################################################################################################
	public static Acordes[] seleccionarTodos(EntityManager manager) throws SQLException{
		Acordes[] acordes = null;		
		acordes = manager.find(Acordes.class);
		return acordes;
	}
	
	
	//################################################################################################################
	/**
	 * selecciona un determinado acorde. 
	 * 		select * FROM ACORDES WHERE NOMBRE LIKE 'nombre'
	 * 
	 * @param manager
	 * @param nombre acorde
	 * @return lista de acordes
	 * @throws SQLException
	 * @throws ORMException 
	 */
	//################################################################################################################
	public static Acordes buscar(EntityManager manager, String acorde) throws SQLException, ORMException{
		try{
			Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + acorde + "'"));
			return ac[0];
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Acorde '"+acorde+"' .");
		}
	}
	
	
	//################################################################################################################
	/**
	 * Verifica si exite un acorde en la base de datos
	 * 
	 * @param manager
	 * @param nombre del acorde
	 * @return true or false.
	 * @throws SQLException
	 */
	//################################################################################################################
	public static boolean existe(EntityManager manager, String nombre) throws SQLException{
		boolean existe = false;
			Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + nombre + "'"));
			if (ac.length > 0) {
				existe = true;
			}
		return existe;
	}

}
