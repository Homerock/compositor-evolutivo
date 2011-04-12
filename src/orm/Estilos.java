package orm;

import net.java.ao.Entity;

public interface Estilos extends Entity {

	public String getNombre();
	public void setNombre(String nombre);
	
	public int getCompasesDeDosTiempos();
	public void setCompasesDeDosTiempos(int compasesDe2Tiempos);
	
	public int getCompasesDeCuatroTiempos();
	public void setCompasesDeCuatroTiempos(int compasesDe4Tiempos);
	
	public int getCompasesDeSeisTiempos();
	public void setCompasesDeSeisTiempos(int compasesDe6Tiempos);
	
	public int getCompasesDeOchoTiempos();
	public void setCompasesDeOchoTiempos(int compasesDe8Tiempos);
	
}
