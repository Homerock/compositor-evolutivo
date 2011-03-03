package orm;

import net.java.ao.Entity;

public interface Tonicas extends Entity {

	public int getIdAcorde();
	public void setIdAcorde(int idAcorde);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public int getIdEstilo();
	public void setIdEstilo(int idEstilo);
	
}
