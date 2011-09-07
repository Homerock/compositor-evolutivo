package orm;

import net.java.ao.Entity;

public interface OcurrenciasEstilos extends Entity {

	public Estilos getEstiloPrincipal();
	public void setEstiloPrincipal(Estilos estiloPrincipal);
	
	public Estilos getEstiloSecundario();
	public void setEstiloSecundario(Estilos estiloSecundario);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
}
