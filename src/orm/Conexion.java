package orm;

import net.java.ao.*;

public class Conexion {
	
	private static final String CADENA_CONEXION_SQL = "jdbc:mysql://localhost/compositordb";
	private static final String USUARIO_CONEXION_SQL = "root";
	private static final String CLAVE_CONEXION_SQL = "root"; 
	
	
	private static final String CADENA_CONEXION_PG = "jdbc:postgresql://localhost/compositordb"; 
	private static final String USUARIO_CONEXION_PG = "compositordb";
	private static final String CLAVE_CONEXION_PG = "hendrix"; 

	public static EntityManager getConexionMysql() {
		EntityManager manager = new EntityManager(CADENA_CONEXION_SQL, USUARIO_CONEXION_SQL, CLAVE_CONEXION_SQL);
		return manager;
	}
	
	
	public static EntityManager getConexionPsql() {
		EntityManager manager = new EntityManager(CADENA_CONEXION_PG, USUARIO_CONEXION_PG, CLAVE_CONEXION_PG);
		return manager;
	}
	
	
	
}
