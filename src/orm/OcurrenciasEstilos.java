package orm;

import net.java.ao.Entity;

public interface OcurrenciasEstilos extends Entity {

	public int getIdEstiloPrincipal();
	public void setIdEstiloPrincipal(int idEstiloPrincipal);
	
	public int getIdEstiloSecundario();
	public void setIdEstiloSecundario(int idEstiloSecundario);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
}
