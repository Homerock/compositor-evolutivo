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
*///##############################################################################################################
public class OcurrenciasEstilosDTO {
	
	//################################################################################################################
	/**
	 * Inserta la ocurrencia de estiloPrincipal ,estiloSecundario , con una cantidad determinada en la base de datos.
	 * 	No se permite repetir la ocurrencia estiloPrincipal ,estiloSecundario.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void insertar(EntityManager manager, Estilos estiloPrincipal,Estilos estiloSecundario,int cant) throws SQLException, ORMException{
		try{
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("estiloprincipalid", estiloPrincipal.getID());
			parametros.put("estilosecundarioid", estiloSecundario.getID());
			parametros.put("cantidad", cant);
			manager.create(OcurrenciasEstilos.class, parametros);
		
		}catch(PSQLException e){
			throw new ORMException("La ocurrencia de estilos a Insertar '"+estiloPrincipal.getNombre()+"' -  "+estiloSecundario.getNombre()+" , ya existe.");
		}
	}	
	
	//################################################################################################################
	/**
	 * Inserta la ocurrencia de estiloPrincipal ,estiloSecundario , con cantidad cero en la base de datos.
	 * 	No se permite repetir la ocurrencia estiloPrincipal ,estiloSecundario.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void insertar(EntityManager manager, Estilos estiloPrincipal,Estilos estiloSecundario) throws SQLException, ORMException{
		try{
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("estiloprincipalid", estiloPrincipal.getID());
			parametros.put("estilosecundarioid", estiloSecundario.getID());
			manager.create(OcurrenciasEstilos.class, parametros);
		
		}catch(PSQLException e){
			throw new ORMException("La ocurrencia de estilos a Insertar '"+estiloPrincipal.getNombre()+"' -  "+estiloSecundario.getNombre()+" , ya existe.");
			
		}
		
	}	
	
	//################################################################################################################
	/**
	 * Eliminar una ocurrencia de estiloPrincipal estiloSecundario de la base de datos.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @throws SQLException
	 * 
	 *///##############################################################################################################
	public static void eliminar(EntityManager manager, Estilos estiloPrincipal,Estilos estiloSecundario) throws SQLException{
		
		OcurrenciasEstilos[] oa = manager.find(OcurrenciasEstilos.class,Query.select().where(
								" estiloprincipalid='"+estiloPrincipal.getID()+"' " +
								" and  estilosecundarioid='"+estiloSecundario.getID()+"' "));
		manager.delete(oa);
		
	}
	
	//################################################################################################################
	/**
	 * Actualiza la cantidad  de la ocurrencia estiloPrincipal estiloSecundario en la base de datos.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static void actualizar(EntityManager manager, Estilos estiloPrincipal,Estilos estiloSecundario,int cant) throws SQLException, ORMException{
		try{
				OcurrenciasEstilos[] oa = manager.find(OcurrenciasEstilos.class,Query.select().where(
									" estiloprincipalid='"+estiloPrincipal.getID()+"' " +
									" and  estilosecundarioid='"+estiloSecundario.getID()+"' "));
			
				oa[0].setCantidad(cant);
				oa[0].save();
				return;
		}catch(Exception e){
			throw new ORMException("La ocurrencia de estilos a actualizar '"+estiloPrincipal.getNombre()+"' - '"+estiloSecundario.getNombre()+"' , NO existe.");
		}
	}
	
	//################################################################################################################
	/**
	 * Verifica si la ocurrencia estiloPrincipal estiloSecundario existe en la base de datos.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @return verdadero o falso.
	 * @throws SQLException
	 * 
	 *///##############################################################################################################
	public static boolean existe(EntityManager manager,Estilos estiloPrincipal,Estilos estiloSecundario) throws SQLException{	
		
		boolean existe = false;
		OcurrenciasEstilos[] oe = manager.find(OcurrenciasEstilos.class,Query.select().where(" estiloprincipalid = '"+estiloPrincipal.getID()+"' and estilosecundarioid = '"+estiloSecundario.getID()+"' "));
		if(oe.length>0){
			existe = true;
		}
		
		return existe;
		
	}
	
	//################################################################################################################
	/**
	 * Busca  la ocurrencia estiloPrincipal estiloSecundario en la base de datos.
	 * 
	 * @param manager
	 * @param estiloPrincipal
	 * @param estiloSecundario
	 * @return OcurrenciasEstilos
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///##############################################################################################################
	public static OcurrenciasEstilos buscar(EntityManager manager, Estilos estiloPrincipal,Estilos estiloSecundario) throws SQLException, ORMException{
		try{
			OcurrenciasEstilos[] oe = manager.find(OcurrenciasEstilos.class,Query.select().where(" estiloprincipalid = '"+estiloPrincipal.getID()+"' and estilosecundarioid = '"+estiloSecundario.getID()+"' "));
			return oe[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("La ocurrencia de estilos '"+estiloPrincipal.getNombre()+"' - '"+estiloSecundario.getNombre()+"' , NO existe.");
		}
		
	}
	
	//################################################################################################################
	/**
	 * Selecciona todas las ocurrencias estiloPrincipal estiloSecundario en la base de datos.
	 * 
	 * @param manager
	 * @return
	 * @throws SQLException
	 * 
	 *///##############################################################################################################
	public static OcurrenciasEstilos[] seleccionarTodos(EntityManager manager) throws SQLException{
		
		OcurrenciasEstilos[] oe = manager.find(OcurrenciasEstilos.class);
		return oe;
	}

}
