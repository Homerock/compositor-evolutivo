package orm;

import net.java.ao.Entity;

public interface Duracion extends Entity  {

	public int getDuracion();
	public void setDuracion(int duracion);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public int getIdEstilo();
	public void setIdEstilo(int idEstilo);
	
}
