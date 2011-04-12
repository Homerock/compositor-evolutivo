package archivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import estructura.EstilosFila;
import estructura.MatrizEstilos;
import excepciones.EstilosException;

/**
 * Esta clase se encarga del manejo de los estilos 
 * 
 * @author yamil gomez , sebastian pazos
 *
 */
public class Estilos {

/*################################################################################################################
 ###################						VARIABLES	y CONSTANTES					########################## 
 ################################################################################################################# */

	private static Map<String,Integer>  misEstilos = new HashMap<String, Integer>();
	private static ArrayList<String> misEstilosOrdenados = new ArrayList<String>();
	private static boolean DEBUG = false;


/*################################################################################################################
 ###################				METODOS ESTATICOS PRIVADOS						############################## 
 ################################################################################################################# */

	/**
	 *   Determina cual de todos los estilos es el principal, segun la cantidad de apariciones o precendencia
	 *   
	 * @return mayor
	 */
	private static String calcularEstiloPrincipal(){
		String mayor = null;
		int cant=0;
		// si es consistente
		if (getMisEstilosOrdenados().size() == getMisEstilos().size()){


			String miGroove="";
			for (int i=0;i<getMisEstilosOrdenados().size();i++){
				
				miGroove=getMisEstilosOrdenados().get(i);
				//me fijo que el groove no tenga en "End"
				if(miGroove.indexOf(Utiles.END_ESTILO)==-1){
					
					int valor = getMisEstilos().get(miGroove);
					if (valor > cant) {
						mayor = miGroove;
						cant = valor;
					}
				}

			}
		}
		return  mayor;
	}

	/**
	 * Incrementa un estilo en la matriz misEstilos si existe y lo agrega si no existe
	 * tambien guarda en una lista los estilos en el orden de aparicion 
	 * (nos sirve para saber el orden de aparicion y luego calcular el mayor)
	 * 
	 * @param estilo
	 */
	private static void agregarEstilo(String estilo){

		Integer cant= new Integer(1);

		//si existe el estilo, incremento su cantidad
		if (getMisEstilos().containsKey(estilo)){
			cant=getMisEstilos().get(estilo);
			cant ++;
		}else{
			//Vamos guardando los estilos en el orden que aparecen
			//(nos sirve para saber el orden de aparicion y luego calcular el mayor)
			getMisEstilosOrdenados().add(estilo);
		}	
		getMisEstilos().put(estilo, cant);

		return;
	}


	/**
	 * Quita los $_LastGroove de una lista de notas con estilos 
	 * la lista no debe tener repeats
	 * 
	 * @param cancionAnalizadaConEstilos
	 * @return cancionAnalizadaConEstilos sin los $_LastGroove
	 * @throws EstilosException : cuando no tiene un $_LastGroove para reemplazar, es decir esta mal hecho el archivo mma.
	 */
	private static ArrayList<String> quitarLastGroove(ArrayList<String> cancionAnalizadaConEstilos) throws EstilosException{		

		int j=-1;
		int ultimoIndiceLista =0;
		String valor="";
		
		String grooveAnteUltimo="";
		String grooveUltimo="";
		ArrayList<String> miListaEstilos=new ArrayList<String>();//estilos que aparecen
		ArrayList<String> miCancionAnalizadaSinLastGroove=new ArrayList<String>();//estilos que aparecen
		
		do{
			j++;
			valor = cancionAnalizadaConEstilos.get(j);//obtengo la linea
			
			//si la linea es la que determina el estilo 
			if (valor.startsWith(Utiles.ESTILO)){
				miListaEstilos.add(Utiles.obtenerDatos(valor," "));//obtenemos el nombre del groove
				if (!grooveUltimo.isEmpty()){					
					grooveAnteUltimo= grooveUltimo;
				}

				grooveUltimo=Utiles.obtenerDatos(valor, " ");//guardo el nombre del groove
				
				try {
						if(grooveUltimo.indexOf(Utiles.VAR_ULTIMO_ESTILO)!=-1){
							ultimoIndiceLista = miListaEstilos.size() - 1;
							grooveUltimo =  miListaEstilos.get(ultimoIndiceLista-2);// el anteultimo groove
							miListaEstilos.set(ultimoIndiceLista, grooveUltimo);
							if(DEBUG)
								System.out.println("reeemplazamos :"+valor +" por:Groove "+grooveUltimo);
							
							valor = Utiles.ESTILO+grooveUltimo;
						}
				} catch (ArrayIndexOutOfBoundsException e) {
					
					throw new EstilosException("EstilosException :  Error en el archivo.");
				}
			}
			
			miCancionAnalizadaSinLastGroove.add(valor);

		}while(j<(cancionAnalizadaConEstilos.size()-1));

		if(DEBUG){
			System.out.println(cancionAnalizadaConEstilos.toString());
			System.out.println(miCancionAnalizadaSinLastGroove.toString());
		}
		return miCancionAnalizadaSinLastGroove;

	}



/*################################################################################################################
 ###################				METODOS ESTATICOS PUBLICOS       				############################## 
 ################################################################################################################# */

