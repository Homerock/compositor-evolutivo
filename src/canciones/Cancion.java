package canciones;

import java.util.ArrayList;
/**
 * Clase que representa una cancion
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */
public class Cancion implements Cloneable {
	

	/*################################################################################################################
	 ###################							ATRIBUTOS					###################################### 
	 ################################################################################################################# */

	private String nombreArchivo;	//nombre de archivo mma
	private String nombreFantasia;	//nombre para guardar en la BD
	private String tempo;
	private int duracion;// cantidad de compases de toda la cancion
	private Acorde tonica ;
	private String estiloPrincipal;
	private ArrayList<Estrofa> estrofas;// estrofas de la cancion
	private String fechaCreacion;
	private String comentario;
	private ArrayList<Integer> numerosEstrofasAlteradas; // cuando es un alteracion de otra estrofa.

	
	/*################################################################################################################
	 ###################						CONSTRUCTORES				    ###################################### 
	 ################################################################################################################# */

	public Cancion(){
		this.setNombreArchivo(null);
		this.setNombreFantasia(null);
		this.setTempo(null);
		this.setDuracion(0);
		this.setTonica(new Acorde());
		this.setEstiloPrincipal(null);
		this.setEstrofas(new ArrayList<Estrofa>());
		this.setNumerosEstrofasAlteradas(new ArrayList<Integer>());
	}
	
	/**
	 * Crea un nuevo objeto cancion.
	 * 
	 * @param nombre
	 * @param tempo
	 * @param duracion
	 * @param tonica
	 * @param estiloPrincipal
	 */
	public Cancion(String nombre,String tempo,int duracion,Acorde tonica,String estiloPrincipal){
		this.setNombreArchivo(nombre);
		this.setNombreFantasia(nombre);
		this.setTempo(tempo);
		this.setDuracion(duracion);
		this.setTonica(tonica);
		this.setEstiloPrincipal(estiloPrincipal);
		this.setEstrofas(new ArrayList<Estrofa>());
		this.setNumerosEstrofasAlteradas(new ArrayList<Integer>());
	}
	
	/**
	 * Crea un nuevo objeto cancion.
	 * 
	 * @param nombre
	 * @param tempo
	 * @param tonica
	 * @param estiloPrincipal
	 */
	public Cancion(String nombre,String tempo,Acorde tonica,String estiloPrincipal){
		this.setNombreArchivo(nombre);
		this.setNombreFantasia(nombre);
		this.setTempo(tempo);
		this.setDuracion(0);
		this.setTonica(tonica);
		this.setEstiloPrincipal(estiloPrincipal);
		this.setEstrofas(new ArrayList<Estrofa>());
		this.setNumerosEstrofasAlteradas(new ArrayList<Integer>());
		
	}

	public Cancion(
			String nombre,
			String tempo,
			int duracion,
			Acorde tonica,
			String estiloPrincipal,
			String comentario,
			String fechaCreacion){
		
		this.setNombreArchivo(nombre);
		this.setNombreFantasia(nombre);
		this.setTempo(tempo);
		this.setDuracion(duracion);
		this.setTonica(tonica);
		this.setEstiloPrincipal(estiloPrincipal);
		this.setEstrofas(new ArrayList<Estrofa>());
		this.setNumerosEstrofasAlteradas(new ArrayList<Integer>());
		this.setComentario(comentario);
		this.setFechaCreacion(fechaCreacion);
	}
	

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	public Object clone(){
        Cancion obj=null;
        try{
            obj=(Cancion)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        
        ArrayList<Estrofa> nuevaLista = new ArrayList<Estrofa>(obj.getEstrofas().size());
        for (Estrofa nuevaEstrofa : obj.getEstrofas()) {
        	nuevaLista.add((Estrofa) nuevaEstrofa.clone());
        }
      
        obj.estrofas = nuevaLista;

        return obj;
    }
	
	public void agregarEstrofa(Estrofa miEstrofa) {
		this.getEstrofas().add(miEstrofa);
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombre) {
		this.nombreArchivo = nombre;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Acorde getTonica() {
		return tonica;
	}

	public void setTonica(Acorde tonica) {
		this.tonica = tonica;
	}

	public String getEstiloPrincipal() {
		return estiloPrincipal;
	}

	public void setEstiloPrincipal(String estiloPrincipal) {
		this.estiloPrincipal = estiloPrincipal;
	}

	public ArrayList<Estrofa> getEstrofas() {
		return estrofas;
	}

	public void setEstrofas(ArrayList<Estrofa> estrofas) {
		this.estrofas = estrofas;
	}

	public void setNumerosEstrofasAlteradas(ArrayList<Integer> numerosEstrofasAlteradas) {
		this.numerosEstrofasAlteradas = numerosEstrofasAlteradas;
	}

	public ArrayList<Integer> getNumerosEstrofasAlteradas() {
		return numerosEstrofasAlteradas;
	}
	
	public void agregarNumeroEstrofaAlterada(int numero){
		this.getNumerosEstrofasAlteradas().add(new Integer(numero));
	}
	

	
	/**
	 * verifica en la lista de estilos de la cancion existe alguna estrofa con un estilo determinado. 
	 * @param estilo que queremos aviriguar si existe
	 * @return true si existe el estilo en alguna de las estrofas de la cancion , false si no.
	 */
	public boolean existeEstrofaEstilo(String estilo){
		
		boolean existe=false;
		ArrayList<Estrofa> estrofas = this.getEstrofas();
	
		for (Estrofa e : estrofas){
			if(e.getEstilo().equals(estilo)){
				existe =true;
				break;
			}
		}
		return existe;
	}
	
	public Estrofa getEstrofaPorNumero (int num) {
		
		return this.getEstrofas().get(num-1);
	}
	
	public Estrofa buscarEstrofaEstilo(String estilo){
		
		ArrayList<Estrofa> estrofas = this.getEstrofas();
		Estrofa miEstrofa = null;
		
		for (Estrofa e : estrofas){
			if(e.getEstilo().equals(estilo)){
				miEstrofa = e;
				break;
			}
		}
		return miEstrofa;
	}

	

	public void actualizarContadores(){
		int contador =0;
		
		for (Estrofa e : estrofas){
			e.actualizarContadores();
			contador = contador + e.getCantidadCompases();
			
		}
		this.setDuracion(contador);
		
	}
	
	@Override
	public String toString() {
		return "Cancion [nombre = " + nombreFantasia + ", tempo = " + tempo + ", duracion = " + duracion + ", tonica = " 
				+ tonica + ", estiloPrincipal = " + estiloPrincipal + "\n"
				+ "estrofas = " + estrofas + "]\n";
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public void setNombreFantasia(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
	}

	

	

}
