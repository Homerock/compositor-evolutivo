package orm;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Acordes extends Entity {
	
	public String getNombre();
	public void setNombre(String nombre);
	
	public int getCantApariciones();
	public void setCantApariciones(int cantApariciones);
	
	
	@OneToMany
	public Acordes[] getAcordes();
	

}