	public static void imprimirMap(){
		Map<String, Integer> mapEstilos = getMisEstilos();

		Iterator it = mapEstilos.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			System.out.println(e.getKey()+" -> Total :"+e.getValue());
		}
	}


	
	public static void guardarEstilosEnMatriz(ArrayList<String> cancionAnalizadaConEstilos, MatrizEstilos miMatrizEstilos) throws EstilosException{		

		//vaciamos las varibles esaticos
		getMisEstilos().clear();
		getMisEstilosOrdenados().clear();

		int cont=0;
		int total=0;
		String valor="";
		String grooveAnteUltimo="";
		String grooveUltimo="";
		EstilosFila miEstilosFila;


		ArrayList miLista = new ArrayList<String>();
		ArrayList miListaEstilos=new ArrayList<String>();//estilos que aparecen
		
		cancionAnalizadaConEstilos = quitarLastGroove(cancionAnalizadaConEstilos);
		 
		

		
		for (int i=0; i< cancionAnalizadaConEstilos.size(); i++){
			
			valor = cancionAnalizadaConEstilos.get(i);

			//si la linea es la que determina el estilo 
			if (valor.startsWith(Utiles.ESTILO)){

				miListaEstilos.add(Utiles.obtenerDatos(valor," "));//obtenemos el nombre del groove
				if (!grooveUltimo.isEmpty()){
					if (DEBUG)
						System.out.println("Groove :" + grooveUltimo);
					if (cont>0){//si tiene por lo menos un compas el estilo--> guardo el estilo
						if(DEBUG)
							agregarEstilo(grooveUltimo);// solo para debug

						//guardo las apariciones de los estilos	
						if(!miMatrizEstilos.ExisteEstilo(grooveUltimo)){

							miMatrizEstilos.agregarEstilo(grooveUltimo);									

						}
						if(!grooveAnteUltimo.isEmpty()){
							miMatrizEstilos.agregaOcurrenciaEstilo(grooveAnteUltimo, grooveUltimo);
						}

						try {//vemos si podemos setear el compas
							//guaradamos el  estilo con sus compases o equivalencias
							miMatrizEstilos.setCompas(grooveUltimo, cont);
							
						} catch (EstilosException e) {
							System.err.println(e.getMessage());
						}

						grooveAnteUltimo= grooveUltimo;

						if (DEBUG)
							System.out.println("cant : "+cont);
					}
					if(!miLista.isEmpty()){//si no es vacia la lista de compases del estilo
						if (DEBUG)
							System.out.println("Acordes : "+miLista.toString());
					}
					if (DEBUG){
						System.out.println("---------------------------");
					}
				}

				grooveUltimo=Utiles.obtenerDatos(valor, " ");//guardo el nombre del groove
				miLista.clear();
				total = total + cont;
				cont=0;	

				// si la linea NO es la que tiene el estilo 	
			}else{//agrego notas (de algun grove, antes seteado) e incremento contador
				//sirve para verificar si un estilo tiene notas 

				miLista.add(valor);
				cont++;
			}

		}
		
		//agrego el ultimo groove
		if(cont>0){
			total = total + cont;
			if(DEBUG)
				agregarEstilo(grooveUltimo);

			if(!miMatrizEstilos.ExisteEstilo(grooveUltimo)){
				miMatrizEstilos.agregarEstilo(grooveUltimo);
			}
			if(!grooveAnteUltimo.isEmpty()){
				miMatrizEstilos.agregaOcurrenciaEstilo(grooveAnteUltimo, grooveUltimo);
			}

			try {
				miMatrizEstilos.setCompas(grooveUltimo, cont);
			} catch (EstilosException e) {
				//if(DEBUG)
				System.err.println(e.getMessage());
			}
			if (DEBUG){
				System.out.println("Groove :" + grooveUltimo);
				System.out.println("cant : "+cont);
				System.out.println("Acordes : "+miLista.toString());
				System.out.println("Duracion del tema en compases: " + total);
			}

		}
		
		return ;

	}

	/**
	 * dado una lista de acordes y estilos (sin repeats)
	 * nos devuelve cual sera el estilo principal de dicha cancion
	 * el criterio es: el estilo que aparece mas veces y si hubiera mas de un estilo con la misma cantidad , se toma el primero de ellos. 
	 * 
	 * @param cancionAnalizadaConEstilos
	 * @return estilo_principal
	 * @throws EstilosException 
	 * 
	 */	
	public static String deteminarEstiloPrincipal(ArrayList<String> cancionAnalizadaConEstilos) throws EstilosException{

		//vaciamos las varibles esaticos
		getMisEstilos().clear();
		getMisEstilosOrdenados().clear();

		int cont=0,j=-1;
		String valor="";
		String GrooveAnt="";
		
		
		cancionAnalizadaConEstilos = quitarLastGroove(cancionAnalizadaConEstilos);
		

		ArrayList miLista = new ArrayList<String>();
		ArrayList miListaEstilos=new ArrayList<String>();//estilos que aparecen
		do{
			j++;
			valor = cancionAnalizadaConEstilos.get(j);

			//si la linea es la que determina el estilo 
			if (valor.startsWith(Utiles.ESTILO)){

				String estilo = Utiles.obtenerDatos(valor," ");
				if (estilo.length() != 0) {
					miListaEstilos.add(estilo);//obtenemos el nombre del groove
					if (!GrooveAnt.isEmpty()){
						if (DEBUG)
							System.out.println("Groove :" + GrooveAnt);
						if (cont>0){//si tiene por lo menos un compas el estilo
							//guardo el estilo
							agregarEstilo(GrooveAnt);

							if (DEBUG)
								System.out.println("cant : "+cont);
						}
						if(!miLista.isEmpty()){//si no es vacia la lista de compases del estilo
							if (DEBUG)
								System.out.println("Acordes : "+miLista.toString());
						}
						if (DEBUG)
							System.out.println("---------------------------");
					}
					GrooveAnt=Utiles.obtenerDatos(valor, " ");//guardo el nombre del groove
					miLista.clear();
					cont=0;	
				}

				// si la linea NO es la que tiene el estilo 	
			}else{//agrego notas (de algun grove, antes seteado) e incremento contador

				miLista.add(valor);
				cont++;

			}


		}while(j<(cancionAnalizadaConEstilos.size()-1));

		//agrego el ultimo groove
		if(cont>0){
			agregarEstilo(GrooveAnt);
			if (DEBUG){
				System.out.println("Groove :" + GrooveAnt);
				System.out.println("cant : "+cont);
				System.out.println("Acordes : "+miLista.toString());
			}

		}				
		String estiloPpal = calcularEstiloPrincipal();//calcula cual es el estilo ppal
		return estiloPpal;
	}


	
	
	public static ArrayList<String> getMisEstilosOrdenados() {
		return misEstilosOrdenados;
	}
	public static void setMisEstilosOrdenados(ArrayList<String> misEstilosOrdenados) {
		misEstilosOrdenados = misEstilosOrdenados;
	}

	public static Map<String, Integer> getMisEstilos() {
		return misEstilos;
	}

	public static void setMisEstilos(Map<String, Integer> misEstilos) {
		misEstilos = misEstilos;
	}

}
