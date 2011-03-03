package archivos;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Utiles {
	
	public static String obtenerDatos(String linea, String token){
		
		StringTokenizer tokens = new StringTokenizer(linea, token);
		tokens.nextToken();
		return tokens.nextToken();
		
	}
	
	public static ArrayList<String> tokenizarLista(ArrayList<String> lista, String token) {
		
		ArrayList<String> listaAux = new ArrayList<String>();
		
		for (int i=0; i < lista.size(); i++) {
			listaAux.add(Utiles.obtenerDatos(lista.get(i), token));
		}
		return listaAux;
	}

}
