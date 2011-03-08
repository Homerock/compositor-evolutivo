package orm;

import java.sql.SQLException;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.Query;

public class TemposDTO {

	public static void Insertar (Tempos c)   
	{
		
	}
	
	public static void Eliminar (Tempos c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (Tempos c)
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
	
	public static Tempos buscar(EntityManager manager,int tempo,Estilos estilo) throws SQLException
	{
		Tempos[] tempos = manager.find(Tempos.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
		return tempos[0];
		
	}
	
	public static boolean existe(EntityManager manager,int tempo,Estilos estilo) throws SQLException
	{	
		boolean existe = false;
		Tempos[] tempos = manager.find(Tempos.class,Query.select().where(" estilosid = '"+estilo.getID()+"' and tempo = '"+tempo+"' "));
		
		if(tempos.length > 0){
			existe =true;
		}

		return existe;
	}
	
}
