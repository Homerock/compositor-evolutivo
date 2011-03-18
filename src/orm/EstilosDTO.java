package orm;

import java.sql.SQLException;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.Query;

public class EstilosDTO {

	public static void Insertar (Estilos c)   
	{
		
	}
	
	public static void Eliminar (Estilos c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (Estilos c)
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
	
	public static Estilos buscar(EntityManager manager,String nombreEstilo) throws SQLException
	{
		Estilos[] e1 = manager.find(Estilos.class,Query.select().where("nombre like '"+nombreEstilo+"' "));
		
		return e1[0];
		
	}
	
	public static boolean existe(EntityManager manager,String NombreEstilo) throws SQLException
	{
		boolean existe = false;
		
		Estilos[] e1 = manager.find(Estilos.class,Query.select(" nombre like '"+NombreEstilo+"' "));
		if (e1.length > 0){
			existe=true;
		}
		
		return existe;
	}
	
}
