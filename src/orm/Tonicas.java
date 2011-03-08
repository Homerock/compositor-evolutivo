package orm;

import net.java.ao.*;

public interface Tonicas extends Entity {

	public Acordes getAcorde();
	public void setAcorde(Acordes acorde);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public Estilos getEstilos();
	public void setEstilos(Estilos estilo);
	
}

