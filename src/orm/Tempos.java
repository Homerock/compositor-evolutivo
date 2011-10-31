package orm;

import net.java.ao.Entity;

public interface Tempos extends Entity {

	public String getTempo();
	public void setTempo(String tempo);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public Estilos getEstilos();
	public void setEstilos(Estilos estilo);
	
}
