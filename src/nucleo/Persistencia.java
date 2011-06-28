package nucleo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import orm.*;

import estructura.EstilosFila;
import estructura.ListaValores;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
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
				miListaDeTonicas.agregarValor(
						t.getAcorde().getNombre(), 
						t.getCantidad(), 
						t.getEstilos().getNombre(),
						false);
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
				miListaDeTempos.agregarValor(
						t.getTempo(),
						t.getCantidad(), 
						t.getEstilos().getNombre(),
						false);
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
				miListaDeDuraciones.agregarValor(
						String.valueOf(t.getDuracion()),
						t.getCantidad(), 
						t.getEstilos().getNombre(),
						false);
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
				miListaDeEstilosPrincipales.agregarValor(e.getNombre(),false);
			}			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
		}
	} 
	//######################################################################################################

	/**
	 * guarda todos los estilos de la base de datos en la matriz, 
	 * pero no las ocurrencias.
	 * 
	 * @param miMatrizEstilos
	 * @throws PersistenciaException
	 */
	public void estilosAMemoria(MatrizEstilos miMatrizEstilos) throws PersistenciaException{
		
		try {
			Estilos[] estilos = EstilosDTO.seleccionarTodos(this.getManager());
			for(Estilos e: estilos){
	
				EstilosFila estiloFila = new EstilosFila(
						e.getNombre(),
						e.getCantUnCompas(),
						e.getCantDosCompases(),
						e.getCantCuatroCompases(),
						e.getCantOchoCompases(),
						e.getUnAcordeEnCompas(),
						e.getDosAcordesEnCompas(),
						e.getTresAcordesEnCompas(),
						e.getCuatroAcordesEnCompas(),
						false);// dice si fue modificado 
				
				miMatrizEstilos.agregarEstilo(e.getNombre(), estiloFila);
			}
			
			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
			
		}
		
	}

	//######################################################################################################
	// primero deben guardarse los estilos y despues las ocurrencias
	/**
	 * guarda las ocurrencias de los estilos.
	 * 
	 * @param miMatrizEstilos
	 * @throws PersistenciaException
	 */
	public void ocurrenciasEstilosAMemoria(MatrizEstilos miMatrizEstilos) throws PersistenciaException {
		try{	
			OcurrenciasEstilos[] ocurrenciasEstilos = OcurrenciasEstilosDTO.seleccionarTodos(this.getManager());
			for(OcurrenciasEstilos o : ocurrenciasEstilos){
				
				miMatrizEstilos.agregaOcurrenciaEstilo(
						o.getEstiloPrincipal().getNombre(), 
						o.getEstiloSecundario().getNombre(),
						o.getCantidad(),
						false);
				
			}
		
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
		}
		
	}
	
	
	//######################################################################################################

	public void ocurrenciasAcordesAMemoria(Map<String, MatrizAcordes> miMatrizEvolutiva) throws PersistenciaException{
		
		boolean modificado = false;
		MatrizAcordes miMatrizAcordes;
		try {
			OcurrenciasAcordes[] ocurrenciasAcordes = OcurrenciasAcordesDTO.seleccionarTodos(this.getManager());
			for (OcurrenciasAcordes o: ocurrenciasAcordes){
				
				miMatrizAcordes = miMatrizEvolutiva.get(o.getEstilos().getNombre());
				if (miMatrizAcordes == null) {
					miMatrizAcordes = new MatrizAcordes();
					miMatrizEvolutiva.put(o.getEstilos().getNombre(), miMatrizAcordes);
				}
				
				// guardamos el acorde ppal si no existe
				if (!miMatrizAcordes.ExisteAcordePpal(o.getAcordePrincipal().getNombre())) {
					miMatrizAcordes.agregarAcordePrincipal(o.getAcordePrincipal().getNombre(),modificado);
				} 
				
				// guardamos la ocurrencia
				miMatrizAcordes.agregaOcurrenciaAcordeSecundario(o.getAcordePrincipal().getNombre(), o.getAcordeSecundario().getNombre(),o.getCantidad(),modificado);	
				
			}
			
			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'ocurrenciasacordes' en la base de datos - "+e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	
	//######################################################################################################
	private EntityManager getManager() {
		return manager;
	}

	private void setManager(EntityManager manager) {
		this.manager = manager;
	}


}
