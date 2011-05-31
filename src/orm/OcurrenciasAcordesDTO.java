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
public class OcurrenciasAcordesDTO {
	
	//################################################################################################################
	/**
	 * Inserta la ocurrencia de acordePrincipal ,acordeSecundario , estilo con una cantidad determinada.
	 * No se puede repetir  la ocurrencia de acordePrincipal ,acordeSecundario , para un estilo.
	 *  
	 * @param manager
	 * @param acordePrincipal
	 * @param acordeSecundario
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void insertar (EntityManager manager, Acordes acordePrincipal, Acordes acordeSecundario,  Estilos estilo,int cant) throws SQLException, ORMException   {
		try{
			Map <String,Object> parametros = new HashMap<String, Object>();
			parametros.put("acordeprincipalid", acordePrincipal.getID());
			parametros.put("acordesecundarioid", acordeSecundario.getID());
			parametros.put("cantidad", cant);
			parametros.put("estilosid", estilo.getID());
			manager.create(OcurrenciasAcordes.class, parametros);
			
			return;
			
		}catch(PSQLException e){
			throw new ORMException("La ocurrencia de acordes a Insertar '"+acordePrincipal.getNombre()+"' - '"+acordeSecundario.getNombre()+"' con el estilo "+estilo.getNombre()+" , ya existe.");
			
		}
		
		
	}
	
	//################################################################################################################
	/**
	 *Elimina la ocurrencia de acordePrincipal ,acordeSecundario , para un estilo.
	 * 
	 * @param manager
	 * @param acordePrincipal
	 * @param acordeSecundario
	 * @param estilo
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static void eliminar (EntityManager manager, Acordes acordePrincipal, Acordes acordeSecundario,  Estilos estilo) throws SQLException {
		
		OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where(
								"  acordeprincipalid = '" + acordePrincipal.getID() +
								"' and acordesecundarioid = '"+ acordeSecundario.getID() +
								"' and estilosid  = '"+estilo.getID()+"' "));
		manager.delete(oa);
		
		return;
	
	}
	
	//################################################################################################################
	/**
	 * Actualiza la cantida para la ocurrencia de acordePrincipal ,acordeSecundario , estilo.
	 * 
	 * @param manager
	 * @param acordePrincipal
	 * @param acordeSecundario
	 * @param estilo
	 * @param cant
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static void actualizar (EntityManager manager, Acordes acordePrincipal, Acordes acordeSecundario,  Estilos estilo,int cant) throws SQLException, ORMException {
		
		try{
			OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where(
									"  acordeprincipalid = '" + acordePrincipal.getID() +
									"' and acordesecundarioid = '"+ acordeSecundario.getID() +
									"' and estilosid  = '"+estilo.getID()+"' "));
			oa[0].setCantidad(cant);
			oa[0].save();
			return;
		}catch(Exception e){
			throw new ORMException("La ocurrencia de acordes a actualizar '"+acordePrincipal.getNombre()+"' - '"+acordeSecundario.getNombre()+"' con el estilo "+estilo.getNombre()+" , NO existe.");
		}
	}
	
	//################################################################################################################
	/**
	 * Verifica si existe la ocurrencia de acordePrincipal ,acordeSecundario , estilo.
	 * 
	 * @param manager
	 * @param acordePrincipal
	 * @param acordeSecundario
	 * @param estilo
	 * @return
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 */	//################################################################################################################
	public static boolean existe(EntityManager manager,
			Acordes acordePrincipal, 
			Acordes acordeSecundario,  
			Estilos estilo) throws SQLException, ORMException {
	
		boolean existe = false;
		
		OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where(
													"  acordeprincipalid = '" + acordePrincipal.getID() +
													"' and acordesecundarioid = '"+ acordeSecundario.getID() +
													"' and estilosid  = '"+estilo.getID()+"' "));
		if (oa.length > 0) {
			existe = true;
		}
		return existe;
	}
	
	//################################################################################################################
	/**
	 * Busca la ocurrencia de acordePrincipal ,acordeSecundario , estilo.
	 * 
	 * @param manager
	 * @param acordePrincipal
	 * @param acordeSecundario
	 * @param estilo
	 * @return OcurrenciasAcordes o ORMException si no existe. 
	 * @throws SQLException
	 * @throws ORMException
	 * 
	 *///################################################################################################################
	public static OcurrenciasAcordes buscar(EntityManager manager,
			Acordes acordePrincipal, 
			Acordes acordeSecundario,  
			Estilos estilo) throws SQLException, ORMException {

		try{
		
			OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where(
					"  acordeprincipalid = '" + acordePrincipal.getID() +
					"' and acordesecundarioid = '"+ acordeSecundario.getID() +
					"' and estilosid  = '"+estilo.getID()+"' "));
			
			return oa[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe la ocurrencia de acordes '"+acordePrincipal.getNombre()+"' - '"+acordeSecundario.getNombre()+"' con el estilo "+estilo.getNombre()+" .");
		}
		
	}
	
	//################################################################################################################
	/**
	 * selecciona todas las ocurrencias acordes
	 * 
	 * @param manager
	 * @return
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static OcurrenciasAcordes[] seleccionarTodos(EntityManager manager) throws SQLException{
		OcurrenciasAcordes[] listaOcurrencias = manager.find(OcurrenciasAcordes.class);
		return listaOcurrencias;
	}
	//################################################################################################################
	/**
	 * selecciona todas las ocurrencias acordes de un estilo .
	 * 
	 * @param manager
	 * @return
	 * @throws SQLException
	 * 
	 *///################################################################################################################
	public static OcurrenciasAcordes[] seleccionarTodos(EntityManager manager,Estilos estilo) throws SQLException{
		
		OcurrenciasAcordes[] listaOcurrencias = manager.find(OcurrenciasAcordes.class, Query.select().where("  estilosid  = '"+estilo.getID()+"' "));
		return listaOcurrencias;
	}
	
	
}
