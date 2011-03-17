package orm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import net.java.ao.EntityManager;
import net.java.ao.Query;


public class TonicasDTO {

	public static void Insertar (EntityManager manager, String nombre, int contador) throws SQLException
	{
		Map <String,Object> parametros = new HashMap<String, Object>();
		parametros.put("acordeid", nombre);
		parametros.put("cantidad", contador);
		
		Tonicas to = manager.create(Tonicas.class, parametros);
	}
	
	public static void Eliminar (Tonicas c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (EntityManager manager, Acordes acorde, int contador) throws SQLException
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
	
	public static Tonicas[] seleccionarTodos(EntityManager manager) throws SQLException
	{
		
		Tonicas[] listaTonicas = null;
				
		listaTonicas = manager.find(Tonicas.class);
				
		return listaTonicas;
		
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
	
	public static boolean existe(EntityManager manager, Acordes acorde) throws SQLException
	{	
		boolean existe = false;
		
		Tonicas[] t1 = manager.find(Tonicas.class,Query.select().where(" acordeid = '"+acorde.getID())+"' ");
		if(t1.length > 0){
			existe =true;
		}
		return existe;
		
	}
	
}
