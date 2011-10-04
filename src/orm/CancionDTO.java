package orm;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.java.ao.EntityManager;
import net.java.ao.Query;

import org.postgresql.util.PSQLException;
import java.sql.Date;
import excepciones.ORMException;

public class CancionDTO {

	
	public static void insertar(
			EntityManager manager, 
			String nombre,
			String tempo,
			String estiloPrincipal,
			String tonica,
			String comentario,
			int duracion
			) throws SQLException, ORMException {
		
		boolean error=false;
		String msgError="";
		if(nombre == null){
			error = true;
			msgError = "nombre.";
		}
		if(comentario ==null){
			comentario = "";
		}
		if (tonica ==null){
			error = true;
			msgError = "tonica.";
		}
		if(estiloPrincipal==null){
			error = true;
			msgError = "estilo principal.";
		}	
		
		if (error)
			throw new ORMException("Faltan datos en la cancion, falta :"+msgError);
		
		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			parametros.put("nombre", nombre);
			parametros.put("tempo", tempo);
			parametros.put("duracion", duracion);
			parametros.put("estiloprincipal", estiloPrincipal);
			parametros.put("tonica", tonica);
			parametros.put("comentario",comentario);
			
			manager.create(Cancion.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("No se pudo grabar la cancion "+nombre + " en la base de datos");
		}
		return;
	}
	/**
	 * busca la ultima cancion insertada en la base de datos.
	 *  
	 * @param manager
	 * @return
	 * @throws SQLException
	 * @throws ORMException
	 */
	public static orm.Cancion buscarUltima(EntityManager manager) throws SQLException, ORMException{
		try{
			Cancion[] c = manager.find(Cancion.class, Query.select().where("id = (SELECT  max(id) from cancion)"));
			return c[0];
			
		}catch(ArrayIndexOutOfBoundsException e){
			throw new ORMException("No existe Ninguna Cancion en la base de datos.");
		}
	}
	
	public static orm.Cancion[] seleccionarTodos(EntityManager manager) throws SQLException {
		orm.Cancion[] canciones= null;
		canciones= manager.find(Cancion.class);
		return canciones;
	}
	
	
	
}
