package orm;

import net.java.ao.Entity;

public interface Tempo extends Entity {

	public int getTempo();
	public void setTempo(int tempo);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public int getIdEstilo();
	public void setIdEstilo(int idEstilo);
	
}
