package nucleo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import canciones.Acorde;
import canciones.Cancion;
import canciones.Compas;
import canciones.Estrofa;

import orm.*;

import estructura.AcordesFila;
import estructura.EstilosFila;
import estructura.ListaValores;
import estructura.MatrizAcordes;
import estructura.MatrizEstilos;
import estructura.ValorAcordes;
import estructura.ValorEstilos;
import estructura.Valores;
import excepciones.ORMException;
import excepciones.PersistenciaException;

import net.java.ao.EntityManager;

public class Persistencia implements Runnable {
	
	private EntityManager manager;
	private boolean DEBUG = true;
	
	public Persistencia() throws PersistenciaException{
		Properties prop = new Properties();
		
		try {
			
			CodeSource codeSource = Persistencia.class.getProtectionDomain().getCodeSource();
			File jarFile;
			
			
			jarFile = new File(codeSource.getLocation().toURI().getPath());
			
			File jarDir = jarFile.getParentFile();
			File propFile = new File(jarDir, "config/conexion.properties");
			FileInputStream fis = new FileInputStream(propFile);
			prop.load(fis);
			
			
			//FileInputStream fis = new FileInputStream("conexion.properties");
			//prop.load(fis);

			String user  = prop.getProperty("user");
			String password = prop.getProperty("password");
			String cadenaConexion = prop.getProperty("cadena_conexion");	
	
			this.setManager( new EntityManager(cadenaConexion, user, password));
			
			fis.close();		
			
		} catch (URISyntaxException e) {
			System.err.println(e.getMessage());
		} catch(FileNotFoundException e){	
			throw new PersistenciaException("No existe el archivo de configuracion (recuerde ponerlo en el directorio config) - "+Persistencia.class);
		} catch(IOException e) {
			System.err.println(e.getMessage());
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
	public EntityManager getManager() {
		return manager;
	}

	private void setManager(EntityManager manager) {
		this.manager = manager;
	}

	//######################################################################################################
	private void ocurrenciasEstiloABaseDeDatos(EstilosFila estilosFila) throws PersistenciaException, ORMException{
		Iterator it2 = estilosFila.getMapEstilos().entrySet().iterator();
		ValorEstilos valorEstilo;
		Estilos estiloPpal;
		Estilos estiloSec;
		
		try {
		
			while (it2.hasNext()) {
				Map.Entry e2 = (Map.Entry)it2.next();
				valorEstilo = (ValorEstilos) e2.getValue();
				
				if(valorEstilo.isModificado()){
					estiloPpal = EstilosDTO.buscar(this.getManager(), estilosFila.getNombreEstilo());
					estiloSec = EstilosDTO.buscar(this.getManager(), e2.getKey().toString());
					if (OcurrenciasEstilosDTO.existe(this.getManager(), estiloPpal, estiloSec)) {
						OcurrenciasEstilosDTO.actualizar(this.getManager(), estiloPpal, estiloSec, valorEstilo.getValor());
					} else {
						OcurrenciasEstilosDTO.insertar(this.getManager(), estiloPpal, estiloSec, valorEstilo.getValor());
					}
				}
			}
		
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'ocurrenciasestilos' en la base de datos - "+e.getMessage());
		}
	}
	
	public void matrizEstilosABaseDeDatos(MatrizEstilos miMatrizEstilos) throws ORMException, PersistenciaException {

		Map<String, EstilosFila> mapEstilos = miMatrizEstilos.getMisEstilos();
		EstilosFila mapEstiloPpal;
		Iterator it = mapEstilos.entrySet().iterator();

		try {

			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				mapEstiloPpal= (EstilosFila) e.getValue();

				if (mapEstiloPpal.isModificado()){
					System.out.println(mapEstiloPpal.toString());
					if (EstilosDTO.existe(this.getManager(), mapEstiloPpal.getNombreEstilo())){
						EstilosDTO.actualizar(this.getManager(), 
								mapEstiloPpal.getNombreEstilo(),
								mapEstiloPpal.getCantUnCompas(),
								mapEstiloPpal.getCantDosCompases(),
								mapEstiloPpal.getCantCuatroCompases(),
								mapEstiloPpal.getCantOchoCompases(),
								mapEstiloPpal.getUnAcordeEnCompas(),
								mapEstiloPpal.getDosAcordesEnCompas(),
								mapEstiloPpal.getTresAcordesEnCompas(),
								mapEstiloPpal.getCuatroAcordesEnCompas()
								); // false porque no es estilo principal, esto se controla despues

					}else{
						EstilosDTO.insertar(this.getManager(), 
								mapEstiloPpal.getNombreEstilo(),
								mapEstiloPpal.getCantUnCompas(),
								mapEstiloPpal.getCantDosCompases(),
								mapEstiloPpal.getCantCuatroCompases(),
								mapEstiloPpal.getCantOchoCompases(),
								mapEstiloPpal.getUnAcordeEnCompas(),
								mapEstiloPpal.getDosAcordesEnCompas(),
								mapEstiloPpal.getTresAcordesEnCompas(),
								mapEstiloPpal.getCuatroAcordesEnCompas()
						);
					}	
				}
			}
		} catch (SQLException e1) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e1.getMessage());
		} 

