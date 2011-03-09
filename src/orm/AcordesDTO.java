package orm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.ao.*;

public class AcordesDTO {
	
	public static void Insertar(EntityManager manager, String nombre, int contador) throws SQLException {
	
	
		Map <String,Object> parametros = new HashMap<String, Object>();
		parametros.put("nombre", nombre);
		parametros.put("cantapariciones", contador);
		
		Acordes ac = manager.create(Acordes.class, parametros);
		
	}
	
	public static void Insertar (Acordes c)   
	{
		
	}
	
	public static void Eliminar (Acordes c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}
	
	public static void Actualizar (EntityManager manager, String nombre, int contador) throws SQLException
	{
		int cant;
		
		Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + nombre + "'"));
		cant = ac[0].getCantApariciones();
		ac[0].setCantApariciones(cant + contador);
		ac[0].save();
	}
	
	/**
	 * retorna todos los acordes de la base (select * from Acordes) 
	 * 
	 * @return Lista con todos los acordes
	 * @throws SQLException 
	 */
	public static Acordes[] seleccionarTodos(EntityManager manager) throws SQLException
	{
		
		
		Acordes[] acordes = null;
		
		acordes = manager.find(Acordes.class);
		
		return acordes;
	}
	
	public static List listar (String id)
	{        
		
		List result = null;
	
		return result;
	}
	
	public static Acordes buscar(EntityManager manager, String nombre) throws SQLException
	{
		Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + nombre + "'"));
		return ac[0];
		
	}
	
	public static boolean existe(EntityManager manager, String nombre) throws SQLException
	{
		boolean existe = false;
		
		
			Acordes[] ac = manager.find(Acordes.class, Query.select().where("nombre like '" + nombre + "'"));
			if (ac.length > 0) {
				existe = true;
			}
		
		return existe;
	}

	
	
}
