package excepciones;


//#########################################################################################
/**
 * Clase ORMException para las excepciones del ORM.
 * 
 * @author Yamil Gomez, Sebastian Pazos
 *
 */
//#########################################################################################
public class ORMException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param message
	 */
	public ORMException(String message) {
		super(message);
	}
	
	
}
