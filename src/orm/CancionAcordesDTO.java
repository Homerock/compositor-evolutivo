package orm;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.java.ao.EntityManager;
import net.java.ao.Query;

import org.postgresql.util.PSQLException;

import excepciones.ORMException;

public class CancionAcordesDTO {

	
	public static void insertar(
			EntityManager manager, 
			orm.Cancion cancion,
			String estiloEstrofa,
			String acorde,
			int numeroEstrofa,
			int numeroCompas,
			int numeroAcorde
			) throws SQLException, ORMException {

		Map <String,Object> parametros = new HashMap<String, Object>();
		try{
			
			parametros.put("cancionid", cancion.getID());
			parametros.put("estiloestrofa", estiloEstrofa);
			parametros.put("acorde", acorde);
			parametros.put("numeroEstrofa", numeroEstrofa);
			parametros.put("numeroCompas",numeroCompas);
			parametros.put("numeroAcorde",numeroAcorde);
			
			manager.create(CancionAcordes.class, parametros);
			
		}catch(PSQLException e){
			throw new ORMException("el acorde "+acorde+" para la cancion , ya existe.");
		}
		return;
	}
	
	
	public static orm.CancionAcordes[] seleccionarTodos(EntityManager manager) throws SQLException {
		orm.CancionAcordes[] cancion_acordes= null;
		cancion_acordes= manager.find(CancionAcordes.class);
		return cancion_acordes;
	}

	
	public static orm.CancionAcordes[] seleccionarTodosPorIDCancion(EntityManager manager,int idCancion) throws SQLException {
		orm.CancionAcordes[] cancion_acordes= manager.find(CancionAcordes.class,Query.select().where("cancionid ="+idCancion+" order by numeroestrofa,numerocompas,numeroacorde "));
		return cancion_acordes;
	}
	
}
