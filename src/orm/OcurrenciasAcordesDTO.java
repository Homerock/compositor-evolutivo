package orm;

import java.sql.SQLException;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.Query;

public class OcurrenciasAcordesDTO {

	public static void Insertar (EntityManager manager, Acordes Ppal, Acordes Sec, int cant) throws SQLException   
	{
		OcurrenciasAcordes oa = manager.create(OcurrenciasAcordes.class);
		oa.setAcordePrincipal(Ppal);
		oa.setAcordeSecundario(Sec);
		oa.setCantidad(cant);
		oa.save();
	}
	
	public static void Eliminar (OcurrenciasAcordes c)
	{
		
	}
	
	public static void EliminarTabla ()
	{
			
	}

	public static void Actualizar (EntityManager manager, Acordes Ppal, Acordes Sec, int contador) throws SQLException
	{
		int cant;
		
		OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where("acordePrincipalID = '" + Ppal.getID() + "' and acordeSecundarioID = '" + Sec.getID() + "'"));
		cant = oa[0].getCantidad();
		oa[0].setCantidad(cant + contador);
		oa[0].save();
	}
	
	public static OcurrenciasAcordes[] seleccionarTodos(EntityManager manager) throws SQLException
	{
		
		OcurrenciasAcordes[] listaOcurrencias=null;
				
		listaOcurrencias = manager.find(OcurrenciasAcordes.class);
				
		return listaOcurrencias;
		
	}
	
	public static List listar (String id)
	{        
		
		List result = null;
	
		return result;
	}
	
	public static OcurrenciasAcordes buscar(String nombre)
	{
		
		return null;
		
	}
	
	public static boolean existe(EntityManager manager, Acordes Ppal, Acordes Sec) throws SQLException
	{
		
		boolean existe = false;
		
		OcurrenciasAcordes[] oa = manager.find(OcurrenciasAcordes.class, Query.select().where("acordePrincipalID = '" + Ppal.getID() + "' and acordeSecundarioID = '" + Sec.getID() + "'"));
		if (oa.length > 0) {
			existe = true;
		}
		
		return existe;
		
	}
	
}
