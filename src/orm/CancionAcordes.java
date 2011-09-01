package orm;
import net.java.ao.*;

public interface CancionAcordes extends Entity{
	
	public Cancion getCancion();
	public void setCancion(Cancion cancion);
	
	public Estilos getEstiloEstrofa();
	public void setEstiloEstrofa(Estilos estiloEstrofa);
	
	public Acordes getAcorde();
	public void setAcorde(Acordes acorde);
	
	public int getNumeroEstrofa();
	public void setNumeroEstrofa(int nro); 
	
	public int getNumeroCompas();
	public void setNumeroCompas(int nro);
	
	public int getNumeroAcorde();
	public void setNumeroAcorde(int nro);
     	
}
