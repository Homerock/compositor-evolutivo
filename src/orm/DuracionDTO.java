package orm;

import java.sql.SQLException;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.Query;

public class DuracionDTO {

	public static void Insertar (Duracion c)   
	{
		
	}
	
	public static void Eliminar (Duracion c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (Duracion c)
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
	
	public static Duracion buscar(EntityManager manager, int duracion,Estilos estilo) throws SQLException
	{
		Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
		return d[0];
		
	}
	
	public static boolean existe(EntityManager manager, int duracion,Estilos estilo) throws SQLException
	{
		boolean existe = false;
		Duracion[] d = manager.find(Duracion.class,Query.select().where(" duracion = '"+duracion+"' and estilosid = '"+estilo.getID()+"'  "));
		
		if(d.length >0){
			existe = true;
		}
		return existe;
	}
	
}
