package orm;

import net.java.ao.Entity;

public interface Estilos extends Entity {

	public String getNombre();
	public void setNombre(String nombre);
	
	public int getCompasesDe2Tiempos();
	public void setCompasesDe2Tiempos(int compasesDe2Tiempos);
	
	public int getCompasesDe4Tiempos();
	public void setCompasesDe4Tiempos(int compasesDe4Tiempos);
	
	public int getCompasesDe6Tiempos();
	public void setCompasesDe6Tiempos(int compasesDe6Tiempos);
	
	public int getCompasesDe8Tiempos();
	public void setCompasesDe8Tiempos(int compasesDe8Tiempos);
	
}
