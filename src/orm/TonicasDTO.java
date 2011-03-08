package orm;

import java.util.List;
import java.sql.SQLException;
import net.java.ao.EntityManager;
import net.java.ao.Query;


public class TonicasDTO {

	public static void Insertar (Tonicas c)   
	{
		
	}
	
	public static void Eliminar (Tonicas c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (Tonicas c)
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
	
	public static Tonicas buscar(EntityManager manager, Acordes acorde, Estilos estilo) throws SQLException
	{
		
		Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where(" acordeid = '"+acorde.getID()+"'  and estilosid = '"+estilo.getID()+"' "));
		
		return t1[0];
		
	}
	
	public static boolean existe(EntityManager manager, Acordes acorde, Estilos estilo) throws SQLException
	{	
		boolean existe = false;
		
		Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where(" acordeid = '"+acorde.getID()+"'  and estilosid = '"+estilo.getID()+"' "));
		if(t1.length > 0){
			existe =true;
		}
		return existe;
		
	}
	
}
