package orm;
import net.java.ao.*;

public interface CancionAcordes extends Entity{
	
	public Cancion getCancion();
	public void setCancion(Cancion cancion);
	
	public String getEstiloEstrofa();
	public void setEstiloEstrofa(String estiloEstrofa);
	
	public String getAcorde();
	public void setAcorde(String acorde);
	
	public int getNumeroEstrofa();
	public void setNumeroEstrofa(int nro); 
	
	public int getNumeroCompas();
	public void setNumeroCompas(int nro);
	
	public int getNumeroAcorde();
	public void setNumeroAcorde(int nro);
     	
}
