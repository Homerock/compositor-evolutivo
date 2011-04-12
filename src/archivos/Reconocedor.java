package archivos;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

public class Reconocedor {
	
	private static Set<String> acordesAmericanos = new HashSet<String>();
	private static Set<String> acordesEuropeos = new HashSet<String>();
	private static Set<String> modalidades = new HashSet<String>();
	private static Set<String> alteraciones = new HashSet<String>();
	private static Map<String, String> tablaCifrados = new HashMap<String, String>();
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	private static void cargarTablaCifrados(){
		
		tablaCifrados.put("La", "A");
		tablaCifrados.put("Si", "B");
		tablaCifrados.put("Do", "C");
		tablaCifrados.put("Re", "D");
		tablaCifrados.put("Mi", "E");
		tablaCifrados.put("Fa", "F");
		tablaCifrados.put("Sol", "G");
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	private static void cargarAcordesAmericanos(){
		
		//String[] notasPpales= {"A","A#","Bb","B","C","C#","Db","D","D#","Eb","E","F","F#","Gb","G","G#","Ab"};
		String[] notasPpales= {"A","B","C","D","E","F","G"};
		acordesAmericanos= new HashSet<String>(Arrays.asList(notasPpales));
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	private static void cargarAcordes(){
		
		String[] notasPpales= {"La","Si","Do","Re","Mi","Fa","Sol"};
		acordesEuropeos = new HashSet<String>(Arrays.asList(notasPpales));
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	private static void cargarSostenidos(){
		
		alteraciones.add("#");
		alteraciones.add("b");
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	private static void cargarModalidades() {
		
		modalidades.add("M");		//Major triad
		modalidades.add("(b5)");		//Major triad with flat 5th
		modalidades.add("add9");		//Major chord plus 9th (no 7th.)
		modalidades.add("m");		//Minor triad.
		modalidades.add("mb5");		//Minor triad with flat 5th (aka dim).
		modalidades.add("m#5");		//Minor triad with augmented 5th.
		modalidades.add("m6");		//Minor 6th (flat 3rd plus a 6th).
		modalidades.add("m6(add9)");	//Minor 6th with added 9th
		modalidades.add("m7");		//Minor 7th (flat 3rd plus dominant 7th).
		modalidades.add("m7#5");		//Minor 7th with sharp 5th.
		modalidades.add("mM7");		//Minor Triad plus Major 7th.
		modalidades.add("m+7b9");		//Augmented minor 7 plus flat 9th.
		modalidades.add("m+7#9");		//Augmented minor 7 plus sharp 9th.
		modalidades.add("mM7(add9)");	//Minor Triad plus Major 7th and 9th.
		modalidades.add("m7b5");			//Minor 7th, flat 5 (aka 1/2 diminished).
		modalidades.add("m7b9");			//Minor 7th with added flat 9th.
		modalidades.add("m7#9");			//Minor 7th with added sharp 9th.
		modalidades.add("7");			//7th.
		modalidades.add("7b5");			//7th, flat 5.
		modalidades.add("dim7");			//Diminished seventh.
		modalidades.add("dim7(addM7)");	//Diminished tirad with added Major 7th.
		modalidades.add("aug");			//Augmented triad.
		modalidades.add("6");			//Major tiad with added 6th.
		modalidades.add("M7");		//Major 7th.
		modalidades.add("M7#5");		//Major 7th with sharp 5th.
		modalidades.add("M7b5");		//Major 7th with a flat 5th.
		modalidades.add("9");		//7th plus 9th.
		modalidades.add("9b5");		//7th plus 9th with flat 5th.
		modalidades.add("m9");		//Minor triad plus 7th and 9th.
		modalidades.add("m7b5b9");	//Minor 7th with flat 5th and flat 9th.
		modalidades.add("m9b5");		//Minor triad, flat 5, plus 7th and 9th.
		modalidades.add("m(sus9)");	//Minor triad plus 9th (no 7th).
		modalidades.add("M9");		//Major 7th plus 9th.
		modalidades.add("M9#11");	//Major 9th plus sharp 11th.
		modalidades.add("7b9");		//7th with flat 9th.
		modalidades.add("7#9");		//7th with sharp 9th.
		modalidades.add("7#9b13");	//7th with sharp 9th and flat 13th.
		modalidades.add("7b5b9");	//7th with flat 5th and flat 9th.
		modalidades.add("7b5#9");	//7th with flat 5th and sharp 9th.
		modalidades.add("7#5#9");	//7th with sharp 5th and sharp 9th.
		modalidades.add("aug7");		//An augmented chord (raised 5th) with a dominant 7th.
		modalidades.add("aug7b9");	//An augmented chord (raised 5th) with a dominant 7th and flat 9th.
		modalidades.add("aug7#9");	//An augmented chord (raised 5th) with a dominant 7th and sharp 9th.
		modalidades.add("aug9M7");	//An augmented chord (raised 5th) with a major 7th and 9th.
		modalidades.add("+7b9#11");	//Augmented 7th with flat 9th and sharp 11th.
		modalidades.add("m+7b9#11");	//Augmented minor 7th with flat 9th and sharp 11th.
		modalidades.add("11");		//9th chord plus 11th (3rd not voiced).
		modalidades.add("m11");		//9th with minor 3rd,  plus 11th.
		modalidades.add("m7(add11)");//Minor 7th  plus 11th.
		modalidades.add("m9#11");	//Minor 7th plus 9th and sharp 11th.
		modalidades.add("m7b9#11");	//Minor 7th plus flat 9th and sharp 11th.
		modalidades.add("m7(add13)");//Minor 7th  plus 13th.
		modalidades.add("11b9");		//7th chord plus flat 9th and 11th.
		modalidades.add("9#5");		//7th plus 9th with sharp 5th (same as aug9).
		modalidades.add("9#11");		//7th plus 9th and sharp 11th.
		modalidades.add("7#9#11");	//7th plus sharp 9th and sharp 11th.
		modalidades.add("7b9#11");	//7th plus flat 9th and sharp 11th.
		modalidades.add("7#11");		//7th plus sharp 11th (9th omitted).
		modalidades.add("M7#11");	//Major 7th plus sharp 11th (9th omitted).
		modalidades.add("m11b5");	//Minor 7th with flat 5th plus 11th.
		modalidades.add("sus4");		//Suspended 4th, major triad with the 3rd raised half tone.
		modalidades.add("7sus");		//7th with suspended 4th, dominant 7th with 3rd
		modalidades.add("7susb9");	//7th with suspended 4th and flat 9th.
		modalidades.add("sus2");		//Suspended 2nd
		modalidades.add("7sus2");	//A sus2 with dominant 7th added.
		modalidades.add("sus9");		//Major 7th plus sharp 11th (9th omitted).
		modalidades.add("13sus");	//7sus, plus 9th and 13th
		modalidades.add("13susb9");	//7sus, plus flat 9th and 13th
		modalidades.add("13");		//7th (including 5th) plus 13th (the 9th and 11th are not voiced).
		modalidades.add("13b5");		//7th with flat 5th,  plus 13th (the 9th and 11th are not voiced).
		modalidades.add("13#9");		//7th (including 5th) plus 13th and sharp 9th (11th not voiced).
		modalidades.add("13b9");		//7th (including 5th) plus 13th and flat 9th (11th not voiced).
		modalidades.add("M13");		//Major 7th (including 5th) plus 13th (9th and  11th not voiced).
		modalidades.add("m13");		//Minor 7th (including 5th) plus 13th (9th and 11th not voiced).
		modalidades.add("13#11");	//7th plus sharp 11th and 13th (9th not voiced).
		modalidades.add("M13#11");	//Major 7th plus sharp 11th and 13th (9th not voiced).
		modalidades.add("5");		//Altered Fifth or Power Chord; root and 5th only.
		modalidades.add("omit3add9");//Triad: root, 5th and 9th.
		modalidades.add("7omit3");	//7th with unvoiced 3rd.
		modalidades.add("m7omit5");	//Minor 7th with unvoiced 5th.
		
		//alias
		modalidades.add("aug9");			//9#5
		modalidades.add("+9");		//9#5
		modalidades.add("+9M7");		//aug9M7
		modalidades.add("+M7");		//M7#5
		modalidades.add("m(add9)");		//m(sus9)
		modalidades.add("69");		//6(add9)
		modalidades.add("m69");		//m6(add9)
		modalidades.add("m(b5)");		//mb5
		modalidades.add("m7(b9)");		//m7b9
		modalidades.add("m7(#9)");		//m7#9
		modalidades.add("9+5");		//9#5
		modalidades.add("m+5");		//m#5
		modalidades.add("M6");		//6
		modalidades.add("m7-5");		//m7b5
		modalidades.add("m7(omit5)");//m7omit5
		modalidades.add("+");		//aug
		modalidades.add("+7");		//aug7
		modalidades.add("7(omit3)");		//7omit3
		modalidades.add("#5");		//aug
		modalidades.add("7#5b9");		//aug7b9
		modalidades.add("7-9");		//7b9
		modalidades.add("7+9");		//7#9
		modalidades.add("maj7");		//M7
		modalidades.add("M7-5");		//M7b5
		modalidades.add("M7+5");		//M7#5
		modalidades.add("M7(add13)");//13b9
		modalidades.add("7alt");		//7b5b9
		modalidades.add("7sus4");	//7sus
		modalidades.add("7+");		//aug7
		modalidades.add("7#5");		//aug7
		modalidades.add("7+5");		//aug7
		modalidades.add("7-5");		//7b5
		modalidades.add("sus");		//sus4
		modalidades.add("maj9");		//M9
		modalidades.add("maj13");	//M13
		modalidades.add("m(maj7)");	//mM7
		modalidades.add("m+7");		//mM7
		modalidades.add("min(maj7)");//mM7
		modalidades.add("min#7");	//mM7
		modalidades.add("m#7");		//mM7
		modalidades.add("dim");		//dim7
		modalidades.add("9sus");		//sus9
		modalidades.add("9-5");		//9b5
		modalidades.add("dim3");		//mb5
		modalidades.add("omit3(add9)");	//omit3add9
		modalidades.add("9sus4");	//sus9
	}
	
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param acord
	 * @return
	 */
	private static boolean EsNota(String acord){
		
		try {
			
			if (acord.length() == 1) {
				if (acordesAmericanos.contains(acord)) {
					return true;
				}
			}
			
			String nota = acord.substring(0, 1);
			String alteracion = acord.substring(1, 2);
			String alt;
			
			// miro si la primer nota es un acorde valido
			if (acordesAmericanos.contains(nota)) {
				// miro si el segundo caracter es una alteracion (# o b)
				if (alteraciones.contains(alteracion)) {
					// si tengo alteracion miro lo demas desde el tercer caracter
					alt = acord.substring(2);
				} else {
					// si tengo alteracion miro lo demas desde el segundo caracter
					alt = acord.substring(1);
				}
				if (alt.length() == 0) {
					return true;
				}
				// si tengo alteracion miro lo demas desde el tercer caracter
				if (modalidades.contains(alt)) {
					return true;
				}
				
				
			}
			return false;
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param acord
	 * @return
	 */
	private static boolean procesarAcorde (String acord) {
		
		String notaDeDos, notaDeTres;
		String acordeFinal, resto;
		
		acordeFinal = acord;
		
		// primero miro si el acorde contiene un solo caracter
		// y busco si es (A - B - C - D - E - F - G)
		if (acord.length() == 1) {
			if (acordesAmericanos.contains(acord)) {
				//System.out.println("SI es valido, acorde de un caracter");
				return true;
			}
			return false;
		} else {
			
			// busco si es acorde de notacion europea (La Si Do Re Mi Fa)
			// y la convierto a notacion europea
			notaDeDos = acord.substring(0, 2);
			resto = acord.substring(2);
			if (acordesEuropeos.contains(notaDeDos)) {
				acordeFinal = tablaCifrados.get(notaDeDos);
				acordeFinal = acordeFinal + resto;
				
			} else {
				// pruebo si el acorde es Sol
				if (acord.length() >= 3) {
					notaDeTres = acord.substring(0, 3);
					resto = acord.substring(3);
					if (acordesEuropeos.contains(notaDeTres)) {
						acordeFinal = tablaCifrados.get(notaDeTres);
						acordeFinal = acordeFinal + resto;
					}
				}
			}
			//System.out.println("nuevo Acorde: " + acordeFinal);
			if (EsNota(acordeFinal)){
				return true;
			}else {
				return false;
			}
		}
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param i
	 * @return
	 */
	public static boolean esAcordeValido(String i) {
		
		String inversion = null;
		String acord;
		
		acord = i;
		//System.out.println("-----------------------------------");
		//System.out.println("Acorde: " + acord);
		acord.trim();
		
		if (acord.equals("/")) {
			return false;
		}
		
		if (acord.contains("/")) {
			try {
				StringTokenizer tokens = new StringTokenizer(i,"/");  
				acord = tokens.nextToken();
				inversion = tokens.nextToken();
			} catch (NoSuchElementException e) {
				return false;
			}
		} 
		
		if (procesarAcorde(acord)) {
			if (inversion == null) {
				return true;
			}
			if (procesarAcorde(inversion)) {
				//System.out.println("nuevo acorde: "+acord+"/"+inversion);
				return true;
			}
		}
		return false;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	public static void cargarTablasAcordes() {
		
		cargarAcordes();
		cargarAcordesAmericanos();
		cargarModalidades();
		cargarSostenidos();
		cargarTablaCifrados();
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		
		cargarTablasAcordes();
		 
		String[] lista = {"La","La/Do#","C/G", "Solb7","DOS","Dm9","Dm","Rem","RE","Re","Gsus2","A#","La#","A#7","Solbm"};
		
		for (int i = 0; i < lista.length; i++) {
			
			if (esAcordeValido(lista[i])) {
				System.out.println("SI, es nota valida");
			} else {
				System.out.println("NO, no es nota valida");
			}
		}	
	}
	
}