		it = mapEstilos.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapEstiloPpal= (EstilosFila) e.getValue();
			this.ocurrenciasEstiloABaseDeDatos(mapEstiloPpal);
		}

	}

	
	/**
	 * actuliza todos los estilos principales
	 * precondicion : el estilo debe existir.
	 * 
	 * @param miListaDeEstilosPrincipales
	 * @throws ORMException
	 * @throws PersistenciaException
	 */
	public void listaDeEstilosPrincipalesABaseDeDatos(ListaValores miListaDeEstilosPrincipales) throws ORMException, PersistenciaException {
		
		ArrayList<Valores> listaValores = miListaDeEstilosPrincipales.getLista();
		
		int principal = 1;
		try {
			for (Valores va : listaValores) {
				if(va.isModificado()){
					if( DEBUG ){
						System.out.println("Actualizo Estilo principal "+va.getEstilo());
					}
					EstilosDTO.actualizar(this.getManager(),va.getEstilo(),principal);
				}	
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
		} 
		
	}
		
	
	/**
	 * 
	 * @param miListaDeTempos
	 * @throws ORMException
	 * @throws PersistenciaException
	 */
	public void listaTemposABaseDeDatos(ListaValores miListaDeTempos)throws ORMException, PersistenciaException {
		
		ArrayList<Valores> listaValores = miListaDeTempos.getLista();
		
		try {
			for (Valores va : listaValores) {
				if(va.isModificado()){
					// si fue modificado
					Estilos estilo = EstilosDTO.buscar(this.getManager(), va.getEstilo());
					
					if(TemposDTO.existe(this.getManager(), va.getValor(), estilo)){
						TemposDTO.actualizar(this.getManager(), va.getValor(), estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Actualizo Tempo "+va.getValor()+" - Estilo "+va.getEstilo());
						}
						
					}else{
						TemposDTO.insertar(this.getManager(), va.getValor(), estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Inserto Tempo "+va.getValor()+" - Estilo "+va.getEstilo());
						}
					}
				}	
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'tempo' en la base de datos - "+e.getMessage());
		} 
		
	}


	
	public void listaDeDuracionesABaseDeDatos(ListaValores miListaDeDuraciones)throws ORMException, PersistenciaException {
		
		ArrayList<Valores> listaValores = miListaDeDuraciones.getLista();
		
		try {
			for (Valores va : listaValores) {
				if(va.isModificado()){
					// si fue modificado
					Estilos estilo = EstilosDTO.buscar(this.getManager(), va.getEstilo());
					
					if(DuracionDTO.existe(this.getManager(), Integer.parseInt(va.getValor()), estilo)){
						DuracionDTO.actualizar(this.getManager(), Integer.parseInt(va.getValor()), estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Actualizo Duracion "+va.getValor()+" - Estilo "+va.getEstilo());
						}
						
					}else{
						DuracionDTO.insertar(this.getManager(), Integer.parseInt(va.getValor()), estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Inserto Duracion "+va.getValor()+" - Estilo "+va.getEstilo());
						}
					}
				}	
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'duracion' en la base de datos - "+e.getMessage());
		} 
		
	}

	/**
	 * Precondicion: Los acordes deben estar cargados
	 * @param miListaDeTonicas
	 * @throws ORMException
	 * @throws PersistenciaException
	 */
	public void listaDeTonicasABaseDeDatos(ListaValores miListaDeTonicas) throws ORMException, PersistenciaException {
		
		ArrayList<Valores> listaValores = miListaDeTonicas.getLista();
		
		try {
			for (Valores va : listaValores) {
				if(va.isModificado()){
					// si fue modificado
					Estilos estilo = EstilosDTO.buscar(this.getManager(), va.getEstilo());
					Acordes acorde = AcordesDTO.buscar(this.getManager(), va.getValor());
					
					if(TonicasDTO.existe(this.getManager(), acorde, estilo)){
						TonicasDTO.actualizar(this.getManager(), acorde, estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Actualizo Tonica "+va.getValor()+" - Estilo "+va.getEstilo());
						}
						
					}else{
						TonicasDTO.insertar(this.getManager(), acorde, estilo, va.getCantidad());
						
						if( DEBUG ){
							System.out.println("Inserto Tonica "+va.getValor()+" - Estilo "+va.getEstilo());
						}
					}
				}	
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'tonicas' en la base de datos - "+e.getMessage());
		} 	
	}

	/**
	 * 
	 * @param matrizEvolutiva
	 * @throws ORMException 
	 * @throws PersistenciaException 
	 */
	public void matrizAcordesABaseDeDatos(Map<String, MatrizAcordes> matrizEvolutiva) throws PersistenciaException, ORMException {
		
		Iterator it = matrizEvolutiva.entrySet().iterator();
		MatrizAcordes miMatriz;
		
		//tengo que iterar para listar todas la matrices del map
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			String estiloPpal = (String) e.getKey();
			miMatriz = (MatrizAcordes) e.getValue();
			acordesABaseDeDatos(miMatriz, estiloPpal);
		}
	}
	
	private void acordesABaseDeDatos(MatrizAcordes miMatriz, String estiloPpal) throws PersistenciaException, ORMException{
		
		Iterator it = miMatriz.getMisAcordes().entrySet().iterator();
		AcordesFila miAcordeFila;
		
		try {
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				miAcordeFila = (AcordesFila) e.getValue();
				
				if (miAcordeFila.isModificado()){
					System.out.println(miAcordeFila.toString());
					if (AcordesDTO.existe(this.getManager(), miAcordeFila.getNombreAcorde())){
						AcordesDTO.actualizar(this.getManager(), miAcordeFila.getNombreAcorde(), miAcordeFila.getValorAcumuladoFila());

					}else{
						AcordesDTO.insertar(this.getManager(), miAcordeFila.getNombreAcorde(),miAcordeFila.getValorAcumuladoFila());
					}	
				}
			}
		
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'ocurrenciasestilos' en la base de datos - "+e.getMessage());
		}
		
		
		
		it = miMatriz.getMisAcordes().entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miAcordeFila = (AcordesFila) e.getValue();
			this.ocurrenciasAcordesABaseDeDatos(miAcordeFila,estiloPpal);	// getKey es el estilo de la matriz
		}
		
	}

	/**
	 * 
	 * @param miAcordeFila
	 * @throws PersistenciaException
	 * @throws ORMException
	 */
	private void ocurrenciasAcordesABaseDeDatos(AcordesFila miAcordeFila, String estiloPpal) 
	throws PersistenciaException, ORMException{
		
		ArrayList<ValorAcordes> ocurrencias = miAcordeFila.getListaOcurrencias();
		Acordes acordePpal;
		Acordes acordeSec;
		Estilos estilo;
		
		try {
		
			for (ValorAcordes oa : ocurrencias) {
				if(oa.isModificado()){
					acordePpal = AcordesDTO.buscar(this.getManager(), miAcordeFila.getNombreAcorde());
					acordeSec = AcordesDTO.buscar(this.getManager(), oa.getAcordeSecundario());
					estilo = EstilosDTO.buscar(this.getManager(), estiloPpal);
					if (OcurrenciasAcordesDTO.existe(this.getManager(), acordePpal, acordeSec, estilo)) {
						OcurrenciasAcordesDTO.actualizar(this.getManager(), acordePpal, acordeSec, estilo, oa.getValor());
					} else {
						OcurrenciasAcordesDTO.insertar(this.getManager(), acordePpal, acordeSec, estilo, oa.getValor());
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'ocurrenciasestilos' en la base de datos - "+e.getMessage());
		}
	}
	
	public void cancionNuevaABaseDeDatos(canciones.Cancion cancion){
		
		try {
			
			CancionDTO.insertar(manager, cancion.getNombreFantasia(), cancion.getTempo(), cancion.getEstiloPrincipal(), cancion.getTonica().getNombre(), cancion.getComentario(),cancion.getDuracion());
			
			orm.Cancion cancionInsertada = CancionDTO.buscarUltima(manager);
			
			ArrayList<Estrofa> estrofas = cancion.getEstrofas();
			
			for (int i =0 ;i<estrofas.size();i++){
				
				
				ArrayList<Compas> compases = estrofas.get(i).getListaDeCompases();
				for (int j=0;j<compases.size() ;j++){
					
					ArrayList<Acorde> acordes = compases.get(j).getAcordes();
					for (int k =0;k<acordes.size();k++){
						
						
						CancionAcordesDTO.insertar(manager, cancionInsertada, estrofas.get(i).getEstilo(), acordes.get(k).getNombre(), i+1, j+1, k+1);
						
					}
					
					
				}
				
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (ORMException e) {
			System.err.println(e.getMessage());
		}
		
	}
	    
	
	
	public void cancionesAMemoria(Map<String, Cancion> listaCanciones) throws PersistenciaException{
		
		try {
			orm.Cancion[] canciones =  CancionDTO.seleccionarTodos(manager);
			canciones.Cancion cancionMemoria;
			
			for(int i=0; i<canciones.length;i++){
		
				orm.Cancion cancionDB = canciones[i];
				//String [] campos = cancionDB.getFechaCreacion().split("\\-");  
				//String fecha = campos[2]+'/'+campos[1]+'/'+campos[0];
				
				 cancionMemoria = 
					new Cancion(cancionDB.getNombre(),
							cancionDB.getTempo(),
							cancionDB.getDuracion(),
							new canciones.Acorde(cancionDB.getTonica()),
							cancionDB.getEstiloPrincipal(),
							cancionDB.getComentario(),
							cancionDB.getFechaCreacion()
						);
				//guardo cancion sin estrofas
				listaCanciones.put(String.valueOf(cancionDB.getID()),cancionMemoria);
			}
			
			Iterator it = listaCanciones.keySet().iterator();
			
			while(it.hasNext()) {
			    String clave = (String) it.next(); 
			    canciones.Cancion  cancionActual = listaCanciones.get(clave);
			    cancionAcordesAMemoria(cancionActual, Integer.parseInt(clave));
			    cancionActual.actualizarContadores();// actualizo todos los contadores de la cancion
			    
			}			
			
			
		} catch (SQLException e) {
			throw new PersistenciaException("Error al acceder a 'estilos' en la base de datos - "+e.getMessage());
			
		}
		
	}
	// es llamado x cancionesAMemoria
	/**
	 * guarda los acordes de una cancion en el objeto, 
	 * primero debe haberse traido la cancion de base de datos
	 * @param cancion
	 * @param idCancion
	 * @throws PersistenciaException
	 */
	private void cancionAcordesAMemoria( canciones.Cancion cancion, int idCancion) throws PersistenciaException{
		
		try {
			orm.CancionAcordes[] cancion_acordes = CancionAcordesDTO.seleccionarTodosPorIDCancion(manager, idCancion);
			
			for(CancionAcordes ac : cancion_acordes){
				Estrofa estrofa =null ;
				Compas compas =null;
				try{
					
					estrofa = cancion.getEstrofaPorNumero(ac.getNumeroEstrofa());
					try{
						compas = estrofa.getCompasPorNumero(ac.getNumeroCompas());
						
						compas.agregarAcorde(new canciones.Acorde(ac.getAcorde()));
						
					}catch(java.lang.IndexOutOfBoundsException e2){
						compas = new canciones.Compas();
						
						compas.agregarAcorde(new canciones.Acorde(ac.getAcorde()));
						
						estrofa.agregarCompas(compas);
					}
					
				}catch(java.lang.IndexOutOfBoundsException e1){
					estrofa = new canciones.Estrofa(ac.getNumeroEstrofa(),ac.getEstiloEstrofa());
					
					compas = new canciones.Compas();
					
					compas.agregarAcorde(new canciones.Acorde(ac.getAcorde()));
					
					estrofa.agregarCompas(compas);
					cancion.agregarEstrofa(estrofa);	
				}
			}
		} catch (SQLException sql_e) {
			
			throw new PersistenciaException(" No se pudieron obtener los acordes de la cancion "+sql_e.getMessage());
		}	
	}


	public void run() {
		// TODO Auto-generated method stub
		
	}

}
