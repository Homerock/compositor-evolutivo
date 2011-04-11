package orm;

import java.sql.SQLException;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.Query;

public class OcurrenciasEstilosDTO {

	public static void Insertar (OcurrenciasEstilos c)   
	{
		
	}
	
	public static void Eliminar (OcurrenciasEstilos c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (OcurrenciasEstilos c)
	{
		
	}
	
	public static List listar ()
	{
		
		List result = null;
	
		return result;
	}
	
	public static List listar (String id)
	{        
		
		List result = null;
	
		return result;
	}
	
	public static OcurrenciasEstilos buscar(EntityManager manager, Estilos estiloPpal, Estilos estiloSec) throws SQLException
	{
		OcurrenciasEstilos[] oe = manager.find(OcurrenciasEstilos.class,Query.select().where(" estiloprincipalid = '"+estiloPpal.getID()+"' and estilosecundarioid = '"+estiloSec.getID()+"' "));
		return oe[0];
		
	}
	
	public static boolean existe(EntityManager manager, Estilos estiloPpal, Estilos estiloSec) throws SQLException
	{	
		
		boolean existe = false;
		OcurrenciasEstilos[] oe = manager.find(OcurrenciasEstilos.class,Query.select().where(" estiloprincipalid = '"+estiloPpal.getID()+"' and estilosecundarioid = '"+estiloSec.getID()+"' "));
		if(oe.length>0){
			existe = true;
		}
		
		return existe;
		
	}
	
}
