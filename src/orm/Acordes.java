package orm;

import net.java.ao.*;

public interface Acordes extends Entity {
	
	public String getNombre();
	public void setNombre(String nombre);
	
	public int getCantApariciones();
	public void setCantApariciones(int cantApariciones);
	
	
	@OneToMany
	public Acordes[] getAcordes();
	

}
