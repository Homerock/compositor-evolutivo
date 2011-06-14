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

public class EstilosDTO {
	//################################################################################################################
	/**
	 * Inserta un estilo en la base de datos. No se permite repetir el estilo.
	 * 
	 * @param manager
	 * @param estilo
	 * @param cantUnCompas
	 * @param cantDosCompases
	 * @param cantCuatroCompases
	 * @param cantOchoCompases
	 * @param unAcordeEnCompas
	 * @param dosAcordesEnCompas
	 * @param tresAcordesEnCompas
	 * @param cuatroAcordesEnCompas
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void insertar(
			EntityManager manager, 
			String estilo, 
			int cantUnCompas,
			int cantDosCompases,
			int cantCuatroCompases,
			int cantOchoCompases,
			int unAcordeEnCompas,
			int dosAcordesEnCompas,
			int tresAcordesEnCompas,
			int cuatroAcordesEnCompas
			) throws SQLException, ORMException {
		
		if (estilo.trim().length()==0  || estilo == null){
			throw new ORMException("El Estilo a Insertar esta vacio.");
		}
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("nombre", estilo);
			parametros.put("cantuncompas", cantUnCompas);
			parametros.put("cantdoscompases", cantDosCompases);
			parametros.put("cantcuatrocompases", cantCuatroCompases);
			parametros.put("cantochocompases", cantOchoCompases);
			parametros.put("unacordeencompas", unAcordeEnCompas);
			parametros.put("dosacordesencompas", dosAcordesEnCompas);
			parametros.put("tresacordesencompas", tresAcordesEnCompas);
			parametros.put("cuatroacordesencompas", cuatroAcordesEnCompas);
			
			manager.create(Estilos.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("El Estilo a Insertar '"+estilo+"' , ya existe .");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 * Inserta un estilo en la base de datos. No se permite repetir el estilo.
	 * 
	 * 
	 * @param manager
	 * @param estilo
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void insertar(
			EntityManager manager, 
			String estilo 
			) throws SQLException, ORMException {
		
		if (estilo.trim().length()==0  || estilo == null){
			throw new ORMException("El Estilo a Insertar esta vacio.");
		}
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("nombre", estilo);
			manager.create(Estilos.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("El Estilo a Insertar '"+estilo+"' , ya existe .");
		}
		return;
	}
	
	//################################################################################################################
	/**
	 * elimina un estilo de la base de datos.
	 * 
	 * @param manager
	 * @param estilo
	 * @throws SQLException
	 */
	//################################################################################################################
	public static void eliminar (EntityManager manager,  String estilo ) throws SQLException{
		
		Estilos[] e1 = manager.find(Estilos.class,Query.select().where("nombre like '"+estilo+"' "));
		manager.delete(e1);
	}
	
	//################################################################################################################
	/**
	 * 
	 * Actualiza todos los contadores de un estilo determinado.
	 * 
	 * @param manager
	 * @param estilo
	 * @param cantUnCompas
	 * @param cantDosCompases
	 * @param cantCuatroCompases
	 * @param cantOchoCompases
	 * @param unAcordeEnCompas
	 * @param dosAcordesEnCompas
	 * @param tresAcordesEnCompas
	 * @param cuatroAcordesEnCompas
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
		public static void actualizar(
			EntityManager manager, 
			String estilo, 
			int cantUnCompas,
			int cantDosCompases,
			int cantCuatroCompases,
			int cantOchoCompases,
			int unAcordeEnCompas,
			int dosAcordesEnCompas,
			int tresAcordesEnCompas,
			int cuatroAcordesEnCompas,
			boolean esPrincipal
			) throws SQLException, ORMException {
		
	
		try{
			
			Estilos[] e= manager.find(Estilos.class, Query.select().where("nombre like '" + estilo + "'"));
			e[0].setCantUnCompas(cantUnCompas);
			e[0].setCantDosCompases(cantDosCompases);
			e[0].setCantCuatroCompases(cantCuatroCompases);
			e[0].setCantOchoCompases(cantOchoCompases);
			e[0].setUnAcordeEnCompas(unAcordeEnCompas);
			e[0].setDosAcordesEnCompas(dosAcordesEnCompas);
			e[0].setTresAcordesEnCompas(tresAcordesEnCompas);
			e[0].setCuatroAcordesEnCompas(cuatroAcordesEnCompas);
			e[0].setEsPrincipal(esPrincipal);
			e[0].save();
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Estilo '"+estilo+"' a actualizar.");
		}		
	}
		
	//################################################################################################################
	/**
	 *  verifica si existe un estilo en la base datos.
	 *  
	 * @param manager
	 * @param estilo
	 * @return verdadero o falso. si existe o no.
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static boolean existe(EntityManager manager,String estilo) throws SQLException
	{
		boolean existe = false;
		
		Estilos[] e1 = manager.find(Estilos.class,Query.select().where(" nombre like '"+estilo+"' "));
		if (e1.length > 0){
			existe=true;
		}
		
		return existe;
	}	
	
	//################################################################################################################
	/**
	 * Busca un estilo en la base de datos.
	 * 			SELECT * FROM estilos where nombre like 'nombre'; 
	 * 
	 * @param manager
	 * @param estilo
	 * @return un estilo
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static Estilos buscar(EntityManager manager,String estilo) throws SQLException, ORMException
	{
		
		try{
			Estilos[] e1 = manager.find(Estilos.class,Query.select().where("nombre like '"+estilo+"' "));
			return e1[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe el Estilo '"+estilo+"' .");
		}
		
	}

	//################################################################################################################
	/**
	 * Selecciona todos los estilos de la base de datos.
	 * 
	 * @param manager
	 * @return
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static Estilos[] seleccionarTodos(EntityManager manager) throws SQLException {
		
		Estilos[] estilos = null;
		estilos= manager.find(Estilos.class);
		return estilos;
	}
	
	
	
	public static Estilos[] seleccionarTodosPrincipales(EntityManager manager) throws SQLException {
				
		Estilos[] e1 = manager.find(Estilos.class,Query.select().where(" esprincipal "));
		return e1;
			
		
	}
	
}
