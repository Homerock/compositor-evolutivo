package archivos;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Reconocedor {
	
	public static Set<String> acordesAmericanos = new HashSet<String>();
	public static Set<String> acordes = new HashSet<String>();
	public static Set<String> alteracion = new HashSet<String>();
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	public static void cargarAcordesAmericanos(){
		String[] notasPpales= {"A","A#","Bb","B","C","C#","Db","D","D#","Eb","E","F","F#","Gb","G","G#","Ab"};
		acordesAmericanos= new HashSet<String>(Arrays.asList(notasPpales));
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	public static void cargarAcordes(){
		String[] notasPpales= {"La","Si","Do","Re","Mi","Fa","Sol"};
		acordes= new HashSet<String>(Arrays.asList(notasPpales));
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 */
	public static void cargarAlteraciones() {
		alteracion.add("M");		//Major triad
		alteracion.add("(b5)");		//Major triad with flat 5th
		alteracion.add("add9");		//Major chord plus 9th (no 7th.)
		alteracion.add("m");		//Minor triad.
		alteracion.add("mb5");		//Minor triad with flat 5th (aka dim).
		alteracion.add("m#5");		//Minor triad with augmented 5th.
		alteracion.add("m6");		//Minor 6th (flat 3rd plus a 6th).
		alteracion.add("m6(add9)");	//Minor 6th with added 9th
		alteracion.add("m7");		//Minor 7th (flat 3rd plus dominant 7th).
		alteracion.add("m7#5");		//Minor 7th with sharp 5th.
		alteracion.add("mM7");		//Minor Triad plus Major 7th.
		alteracion.add("m+7b9");		//Augmented minor 7 plus flat 9th.
		alteracion.add("m+7#9");		//Augmented minor 7 plus sharp 9th.
		alteracion.add("mM7(add9)");	//Minor Triad plus Major 7th and 9th.
		alteracion.add("m7b5");			//Minor 7th, flat 5 (aka 1/2 diminished).
		alteracion.add("m7b9");			//Minor 7th with added flat 9th.
		alteracion.add("m7#9");			//Minor 7th with added sharp 9th.
		alteracion.add("7");			//7th.
		alteracion.add("7b5");			//7th, flat 5.
		alteracion.add("dim7");			//Diminished seventh.
		alteracion.add("dim7(addM7)");	//Diminished tirad with added Major 7th.
		alteracion.add("aug");			//Augmented triad.
		alteracion.add("6");			//Major tiad with added 6th.
		alteracion.add("M7");		//Major 7th.
		alteracion.add("M7#5");		//Major 7th with sharp 5th.
		alteracion.add("M7b5");		//Major 7th with a flat 5th.
		alteracion.add("9");		//7th plus 9th.
		alteracion.add("9b5");		//7th plus 9th with flat 5th.
		alteracion.add("m9");		//Minor triad plus 7th and 9th.
		alteracion.add("m7b5b9");	//Minor 7th with flat 5th and flat 9th.
		alteracion.add("m9b5");		//Minor triad, flat 5, plus 7th and 9th.
		alteracion.add("m(sus9)");	//Minor triad plus 9th (no 7th).
		alteracion.add("M9");		//Major 7th plus 9th.
		alteracion.add("M9#11");	//Major 9th plus sharp 11th.
		alteracion.add("7b9");		//7th with flat 9th.
		alteracion.add("7#9");		//7th with sharp 9th.
		alteracion.add("7#9b13");	//7th with sharp 9th and flat 13th.
		alteracion.add("7b5b9");	//7th with flat 5th and flat 9th.
		alteracion.add("7b5#9");	//7th with flat 5th and sharp 9th.
		alteracion.add("7#5#9");	//7th with sharp 5th and sharp 9th.
		alteracion.add("aug7");		//An augmented chord (raised 5th) with a dominant 7th.
		alteracion.add("aug7b9");	//An augmented chord (raised 5th) with a dominant 7th and flat 9th.
		alteracion.add("aug7#9");	//An augmented chord (raised 5th) with a dominant 7th and sharp 9th.
		alteracion.add("aug9M7");	//An augmented chord (raised 5th) with a major 7th and 9th.
		alteracion.add("+7b9#11");	//Augmented 7th with flat 9th and sharp 11th.
		alteracion.add("m+7b9#11");	//Augmented minor 7th with flat 9th and sharp 11th.
		alteracion.add("11");		//9th chord plus 11th (3rd not voiced).
		alteracion.add("m11");		//9th with minor 3rd,  plus 11th.
		alteracion.add("m7(add11)");//Minor 7th  plus 11th.
		alteracion.add("m9#11");	//Minor 7th plus 9th and sharp 11th.
		alteracion.add("m7b9#11");	//Minor 7th plus flat 9th and sharp 11th.
		alteracion.add("m7(add13)");//Minor 7th  plus 13th.
		alteracion.add("11b9");		//7th chord plus flat 9th and 11th.
		alteracion.add("9#5");		//7th plus 9th with sharp 5th (same as aug9).
		alteracion.add("9#11");		//7th plus 9th and sharp 11th.
		alteracion.add("7#9#11");	//7th plus sharp 9th and sharp 11th.
		alteracion.add("7b9#11");	//7th plus flat 9th and sharp 11th.
		alteracion.add("7#11");		//7th plus sharp 11th (9th omitted).
		alteracion.add("M7#11");	//Major 7th plus sharp 11th (9th omitted).
		alteracion.add("m11b5");	//Minor 7th with flat 5th plus 11th.
		alteracion.add("sus4");		//Suspended 4th, major triad with the 3rd raised half tone.
		alteracion.add("7sus");		//7th with suspended 4th, dominant 7th with 3rd
		alteracion.add("7susb9");	//7th with suspended 4th and flat 9th.
		alteracion.add("sus2");		//Suspended 2nd
		alteracion.add("7sus2");	//A sus2 with dominant 7th added.
		alteracion.add("sus9");		//Major 7th plus sharp 11th (9th omitted).
		alteracion.add("13sus");	//7sus, plus 9th and 13th
		alteracion.add("13susb9");	//7sus, plus flat 9th and 13th
		alteracion.add("13");		//7th (including 5th) plus 13th (the 9th and 11th are not voiced).
		alteracion.add("13b5");		//7th with flat 5th,  plus 13th (the 9th and 11th are not voiced).
		alteracion.add("13#9");		//7th (including 5th) plus 13th and sharp 9th (11th not voiced).
		alteracion.add("13b9");		//7th (including 5th) plus 13th and flat 9th (11th not voiced).
		alteracion.add("M13");		//Major 7th (including 5th) plus 13th (9th and  11th not voiced).
		alteracion.add("m13");		//Minor 7th (including 5th) plus 13th (9th and 11th not voiced).
		alteracion.add("13#11");	//7th plus sharp 11th and 13th (9th not voiced).
		alteracion.add("M13#11");	//Major 7th plus sharp 11th and 13th (9th not voiced).
		alteracion.add("5");		//Altered Fifth or Power Chord; root and 5th only.
		alteracion.add("omit3add9");//Triad: root, 5th and 9th.
		alteracion.add("7omit3");	//7th with unvoiced 3rd.
		alteracion.add("m7omit5");	//Minor 7th with unvoiced 5th.
		
		//alias
		alteracion.add("aug9");			//9#5
		alteracion.add("+9");		//9#5
		alteracion.add("+9M7");		//aug9M7
		alteracion.add("+M7");		//M7#5
		alteracion.add("m(add9)");		//m(sus9)
		alteracion.add("69");		//6(add9)
		alteracion.add("m69");		//m6(add9)
		alteracion.add("m(b5)");		//mb5
		alteracion.add("m7(b9)");		//m7b9
		alteracion.add("m7(#9)");		//m7#9
		alteracion.add("9+5");		//9#5
		alteracion.add("m+5");		//m#5
		alteracion.add("M6");		//6
		alteracion.add("m7-5");		//m7b5
		alteracion.add("m7(omit5)");//m7omit5
		alteracion.add("+");		//aug
		alteracion.add("+7");		//aug7
		alteracion.add("7(omit3)");		//7omit3
		alteracion.add("#5");		//aug
		alteracion.add("7#5b9");		//aug7b9
		alteracion.add("7-9");		//7b9
		alteracion.add("7+9");		//7#9
		alteracion.add("maj7");		//M7
		alteracion.add("M7-5");		//M7b5
		alteracion.add("M7+5");		//M7#5
		alteracion.add("M7(add13)");//13b9
		alteracion.add("7alt");		//7b5b9
		alteracion.add("7sus4");	//7sus
		alteracion.add("7+");		//aug7
		alteracion.add("7#5");		//aug7
		alteracion.add("7+5");		//aug7
		alteracion.add("7-5");		//7b5
		alteracion.add("sus");		//sus4
		alteracion.add("maj9");		//M9
		alteracion.add("maj13");	//M13
		alteracion.add("m(maj7)");	//mM7
		alteracion.add("m+7");		//mM7
		alteracion.add("min(maj7)");//mM7
		alteracion.add("min#7");	//mM7
		alteracion.add("m#7");		//mM7
		alteracion.add("dim");		//dim7
		alteracion.add("9sus");		//sus9
		alteracion.add("9-5");		//9b5
		alteracion.add("dim3");		//mb5
		alteracion.add("omit3(add9)");	//omit3add9
		alteracion.add("9sus4");	//sus9
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param miSet
	 */
	public static void listarColeccion(Set miSet){
		Iterator iter = (Iterator) miSet.iterator();
		
		while (iter.hasNext())
			System.out.println(iter.next());
		
	}
	
	/**---------------------------------------------------------------------------
	 * 
	 * @param acord
	 * @return
	 */
	public static boolean EsNota(String acord){
		
		try {
			acord.trim();
			String notaDeUna = acord.substring(0, 1);
			
			if (acord.length() == 1) {
				if (acordesAmericanos.contains(notaDeUna)) {
					return true;
				}
			}
			
			String notaDeDos = acord.substring(0, 2);
			
			if (acordesAmericanos.contains(notaDeDos)) {
				String alt = acord.substring(2);
				if (alteracion.contains(alt)) {
					return true;
				}
			} else {
				if (acordesAmericanos.contains(notaDeUna)) {
					String alt = acord.substring(1);
					if (alteracion.contains(alt)) {
						return true;
					}
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
	 * @param args
	 */
	public static void main(String args[]){
		
		cargarAcordes();
		cargarAcordesAmericanos();
		cargarAlteraciones();
		
		String acord = "Argentina";
		System.out.println(acord);
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
acord = "C#m79";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
acord = "C";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
acord = "Am";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
acord = "CM";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
		
acord = "Cb#4";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
acord = "BB";
System.out.println(acord);		
		if (EsNota(acord)){
			System.out.println("es valido ");
		}else {
			System.out.println("NO  es valido ");
		}
		
	}

}
