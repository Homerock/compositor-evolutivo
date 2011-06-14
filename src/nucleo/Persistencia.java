package nucleo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import orm.*;

import estructura.ListaValores;
import excepciones.PersistenciaException;

import net.java.ao.EntityManager;

public class Persistencia {
	
	private EntityManager manager;

	public Persistencia() throws PersistenciaException{
		Properties prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream("conexion.properties");
			prop.load(fis);

			String user  = prop.getProperty("user");
			String password = prop.getProperty("password");
			String cadenaConexion = prop.getProperty("cadena_conexion");	
	
			this.setManager( new EntityManager(cadenaConexion, user, password));
			
			fis.close();		
			
		}catch(FileNotFoundException e){	
			throw new PersistenciaException("No existe el archivo de configuracion - "+Persistencia.class);
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}

	//######################################################################################################
		
	public void tonicasAMemoria(ListaValores miListaDeTonicas) throws PersistenciaException{
		
		try {
			
			Tonicas[] tonicas = TonicasDTO.seleccionarTodos(this.getManager());
			for (Tonicas t : tonicas ){
				miListaDeTonicas.agregarValor(t.getAcorde().getNombre(), t.getCantidad(), t.getEstilos().getNombre());
			}			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a tonicas en la base de datos - "+e.getMessage());
		}
	} 
	//######################################################################################################
	
	
	public void temposAMemoria(ListaValores miListaDeTempos) throws PersistenciaException{
		try {
			Tempos[] tempos = TemposDTO.seleccionarTodos(this.getManager());
			for (Tempos t : tempos ){
				miListaDeTempos.agregarValor(t.getTempo(), t.getCantidad(), t.getEstilos().getNombre());
			}			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a tempos en la base de datos - "+e.getMessage());
		}
	} 
	
	
	//######################################################################################################

	public void duracionAMemoria(ListaValores miListaDeDuraciones) throws PersistenciaException{
		try {
			Duracion[] duraciones = DuracionDTO.seleccionarTodos(this.getManager());
			for (Duracion t : duraciones ){
				miListaDeDuraciones.agregarValor(String.valueOf(t.getDuracion()), t.getCantidad(), t.getEstilos().getNombre());
			}			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'duracion' en la base de datos - "+e.getMessage());
		}
	} 
	
	//######################################################################################################

	
	public void estilosPrincipalesAMemoria(ListaValores miListaDeEstilosPrincipales) throws PersistenciaException{
		try {
			Estilos[] estilos = EstilosDTO.seleccionarTodosPrincipales(this.getManager());
			for (Estilos e : estilos ){
				miListaDeEstilosPrincipales.agregarValor(e.getNombre());
			}			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
		}
	} 
	
	
	
	
	
	
	private EntityManager getManager() {
		return manager;
	}

	private void setManager(EntityManager manager) {
		this.manager = manager;
	}


}
