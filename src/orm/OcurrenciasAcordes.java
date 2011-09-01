package orm;



import net.java.ao.*;

public interface OcurrenciasAcordes extends Entity {
	
	public Acordes getAcordePrincipal();
	public void setAcordePrincipal(Acordes acordePrincipal);
	
	public Acordes getAcordeSecundario();
	public void setAcordeSecundario(Acordes acordeSecundario);
	
	public int getCantidad();
	public void setCantidad(int cantidad);
	
	public Estilos getEstilos();
	public void setEstilos(Estilos estilo);
	
	
}
