package archivos;

import java.io.File;
import java.io.FilenameFilter;

//#########################################################################################
/**
 * 
 * @author Gomez - Pazos
 *
 */
//#########################################################################################
public class Filtro implements FilenameFilter{
    
	String extension;
    
	Filtro(String extension){
        this.extension=extension;
    }
    
    //#########################################################################################
    /**
     * 
     */
    //#########################################################################################
    public boolean accept(File dir, String name){
        return name.endsWith(extension);
    }
}